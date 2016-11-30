package com.kuaxue.demo.netparse;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.loopj.android.http.AsyncHttpClient;

/**
 * 加上Context参数，以在Activity pause或stop时取消掉没用的请求。
 * Created by xieqq on 2015-10-09 .
 */
public class NetRestClient{
    public  AsyncHttpClient client = null;
    private static NetRestClient inst;
    private Context appContext = null;

    private NetRestClient() {
        client = new AsyncHttpClient();
    }

    public static synchronized NetRestClient Instance() {
        if (inst == null) {
            inst = new NetRestClient();
        }
        return inst;
    }
    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    public void AddHeader(String param, String value){
//        client.addHeader("Authorization", "Bearer " + token);
        client.addHeader(param, value);
    }

    /**
     * 请求头设置
     */
    public void HeaderConfig(){
        try {
            client.addHeader("Accept", "application/json");

            TelephonyManager tm = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
            client.addHeader("imei", tm.getDeviceId());
            client.addHeader("imsi", tm.getSimSerialNumber());

            client.addHeader("os-type", "android");
            client.addHeader("os-version", Build.VERSION.RELEASE);
            client.addHeader("device-model", Build.MODEL);
            client.addHeader("device-sn", Build.SERIAL);
            client.setTimeout(2 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
