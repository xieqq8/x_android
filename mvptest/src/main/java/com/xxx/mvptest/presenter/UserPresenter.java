package com.xxx.mvptest.presenter;

import com.xxx.mvptest.bean.UserBean;
import com.xxx.mvptest.model.IUserModel;
import com.xxx.mvptest.model.UserModel;
import com.xxx.mvptest.view.IUserView;

/**
 * Created by kuaX on 2017/4/21.
 */

public class UserPresenter {
    private IUserView mUserView;
    private IUserModel mUserModel;

    public UserPresenter(IUserView view) {
        mUserView = view;
        mUserModel = new UserModel();
    }

    public void saveUser( int id, String firstName, String lastName) {
        mUserModel.setID(id);
        mUserModel.setFirstName(firstName);
        mUserModel.setLastName(lastName);


    }

    public void loadUser( int id) {
        UserBean user = mUserModel.load(id);
        mUserView.setFirstName(user.getmFirstName()); // 通过调用IUserView的方法来更新显示
        mUserView.setLastName(user.getmLastName());
    }
}
