package com.xxx.appxxx.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.xxx.appxxx.R;
import com.xxx.base.BaseActivity;

public class Act000Welcome extends BaseActivity {

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


        setContentView(R.layout.act_000_welcome);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // 延迟时间
        new Handler().postDelayed(r, 2200);
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {

//            // 如果信鸽注册失败
//            if (ConfigUtil.Instance().GetConfigValue(ConfigUtil.C_Local_Xg_token).equals("0")) {
//                ConfigUtil.Instance().SetConfigValue(ConfigUtil.C_Local_Xg_token, String.valueOf(java.util.UUID.randomUUID()));
//            }

//            // 跳到主页
//            boolean isFrist = PreferencesUtil.getSharedPreference(mContext).getBoolean("FirstStart",true);
//            long lastVersion = PreferencesUtil.getSharedPreference(mContext).getLong("lastVersion", 0);
//            int versionCode = UpdateManager.getVersionCode(mContext);
//            if (lastVersion != versionCode){
//                SharedPreferences.Editor editor = PreferencesUtil.getSharedPreference(mContext).edit();
//                editor.putLong("lastVersion",versionCode).commit();
//                startActivity(new Intent(mContext, Act004GuidePage.class));
//            }else{
//                if (isFrist) {
//                    startActivity(new Intent(mContext, Act004GuidePage.class));
//                }else {
                startActivity(new Intent(mContext, MainActivity.class));
//                }
//            }
            finish();
        }
    };
}
