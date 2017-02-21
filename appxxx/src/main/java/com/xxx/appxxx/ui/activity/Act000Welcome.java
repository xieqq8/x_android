package com.xxx.appxxx.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.githang.statusbar.StatusBarCompat;
import com.githang.statusbar.StatusBarExclude;
import com.xxx.appxxx.R;
import com.xxx.appxxx.uitest.Act00NavBar;
import com.xxx.base.BaseApcActivity;
import com.xxx.utils.LogX;

public class Act000Welcome extends BaseApcActivity {

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
//        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.act_000_welcome);

//        StatusBarCompat.setStatusBarColor(this, R.color.orange_r); // 这样没有效果  getResources().getColor(R.color.orange_r)
//        StatusBarCompat.setStatusBarColor(this, Color.argb(0xff,0x81,0xff,0x00));
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.orange_r));   // 这样有效果  Color.argb(0xff,0xec,0x69,0x41)

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
        LogX.getLogger().d("Act000Welcome onCreate");
        // 延迟时间
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) { // 兼容 4.4 的

            new Handler().postDelayed(r, 200);
        } else {
            new Handler().postDelayed(r, 1200);
        }
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
//                startActivity(new Intent(mContext, Act002Login.class));//Act00NavBar
                    startActivity(new Intent(mContext, Act00NavBar.class));//
//                }
//            }
            finish();
        }
    };
}
