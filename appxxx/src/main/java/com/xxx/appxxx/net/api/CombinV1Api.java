package com.xxx.appxxx.net.api;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.HttpManagerApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextSubListener;
import com.xxx.appxxx.net.HttpPostGetService;

/**
 * 多api共存方案
 * Created by WZG on 2017/4/13.
 */

public class CombinV1Api extends HttpManagerApi {

    public CombinV1Api(HttpOnNextListener onNextListener, RxAppCompatActivity appCompatActivity) {
        super(onNextListener, appCompatActivity);
        /*统一设置*/
        setCache(true);

        setShowProgress(false); // 设置进度框
    }

    public CombinV1Api(HttpOnNextSubListener onNextSubListener, RxAppCompatActivity appCompatActivity) {
        super(onNextSubListener, appCompatActivity);
        /*统一设置*/
        setCache(true);
    }


    /**
     *
     */
    public void postApiServertime() {
        /*也可单独设置请求，会覆盖统一设置*/
        setCache(false);
        setMethod("server_time");
        HttpPostGetService httpService = getRetrofit().create(HttpPostGetService.class);
        doHttpDeal(httpService.getServerTime());
    }

    /**
     * post请求演示
     * api-2
     *
     * @param all 参数
     */
    public void postApiOther(boolean all) {
        setCache(true);
        setMethod("AppFiftyToneGraph/videoLink");
        HttpPostGetService httpService = getRetrofit().create(HttpPostGetService.class);
        doHttpDeal(httpService.getAllVedioBy(all));
    }

}
