package com.xxx.blogx.net.api;

import android.content.Context;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.xxx.blogx.net.HttpPostGetService;
import com.xxx.utils.MD5Util;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 获取分类接口
 * Created by xie on 2017/2/25.
 */
public class GetTypeApi extends BaseApi {

    /**
     * 默认初始化需要给定回调和rx周期类
     * 可以额外设置请求设置加载框显示，回调等（可扩展）
     * 设置可查看BaseApi
     */
    public GetTypeApi() {
        setShowProgress(false); // 加载框
        setMethod("getType");
        setCache(false);
    }

    public GetTypeApi(Context context) {
        setShowProgress(false);
        setMethod("getType");
        setCache(false);
        setmContext(context);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostGetService httpService = retrofit.create(HttpPostGetService.class);
        String Sha1md5 = MD5Util.Sha1MD5EncodeNoPhone(getmContext());
        return httpService.getType(Sha1md5);
    }
}
