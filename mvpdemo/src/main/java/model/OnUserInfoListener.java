package model;

import bean.User;

/**
 * Created by kuaX on 2017/4/20.
 */

public interface OnUserInfoListener {
    void getUserInfoSuccess(User user);

    void getUserInfoFailed();
}
