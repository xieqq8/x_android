package com.xxx.mvptest.model;

import com.xxx.mvptest.bean.UserBean;

/**
 * Created by kuaX on 2017/4/21.
 */

public interface IUserModel {
    void setID(int id);

    void setFirstName(String firstName);

    void setLastName(String lastName);

    int getID();

    UserBean load(int id);// 通过id读取user信息,返回一个UserBean
}
