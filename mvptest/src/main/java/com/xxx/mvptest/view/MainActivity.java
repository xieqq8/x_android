package com.xxx.mvptest.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xxx.mvptest.R;
import com.xxx.mvptest.bean.UserBean;
import com.xxx.mvptest.databinding.ActivityMainBinding;
import com.xxx.mvptest.presenter.UserPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IUserView {

// mvp 中 的presenter
//    UserPresenter presenter;
//    EditText id,first,last;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.save).setOnClickListener( this);
//        findViewById(R.id.load).setOnClickListener( this);
//        id = (EditText) findViewById(R.id.id);
//        first = (EditText) findViewById(R.id. first);
//        last = (EditText) findViewById(R.id. last);
//        presenter = new UserPresenter( this);


        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        UserBean user = new UserBean("Test", "User");
        binding.setNews(user);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.save:
//                presenter.saveUser(getID(), getFristName(), getLastName());  // 保存页面的数据
//                break;
            case R.id.load:
//                presenter.loadUser(getID()); // 刷新页面
                break;
            default:
                break;
        }
    }
    @Override
    public int getID() {
//        return new Integer( id.getText().toString());
        return 0;
    }

    @Override
    public String getFristName() {
//        return first.getText().toString();
        return null;
    }

    @Override
    public String getLastName() {
//        return last.getText().toString();
        return null;
    }

    @Override
    public void setFirstName(String firstName) {
//        first.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
//        last.setText(lastName);
    }


}
