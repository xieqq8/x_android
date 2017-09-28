package com.xxx.blogx.net;

/**
 * 网络解析要访问的URL
 *
 * @author xieqq
 * @code: create：2015-8-27 下午1:33
 */
public class HttpUrlConstant {

    // 接口地址
//    public static final String HOST = "http://192.168.1.35:8686/api/";
    public static final String HOST = "http://192.168.1.35/api/";


    //defined network response
    public static final String NETWORK_ERROR_DEFAULT = "网络异常，请稍后再试！";
    public static final String NETWORK_ERROR_TIMEOUT = "连接超时！";
    public static final String NETWORK_ERROR_SERVERERROR = "连接服务器失败！";

    public static final String getblogcatalog = HOST + "blog_catalog";

    public static final String getblog = HOST + "blog";

    // 时间校准
    public static final String servertime = HOST + "server_time";


}
