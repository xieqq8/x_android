package com.kuaxue.demo.netparse.entity;

import java.io.Serializable;

/**
 * 登录返回
 * Created by xieqq on 2015-09-29 .
 */
public class LoginBack implements Serializable {
    private int status = 0;
    private String Message = null;
    private String access_token=null;
    private String username=null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
