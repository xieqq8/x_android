package com.kuaxue.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.xxx.fudao.R;
import com.xxx.xbase.BaseActivity;

public class Act001Welcome extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_welcome);
        mContext = this;

        // 延迟时间
        new Handler().postDelayed(r, 1000);
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            // 跳到主页
            startActivity(new Intent(mContext, Act000Main.class));
            finish();
        }
    };
}
