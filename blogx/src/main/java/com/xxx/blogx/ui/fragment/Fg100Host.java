package com.xxx.blogx.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.ObservableBody;
import com.xxx.base.BackHandledFragment;
import com.xxx.blogx.R;
import com.xxx.blogx.callback.JsonConvert;
import com.xxx.blogx.model.BlogCatalog;
import com.xxx.blogx.model.LzyResponse;
import com.xxx.blogx.model.ServerModel;
import com.xxx.blogx.net.HttpUrlConstant;
import com.xxx.utils.LogX;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
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

    private List<String> mTitle = new ArrayList<String>();
    private List<Fragment> mFragment = new ArrayList<Fragment>();
    private ViewPager viewPager;

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
        return R.layout.act_001_main;               //  这个 Toolbar 上滑可隐藏
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


    @Override
    public void initView() {
        if(mLayoutView == null)
            return;

        LogX.getLogger().d("Fg100Host initView:" + mParam1);

        SetTable();
    }

    private void SetTable(){

        TabLayout tabLayout = (TabLayout) mLayoutView.findViewById(R.id.tabLayout);

        viewPager = (ViewPager) mLayoutView.findViewById(R.id.vp_view);
//        for (int i = 0; i < 3; i++)
//            tabLayout.addTab(tabLayout.newTab().setText("选项卡槽" + i));
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mTitle.add("tab1");
        mTitle.add("tab2");
        mTitle.add("tab3");

        mFragment.add(Fg110.newInstance("",""));
        mFragment.add(Fg110.newInstance("",""));
        mFragment.add(Fg110.newInstance("",""));
        MyAdapter adapter = new MyAdapter(getChildFragmentManager(), mTitle, mFragment);

        viewPager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        tabLayout.setupWithViewPager(viewPager);
//
        OkGo.<LzyResponse<ServerModel>>get(HttpUrlConstant.getblogcatalog)//
                .headers("aaa", "111")//
                .params("bbb", "222")//
                .converter(new JsonConvert<LzyResponse<ServerModel>>() {})//
                .adapt(new ObservableBody<LzyResponse<ServerModel>>())//
                .subscribeOn(Schedulers.io())//
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoading();
                    }
                })//
                .map(new Function<LzyResponse<ServerModel>, ServerModel>() {
                    @Override
                    public ServerModel apply(@NonNull LzyResponse<ServerModel> response) throws Exception {
                        return response.data;
                    }
                })//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<ServerModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull ServerModel serverModel) {
//                        handleResponse(serverModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();            //请求失败
//                        showToast("请求失败");
//                        handleError(null);
                    }

                    @Override
                    public void onComplete() {
                        dismissLoading();
                    }
                });
    }

    @Override
    public boolean onBackPressed() {
        Toast.makeText(getActivity(), "别点了，再点就退出", Toast.LENGTH_LONG).show();
//        getActivity().finish();
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
    public void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        dispose();
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
