/**
 *
 * copyright(C)2014- 
 *
 */
package com.kuaxue.demo.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.xxx.fudao.R;
import com.xxx.xbase.BaseActivityWithSlideBack;

/**
 * @author jixiaolong
 * @code: create：14-7-3下午4:57
 * class: com.kuaxue.laoshibang.ui.activity.LoginActivity.Java
 */
public class Act002Login extends BaseActivityWithSlideBack implements View.OnClickListener{
    private EditText nameET;
    private EditText pwdET;
    private View loginBtn;
    private View menu_title = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView(){
        menu_title = findViewById(R.id.menu_title);
        // 返回
        menu_title.setVisibility(View.VISIBLE);
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

    private void checkInput(){
        loginBtn.setEnabled(!TextUtils.isEmpty(nameET.getText().toString())&&!TextUtils.isEmpty(pwdET.getText().toString()));
    }

    @Override
    public void onClick(final View v) {
//        switch (v.getId()){
//            case R.id.btn_login:
//                if(checkData()){
//                    final String name = nameET.getText().toString().trim();
//                    RequestParameter rp = RequestParameter.build(HTTPURL.LOGIN);
//                    rp.put("username",name);
//                    rp.put("identify","student");
//                    rp.put("password",pwdET.getText().toString().trim());
//                    NetCenter.Instance(LoginActivity.this).post(rp,new ResponseHandler<String[]>(LoginActivity.this) {
//                        AlertUtil.TitleProgressBar pbV;
//                        @Override
//                        public void onPreRequest(RequestParameter requestParameter) {
//                            v.setEnabled(false);
//                            pbV = AlertUtil.showProgressBar(findViewById(R.id.rl_content),LoginActivity.this,"正在登录...");
//
//                        }
//
//                        @Override
//                        public String[] parser(String response) throws Exception {
//                            return new LoginParser().parse(response);
//                        }
//
//                        @Override
//                        public void onSuccess(RequestParameter requestParameter, String[] response) {
//                            if(LoginActivity.this.getBaseContext() != null){
//                                AlertUtil.hideProgressBar(pbV, LoginActivity.this, "登录成功");
//                                SharedPreferences.Editor editor = PreferencesUtil.getSharedPreference(LoginActivity.this).edit();
//                                editor.putString(PreferencesUtil.PREF_KEY_USERNAME,response[1])
//                                        .putString(PreferencesUtil.PREF_KEY_ACCESS_TOKEN,response[0])
//                                        .apply();
//                                Log.e("", "set token:" + response[0]);
//                                NetCenter.Instance(LoginActivity.this).initToken(response[0]);
//                                ClientConfig cc = new ClientConfig();
//                                cc.setUserName(response[1]);
//                                cc.setPwd(response[0]);
//                                cc.save(LoginActivity.this);
//                                startService(new Intent(LoginActivity.this, XMPPService.class));
//                                // 信鸽注册
//                                XGPushManager.registerPush(getApplicationContext(),response[1]);
//                                finish();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(RequestParameter arg0, Exception arg1) {
//                            super.onFailure(arg0,arg1);
//                            if(LoginActivity.this.getBaseContext() != null){
//                                if(!isIntercept(arg1)){
//                                    AlertUtil.showToast(LoginActivity.this, BaseParser.parseHTTPException(arg1));
//                                }
//                                AlertUtil.hideProgressBar(pbV, LoginActivity.this, null);
//                            }
//                        }
//
//                        @Override
//                        public void onPostRequest(RequestParameter arg0) {
//                            if(LoginActivity.this.getBaseContext() != null){
//                                v.setEnabled(true);
//                            }
//                            pbV = null;
//                        }
//                    });
//                }
//                break;
//            case R.id.tv_forget_pwd:
//                startActivity(new Intent(LoginActivity.this,ForgetPWDActivity.class));
//                break;
//        }
    }

    private boolean checkData(){
//        String name = nameET.getText().toString().trim();
//        String pwd = pwdET.getText().toString().trim();
//        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)){
//            AlertUtil.showToast(getBaseContext(),getString(R.string.error_empty_txt));
//            return false;
//        }
        return true;
    }
}
