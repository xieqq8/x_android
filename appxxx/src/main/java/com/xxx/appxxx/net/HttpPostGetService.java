package com.xxx.appxxx.net;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 测试接口service-post相关
 * Created by WZG on 2016/12/19.
 */

public interface HttpPostGetService {


    @GET("AppFiftyToneGraph/videoLink/{once_no}")
    Observable<String> getAllVedioBy(@Query("once_no") boolean once_no);

//    @FormUrlEncoded
    //    @POST("AppFiftyToneGraph/videoLink")
//    Observable<String> getAllVedioBy(@Field("once_no") boolean once_no);

    /**
     * 服务器时间获取接口  这个前面的头不一样 http://api.kuaxue.cn/ParentClient/server_time
     * @return
     */
    @GET("server_time")
    Observable<String> getServerTime();

    /**
     *  获取分类接口
     * @return
     */
    @GET("getType")
    Observable<String> getType(@Query("sign") String token);

    /**
     *
     * @param token
     * @param username  会员手机号
     * @param cid       课程ID
     * @param score     分数
     * @param content   评论内容
     * @return
     */
    @POST("courseCommend")
    Observable<String> courseCommend(@Field("sign") String token, @Field("username") String username
            , @Field("cid") String cid
            , @Field("score") String score
            , @Field("content") String content);
}
