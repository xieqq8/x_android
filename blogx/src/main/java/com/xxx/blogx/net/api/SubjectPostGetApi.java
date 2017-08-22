package com.xxx.blogx.net.api;


import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.xxx.blogx.net.HttpPostGetService;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 测试数据
 * Created by xie on 2016/7/16.
 */
public class SubjectPostGetApi extends BaseApi {
    //    接口需要传入的参数 可自定义不同类型
    private boolean all;
    /*任何你先要传递的参数*/
//    String xxxxx;
//    String xxxxx;
//    String xxxxx;
//    String xxxxx;


    /**
     * 默认初始化需要给定回调和rx周期类
     * 可以额外设置请求设置加载框显示，回调等（可扩展）
     * 设置可查看BaseApi
     */
    public SubjectPostGetApi() {
//        setMethod("AppFiftyToneGraph/videoLink");
    }


    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostGetService httpService = retrofit.create(HttpPostGetService.class);
        return httpService.getAllVedioBy(isAll());
    }

}
