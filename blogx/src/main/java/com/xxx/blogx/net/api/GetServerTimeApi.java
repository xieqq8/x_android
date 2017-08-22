package com.xxx.blogx.net.api;


import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.xxx.blogx.net.HttpPostGetService;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 得到服务器时间
 * Created by xie on 2017/2/25.
 */
public class GetServerTimeApi extends BaseApi {

    /**
     * 默认初始化需要给定回调和rx周期类
     * 可以额外设置请求设置加载框显示，回调等（可扩展）
     * 设置可查看BaseApi
     */
    public GetServerTimeApi() {
        setShowProgress(false);
        setMethod("server_time");
        setCache(true);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostGetService httpService = retrofit.create(HttpPostGetService.class);
        return httpService.getServerTime();
    }
}
