package com.xxx.http.okhttp.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by xxx on 15/12/14.
 */
public abstract class StringCallback extends Callback<String>
{
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException
    {
        return response.body().string();
    }
}
