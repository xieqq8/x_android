package com.xxx.blogx.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.xxx.base.BackHandledFragment;
import com.xxx.blogx.R;
import com.xxx.blogx.databinding.FragmentFg110Binding;
import com.xxx.blogx.databinding.FragmentFg300MeBinding;
import com.xxx.blogx.ui.activity.Act00NavBar;
import com.xxx.utils.LogX;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fg300Me#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fg300Me extends BackHandledFragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Fg300Me() {
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
    public static Fg300Me newInstance(String param1, String param2) {
        Fg300Me fragment = new Fg300Me();
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
        return R.layout.fragment_fg300_me;
//        return R.layout.act_001_main;               //  这个 Toolbar 上滑可隐藏
    }
    private FragmentFg300MeBinding bind; // Act00NavBarBinding这个是activity layout 的名字

    @Override
    public void initView(ViewDataBinding binding) {
        bind=(FragmentFg300MeBinding)binding;
//        Button button = (Button) mLayoutView.findViewById(R.id.button2);

        bind.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), ScrollingActivity.class));
            }
        });

//        Button btn = (Button) mLayoutView.findViewById(R.id.button);
        bind.button.setText(mParam2);
        bind.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Act00NavBar.ATOB;
                onButtonPressed(uri);

//                startActivity( new Intent(getActivity(), DWeatherActivity.class));

            }
        });

//        Button btn3 = (Button) mLayoutView.findViewById(R.id.button_appbar);
//        btn3.setText(mParam2);
        bind.buttonAppbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Act00NavBar.ATOB;
                onButtonPressed(uri);

//                startActivity( new Intent(getActivity(), DrawAppBarDemoActivity.class));

            }
        });
        //获取控件实例
//        cityET = (EditText) mLayoutView.findViewById(R.id.city);
//        queryTV = (TextView) mLayoutView.findViewById(R.id.query);
//        weatherTV = (TextView) mLayoutView.findViewById(R.id.weather);
        //对查询按钮侦听点击事件
        bind.button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // 查询

                bind.weather.setText("");
                String city = bind.city.getText().toString();
                if(TextUtils.isEmpty(city)){
                    Toast.makeText(getActivity(), "城市不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });


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


    /**
     * 天气预报API地址
     */
    private static final String WEATHRE_API_URL="http://php.weather.sina.com.cn/xml.php?city=%s&password=DJOYnieT8234jlsK&day=0";
//    private EditText cityET;     //城市
//    private TextView queryTV;    //查询按钮
//    private TextView weatherTV;  //天气结果



}
