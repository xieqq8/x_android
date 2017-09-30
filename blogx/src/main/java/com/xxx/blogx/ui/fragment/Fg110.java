package com.xxx.blogx.ui.fragment;

import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.ObservableBody;
import com.xxx.base.BackHandledFragment;
import com.xxx.blogx.R;
import com.xxx.blogx.adapter.BlogPullToRefreshAdapter;
import com.xxx.blogx.callback.JsonConvert;
import com.xxx.blogx.databinding.Act001MainBinding;
import com.xxx.blogx.databinding.FragmentFg110Binding;
import com.xxx.blogx.model.BlogCatalog;
import com.xxx.blogx.model.BlogModel;
import com.xxx.blogx.model.LzyResponse;
import com.xxx.blogx.net.HttpUrlConstant;
import com.xxx.utils.AlertUtil;
import com.xxx.utils.LogX;
import com.xxx.utils.StringUtil;
import com.xxx.widget.RecyclerViewDivider;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fg110#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fg110 extends BackHandledFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1Id;
    private String mParam2Url;

    private OnFragmentInteractionListener mListener;

//    private RecyclerView mRecyclerView;
//    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BlogPullToRefreshAdapter pullToRefreshAdapter;
    private int mCurrentCounter = 0;
    private boolean isErr;
    private boolean mLoadMoreEndGone = false;
    private boolean bhasNext = false;
    private int nPage = 1;
    private boolean bRefresh = false; // 是否刷新

    public Fg110() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fg110.
     */
    // TODO: Rename and change types and number of parameters
    public static Fg110 newInstance(String param1, String param2) {
        Fg110 fragment = new Fg110();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_fg110;
    }


    private FragmentFg110Binding bind; // Act00NavBarBinding这个是activity layout 的名字

    @Override
    public void initView(ViewDataBinding binding) {

//        mRecyclerView = ((RecyclerView) mLayoutView.findViewById(R.id.lv));
        bind = (FragmentFg110Binding)binding;
        LogX.getLogger().d("Fg110 initView:" );

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bind.lv.setLayoutManager(layoutManager);

        // 分割线
        bind.lv.addItemDecoration(new RecyclerViewDivider(
                getContext(), LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(getContext(), R.color.gray_l)));

//        mSwipeRefreshLayout = (SwipeRefreshLayout) mLayoutView.findViewById(R.id.swipeLayout);
        bind.swipeLayout.setOnRefreshListener(this);
        bind.swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        initAdapter();

        onRefresh(); // 第一次加载
        bind.swipeLayout.setRefreshing(true); // 显示转圈圈的
    }

    private void initAdapter() {
        pullToRefreshAdapter = new BlogPullToRefreshAdapter();
        pullToRefreshAdapter.setOnLoadMoreListener(this, bind.lv);
        pullToRefreshAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT); // 设置载入动画
//        pullToRefreshAdapter.setPreLoadNumber(3);
        bind.lv.setAdapter(pullToRefreshAdapter);
        mCurrentCounter = pullToRefreshAdapter.getData().size();

        bind.lv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                // 点击
                Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1Id = getArguments().getString(ARG_PARAM1);
            mParam2Url = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_fg110, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            // 页面正在展示,在这里加载你的数据
        } else {
            // 页面没有展示
        }
    }

    @Override
    public void onRefresh() {
        nPage = 1;
        bRefresh = true;
        refreshData();
    }

    private void refreshData() {
        // 刷新
        // <LzyResponse<BlogCatalog>>       // 只是对象的这样也
        // <LzyResponse<List<BlogCatalog>>>  // 如果是数组可以这样写
        OkGo.<LzyResponse<BlogModel>>get(HttpUrlConstant.getblog + "/" +mParam1Id)//
                .params("page", nPage)
                .converter(new JsonConvert<LzyResponse<BlogModel>>() {
                })//
                .adapt(new ObservableBody<LzyResponse<BlogModel>>())//
                .subscribeOn(Schedulers.io())//
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
//                        mSwipeRefreshLayout
                    }
                })//
                .map(new Function<LzyResponse<BlogModel>, BlogModel>() {
                    @Override
                    public BlogModel apply(@NonNull LzyResponse<BlogModel> response) throws Exception {
                        return response.data;
                    }
                })//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<BlogModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                        LogX.getLogger().d("onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull BlogModel blogModel) {
                        if (bRefresh) {
                            pullToRefreshAdapter.setNewData(blogModel.getList());   // 刷新
                            bRefresh = false;
                        } else {
                            pullToRefreshAdapter.addData(blogModel.getList());    // 下拉
                        }

                        bhasNext = blogModel.isHasNext();
                        if(bhasNext) {
                            nPage++; // 如果下一页
                            pullToRefreshAdapter.loadMoreComplete();
                        } else {
                            pullToRefreshAdapter.setEnableLoadMore(true); // true会显示没有更多数据
                            pullToRefreshAdapter.loadMoreEnd(false);    //true is gone,false is visible 显示加载完成 Refresh end, no more data
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();            //请求失败
                        LogX.getLogger().d("error:" + e.getMessage() + HttpUrlConstant.getblog + "/" +mParam1Id);
                        if(bRefresh){

                        } else {
                            pullToRefreshAdapter.loadMoreFail();
                        }
                    }

                    @Override
                    public void onComplete() {

                        bind.swipeLayout.setRefreshing(false);
//                        pullToRefreshAdapter.setEnableLoadMore(true);
                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
        // 加载更多
        bind.swipeLayout.setEnabled(false);

        if (!bhasNext) {
            pullToRefreshAdapter.loadMoreEnd(true); // //true is gone,false is visible
        } else {
                refreshData();

            bind.swipeLayout.setEnabled(true);
        }
    }

//    @Override
//    public void onDestroy() { base onDetach()
//        super.onDestroy();
//
//        LogX.getLogger().d("Fg110 onDestroy ");
//        dispose();
//    }
}
