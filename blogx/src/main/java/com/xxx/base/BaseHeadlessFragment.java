package com.xxx.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.xxx.utils.LogX;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 无UI的Fragment，即在onCreateView()中返回的是null
 * 待完善
 */
public abstract class BaseHeadlessFragment extends Fragment {
    private AppCompatActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true); // Fragment即使在其Activity重做时也不进行销毁那么就要设置setRetainInstance(true)

        // Fragment即使在其Activity重做时也不进行销毁那么就要设置setRetainInstance(true)。
        // 进行了这样的操作后，一旦发生Activity重组现象，Fragment会跳过onDestroy直接进行onDetach（界面消失、对象还在），
        // 而Framgnet重组时候也会跳过onCreate，而onAttach和onActivityCreated还是会被调用。需要注意的是，要使用这种操作
        // 的Fragment不能加入backstack后退栈中。并且，被保存的Fragment实例不会保持太久，若长时间没有容器承载它，也会被系
        // 统回收掉的。
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    /**
     * 获取当前Fragment状态
     *
     * @return true为正常 false为未加载或正在删除
     */
    private boolean getStatus() {
        return (isAdded() && !isRemoving());
    }

    /**
     * 获取Activity
     *
     * @return
     */
    public AppCompatActivity getBaseActivity() {
        if (mActivity == null) {
            mActivity = (AppCompatActivity) getActivity();
        }
        return mActivity;
    }

    @Override
    public Context getContext() {
        return getActivity();
    }


    @Override
    public void onAttach(Context context) {

        LogX.getLogger().d("Base Fragment onAttach()");


        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

        LogX.getLogger().d("Base Fragment onDetach()");
    }

}
