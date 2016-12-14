package com.xxx.appxxx.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxx.appxxx.R;
import com.xxx.base.BackHandledFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fg120 extends BackHandledFragment {


    public Fg120() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container,savedInstanceState);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_fg120;
    }

    @Override
    public void initView() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
