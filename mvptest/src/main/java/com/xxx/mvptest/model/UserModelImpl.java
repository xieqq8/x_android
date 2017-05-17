package com.xxx.mvptest.model;

import com.xxx.mvptest.bean.UserBean;

/**
 * Created by kuaX on 2017/4/21.
 */

/**
 * 数据层
 */
public class UserModelImpl implements IUserModel{
    @Override
    public void setID(int id) {

    }

    @Override
    public void setFirstName(String firstName) {

    }

    @Override
    public void setLastName(String lastName) {

    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public UserBean load(int id) {
        // 根据ID查找
        UserBean userBean= new UserBean("firstName", "lastName");
        return userBean;
    }
}
