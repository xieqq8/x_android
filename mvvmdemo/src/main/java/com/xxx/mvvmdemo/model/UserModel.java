package com.xxx.mvvmdemo.model;


/**
 * Created by kuaX on 2017/4/21.
 */

import com.xxx.mvvmdemo.bean.UserLoginBean;

/**
 * 数据层
 */
public class UserModel implements IUserModel{

    @Override
    public boolean login(UserLoginBean userLoginBean) {
        return false;
    }
}
