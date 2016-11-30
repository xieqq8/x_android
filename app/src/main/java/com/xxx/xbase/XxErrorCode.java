package com.xxx.xbase;

/**
 * 错误类型
 * XxErrorCode.NETWORK_ERROR.Value()
 * Created by xieqq on 2016-06-30 .
 */
public enum XxErrorCode {
    NETWORK_ERROR(-10),
    PROCESS_FAIL(-11),
    INVALID_REQUEST(-12);

    private int nErrorCode;

    private XxErrorCode(int var3) {
        this.nErrorCode = var3;
    }

    public int Value() {
        return this.nErrorCode;
    }
}
