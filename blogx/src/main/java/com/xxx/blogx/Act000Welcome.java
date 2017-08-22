package com.xxx.blogx;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.githang.statusbar.StatusBarCompat;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.HttpManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;
import com.xxx.base.BaseApcActivity;
import com.xxx.blogx.net.api.CombinV1Api;
import com.xxx.blogx.net.api.GetServerTimeApi;
import com.xxx.utils.LogX;
import com.xxx.utils.PreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 首页 欢迎页
 */
public class Act000Welcome extends BaseApcActivity implements HttpOnNextListener {

    private long lCurtime;

    //    公用一个HttpManager
    private HttpManager manager;
    //  post请求接口信息
    private GetServerTimeApi postEntity;
    private CombinV1Api combinApi;

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
        postEntity = new GetServerTimeApi();
        getwebdatetime();

        // 或者
//        combinApi = new CombinV1Api(this,this);
//        combinApi.postApiServertime();


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

//            startActivity(new Intent(mContext, Act00NavBar.class));//

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
        // 进度框不显示
        postEntity.setShowProgress(false);

//        manager.doHttpDeal(postEntity, "http://api.kuaxue.cn/ParentClient/");

        manager.doHttpDeal(postEntity, "http://192.168.1.11/thinkcmf/public/index.php/api/user/");
    }

    @Override
    public void onNext(String resulte, String mothead) {
        LogX.getLogger().d("onNext");
        /*post返回处理*/
        if (mothead.equals(postEntity.getMethod())) {

            LogX.getLogger().d(resulte);
//            BaseResultEntity<ArrayList<SubjectResulte>>   subjectResulte = com.alibaba.fastjson.JSONObject.parseObject(resulte, new
//                    TypeReference<BaseResultEntity<ArrayList<SubjectResulte>>>(){});

//            LogX.getLogger().d(subjectResulte.getData().toString());


            Calendar calendar = new GregorianCalendar();
            Log.d("XLogger", "__" + calendar.getTimeZone().getOffset(System.currentTimeMillis()));

            // 8*60*60 = 28800 * 1000 = 28800 000  八小时
            lCurtime = System.currentTimeMillis() + calendar.getTimeZone().getOffset(System.currentTimeMillis()) - 28800000;

            try {
                JSONObject jsonObject = new JSONObject(resulte);
                long server = jsonObject.optLong("date3") * 1000;
                long internal = server - lCurtime;
                LogX.getLogger().d("internal:"+ internal +"_server:"+server+"_local:"+ String.valueOf(lCurtime));

                PreferencesUtil.getSharedPreference(mContext).edit().putLong("server_internal",internal).commit();

            } catch (JSONException e) {
                e.printStackTrace();
            }

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
    public void onNextCache(String resulte, String method) {
        LogX.getLogger().d("onNextCache:" + resulte);  // onNextCache 无网络的情况读出来的

    }

    @Override
    public void onError(ApiException e) {
        LogX.getLogger().d("onError");

    }

    @Override
    public void onCompleted(String method) {
        LogX.getLogger().d("onCompleted");

    }

}
