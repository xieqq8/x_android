package com.xxx.appxxx.ui.activity;

import android.os.Bundle;
import android.view.Window;

import com.xxx.appxxx.R;
import com.xxx.base.BaseApcActivity;

/**
 * 主页一
 */
public class Act001Main extends BaseApcActivity {

    @Override
    public void initContentView() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

//        android:theme="@style/AppTheme.NoActionBar" 会把标题隐藏掉
        
        setContentView(R.layout.act_001_main);


//        OkHttpClient client = new OkHttpClient();
    }
}
