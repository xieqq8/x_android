package com.kuaxue.demo.activity;

import android.os.Bundle;

import com.xxx.fudao.R;
import com.xxx.xbase.BaseActivityWithSlideBack;

/**
 * Created by xieqq on 2015-10-15 .
 */
public class Act431ChangePwd extends BaseActivityWithSlideBack {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        // 标题返回
        addTitleBtnBack();
    }
}
