package com.xxx.appxxx.uitest;

import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.githang.statusbar.StatusBarCompat;
import com.xxx.appxxx.R;
import com.xxx.appxxx.ui.fragment.Fg100Host;
import com.xxx.appxxx.ui.fragment.Fg200Books;
import com.xxx.appxxx.ui.fragment.Fg300Me;
import com.xxx.base.BackHandledFragment;
import com.xxx.base.BaseApcActivity;
import com.xxx.base.BaseFragment;
import com.xxx.utils.LogX;

import java.util.ArrayList;

public class Act00NavBar extends BaseApcActivity implements BottomNavigationBar.OnTabSelectedListener,
        BaseFragment.OnFragmentInteractionListener,
        BackHandledFragment.BackHandledInterface {
    private int lastSelectedPosition = 0; // 初始选中

    public final static Uri ATOB = Uri.parse("atob");

    @Override
    public void initContentView() {
        setContentView(R.layout.act00_nav_bar);

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED); // 导航栏模式

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_location_on_white_24dp, "UI").setActiveColor(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.ic_find_replace_white_24dp, "网络").setActiveColor(R.color.orange))//blue))
                .addItem(new BottomNavigationItem(R.mipmap.ic_favorite_white_24dp, "进阶").setActiveColor(R.color.orange))//green))
//                .addItem(new BottomNavigationItem(R.mipmap.ic_book_white_24dp, "综合").setActiveColor(R.color.orange))//blue))
                .setFirstSelectedPosition(lastSelectedPosition )//设置默认选中
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);

        StatusBarCompat.setStatusBarColor(this, Color.argb(0xff,0xec,0x69,0x41));   // 这样有效果

    }

    @Override
    public void initView() {
        fragments = getFragments();

        setDefaultFragment();

    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private ArrayList<Fragment> fragments;

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                LogX.getLogger().d("onTabSelected posion:" + position);
                if (fragment.isAdded()) {
//                    ft.replace(R.id.layFrame, fragment);
                    ft.show(fragment);
                } else {

                    ft.add(R.id.layFrame, fragment);
                }
                ft.commitAllowingStateLoss();

//                ft.replace(R.id.layFrame,fragment);
//                ft.commit();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            LogX.getLogger().d("onTabUnselected posion:" + position);

            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
//                ft.remove(fragment);
                ft.hide(fragment);

                ft.commitAllowingStateLoss(); // 用hide show 没有 切换后滑动消失的

//                ft.commit();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {
        LogX.getLogger().d("onTabReselected posion:" + position);

    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
//        transaction.replace(R.id.layFrame, Fg100Host.newInstance("Home","home"));
//        transaction.commit();
//        Fragment fragment = fragments.get(0);
//        transaction.remove(fragment);
//        transaction.commitAllowingStateLoss();

        Fragment fragment = fragments.get(0);
        if (fragment.isAdded()) {
            transaction.replace(R.id.layFrame, fragment);
        } else {
            transaction.add(R.id.layFrame, fragment);
        }
        transaction.commitAllowingStateLoss();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(Fg100Host.newInstance("Home","home"));
        fragments.add(Fg200Books.newInstance("Books","books"));
//        fragments.add(Fg100Host.newInstance("Music","music"));
        fragments.add(Fg300Me.newInstance("Music","music"));

        return fragments;
    }

    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // 这里的Uri可以自己改
        LogX.getLogger().d("Act00NavBar  onFragmentInteraction_" + uri);
    }

    ///
    // http://blog.csdn.net/lovexieyuan520/article/details/50594271
    // 这个时候我们的app已经被销毁，我们按多任务键切换回来，发现界面上多个Fragment出现了重叠的情况，
    // 这是因为多个Fragment同时显示了，出现了重叠的情况，解决的办法如下：重写Activity的 onRestoreInstanceState 方法
    ///
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

//        for (int i = 0; i < radioGroup.getChildCount(); i++) {
//            RadioButton mTab = (RadioButton) radioGroup.getChildAt(i);
//            FragmentManager fm = getSupportFragmentManager();
//            Fragment fragment = fm.findFragmentByTag((String) mTab.getTag());
//            FragmentTransaction ft = fm.beginTransaction();
//            if (fragment != null) {
//                if (!mTab.isChecked()) {
//                    ft.hide(fragment);
//                }
//            }
//            ft.commit();
//        }
    }


}
