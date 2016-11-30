package com.kuaxue.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.kuaxue.database.ConfigUtil;
import com.xxx.fudao.R;
import com.xxx.xbase.BaseActivityWithSlideBack;

/**
 * 个人信息 前级入口 Fg400MainMe
 * Created by xieqq on 2015-09-29 .
 */
public class Act430UserInfo extends BaseActivityWithSlideBack {

    private Button btn_logout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        AddBtnLoginout();

        AddChangePwd();
        // 标题返回
        addTitleBtnBack();
    }

    private void AddBtnLoginout(){
        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigUtil.Instance().SetConfigValue(ConfigUtil.C_ACCESS_TOKEN, "");
                finish();
            }
        });

    }
    private void AddChangePwd(){
        RelativeLayout rl_pwd = (RelativeLayout)findViewById(R.id.rl_pwd);
        rl_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Act431ChangePwd.class));
            }
        });

    }
}
