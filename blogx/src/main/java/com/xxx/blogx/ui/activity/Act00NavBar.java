package com.xxx.blogx.ui.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
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

public class Act00NavBar extends BaseApcActivity implements BaseFragment.OnFragmentInteractionListener,
        BackHandledFragment.BackHandledInterface {

    private Act00NavBarBinding bind; // Act00NavBarBinding这个是activity layout 的名字

    private int lastSelectedPosition = 0; // 初始选中

    public final static Uri ATOB = Uri.parse("ATOB_Act00NavBar");

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

        setViewFragment(0);

    }

    @Override
    public void initPresenter() {
        bind.bnve.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
                    setViewFragment(0);
                    return true;
                }
                case R.id.i_me: {
                    setViewFragment(1);
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
     * 设置默认的
     */
    private void setViewFragment(int index) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        Fragment fragment = fragments.get(index);
        if (fragment.isAdded()) {
            transaction.replace(R.id.layFrame, fragment);
        } else {
            transaction.add(R.id.layFrame, fragment);
        }
        transaction.commitAllowingStateLoss();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(Fg100Host.newInstance("Home", "home"));
//        fragments.add(Fg200Books.newInstance("Books","books"));
//        fragments.add(Fg100Host.newInstance("Music","music"));
        fragments.add(Fg300Me.newInstance("Music", "music"));

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


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }


}
