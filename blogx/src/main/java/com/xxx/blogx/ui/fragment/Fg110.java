package com.xxx.blogx.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lzy.okgo.OkGo;
import com.lzy.okrx2.adapter.ObservableBody;
import com.xxx.base.BackHandledFragment;
import com.xxx.blogx.R;
import com.xxx.blogx.callback.JsonConvert;
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
public class Fg110 extends BackHandledFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1Id;
    private String mParam2Url;

    private OnFragmentInteractionListener mListener;

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
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_fg110;
    }

    private RecyclerView lv;


    @Override
    public void initView() {

        lv = ((RecyclerView) mLayoutView.findViewById(R.id.lv));


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lv.setLayoutManager(layoutManager);

        // 分割线
        lv.addItemDecoration(new RecyclerViewDivider(
                getContext(), LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(getContext(), R.color.gray_l)));

        // <LzyResponse<BlogCatalog>>       // 只是对象的这样也
        // <LzyResponse<List<BlogCatalog>>>  // 如果是数组可以这样写
        OkGo.<LzyResponse<BlogModel>>get(HttpUrlConstant.getblog + "/" +mParam1Id)//
                .converter(new JsonConvert<LzyResponse<BlogModel>>() {
                })//
                .adapt(new ObservableBody<LzyResponse<BlogModel>>())//
                .subscribeOn(Schedulers.io())//
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
//                        showLoading();
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
                        LogX.getLogger().d("addDisposable");
                    }

                    @Override
                    public void onNext(@NonNull BlogModel blogModel) {
//                        handleResponse(serverModel);
//                        AlertUtil.showToast(getContext(), blogCatalog.get(0).getLabel());
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
}
