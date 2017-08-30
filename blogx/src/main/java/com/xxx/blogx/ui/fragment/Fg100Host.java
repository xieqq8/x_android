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
import com.xxx.blogx.net.HttpUrlConstant;
import com.xxx.utils.AlertUtil;
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
    private TabLayout tabLayout;
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

        tabLayout = (TabLayout) mLayoutView.findViewById(R.id.tabLayout);

        viewPager = (ViewPager) mLayoutView.findViewById(R.id.vp_view);

//        mTitle.add("tab1");
//        mTitle.add("tab2");
//        mTitle.add("tab3");
//
//        mFragment.add(Fg110.newInstance("",""));
//        mFragment.add(Fg110.newInstance("",""));
//        mFragment.add(Fg110.newInstance("",""));
//        MyAdapter adapter = new MyAdapter(getChildFragmentManager(), mTitle, mFragment);
//
//        viewPager.setAdapter(adapter);
//        //为TabLayout设置ViewPager
//        tabLayout.setupWithViewPager(viewPager);

        //  LzyResponse<ServerModel>或LzyResponse<List<ServerModel>>整体作为一个泛型传递
//         // <LzyResponse<BlogCatalog>>       // 只是对象的这样也
        // <LzyResponse<List<BlogCatalog>>>  // 如果是数组可以这样写
        OkGo.<LzyResponse<List<BlogCatalog>>>get(HttpUrlConstant.getblogcatalog)//
                .converter(new JsonConvert<LzyResponse<List<BlogCatalog>>>() {})//
                .adapt(new ObservableBody<LzyResponse<List<BlogCatalog>>>())//
                .subscribeOn(Schedulers.io())//
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
//                        showLoading();
                    }
                })//
                .map(new Function<LzyResponse<List<BlogCatalog>>,List<BlogCatalog>>() {
                    @Override
                    public List<BlogCatalog> apply(@NonNull LzyResponse<List<BlogCatalog>> response) throws Exception {
                        LogX.getLogger().d(String.valueOf(response.success));
                        LogX.getLogger().d(String.valueOf(response.code));
                        LogX.getLogger().d(String.valueOf(response.msg));

                        return response.data; // //通过map将LzyResponse<List<BlogCatalog>> 变换成 List<BlogCatalog>
                    }
                })//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<List<BlogCatalog>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);   // 统一管理，页面销毁时取消
                        LogX.getLogger().d("addDisposable");
                    }

                    @Override
                    public void onNext(@NonNull List<BlogCatalog> blogCatalog) {
//                        handleResponse(serverModel);
//                        AlertUtil.showToast(getContext(),blogCatalog.get(0).getLabel());

                        mTitle.clear();
                        mFragment.clear();
                        for (BlogCatalog catalog : blogCatalog
                                ) {
                            mTitle.add(catalog.getLabel());
                            mFragment.add(Fg110.newInstance(catalog.getId(),catalog.getUrl()));

                        }
                        MyAdapter adapter = new MyAdapter(getChildFragmentManager(), mTitle, mFragment);

                        viewPager.setAdapter(adapter);
                        //为TabLayout设置ViewPager
                        tabLayout.setupWithViewPager(viewPager);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();            //请求失败
//                                AlertUtil.showToast(getContext(),"error:" + e.getMessage());
                        LogX.getLogger().d("error:" + e.getMessage());

//                        showToast("请求失败");
//                        handleError(null);
                    }

                    @Override
                    public void onComplete() {
//                                dismissLoading();
                    }
                });
    }

    @Override
    public boolean onBackPressed() { // 未执行
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
