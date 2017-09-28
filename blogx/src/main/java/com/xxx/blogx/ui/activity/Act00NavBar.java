package com.xxx.blogx.ui.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.xxx.base.BackHandledFragment;
import com.xxx.base.BaseApcActivity;
import com.xxx.base.BaseFragment;
import com.xxx.blogx.R;
import com.xxx.blogx.databinding.Act00NavBarBinding;
import com.xxx.blogx.ui.fragment.Fg100Host;
import com.xxx.blogx.ui.fragment.Fg300Me;
import com.xxx.utils.LogX;

import java.util.ArrayList;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class Act00NavBar extends BaseApcActivity implements BaseFragment.OnFragmentInteractionListener,
        BackHandledFragment.BackHandledInterface {

    private Act00NavBarBinding bind; // Act00NavBarBinding这个是activity layout 的名字

    private int lastSelectedPosition = 0; // 初始选中

    public final static Uri ATOB = Uri.parse("ATOB_Act00NavBar");

    private int nShowFg = -1;

    @Override
    public void initContentView() {
        bind = DataBindingUtil.setContentView(this, R.layout.act00_nav_bar);

//        StatusBarCompat.setStatusBarColor(this, Color.argb(0xff,0xec,0x69,0x41));   // 这样有效果
        StatusBarCompat.setStatusBarColor(this, Color.argb(0xff, 0xec, 0x69, 0x41), true);   // 这样有效果
    }

    private ArrayList<Fragment> fragments;

    @Override
    public void initView() {
        fragments = getFragments();
        ChangeFragment(0, nShowFg);
        nShowFg = 0;
//        addBadgeAt(1, 5);
    }

    @Override
    public void initPresenter() {
        bind.bnve.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private Badge addBadgeAt(int position, int number) {
        // add badge
        return new QBadgeView(this)
                .setBadgeNumber(number)
                .setGravityOffset(12, 2, true)
                .bindTarget(bind.bnve.getBottomNavigationItemView(position))
                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                        if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState)
                            Toast.makeText(Act00NavBar.this, "Badge is removed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 用的fragment替换，也可以用viewpager或以滑动
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.i_music: {
                    ChangeFragment(0, nShowFg);
                    nShowFg = 0;
                    return true;
                }
                case R.id.i_me: {
                    ChangeFragment(1, nShowFg);
                    nShowFg = 1;
                    return true;
                }
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 改变显示的fragment
     * @param newIndex
     * @param old
     */
    private void ChangeFragment(int newIndex, int old){

        LogX.getLogger().d(old + "_ChangeFragment_" + newIndex);

        if(old == newIndex)
            return;

        FragmentManager mFragmentMan = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentMan.beginTransaction();
        Fragment fragmentNew = fragments.get(newIndex);

        if(old == -1){
            // 之前没有，新加一下
            transaction.add(R.id.layFrame, fragmentNew).commitAllowingStateLoss();
            return;
        }

        Fragment fragmentOld = fragments.get(old);

        if(fragmentNew.isAdded()){
            transaction.hide(fragmentOld).show(fragmentNew).commitAllowingStateLoss();
        } else {
            transaction.hide(fragmentOld).add(R.id.layFrame, fragmentNew).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
        }

//        commitAllowingStateLoss则直接跳过这步，这里我们调用commit方法时，系统系判断状态（mStateSaved）是否已经保存，如果已经保存，
// 则抛出"Can not perform this action after onSaveInstanceState"异常，这就是我们遇到的问题了，而用commitAllowingStateLoss方法
// 则不会这样，这就与我们之前分析的activity去保存状态对应上了，在activity保存状态完成之后调用commit时，这个mStateSaved就是已经保存状态，
// 所以会抛出异常。

    }


    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(Fg100Host.newInstance("Home", "home"));
//        fragments.add(Fg200Books.newInstance("Books","books"));
//        fragments.add(Fg100Host.newInstance("Music","music"));
        fragments.add(Fg300Me.newInstance("Music", "music"));

        return fragments;
    }

    /**
     * 设置选中的Fragment  可以知道当前选中的fragment
     * @param selectedFragment
     */
    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
//        LogX.getLogger().d("setSelectedFragment_");
        // 当前选中的fragment
    }

    /**
     * fragment中点击就这里可以响应  fragment和activity交互
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {
        // 这里的Uri可以自己改
        LogX.getLogger().d("Act00NavBar  onFragmentInteraction_" + uri);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    // 双击返回键退出用
    private long exitTime = 0;
    /**
     * 再按一次退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                this.finish();
//                ActivityManager.getAppManager().AppExit(mContext);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
