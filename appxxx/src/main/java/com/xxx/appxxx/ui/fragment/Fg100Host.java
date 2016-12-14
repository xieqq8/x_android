package com.xxx.appxxx.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.xxx.appxxx.R;
import com.xxx.appxxx.uitest.Act00NavBar;
import com.xxx.appxxx.uitest.ScrollingActivity;
import com.xxx.base.BackHandledFragment;
import com.xxx.base.BaseFragment;
import com.xxx.utils.LogX;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.tabs;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link Fg100Host.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fg100Host#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fg100Host extends BackHandledFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fg100Host() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fg100Host.
     */
    // TODO: Rename and change types and number of parameters
    public static Fg100Host newInstance(String param1, String param2) {
        Fg100Host fragment = new Fg100Host();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
//        return R.layout.fragment_fg100_host;
        return R.layout.activity_scrolling;
    }

    // 防止 恢复Fragment的时候会出现菜单混乱
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
//            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//            setHasOptionsMenu(true);
//        }
    }

    private List<String> mTitle = new ArrayList<String>();
    private List<Fragment> mFragment = new ArrayList<Fragment>();
    private ViewPager viewPager;

    @Override
    public void initView() {
        if(mLayoutView == null)
            return;

        LogX.getLogger().d("Fg100Host initView:" + mParam1);

        TabLayout tabLayout = (TabLayout) mLayoutView.findViewById(R.id.tabLayout);

        viewPager = (ViewPager) mLayoutView.findViewById(R.id.vp_view);
//        for (int i = 0; i < 3; i++)
//            tabLayout.addTab(tabLayout.newTab().setText("选项卡槽" + i));
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mTitle.add("tab1");
        mTitle.add("tab2");
        mTitle.add("tab3");

        mFragment.add(Fg110.newInstance("",""));
        mFragment.add(Fg120.newInstance("",""));
        mFragment.add(Fg110.newInstance("",""));
//        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), mTitle, mFragment);
        MyAdapter adapter = new MyAdapter(getChildFragmentManager(), mTitle, mFragment);

        viewPager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        tabLayout.setupWithViewPager(viewPager);
        //使用ViewPager的适配器
//        tabLayout.setTabsFromPagerAdapter(adapter);
    }


    @Override
    public boolean onBackPressed() {
        Toast.makeText(getActivity(), "别点了，再点就退出", Toast.LENGTH_LONG).show();
        getActivity().finish();
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        if (savedInstanceState == null) {
//            // getFragmentManager().beginTransaction()...commit()
//        }else{
//            //先通过id或者tag找到“复活”的所有UI-Fragment
//            UIFragment fragment1 = getFragmentManager().findFragmentById(R.id.fragment1);
//            UIFragment fragment2 = getFragmentManager().findFragmentByTag("tag");
//            UIFragment fragment3 = ...
//            ...
//            //show()一个即可
//            getFragmentManager().beginTransaction()
//                    .show(fragment1)
//                    .hide(fragment2)
//                    .hide(fragment3)
//                    .hide(...)
//            .commit();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container,savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

    }



    class MyAdapter extends FragmentPagerAdapter {

        private List<String> title;
        private List<Fragment> views;

        public MyAdapter(FragmentManager fm, List<String> title, List<Fragment> views) {
            super(fm);
            this.title = title;
            this.views = views;
        }

        @Override
        public Fragment getItem(int position) {
            return views.get(position);
        }

        @Override
        public int getCount() {
            return views.size();
        }


        //配置标题的方法
        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }
    }
}
