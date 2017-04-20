package view;

import bean.User;

/**
 * Created by kuaX on 2017/4/20.
 */

public interface IShowUserView {
    void showLoading();
    void hideLoading();
    void toMainActivity(User user);
    void showFailedError();
}
