package com.kuaxue.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kuaxue.database.ConfigUtil;
import com.kuaxue.demo.netinter.IloginCallback;
import com.kuaxue.demo.netinter.LoginUtil;
import com.kuaxue.demo.netparse.NetRestClient;
import com.kuaxue.demo.adapter.Adapter000FgMainViewPager;
import com.xxx.fudao.R;
import com.xxx.xbase.BaseFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息
 */
public class Fg300MainMsg extends BaseFragment implements IloginCallback {

    // 切换
    private RadioGroup mGroup = null;
    //
    private List<Fragment> listFrgment = null;
    private ViewPager mViewPager = null;

    private View dlgLogin = null;

    // 登录遮盖处理
    private LoginUtil login = null;

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
//		logx.d("onCreateView ");
        if (view == null)
            view = inflater.inflate(R.layout.fragment_main_msg, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//		logx.d("onViewCreated list ");
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        mGroup = (RadioGroup) view.findViewById(R.id.rg_main_msg_tab);

        // 登录框
        dlgLogin = view.findViewById(R.id.dlg_login);
        if (ConfigUtil.Instance().GetConfigValue(ConfigUtil.C_ACCESS_TOKEN).isEmpty()) {
            dlgLogin.setVisibility(View.VISIBLE);
        } else {
            // 己登录的处理
            loginSuccess();
        }
        // 默认选中提问
        mGroup.check(R.id.rb_tab_msg_ask);

        // 登录做为 RelativeLayout 的一层处理
        login = new LoginUtil(view, NetRestClient.Instance().client, mContext, mActivity, this);
    }

    /**
     * 登录成功的处理
     *
     * @return
     */
    public int loginSuccess() {
        dlgLogin.setVisibility(View.GONE);

        // 初始化滑动 viewPager 处理
        initViewPager();
        AddRadioGroup();
        return 0;
    }

    /**
     * 登出
     *
     * @return
     */
    public int loginOut() {
        return 0;
    }

    @Override
    public void onDestroyView() {
//		logx.d("onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
//		logx.d("onDestroy");
        super.onDestroy();
    }

    /**
     * 初始化滑动 viewPager 处理
     */
    private void initViewPager() {
        mViewPager = (ViewPager) view.findViewById(R.id.vp_msg);

        // 切换处理
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: // 答疑消息
                        ((RadioButton) view.findViewById(R.id.rb_tab_msg_ask)).setChecked(true);
                        logx.d("main msg view pager msg ask");
                        break;
                    case 1: // 系统消息
                        ((RadioButton) view.findViewById(R.id.rb_tab_msg_sys)).setChecked(true);
                        logx.d("main msg view pager msg ask");
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        // 内容填充
        listFrgment = new ArrayList<Fragment>();
        Fg310MsgAsk ask = new Fg310MsgAsk();
        Fg320MsgSys sys = new Fg320MsgSys();
        listFrgment.add(ask);
        listFrgment.add(sys);
        //
        mViewPager.setAdapter(new Adapter000FgMainViewPager(getChildFragmentManager(), listFrgment));
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onDetach() {
        logx.d("____onDetach____");
        // FragmentStatePagerAdapter 在Fragment被detached的时候去重置ChildFragmentManager
        // FragmentPagerAdapter 这里不要
        Field childFragmentManager;
        try {
            childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        // list要清空，要不会越来越多
        if (listFrgment != null)
            listFrgment.clear();
        super.onDetach();
    }

    /**
     *
     */
    private void AddRadioGroup() {
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mViewPager == null) {
                    return;
                }

                switch (checkedId) {
                    case R.id.rb_tab_msg_ask:
                        logx.d("main msg radio group msg ask");
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_tab_msg_sys:
                        logx.d("main msg radio group msg sys");
                        mViewPager.setCurrentItem(1);
                        break;
                }
            }
        });
    }
}
