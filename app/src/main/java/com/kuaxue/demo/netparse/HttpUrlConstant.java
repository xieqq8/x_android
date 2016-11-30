package com.kuaxue.demo.netparse;

/**
 * 网络解析要访问的URL
 * @author xieqq
 * @code: create：2015-8-27 下午1:33
 */
public class HttpUrlConstant {
    public static final String HOST = "http://api.laoshibang.kuaxue.com/";
    //defined network response
    public static final String NETWORK_ERROR_DEFAULT = "网络异常，请稍后再试！";
    public static final String NETWORK_ERROR_TIMEOUT = "连接超时！";
    public static final String NETWORK_ERROR_SERVERERROR = "连接服务器失败！";

    // 登录
    public static final String LOGIN = HOST+"login";

    // 问题
    public static final String QUESTION = HOST + "question";
    // 问题列表
    public static final String QUESTION_RECOMMEND = QUESTION + "/recommendation";
    // 老师
    public static final String TEACHERS_ONLINE = HOST + "teachers";
    public static final String TEACHERS_FOLLOW = TEACHERS_ONLINE + "/following";
    public static final String TEACHERS_RECENT = TEACHERS_ONLINE + "/recent";

    // 用户协议
    public static final String USE_ARGEEMENT = HOST+"m/privacy";

    // 系统消息
    public static final String SYS_MSG = HOST + "user/messages/system"; // {id}
    public static final String ASK_MSG = HOST + "user/messages/qa";

    public static final String UPDATE = HOST + "qa/client/android/app-update";

}
