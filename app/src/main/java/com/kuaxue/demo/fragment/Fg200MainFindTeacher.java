package com.kuaxue.demo.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kuaxue.database.ConfigUtil;
import com.kuaxue.database.dao.CourseInfo;
import com.kuaxue.database.dao.CourseInfoDao;
import com.kuaxue.database.dao.DaoMaster;
import com.kuaxue.database.dao.DaoSession;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.kuaxue.demo.adapter.FgPagerAdapter;
import com.xxx.fudao.R;
import com.kuaxue.demo.netparse.NetRestClient;
import com.kuaxue.demo.netparse.entity.CourseNianJiSubjects;
import com.kuaxue.demo.netparse.parse.CourseNianjiParser;
import com.xxx.views.PagerSlidingTabStrip;
import com.xxx.xbase.BaseFragment;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;


/**
 * 课程页
 */
public class Fg200MainFindTeacher extends BaseFragment implements View.OnClickListener {
	// 标题栏
	private TextView tvTitle;
	private RelativeLayout topBar;
	private PagerSlidingTabStrip viewPagerTab;

	private ViewPager mViewPager;
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	private ImageButton imgbtn_course_category;

	// 数据库读写操作相关
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private CourseInfoDao courseInfoDao;
	private View vPopupWindow;
	private PopupWindow pw;
	private LayoutInflater inflater;

	// 当前年级，默认全部
	private int nCurNianji = 0;
	private CourseNianjiParser allNianJi = null;
//	private List<NianJi> allNianJiList = new ArrayList<NianJi.NianJi>();
	private List<CourseInfo> allKeMuList = new ArrayList<CourseInfo>();
	private int nianjiId;

	private boolean flag;
	private int arg;

	/**
	 * onCreate是指创建该fragment
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		logx.d("onCreate ");
		Bundle args = getArguments();

		super.onCreate(savedInstanceState);
	}

	/**
	 * onCreateView是创建该fragment对应的视图
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		if(view == null)
			view = inflater.inflate(R.layout.fragment_main_find_teacher, null);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
//		logx.d("onViewCreated list ");
		super.onViewCreated(view, savedInstanceState);

		// 初始化数据请求 // 初始所有课程数据
		InitNianjiData();
		//
		initControl();
	}

	private void InitNianjiData() {
		String url = "http://www.kuaxue.com/Api/Videocourse/getNianjisList";
		RequestParams params = new RequestParams();

		NetRestClient.Instance().client.get(url, null, new TextHttpResponseHandler() {
			@Override
			public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
				// 失败

			}

			@Override
			public void onSuccess(int i, Header[] headers, String s) {
				// 成功
				if(allNianJi == null) {
					allNianJi = new CourseNianjiParser();
				}
				if ("success".equals(allNianJi.getStrResult())) {
					// 添加初一、初二、初三、高一、高二、高三
					allNianJi.parse(s);
					// 添加 全部两个字
					allNianJi.addTotalP();

					CourseNianJiSubjects nianJiSubjects = new CourseNianJiSubjects(mContext);
					// 清空
					DeleteQuery<CourseInfo> bd =  courseInfoDao.queryBuilder().where(CourseInfoDao.Properties.ClsID.eq(0)).buildDelete();
					bd.executeDeleteWithoutDetachingEntities();
					nianJiSubjects.addTotalKemu(courseInfoDao); // 填充kemulist
					allKeMuList.addAll(nianJiSubjects.kemusList);
					initFragment();
				}
//				else {
//					Toast.makeText(mContext, mContext.getString(R.string.net_back_errror), Toast.LENGTH_SHORT).show();
//				}
			}
		});
	}
		/**
         * 初始化课程数据
         */
	private void initControl() {

		topBar = (RelativeLayout) view.findViewById(R.id.rl_top_bar);

		viewPagerTab = (PagerSlidingTabStrip) view.findViewById(R.id.mColumnHorizontalScrollView);
		mViewPager = (ViewPager) view.findViewById(R.id.mViewPager);

		// 添加分类的按钮图片
		imgbtn_course_category = (ImageButton) view.findViewById(R.id.imgbtn_course_categoryAA);
		// 标题栏中点击事件
		RelativeLayout rl_course_title_conten = (RelativeLayout) view.findViewById(R.id.rl_course_title_conten);

		tvTitle = (TextView) view.findViewById(R.id.tvTitle);

		imgbtn_course_category.setOnClickListener(this);
		rl_course_title_conten.setOnClickListener(this);

		// 弹出的按钮事件初始化
		btnInit();

		// 创建或者打开数据库
		DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, ConfigUtil.DB_NAME, null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		courseInfoDao = daoSession.getCourseInfoDao();

		// 初始化科目数据
		initFragmentFromDB();
	}

	/**
	 * 点全部课程弹出
	 */
	private void btnInit() {
		if (pw == null) {
			// 现在加载的pop布局
			vPopupWindow = inflater.inflate(R.layout.layout_public_nianji, null, false);
			pw = new PopupWindow(vPopupWindow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
			pw.setAnimationStyle(R.style.pop_window);
			pw.setOnDismissListener(new PopupWindow.OnDismissListener() {

				@Override
				public void onDismiss() {
					flag = false;
				}
			});
		}
		pw.setOutsideTouchable(false);

		Button btn0_jianji = (Button) vPopupWindow.findViewById(R.id.btn0_jianji);
		Button btn1_jianji = (Button) vPopupWindow.findViewById(R.id.btn1_nianji);
		Button btn2_jianji = (Button) vPopupWindow.findViewById(R.id.btn2_nianji);
		Button btn3_nianji = (Button) vPopupWindow.findViewById(R.id.btn3_nianji);
		Button btn4_nianji = (Button) vPopupWindow.findViewById(R.id.btn4_nianji);
		Button btn5_nianji = (Button) vPopupWindow.findViewById(R.id.btn5_nianji);
		Button btn6_nianji = (Button) vPopupWindow.findViewById(R.id.btn6_nianji);

		btn0_jianji.setOnClickListener(this);
		btn1_jianji.setOnClickListener(this);
		btn2_jianji.setOnClickListener(this);
		btn3_nianji.setOnClickListener(this);
		btn4_nianji.setOnClickListener(this);
		btn5_nianji.setOnClickListener(this);
		btn6_nianji.setOnClickListener(this);
	}

	/**
	 * 从数据库初始化Fragment
	 * */
	private void initFragmentFromDB() {
		// 清空
		fragments.clear();
		// 0表示全部年级, 第一次是当前用户的全部年级
		QueryBuilder<CourseInfo> qb = courseInfoDao.queryBuilder().where(
				com.kuaxue.database.dao.CourseInfoDao.Properties.ClsID.eq(0),
				CourseInfoDao.Properties.Username.eq("13912345678"));

		// 设置科目课程列表fragment
		for (int i = 0; i < qb.list().size(); i++) {
			Bundle data = new Bundle();
			// 科目id
			data.putString("kemu_id", String.valueOf(qb.list().get(i).getKemuID()));
			// 科目名字
			data.putString("kemu_name", qb.list().get(i).getKemuName());
			// 年级id
			data.putInt("nianji_id", qb.list().get(i).getClsID());
			data.putString("username", "13912345678"); // 用户名

			Fg210Course newfragment = new Fg210Course();
			newfragment.setArguments(data);
			fragments.add(newfragment);
		}

		// 设置viewpager
		if (qb.list().size() > 0) {
			FgPagerAdapter mAdapter = new FgPagerAdapter(getChildFragmentManager(), fragments, qb.list());
			mViewPager.setAdapter(mAdapter);
			viewPagerTab.setViewPager(mViewPager);
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	// 重写的弹出蛋图年级选择框
	private void popSelectCourse() {
		if (flag == true) {
			pw.dismiss();
		}

		if (flag == false) {
			flag = true;
			// AnimationUtil.rAnimForward(imgbtn_course_category);
		}

		pw.setFocusable(true);
		pw.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
		pw.showAsDropDown(topBar, 0, 0);
		allKeMuList.clear();
	}

	private void dopop() {

		flag = false;
		imgbtn_course_category.setImageResource(R.drawable.teseds_down_arror_select);
		if(allNianJi == null || allNianJi.getmList().size()==0){
			pw.dismiss();
			Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
			return;//网络异常
		}
		nianjiId = Integer.parseInt(allNianJi.getmList().get(arg).getId());
		if (nCurNianji == nianjiId) {
			// 年级选择没有变化
			pw.dismiss();
			return;
		}

		nCurNianji = nianjiId;
		// 根据年级读取科目
		if (nCurNianji == 0) { //
			// 所有年级
			CourseNianJiSubjects allSubjects4NianJi = new CourseNianJiSubjects(mContext);

			DeleteQuery<CourseInfo> bd =  courseInfoDao.queryBuilder().where(CourseInfoDao.Properties.ClsID.eq(0)).buildDelete();
			bd.executeDeleteWithoutDetachingEntities();

			allSubjects4NianJi.addTotalKemu(courseInfoDao); // 填充kemulist
			allKeMuList.addAll(allSubjects4NianJi.kemusList);
			initFragment();
		} else {
			// 读出当前年级有的科目
			getSubjectData(nianjiId);
		}
		tvTitle.setText(allNianJi.getmList().get(arg).getName() + "课程");
		pw.dismiss();
	}

	/**
	 *
	 * @param nianjiId
	 */
	private void getSubjectData(int nianjiId) {
//		// 提交POST请求
//		String json = "{\"nianjiId\":" + nianjiId + "}"; // 双引号方式
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("json", json);
//		networkHelper.sendPostRequest(Constant.COURSE_ALL_SUBJECT_LIST_FOR_NIANJI, params, Constant.QUEST_ID_TWO);
	}
	/**
	 * 初始化每个科目下的课程
	 */
	private void initFragment() {
		// 清空
		fragments.clear();

		int count = allKeMuList.size();
		for (int i = 0; i < count; i++) {
			Bundle data = new Bundle();
			// 科目id
			data.putString("kemu_id", String.valueOf(allKeMuList.get(i).getKemuID()));
			// 科目名字
			data.putString("kemu_name", allKeMuList.get(i).getKemuName());
			// 年级id
			data.putInt("nianji_id", nCurNianji);

			Fg210Course newfragment = new Fg210Course();
			newfragment.setArguments(data);
			fragments.add(newfragment);
		}

		FgPagerAdapter mAdapter = new FgPagerAdapter(getChildFragmentManager(), fragments, allKeMuList);
		mViewPager.setAdapter(mAdapter);
		viewPagerTab.setViewPager(mViewPager);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.imgbtn_course_categoryAA:
			case R.id.rl_course_title_conten:
				popSelectCourse();
				break;
			case R.id.btn0_jianji:
				arg = 0;
				dopop();
				break;
			case R.id.btn1_nianji:
				arg = 1;
				dopop();
				break;
			case R.id.btn2_nianji:
				arg = 2;
				dopop();
				break;
			case R.id.btn3_nianji:
				arg = 3;
				dopop();
				break;
			case R.id.btn4_nianji:
				arg = 4;
				dopop();
				break;
			case R.id.btn5_nianji:
				arg = 5;
				dopop();
				break;
			case R.id.btn6_nianji:
				arg = 6;
				dopop();
				break;
		}
	}
}
