package com.xxx.appxxx.uitest;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.xxx.appxxx.R;
import com.xxx.appxxx.ui.fragment.Fg100Host;
import com.xxx.appxxx.ui.fragment.Fg200Books;
import com.xxx.base.BackHandledFragment;
import com.xxx.base.BaseApcActivity;
import com.xxx.base.BaseFragment;
import com.xxx.utils.LogX;

import java.util.ArrayList;

public class Act00NavBar extends BaseApcActivity implements BottomNavigationBar.OnTabSelectedListener,
        BaseFragment.OnFragmentInteractionListener,
        BackHandledFragment.BackHandledInterface {
    private int lastSelectedPosition = 0;

    public final static Uri ATOB = Uri.parse("atob");

    @Override
    public void initContentView() {
        setContentView(R.layout.act00_nav_bar);

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_location_on_white_24dp, "UI").setActiveColor(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.ic_find_replace_white_24dp, "网络").setActiveColor(R.color.orange))//blue))
                .addItem(new BottomNavigationItem(R.mipmap.ic_favorite_white_24dp, "进阶").setActiveColor(R.color.orange))//green))
//                .addItem(new BottomNavigationItem(R.mipmap.ic_book_white_24dp, "综合").setActiveColor(R.color.orange))//blue))
                .setFirstSelectedPosition(lastSelectedPosition )//设置默认选中
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
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
                LogX.getLogger().d("posion:" + position);
                if (fragment.isAdded()) {
                    ft.replace(R.id.layFrame, fragment);
                } else {
                    ft.add(R.id.layFrame, fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            LogX.getLogger().d("posion:" + position);

            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

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
        fragments.add(Fg100Host.newInstance("Music","music"));

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
}
