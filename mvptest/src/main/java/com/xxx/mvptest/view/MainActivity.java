package com.xxx.mvptest.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xxx.mvptest.R;
import com.xxx.mvptest.model.UserModelImpl;
import com.xxx.mvptest.presenter.UserPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IUserView {

// mvp 中 的presenter
    UserPresenter presenter;
    EditText id,first,last;

//    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.save).setOnClickListener( this);
        findViewById(R.id.load).setOnClickListener( this);
        id = (EditText) findViewById(R.id.id);
        first = (EditText) findViewById(R.id. first);
        last = (EditText) findViewById(R.id. last);
        presenter = new UserPresenter( this);


//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//
//        UserBean user = new UserBean("Test", "User");
//        binding.setNews(user);
//        UserBean userBean = binding.getNews(); // 取数，不用 findviewById


        // fragment
//        final View root = inflater.inflate(R.layout.addtask_frag, container, false);
//        binding = ActivityMainBinding.bind(root);
//        binding.getRoot(); // fragment
    }

//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        com.sunzxyong.binding.databinding.ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        final User user = new User("Sunzxyong", "12345678");
//        mBinding.setUser(user);
//        mBinding.btn.setOnClickListener(new android.view.View.OnClickListener() {
//            @Override
//            public void onClick(android.view.View v) {
//                user.setUserName("Hello");
//                user.setUserPassword("87654321");
//            }
//        });
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.save:
//                presenter.saveUser(getID(), getmFristName(), getmLastName());  // 保存页面的数据
//                break;
            case R.id.load:
                UserModelImpl userModel = new UserModelImpl();
//               binding.setNews(userModel.load(getID()));  // mv vm
                presenter.loadUser(getID()); // 刷新页面  mvp
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
    public String getmFristName() {
//        return first.getText().toString();
        return null;
    }

    @Override
    public String getmLastName() {
//        return last.getText().toString();
        return null;
    }

    @Override
    public void setmFirstName(String firstName) {
//        first.setText(firstName);
    }

    @Override
    public void setmLastName(String lastName) {
//        last.setText(lastName);
    }


}
