package com.kuaxue.demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuaxue.demo.netparse.entity.CourseVideoInfo;
import com.xxx.views.PullToRefreshView;
import com.xxx.xbase.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 相关年级、相关科目课程
 * @author Lou
 *
 */
public class Fg210Course extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener {
	private final String TAG = "FragmentCourse";
	
	private GridView gv_course_base = null;
	private ProgressBar pb_course_base_loading;
	private TextView tv_course_base_loading;
	private int pageId=1;
	private String grkid;
	private int mycount;
	private int totalcount;
	private List<CourseVideoInfo> courseVideoInfoList = new ArrayList<CourseVideoInfo>();
//	private AllVieoInfoAdapter allVieoInfoAdapter;
	
	private View v;
	
	private String kemuId;
	private String kemuName;
	private int nianjiId;

	private RelativeLayout rl_course_no_data;
	
	//下拉刷新、底部加载
	private PullToRefreshView pullToRefreshView;
	private boolean isRefesh=true;
	
	private Context context;
	
	//ww 获取屏幕的宽度和高度
	private int mScreenHeight;
	private int mScreenWidth;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		kemuId = args != null ? args.getString("kemu_id") : "";
		kemuName = args != null ? args.getString("kemu_name") : "";
		nianjiId = args != null ? args.getInt("nianji_id", 0) : 0;
		
		// ww 获取到屏幕的宽度和高度
		Display display = getActivity().getWindowManager().getDefaultDisplay();  
        mScreenHeight= display.getHeight();  
        mScreenWidth = display.getWidth(); 
        
		super.onCreate(savedInstanceState);
	}
	
	public void otherImagevClearBackcolor(GridView view,int select){
		for (int x=0;x<view.getChildCount();x++) {
			if (select!=x) {
//				ImageView iv = (ImageView) view.getChildAt(x).findViewById(R.id.iv_course_base_bg_press);
//				iv.setVisibility(View.INVISIBLE);
			}
		}
	}
	
	@Override  
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		courseVideoInfoList.clear();
		if (v==null) {
//			v = inflater.inflate(R.layout.fragment_course, container,false);
//			pullToRefreshView =(PullToRefreshView)v.findViewById(R.id.gv_pullToRefresh);
//			gv_course_base = (GridView) v.findViewById(R.id.gv_course_base);
//			pb_course_base_loading = (ProgressBar) v.findViewById(R.id.pb_course_base_loading);
//			tv_course_base_loading = (TextView) v.findViewById(R.id.tv_course_base_loading);
//			rl_course_no_data = (RelativeLayout) v.findViewById(R.id.rl_course_no_data);
//
//			//下拉刷新、底部加载
//			pullToRefreshView.setOnHeaderRefreshListener(this);
//			pullToRefreshView.setOnFooterRefreshListener(this);
//			pullToRefreshView.setLastUpdated(getRefreshTime());
//
			gv_course_base.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
//					grkid = courseVideoInfoList.get(arg2).id;
//					otherImagevClearBackcolor(gv_course_base, arg2);
//					final ImageView iv_course_base_bg_press= (ImageView) arg1.findViewById(R.id.iv_course_base_bg_press);
//					iv_course_base_bg_press.setVisibility(View.VISIBLE);
//
//					// ww 给点击时的黑色背景设置大小
//					ViewGroup.LayoutParams layoutParams1 =iv_course_base_bg_press.getLayoutParams();
//					layoutParams1.height = mScreenHeight/4;//一屏幕显示4行
//					layoutParams1.width = (mScreenWidth-20)/2;//一屏显示两列
//					iv_course_base_bg_press.setLayoutParams(layoutParams1);
//
//					iv_course_base_bg_press.postDelayed(new Runnable() {
//						@Override
//						public void run() {
//							if(!NetworkUtil.isWifiActive(mContext)){
//								Toast.makeText(mContext, "您当前处于非WIFI状态，建议您切换到WIFI后在观看视频", Toast.LENGTH_LONG).show();
//							}
//
//							Intent intent = new Intent(mContext, ActCCMediaPlayer.class);
//							intent.putExtra("grkid", grkid);
//							mContext.startActivity(intent);
//							iv_course_base_bg_press.setVisibility(View.INVISIBLE);
//						}
//					}, 500);
					
				}
			});
			
		} else {
			removeSelfFromParent(v);
		}
		
		return v;
	}

//	public void initData() {
//		postNetAllSub();
//	}
//
//	private void postNetAllSub() {
//		if(isRefesh){
//			pullToRefreshView.headerRefreshing2();
//		}
//		String strPost = "";
////		String jsonstr = "{\"userId\":"+SPUtil.get(mContext, "uid"); //双引号方式
//		String jsonstr = "{\"userId\": 0"; // 252055
//		Map<String, String> params = new HashMap<String, String>();
//		if(nianjiId==0){
//			strPost = Constant.COURSE_YUWEN_FOR_ALLV1;
//			if(isRefesh==true){
//				jsonstr += (",\"kemuName\":\"" + kemuName + "\",\"pageId\":" + pageId + "}");
//			}else{
//				jsonstr += (",\"kemuName\":\"" + kemuName + "\",\"pageId\":"+ 1 +"}");
//			}
//		} else {
//			strPost = Constant.COURSE_SUBJECT_FOR_NIANJI_LIST_PAGEV1;
//			jsonstr += (",\"nianjiId\":" + nianjiId +",\"kemuId\":" + kemuId + ",\"pageId\":" + pageId + "}");
//		}
//		params.put("json", jsonstr);
//		tv_course_base_loading.setVisibility(View.GONE);
//		pb_course_base_loading.setVisibility(View.GONE);
//		networkHelper.sendPostRequest(strPost, params, 0);
//	}
	//上拉加载方法
	@Override
	public void onFooterRefresh(PullToRefreshView view) {

		pullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
            	isRefesh = true;
            	pageId++;
//				postNetAllSub();
            	pullToRefreshView.onFooterRefreshComplete();
            }
        },1000);
	}

	//下拉刷新方法
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		pullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				isRefesh = false;
				pageId = 1;
//				postNetAllSub();
//				String time =getRefreshTime();
//				if(time.equals("")||time.equals("null")||time==null){
//					pullToRefreshView.onHeaderRefreshComplete("更新于:刚刚");
//				}else{
//					pullToRefreshView.onHeaderRefreshComplete("更新于:"+getRefreshTime());
//				}
//				saveRefreshTime();
			}
		}, 1000);
	}
//	//判断学科类型
//	private String judgeType(){
//		String name ="";
//		if(kemuName.equals("语文")){
//			name ="yuwenRefeshTime";
//		}else if(kemuName.equals("数学")){
//			name ="shuxueRefeshTime";
//		}else if(kemuName.equals("英语")){
//			name ="yingyuRefeshTime";
//		}else if(kemuName.equals("物理")){
//			name ="wuliRefeshTime";
//		}else if(kemuName.equals("化学")){
//			name ="huaxueRefeshTime";
//		}else if(kemuName.equals("政治")){
//			name ="zhengzhiRefeshTime";
//		}else if(kemuName.equals("历史")){
//			name ="lishiRefeshTime";
//		}else if(kemuName.equals("地理")){
//			name ="diliRefeshTime";
//		}else if(kemuName.equals("生物")){
//			name ="shengwuRefeshTime";
//		}
//		return name;
//	}
//	//保存不同的fragment下拉刷新时间
//	private void saveRefreshTime(){
//		SPUtil.set(mContext, judgeType(), new Date().toLocaleString());
//	}
//	//获取不同的fragment下拉刷新时间
//	private String getRefreshTime(){
//		return SPUtil.get(mContext, judgeType());
//	}
//
//	public class AllVieoInfoAdapter extends BaseAdapter{
//
//		private Context con;
//		private ArrayList<CourseVideoInfo> list;
//		public AllVieoInfoAdapter(Context mContext,List<CourseVideoInfo> list){
//			this.con = mContext;
//			this.list = (ArrayList<CourseVideoInfo>) list;
//
//		}
//		@Override
//		public int getCount() {
//			return list.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return position;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//
//			ViewHolder holder;
//
//			if (convertView == null) {
//				convertView = View.inflate(con, R.layout.item_course_base_new, null);
//
//				LinearLayout ll_test_score_exchange_gritem_new = (LinearLayout) convertView.findViewById(R.id.ll_coursebase_new_total);
//				ViewGroup.LayoutParams layoutParams =ll_test_score_exchange_gritem_new.getLayoutParams();
//				layoutParams.height = mScreenHeight/4;//一屏幕显示4行
//				layoutParams.width = (mScreenWidth-20)/2;//一屏显示两列
//				ll_test_score_exchange_gritem_new.setLayoutParams(layoutParams);
//
//				holder = new ViewHolder();
//				holder.keCheng = (ImageView) convertView.findViewById(R.id.iv_course_base_bg);
//				holder.grkName = (TextView) convertView.findViewById(R.id.tv_course_base_kemu);
//				holder.teacherName = (TextView) convertView.findViewById(R.id.tv_course_base_gridview_teacher);
//				holder.nianjiName = (TextView) convertView.findViewById(R.id.tv_course_base_kemu_nianji);
//
//				convertView.setTag(holder);
//			}else {
//				holder=(ViewHolder) convertView.getTag();
//			}
//			//加载头像
//			ImageLoadUtil.loadImage(holder.keCheng, list.get(position).image);
//			holder.grkName.setText(list.get(position).grkcname);
//			holder.teacherName.setText(list.get(position).teacherName);
//			holder.nianjiName.setText(list.get(position).nianjiName);
//			return convertView;
//		}
//
//		public  class ViewHolder{
//			public ImageView keCheng;
//			public TextView grkName;
//			public TextView teacherName;
//			public TextView nianjiName;
//			public ImageView ivHot;
//		}
//	}
//
//	@Override
//	public void onDataCallback(int questId, JSONObject data) {
//		super.onDataCallback(questId, data);
//		if(isRefesh){
//			pullToRefreshView.onHeaderRefreshComplete("更新于：刚刚");
//		}
//		tv_course_base_loading.setVisibility(View.GONE);
//		pb_course_base_loading.setVisibility(View.GONE);
//		LogX.v(TAG, data.toString());
//		CourseAllSubjectsPage allSubjects4Page = CommDef.parseJson(data.toString(), CourseAllSubjectsPage.class);
//		mycount = allSubjects4Page.resultRecordsCount;
//		totalcount=allSubjects4Page.totalRecordsCount;
//		if ("success".equals(allSubjects4Page.result)) {
//
//			if (0==totalcount) {
//				rl_course_no_data.setVisibility(View.VISIBLE);
//				pb_course_base_loading.setVisibility(View.GONE);
//				tv_course_base_loading.setVisibility(View.GONE);
//			} else {
//				if(mycount!=0){
//
//					//下拉刷新和初次加载时，清空list
//					LogX.v(TAG, isRefesh+"");
//					if(isRefesh == false){
//						courseVideoInfoList.clear();
//					}
//					courseVideoInfoList.addAll(allSubjects4Page.videocourseList);
//					if (allVieoInfoAdapter==null) {
//						allVieoInfoAdapter = new AllVieoInfoAdapter(mContext, courseVideoInfoList);
//						if(gv_course_base != null){
//							gv_course_base.setAdapter(allVieoInfoAdapter);
//						}
//					}else{
//						allVieoInfoAdapter.notifyDataSetChanged();
//					}
//					rl_course_no_data.setVisibility(View.GONE);
//				}else{
//					Toast.makeText(mContext, "没有更多数据", Toast.LENGTH_SHORT).show();
//				}
//
//			}
//		}
//	}
//
//	@Override
//	public void onErrorCallback(int questId,String errorMessage) {
//		super.onErrorCallback(questId,errorMessage);
//		LogX.v(TAG, errorMessage.toString());
//		Toast.makeText(mContext, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
//		pb_course_base_loading.setVisibility(View.INVISIBLE);
//		tv_course_base_loading.setText("网络繁忙，请重试");
//		tv_course_base_loading.setVisibility(View.GONE);
//	}
}
