package com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;

/**
 * 成功回调处理
 * Created by WZG on 2016/7/16.
 */
public interface  HttpOnNextListener {
    /**
     * 成功后回调方法
     * @param result
     * @param mothead
     */
   void onNext(String result,String mothead);

    /**
     * 失败
     * 失败或者错误方法
     * 自定义异常处理
     * @param e
     */
    void onError(ApiException e);

//    /**
//     * 之后要加的这个
//     * 开始下载之前要执行的
//     */
//    void onStart();
//
//    /**
//     *
//     * 完成下载后要执行的
//     */
//    void onComplete();
}
