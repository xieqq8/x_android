package com.xxx.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.githang.statusbar.StatusBarCompat;
import com.xxx.appxxx.R;
import com.xxx.kuaxue.swipebacklib.SwipeBackLayout;
import com.xxx.kuaxue.swipebacklib.app.SwipeBackActivity;
import com.xxx.utils.LogX;

/**
 * 带滑动返回的activity
 */
public abstract class BaseApcActivityWithSwipeBack extends SwipeBackActivity {
    protected LogX logx = LogX.getLogger();

    private ProgressDialog mProgressDialog;
    FragmentManager fragmentManager;

    // 定义的context SwipeBackActivity己定义
//    protected Context mContext = null;
//    protected Activity mActivity = null;

    private SwipeBackLayout mSwipeBackLayout;
    private int edgeFlag = SwipeBackLayout.EDGE_LEFT;

    /**
     * 初始化布局
     */
    public abstract void initContentView();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始化控制中心
     */
    public abstract void initPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mContext = this;
//        mActivity = this;

        mSwipeBackLayout = getSwipeBackLayout();
        // 设置左滑返回
        mSwipeBackLayout.setEdgeTrackingEnabled(edgeFlag);

        // 初始化布局
        initContentView();
        // 初始化控制中心
        initPresenter();
        // 初始化控件
        initView();

    }


//    /**
//     * 返回
//     */
//    protected void addTitleBtnBack(){
//        ImageView iv_left = (ImageView)findViewById(R.id.iv_left);
//        iv_left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//    }

    @Override
    public void finish() {
        super.finish();
        // 退出的动画
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }

    @Override
    public void onBackPressed() {
//		ActivityManager.getAppManager().finishActivity(this);
        super.onBackPressed();
        logx.d("onBackPressed");
    }

    /**
     * 改变状态栏颜色
     */
    protected void changeStausBarBg(int color){

        StatusBarCompat.setStatusBarColor(this, color);   // 这样有效果

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            //设置状态栏颜色
//            window.setStatusBarColor(color);
//            ViewGroup mContentView = (ViewGroup)this.findViewById(Window.ID_ANDROID_CONTENT);
//            View mChildView = mContentView.getChildAt(0);
//            if(mChildView != null){
//                //注意不是设置ContentView的FitsSystemWindows,而是设置ContentView的第一个子View预留出系统View的空间.
//                ViewCompat.setFitsSystemWindows(mChildView,true);
//            }
//        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//&& Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){ // 兼容 4.4 的
            Window window = getWindow();
            ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);

            //First translucent status bar.
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight();

            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
                //如果已经为 ChildView 设置过了 marginTop, 再次调用时直接跳过
                if (lp != null && lp.topMargin < statusBarHeight && lp.height != statusBarHeight) {
                    //不预留系统空间
                    ViewCompat.setFitsSystemWindows(mChildView, false);
                    lp.topMargin += statusBarHeight;
                    mChildView.setLayoutParams(lp);
                }
            }

            View statusBarView = mContentView.getChildAt(0);
            if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == statusBarHeight) {
                //避免重复调用时多次添加 View
                statusBarView.setBackgroundColor(color);
                return;
            }
            statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            statusBarView.setBackgroundColor(color);
            //向 ContentView 中添加假 View
            mContentView.addView(statusBarView, 0, lp);
        }
    }

    /**
     * 获取状态栏高度
     */
    public int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 显示单选对话框
     *
     * @param title           标题
     * @param message         提示信息
     * @param strings         选项数组
     * @param checkedItem     默认选中
     * @param onClickListener 点击事件的监听
     */
    public void showRadioButtonDialog(String title, String message, String[] strings, int checkedItem, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.setSingleChoiceItems(strings, checkedItem, onClickListener);
        builder.create();
        builder.show();
    }

    /**
     * 显示单选对话框
     *
     * @param title           标题
     * @param strings         选项数组
     * @param onClickListener 点击事件的监听
     */
    public void showRadioButtonDialog(String title, String[] strings, DialogInterface.OnClickListener onClickListener) {
        showRadioButtonDialog(title, null, strings, 0, onClickListener);
    }

    /**
     * 弹出自定义对话框
     */
    public void showConfirmDialog(String title, View.OnClickListener positiveListener) {
//        CustomConfirmDialog confirmDialog = new CustomConfirmDialog(this, title, positiveListener);
//        confirmDialog.show();
    }


    //--------------------------Fragment相关--------------------------//
    /**
     * 获取Fragment管理器
     *
     * @return
     */
    public FragmentManager getBaseFragmentManager() {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        return fragmentManager;
    }

    /**
     * 获取Fragment事物管理
     *
     * @return
     */
    public FragmentTransaction getFragmentTransaction() {
        return getBaseFragmentManager().beginTransaction();
    }

    /**
     * 替换一个Fragment
     *
     * @param res
     * @param fragment
     */
    public void replaceFragment(int res, BaseFragment fragment) {
        replaceFragment(res, fragment, false);
    }

    /**
     * 替换一个Fragment并设置是否加入回退栈
     *
     * @param res
     * @param fragment
     * @param isAddToBackStack
     */
    public void replaceFragment(int res, BaseFragment fragment, boolean isAddToBackStack) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.replace(res, fragment);
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

    }

    /**
     * 添加一个Fragment
     *
     * @param res
     * @param fragment
     */
    public void addFragment(int res, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.add(res, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 移除一个Fragment
     *
     * @param fragment
     */
    public void removeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    /**
     * 显示一个Fragment
     *
     * @param fragment
     */
    public void showFragment(Fragment fragment) {
        if (fragment.isHidden()) {
            FragmentTransaction fragmentTransaction = getFragmentTransaction();
            fragmentTransaction.show(fragment);
            fragmentTransaction.commit();
        }
    }

    /**
     * 隐藏一个Fragment
     *
     * @param fragment
     */
    public void hideFragment(Fragment fragment) {
        if (!fragment.isHidden()) {
            FragmentTransaction fragmentTransaction = getFragmentTransaction();
            fragmentTransaction.hide(fragment);
            fragmentTransaction.commit();
        }
    }

    //--------------------------Fragment相关end--------------------------//
}
