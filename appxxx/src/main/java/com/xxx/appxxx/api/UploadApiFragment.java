package com.xxx.appxxx.api;

import com.trello.rxlifecycle.components.support.RxFragment;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApiFragment;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.HttpService;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * 上传请求api
 * Created by WZG on 2016/10/20.
 */

public class UploadApiFragment<T> extends BaseApiFragment<T> {
    /*需要上传的文件*/
    private MultipartBody.Part part;


    public UploadApiFragment(HttpOnNextListener listener, RxFragment rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
        setShowProgress(true);
        setMothed("AppFiftyToneGraph/videoLink");
    }

    public MultipartBody.Part getPart() {
        return part;
    }

    public void setPart(MultipartBody.Part part) {
        this.part = part;
    }

    @Override
    public Observable getObservable(HttpService methods) {
        RequestBody uid= RequestBody.create(MediaType.parse("text/plain"), "4811420");
        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), "cfed6cc8caad0d79ea56d917376dc4df");
        return methods.uploadImage(uid,key,getPart());
    }

}
