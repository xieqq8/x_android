package com.xxx.appxxx.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.githang.statusbar.StatusBarCompat;
import com.githang.statusbar.StatusBarExclude;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.HttpManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextSubListener;
import com.xxx.appxxx.R;
import com.xxx.appxxx.api.SubjectPostApi;
import com.xxx.appxxx.net.HttpUrlConstant;
import com.xxx.appxxx.resulte.BaseResultEntity;
import com.xxx.appxxx.resulte.SubjectResulte;
import com.xxx.appxxx.resulte.UploadResulte;
import com.xxx.appxxx.uitest.Act00NavBar;
import com.xxx.appxxx.uitest.Act01MainViewPage;
import com.xxx.base.BaseApcActivity;
import com.xxx.utils.LogX;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import rx.Observable;

public class Act000Welcome extends BaseApcActivity implements HttpOnNextListener, HttpOnNextSubListener {

    //    公用一个HttpManager
    private HttpManager manager;
    //    post请求接口信息
    private SubjectPostApi postEntity;

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

        // 不显示系统的标题栏，保证windowBackground和界面activity_main的大小一样，显示在屏幕不会有错位（去掉这一行试试就知道效果了）
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

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
        /*初始化数据*/
        manager = new HttpManager(this, this);

        postEntity = new SubjectPostApi();
        postEntity.setAll(true); // 接口需要传入的参数
        getwebdatetime();

        LogX.getLogger().d("Act000Welcome onCreate " + Build.VERSION.SDK_INT + Build.VERSION_CODES.KITKAT);
        // 延迟时间
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) { // 兼容 4.4 的
            new Handler().postDelayed(r, 1200); // 由于install run,4.4版本后debug版本有的开机 白屏( 用 android:windowBackground 替换)
        } else {
            new Handler().postDelayed(r, 2000);
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

//            startActivity(new Intent(mContext, Act01MainViewPage.class));//

//            startActivity(new Intent(mContext, Act001Main.class));//


//                }
//            }
            finish();
        }
    };

    // 时间校准

    /**
     * 得到网络时间和本地的间隔时间
     */
    private void getwebdatetime(){

        manager.doHttpDeal(postEntity);

//        NetRestClient.Instance().client.get(mContext, HttpUrlConstant.servertime, null, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//            }
//
//            @Override
//            public void onSuccess(int i, Header[] headers, String s) {
////                SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
////                String ee = dff.format(new Date());
////
////                long cur = System.currentTimeMillis();
////
////                SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
////                formatter1.setTimeZone(TimeZone.getTimeZone("GMT+08"));
////                Date curDate = new Date(cur);//获取当前时间
////                String str = formatter1.format(new Date()); //北京时间
////                logx.d("北京时间: %s ",str); // 得到北京时间的格式
////
////                Calendar calendarA = Calendar.getInstance(TimeZone.getTimeZone("GMT+08"));
////                calendarA.setTime(new Date());
////
////                try {
////                    Date date = stringToDate(str, formatter1);
////                    long cur2 = date.getTimezoneOffset();
////
////
////                    Log.d("XLogger", String.valueOf(cur));
////                    Log.d("XLogger", String.valueOf(cur2));
////                    Log.d("XLogger", String.valueOf(cur2-cur));
////
//////                    logx.d("时间差: %s _ %s _ %s " , String.valueOf(cur) , String.valueOf(cur2) , String.valueOf(cur2 - cur));
////                } catch (java.text.ParseException e) {
////                    e.printStackTrace();
////                }
////                Calendar calendarB = Calendar.getInstance(TimeZone.getDefault());
////                calendarA.setTime(new Date());
////
////                long diffDateInMillisSeconds = calendarA.getTimeInMillis() - calendarB.getTimeInMillis();
//
//                Calendar calendar = new GregorianCalendar();
//                Log.d("XLogger", "__" + calendar.getTimeZone().getOffset(System.currentTimeMillis()));
//
//
//                lCurtime = System.currentTimeMillis() + calendar.getTimeZone().getOffset(System.currentTimeMillis()) - 28800000;
//
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    long server = jsonObject.optLong("date3") * 1000;
//                    long internal = server - lCurtime;
//                    logx.d("internal:"+ internal +"_server:"+server+"_local:"+ String.valueOf(lCurtime));
//
//                    PreferencesUtil.getSharedPreference(mContext).edit().putLong("server_internal",internal).commit();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    @Override
    public void onNext(String resulte, String mothead) {
        /*post返回处理*/
        if (mothead.equals(postEntity.getMethod())) {
            BaseResultEntity<ArrayList<SubjectResulte>>   subjectResulte = com.alibaba.fastjson.JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<ArrayList<SubjectResulte>>>(){});
//            tvMsg.setText("post返回：\n" + subjectResulte.getData().toString());
        }

//        /*上传返回处理*/
//        if (mothead.equals(uplaodApi.getMethod())) {
//            BaseResultEntity<UploadResulte> subjectResulte = com.alibaba.fastjson.JSONObject.parseObject(resulte, new
//                    TypeReference<BaseResultEntity<UploadResulte>>(){});
//            UploadResulte uploadResulte = subjectResulte.getData();
//            tvMsg.setText("上传成功返回：\n" + uploadResulte.getHeadImgUrl());
//            Glide.with(MainActivity.this).load(uploadResulte.getHeadImgUrl()).skipMemoryCache(true).into(img);
//        }
    }

    @Override
    public void onError(ApiException e) {

    }

    @Override
    public void onNext(Observable observable) {

    }
}
