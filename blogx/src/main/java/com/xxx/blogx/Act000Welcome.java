package com.xxx.blogx;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.githang.statusbar.StatusBarCompat;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableResponse;
import com.xxx.base.BaseApcActivity;
import com.xxx.blogx.net.HttpUrlConstant;
import com.xxx.blogx.ui.activity.Act00NavBar;
import com.xxx.utils.LogX;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observer;

/**
 * 首页 欢迎页
 */
public class Act000Welcome extends BaseApcActivity {

    private long lCurtime;

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
        
        setContentView(R.layout.act_000_welcome);

//        StatusBarCompat.setStatusBarColor(this, R.color.orange_r); // 这样没有效果  getResources().getColor(R.color.orange_r)
//        StatusBarCompat.setStatusBarColor(this, Color.argb(0xff,0x81,0xff,0x00));
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.orange_r));   // 这样有效果  Color.argb(0xff,0xec,0x69,0x41)


        getwebdatetimeOkGo();

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
            startActivity(new Intent(mContext, Act00NavBar.class));//

            finish();
        }
    };

    // 时间校准
    private void getwebdatetimeOkGo(){
        OkGo.<String>post(HttpUrlConstant.servertime)//
                .headers("aaa", "111")//
                .params("bbb", "222")//
                .converter(new StringConvert())//
                .adapt(new ObservableResponse<String>())//
                .subscribeOn(Schedulers.io())//
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
//                        showLoading();
                        return;
                    }
                })//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
//                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<String> response) {
//                        handleResponse(response);
                        LogX.getLogger().d(response.body().toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
//                        showToast("请求失败");
//                        handleError(null);
                    }

                    @Override
                    public void onComplete() {
//                        dismissLoading();
                    }
                });
    }

}
