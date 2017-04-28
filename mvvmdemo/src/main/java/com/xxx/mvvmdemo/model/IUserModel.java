package com.xxx.mvvmdemo.model;


import com.xxx.mvvmdemo.bean.UserLoginBean;

/**
 * Created by kuaX on 2017/4/21.
 */

public interface IUserModel {
//    void setID(int id);
//
//    void setFirstName(String firstName);
//
//    void setPassword(String password);
//
//    int getID();

    /**
     *
     * @param userLoginBean
     * @return
     */
    boolean login(UserLoginBean userLoginBean);
}
