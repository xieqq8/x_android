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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Fg120() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static Fg120 newInstance(String param1, String param2) {
        Fg120 fragment = new Fg120();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
