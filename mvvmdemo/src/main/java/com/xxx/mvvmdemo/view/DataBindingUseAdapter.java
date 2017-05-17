package com.xxx.mvvmdemo.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxx.mvvmdemo.BR;
import com.xxx.mvvmdemo.R;
import com.xxx.mvvmdemo.bean.Food;

import java.util.List;

/**
 * Created by luoxiongwen on 16/10/24.
 */

public class DataBindingUseAdapter extends BaseQuickAdapter<Food, DataBindingUseAdapter.MovieViewHolder> {

    private MoviePresenter mPresenter;

    public DataBindingUseAdapter(int layoutResId, List<Food> data) {
        super(layoutResId, data);

        mPresenter = new MoviePresenter();
    }

    @Override
    protected void convert(MovieViewHolder helper, Food item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.food, item);
        binding.setVariable(BR.presenter, mPresenter);
        binding.executePendingBindings(); // 刷新 此方法必须执行在UI线程，当绑定的数据修改时更新视图
//        switch (helper.getLayoutPosition() %
//                2) {
//            case 0:
//                helper.setImageResource(R.id.iv, R.mipmap.ic_launcher);
//                break;
//            case 1:
//                helper.setImageResource(R.id.iv_Picasso, R.mipmap.ic_launcher_round);
//                break;
//
//        }
    }

  /*  @Override
    protected MovieViewHolder createBaseViewHolder(View view) {
        return new MovieViewHolder(view);
    }
*/
    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    public class MovieViewHolder extends BaseViewHolder {

        public MovieViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
