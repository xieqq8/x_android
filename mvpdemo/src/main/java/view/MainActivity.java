package view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xxx.mvpdemo.R;

import bean.User;
import model.GetUserInfo;
import model.IGetUser;
import presenter.UserInfoPresenter;
import view.base.BaseMvpActivity;

/**
 * mvptest http://www.cnblogs.com/wytiger/p/5305087.html
 * mvpdemo http://mp.weixin.qq.com/s?__biz=MjM5NDkxMTgyNw==&mid=404577331&idx=1&sn=e92bf72c490d728470378e4ee1953a80&scene=21#wechat_redirect
 *
 * 如何更高效的使用MVP以及官方MVP架构解析 http://blog.csdn.net/dantestones/article/details/51445208
 */
public class MainActivity extends BaseMvpActivity<IShowUserView,UserInfoPresenter> implements IShowUserView{

    private Button btn;
    private TextView name_tv, age_tv, sex_tv;
    private ProgressDialog pd = null;

//    private UserInfoPresenter userInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        userInfoPresenter = new UserInfoPresenter();
        btn = (Button) findViewById(R.id.btn);
        name_tv = (TextView) findViewById(R.id.name_tv);
        age_tv = (TextView) findViewById(R.id.age_tv);
        sex_tv = (TextView) findViewById(R.id.sex_tv);
        pd = new ProgressDialog(this);
        pd.setMessage("正在加载……");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getUserInfoToShow(1); // 设置数据
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                IGetUser iGetUser = new GetUserInfo();
//                iGetUser.getUserInfo(1 ,this);
                presenter.getUserInfoToShow(2);
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        userInfoPresenter.attach(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        userInfoPresenter.dettach();
//        super.onDestroy();
//    }

    @Override
    public UserInfoPresenter initPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    public void showLoading() {
        pd.show(); // 加载中
    }

    @Override
    public void hideLoading() {
        pd.cancel(); // 加载完成
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
