package com.xxx.http.okhttp.builder;

import java.util.Map;

/**
 * Created by xxx on 16/3/1.
 */
public interface HasParamsable
{
    OkHttpRequestBuilder params(Map<String, String> params);
    OkHttpRequestBuilder addParams(String key, String val);
}
