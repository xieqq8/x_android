package com.wzgiceman.rxretrofitlibrary.retrofit_rx.http;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApiFragment;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.RetryWhenNetworkException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie.CookieInterceptor;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.subscribers.ProgressSubscriber;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http交互处理类
 * Created by WZG on 2016/7/16.
 */
public class HttpManager {
    private volatile static HttpManager INSTANCE;

    //构造方法私有
    private HttpManager() {
    }

    //获取单例
    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public void doHttpDeal(BaseApiFragment basePar) {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(basePar.getConnectionTime(), TimeUnit.SECONDS);
        builder.addInterceptor(new CookieInterceptor(basePar.isCache()));
        /*get缓存去掉无效逻辑*/
//        builder.addInterceptor(new CookieInterceptor(basePar.isCache()));     
//        builder.cache(new Cache(MyApplication.app.getCacheDir(),10*1024*1024));

        /*创建retrofit对象*/
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(basePar.getBaseUrl())
                .build();
        HttpService httpService = retrofit.create(HttpService.class);

        /*rx处理*/
        ProgressSubscriber subscriber = new ProgressSubscriber(basePar);
        Observable observable = basePar.getObservable(httpService)
                /*失败后的retry配置*/
                .retryWhen(new RetryWhenNetworkException())
                /*生命周期管理*/
                .compose(basePar.getRxAppCompatActivity().bindToLifecycle())
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*结果判断*/
                .map(basePar);

        /*数据回调*/
        observable.subscribe(subscriber);
    }
}
