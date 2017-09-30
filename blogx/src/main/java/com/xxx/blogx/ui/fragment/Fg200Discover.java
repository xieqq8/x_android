package com.xxx.blogx.ui.fragment;


import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xxx.base.BackHandledFragment;
import com.xxx.blogx.R;
import com.xxx.blogx.databinding.Fg200DiscoverBinding;
import com.xxx.blogx.databinding.FragmentFg300MeBinding;
import com.xxx.blogx.ui.activity.Act00NavBar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fg200Discover#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fg200Discover extends BackHandledFragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Fg200Discover() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fg300Me.
     */
    // TODO: Rename and change types and number of parameters
    public static Fg200Discover newInstance(String param1, String param2) {
        Fg200Discover fragment = new Fg200Discover();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container,savedInstanceState);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fg200_discover;
    }
    private Fg200DiscoverBinding bind; // Act00NavBarBinding这个是activity layout 的名字

    @Override
    public void initView(ViewDataBinding binding) {
        bind=(Fg200DiscoverBinding)binding;
    }

}
