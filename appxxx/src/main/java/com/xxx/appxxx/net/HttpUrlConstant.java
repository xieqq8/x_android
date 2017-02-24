package com.xxx.appxxx.net;

/**
 * 网络解析要访问的URL
 *
 * @author xieqq
 * @code: create：2015-8-27 下午1:33
 */
public class HttpUrlConstant {
    // 时间校准
    public static final String servertime = "https://api.kuaxue.com/ParentClient/server_time";

    // 接口地址
    public static final String HOST = "https://www.meishuroom.com/Api/";


    //defined network response
    public static final String NETWORK_ERROR_DEFAULT = "网络异常，请稍后再试！";
    public static final String NETWORK_ERROR_TIMEOUT = "连接超时！";
    public static final String NETWORK_ERROR_SERVERERROR = "连接服务器失败！";


}
