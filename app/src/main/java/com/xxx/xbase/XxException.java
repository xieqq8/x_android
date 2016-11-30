package com.xxx.xbase;

/**
 * Created by xieqq on 2016-06-30 .
 */
public class XxException extends Exception {
    private XxErrorCode errorCode;
    private String strErrMsg;

    public XxErrorCode getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.strErrMsg;
    }

    /**
     *
     * @param msg
     */
    public XxException(String msg) {
        super(msg);
    }

    public XxException(XxErrorCode code, String... msg) {
        this.errorCode = code;
        if(msg != null && msg.length > 0) {
            StringBuffer var3 = new StringBuffer();
            String[] var4 = msg;
            int var5 = msg.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String var7 = var4[var6];
                var3.append(var7).append(" ");
            }

            this.strErrMsg = var3.toString();
        }

    }

    /**
     * 检查型异常checked
     *
     * @param message
     * @param cause
     */
    public XxException(String message, Throwable cause) {
        super(message, cause);
    }
}
