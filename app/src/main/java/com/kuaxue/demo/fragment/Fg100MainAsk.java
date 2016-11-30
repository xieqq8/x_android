package com.kuaxue.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.kuaxue.demo.activity.Act110TeacherList;
import com.kuaxue.demo.activity.Act111ListViewCheck;
import com.xxx.fudao.R;
import com.xxx.xbase.BaseFragment;


/**
 * 提问
 */
public class Fg100MainAsk extends BaseFragment {

    /**
     * onCreate是指创建该fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        logx.d("onCreate ");
        Bundle args = getArguments();
        mContext = getActivity();
        super.onCreate(savedInstanceState);
    }


    /**
     * onCreateView是创建该fragment对应的视图
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        logx.d("onCreateView ");
        if (view == null)
            view = inflater.inflate(R.layout.fragment_main_ask, null);

        AddBtnAsk();
        AddBtnLst();
        AddBtnDlgFg();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        logx.d("onViewCreated list ");
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        return;
    }

    private void AddBtnAsk() {
        ImageButton btn = (ImageButton) view.findViewById(R.id.ivbAsk);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, Act110TeacherList.class);
                startActivity(i);
                // 打开的动画
                mActivity.overridePendingTransition(R.anim.base_slide_right_in, 0);
            }
        });
    }

    private void AddBtnLst() {
        Button btnLst = (Button) view.findViewById(R.id.btnLst);
        btnLst.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, Act111ListViewCheck.class);
                startActivity(i);
                // 打开的动画
                mActivity.overridePendingTransition(R.anim.base_slide_right_in, 0);
            }
        });
    }

    private void AddBtnDlgFg() {
        Button btnFg = (Button) view.findViewById(R.id.dlgFrag);
        btnFg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Fg112Dlg editNameDialog = new Fg112Dlg();
                 editNameDialog.show(getFragmentManager(), "EditNameDialog");
            }
        });
    }

    @Override
    public void onDestroyView() {
//        logx.d("onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
//        logx.d("onDestroy");
        super.onDestroy();
    }
}
