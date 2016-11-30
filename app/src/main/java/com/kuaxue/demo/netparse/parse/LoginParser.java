package com.kuaxue.demo.netparse.parse;

import com.kuaxue.demo.netparse.entity.LoginBack;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {"status":500,"message":"用户不存在"}
 * {"status":400,"message":"密码错误","verifyCode":false}
 * {"status":200,"message":"登录成功","access_token":"225a3666-ae4a-4c69-b1a7-2ce0f5d60cc7","username":"13439326862"}
 * Created by xieqq on 2015-09-29 .
 */
public class LoginParser {

    public LoginBack parse(String response) throws JSONException {
        LoginBack bean = new LoginBack();
        String[] v = new String[2];
        try {
            JSONObject js = new JSONObject(response);
            bean.setStatus(js.optInt("status"));
            bean.setMessage(js.optString("message"));
            bean.setAccess_token(js.optString("access_token"));
            bean.setUsername(js.optString("username"));

        } catch (JSONException e) {
            e.printStackTrace();
//            throw new Exception("登录失败");
        }

        return bean;
    }
}
