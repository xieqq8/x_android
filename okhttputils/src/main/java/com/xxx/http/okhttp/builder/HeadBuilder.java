package com.xxx.http.okhttp.builder;

import com.xxx.http.okhttp.OkHttpUtils;
import com.xxx.http.okhttp.request.OtherRequest;
import com.xxx.http.okhttp.request.RequestCall;

/**
 * Created by xxx on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
