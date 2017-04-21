package model;

import bean.User;

/**
 * Created by kuaX on 2017/4/20.
 */

public class GetUserInfo implements IGetUser {
    @Override
    public void getUserInfo(final int id, final OnUserInfoListener listener) {
        // 模拟子线程耗时操作
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }                // 模拟信息获取成功
                if (id == 1) {
                    User user = new User();
                    user.setName("非著名程序员");
                    user.setAge("26");
                    user.setSex("男");
                    user.setId("1");
                    listener.getUserInfoSuccess(user);
                } else {
                    listener.getUserInfoFailed();
                }
            }
        }.start();
    }
}
