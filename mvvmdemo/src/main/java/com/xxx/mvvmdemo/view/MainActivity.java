package com.xxx.mvvmdemo.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


//import com.xxx.mvvmdemo.CustomBinding;
import com.xxx.mvvmdemo.R;
import com.xxx.mvvmdemo.bean.UserLoginBean;
import com.xxx.mvvmdemo.databinding.ActivityMainBinding;
import com.xxx.mvvmdemo.model.UserModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
//    private CustomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        UserLoginBean loginBean = new UserLoginBean();
        loginBean.mFirstName.set("1123");
        loginBean.mPassword.set("321");
        loginBean.userface = "http://img2.cache.netease.com/auto/2016/7/28/201607282215432cd8a.jpg";
        binding.setUser(loginBean);

        // 点击登录
       binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel  userModel = new UserModel();

                // 从页面获取 databinding
//                UserLoginBean userBean = binding.getUser();
//
//                userModel.login(userBean);

                Intent intent = new Intent(MainActivity.this, ListActivity.class);
//                intent.setClass(MainActivity.this, ListActivity.this);
                startActivity(intent);
//                Intent saveIntent = new Intent(MainActivity.this, ListActivity.class);
//                startActivity(saveIntent);


            }
        });
    }
}
