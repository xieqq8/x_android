package presenter;


import android.os.Handler;

import bean.User;
import model.GetUserInfo;
import model.IGetUser;
import model.OnUserInfoListener;
import view.IShowUserView;

/**
 * Created by kuaX on 2017/4/20.
 */

public class UserInfoPresenter {
    private IGetUser iGetUser;
    private IShowUserView iShowUserView;
    private Handler mHandler = new Handler();

    public UserInfoPresenter(IShowUserView iShowUserView) {
        this.iShowUserView = iShowUserView;
        this.iGetUser = new GetUserInfo();
    }

    public void getUserInfoToShow(int id) {
        iShowUserView.showLoading();
        iGetUser.getUserInfo(id, new OnUserInfoListener() {
            @Override
            public void getUserInfoSuccess(final User user) {
                // 需要在UI线程执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iShowUserView.toMainActivity(user);
                        iShowUserView.hideLoading();
                    }
                });
            }

            @Override
            public void getUserInfoFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iShowUserView.showFailedError();
                        iShowUserView.hideLoading();
                    }
                });
            }
        });
    }
}
