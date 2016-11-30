package com.kuaxue.demo.netinter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kuaxue.database.ConfigUtil;
import com.xxx.fudao.R;
import com.kuaxue.demo.netparse.HttpUrlConstant;
import com.kuaxue.demo.netparse.entity.LoginBack;
import com.kuaxue.demo.netparse.parse.LoginParser;
import com.xxx.utils.AlertUtil;
import com.xxx.utils.LogX;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;

/**
 * Created by xieqq on 2015-09-29 .
 */
public class LoginUtil {

    private EditText etName;
    private EditText etPwd;
    private View btnLogin;
    private TextView tv_error;
    private View view;
    private AsyncHttpClient client;
    private Context mContext;
    private Activity mActivity;

    private IloginCallback callback;//登录的回调接口

    public LoginUtil(View view, AsyncHttpClient client, Context context, Activity activity, IloginCallback callback){
        this.view = view;
        this.client = client;
        this.mContext = context;
        this.mActivity = activity;
        this.callback = callback;
        // 登录
        tv_error = (TextView) view.findViewById(R.id.tv_error);
        btnLogin = view.findViewById(R.id.btn_login);
        etName = (EditText) view.findViewById(R.id.et_name);
        etPwd = (EditText) view.findViewById(R.id.et_pwd);
        etName.addTextChangedListener(textWatcher);
        etPwd.addTextChangedListener(textWatcher);
        // 登录点击处理
        execLogin();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkInput();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void checkInput() {
        btnLogin.setEnabled(!TextUtils.isEmpty(etName.getText().toString()) && !TextUtils.isEmpty(etPwd.getText().toString()));
    }

    /**
     * 登录点击处理
     */
    private void execLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();

                RequestParams params = new RequestParams();
                params.put("username", name);
                params.put("password", pwd);
                params.put("identify", "student");
                client.post(HttpUrlConstant.LOGIN, params, new TextHttpResponseHandler() {
                    AlertUtil.TitleProgressBar pbV;

                    @Override
                    public void onStart() {
                        LogX.getLogger().d("onstart");
                        pbV = AlertUtil.showProgressBar(view.findViewById(R.id.menu_title), mActivity, "登录中...");
                        tv_error.setText("");
                        super.onStart();
                    }

                    @Override
                    public void onFinish() {
                        LogX.getLogger().d("onFinish");
                        AlertUtil.hideProgressBar(pbV, mActivity, null);

                        super.onFinish();
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        // 失败
                        LogX.getLogger().d("onFailure:" + s);
                        LoginParser lp = new LoginParser();
                        try {
                            LoginBack response = lp.parse(s);
//                            AlertUtil.showToast(mContext,response.getMessage());
                            tv_error.setText(response.getMessage());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                        }
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        LogX.getLogger().d("onSuccess:" + s);
                        LoginParser lp = new LoginParser();
                        try {
                            LoginBack response = lp.parse(s);
                            // 成功
                            ConfigUtil.Instance().SetConfigValue(ConfigUtil.C_ACCESS_TOKEN, response.getAccess_token());

                            callback.loginSuccess();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                        }
                    }


                });
            }
        });
    }


//    @Override
//    public int login(String username, String pass) {
//        return 0;
//    }
//
//    @Override
//    public int logout(){
//        return 0;
//    }
//
//    @Override
//    public int changPwd(){
//        return 0;
//    }
}
