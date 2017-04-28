package com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;

/**
 * 成功回调处理
 * Created by WZG on 2016/7/16.
 */
public interface  HttpOnNextListener {
    // 修改 下 onNetStart开始  onNetSuccess成功  onNetError出错  onNetCompleted完成
    /**
     * 成功后回调方法
     * @param resulte
     * @param method
     */
   void onNext(String resulte,String method);

    /**
     * 失败
     * 失败或者错误方法
     * 自定义异常处理
     * @param e
     */
    void onError(ApiException e);


//    /**
//     * 调用完成后
//     * @param method
//     */
//    void onCompleted(String method);

}
