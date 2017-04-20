package com.xxx.mvpdemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import bean.User;
import presenter.UserInfoPresenter;
import view.IShowUserView;

/**
 * mvptest http://www.cnblogs.com/wytiger/p/5305087.html
 * mvpdemo http://mp.weixin.qq.com/s?__biz=MjM5NDkxMTgyNw==&mid=404577331&idx=1&sn=e92bf72c490d728470378e4ee1953a80&scene=21#wechat_redirect
 *
 */
public class MainActivity extends AppCompatActivity implements IShowUserView {

    private Button btn;
    private TextView name_tv, age_tv, sex_tv;
    private ProgressDialog pd = null;

    private UserInfoPresenter userInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInfoPresenter = new UserInfoPresenter(this);
        btn = (Button) findViewById(R.id.btn);
        name_tv = (TextView) findViewById(R.id.name_tv);
        age_tv = (TextView) findViewById(R.id.age_tv);
        sex_tv = (TextView) findViewById(R.id.sex_tv);
        pd = new ProgressDialog(this);
        pd.setMessage("正在加载……");

        btn.setOnClickListener(new View.OnClickListener() {            @Override
        public void onClick(View v) {
            userInfoPresenter.getUserInfoToShow(1);
        }
        });

        Button btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfoPresenter.getUserInfoToShow(2);
            }
        });
    }

    @Override
    public void showLoading() {
        pd.show();
    }

    @Override
    public void hideLoading() {
        pd.cancel();
    }

    @Override
    public void toMainActivity(User user) {
        name_tv.setText(user.getName());
        age_tv.setText(user.getAge());
        sex_tv.setText(user.getSex());
    }

    @Override
    public void showFailedError() {
        Toast.makeText(this, "获取信息有误", Toast.LENGTH_SHORT).show();
    }
}
