package com.xxx.appxxx.uitest.DrewAppbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xxx.appxxx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 简单的Fragment
 * <p/>
 * Created by wangchenlong on 15/11/9.
 */
public class SimpleFragment extends Fragment {
    private static final String ARG_SELECTION_NUM = "arg_selection_num";

    private static final int[] TEXTS = {R.string.tiffany_text, R.string.taeyeon_text, R.string.yoona_text};

    @BindView(R.id.main_tv_text)
    TextView mTvText;

    public SimpleFragment() {
    }

    public static SimpleFragment newInstance(int selectionNum) {
        SimpleFragment simpleFragment = new SimpleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SELECTION_NUM, selectionNum);
        simpleFragment.setArguments(args);
        return simpleFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvText.setText(TEXTS[getArguments().getInt(ARG_SELECTION_NUM)]);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }
}
