package com.wzgiceman.rxretrofitlibrary.retrofit_rx.http;

import android.util.Log;

import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.RetryWhenNetworkException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.func.ExceptionFunc;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.func.ResulteFunc;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextSubListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.subscribers.ProgressSubscriber;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http交互处理类
 * Created by WZG on 2016/7/16.
 */
public class HttpManager {
    /*软引用對象*/
    private SoftReference<HttpOnNextListener> onNextListener;
    private SoftReference<HttpOnNextSubListener> onNextSubListener;  // 链式返回
    private SoftReference<RxAppCompatActivity> appCompatActivity;

    public HttpManager(HttpOnNextListener onNextListener, RxAppCompatActivity appCompatActivity) {
        this.onNextListener = new SoftReference(onNextListener);
        this.appCompatActivity = new SoftReference(appCompatActivity);
    }

    public HttpManager(HttpOnNextSubListener onNextSubListener, RxAppCompatActivity appCompatActivity) {
        this.onNextSubListener = new SoftReference(onNextSubListener);
        this.appCompatActivity = new SoftReference(appCompatActivity);
    }

    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public void doHttpDeal(BaseApi basePar) {
        doHttpDeal(basePar, basePar.getBaseUrl());
    }
    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public void doHttpDeal(final BaseApi basePar, String url) {
        Retrofit retrofit = getReTrofit(basePar.getConnectionTime(), url);
        httpDeal(basePar.getObservable(retrofit), basePar);
    }
    /**
     * 获取Retrofit对象
     *
     * @param connectTime
     * @param baseUrl
     * @return
     */
    public Retrofit getReTrofit(int connectTime, String baseUrl) {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //  使用Interceptor来拦截并重新设置请求头 ?? 未测试是否可用
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request()
//                        .newBuilder()
//                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                        .addHeader("Accept-Encoding", "gzip, deflate")
//                        .addHeader("Connection", "keep-alive")
//                        .addHeader("Accept", "*/*")
//                        .addHeader("Cookie", "add cookies here")
//                        .build();
//                return chain.proceed(request);
//            }
//        });

        // HttpRequestInterceptor就是Http请求拦截器。Http消息发出前，对HttpRequest  request做些处理。比如加头啊
        // Http到达后，正式处理前，对HttpRequest  request做些处理。

        builder.connectTimeout(connectTime, TimeUnit.SECONDS);
        if (RxRetrofitApp.isDebug()) {
            builder.addInterceptor(getHttpLoggingInterceptor());
        }

        /*创建retrofit对象*/
        final Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit;
    }

    /**
     * RxRetrofit处理
     *
     * @param observable
     * @param basePar
     */
    public void httpDeal(Observable observable, BaseApi basePar) {
          /*失败后的retry配置*/
        observable = observable.retryWhen(new RetryWhenNetworkException(basePar.getRetryCount(),
                basePar.getRetryDelay(), basePar.getRetryIncreaseDelay()))
                /*异常处理*/
                .onErrorResumeNext(new ExceptionFunc())
                /*生命周期管理*/
                //.compose(appCompatActivity.get().bindToLifecycle())
                //Note:手动设置在activity onDestroy的时候取消订阅
                .compose(appCompatActivity.get().bindUntilEvent(ActivityEvent.DESTROY))
                /*返回数据统一判断*/
                .map(new ResulteFunc())
                /*http请求线程*/
                .subscribeOn(Schedulers.io())  // // 指定 subscribe() 发生在 IO 线程
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread());  // 指定 Subscriber 的回调发生在主线程

        /*ober回调，链接式返回*/
        if (onNextSubListener != null && null != onNextSubListener.get()) {
            onNextSubListener.get().onNext(observable, basePar.getMethod());
        }

        /*数据String回调*/
        if (onNextListener != null && null != onNextListener.get()) {
            ProgressSubscriber subscriber = new ProgressSubscriber(basePar, onNextListener, appCompatActivity);
            observable.subscribe(subscriber);
        }
    }

    /**
     * 日志输出
     * 自行判定是否添加
     *
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        /* 可以通过 setLevel 改变日志级别
        共包含四个级别：NONE、BASIC、HEADER、BODY

        NONE 不记录

        BASIC 请求/响应行
        --> POST /greeting HTTP/1.1 (3-byte body)
        <-- HTTP/1.1 200 OK (22ms, 6-byte body)

        HEADER 请求/响应行 + 头

        --> Host: example.com
        Content-Type: plain/text
        Content-Length: 3

        <-- HTTP/1.1 200 OK (22ms)
        Content-Type: plain/text
        Content-Length: 6

        BODY 请求/响应行 + 头 + 体
        */
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RxRetrofit", "Retrofit====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }


}
