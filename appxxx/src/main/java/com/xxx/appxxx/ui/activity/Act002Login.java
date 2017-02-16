/**
 * copyright(C)2014-
 */
package com.xxx.appxxx.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.xxx.appxxx.R;
import com.xxx.base.BaseApcActivity;
import com.xxx.base.BaseApcActivityWithSwipeBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author xieqq
 * @code: create：14-7-3下午4:57
 * class: com.kuaxue.artspace.activity.Act002Login.Java
 */
public class Act002Login extends BaseApcActivityWithSwipeBack implements View.OnClickListener {
//public class Act002Login extends BaseApcActivity implements View.OnClickListener {
    private String TAG = "Login";
    private EditText nameET;
    private EditText pwdET;
    private Button btn_login;
    private TextView tv_title = null;

    private Button btn_right = null;

    private TextView tv_forget_pwd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStausBarBg(getResources().getColor(R.color.orange_r));

        // 为什么WithSwipeBack这个没有效果
//        StatusBarCompat.setStatusBarColor(this, Color.argb(0xff,0xec,0x69,0x41));   // 这样有效果
    }

    /**
     * 111111
     */
    @Override
    public void initContentView() {
        setContentView(R.layout.activity_login);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        mToolbar.setNavigationIcon(R.mipmap.title_back);//);

//        ff-81-ff-00
//        Color.argb(0xff,0x81,0xff,0x00);
//        StatusBarCompat.setStatusBarColor(this, Color.argb(0xff,0x81,0xff,0x00));
//        StatusBarCompat.setStatusBarColor(this, Color.argb(0xff,0xec,0x69,0x41));   // 这样有效果

//        // 返回
//        ImageView iv_left = (ImageView) findViewById(R.id.iv_left);
//        iv_left.setVisibility(View.VISIBLE);
//        iv_left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        tv_title = (TextView) findViewById(R.id.tv_title);
//        tv_title.setText("登录");
//
//        nameET = (EditText) findViewById(R.id.et_name);
//        pwdET = (EditText) findViewById(R.id.et_pwd);
//        btn_login = (Button) findViewById(R.id.btn_login);
//        btn_right = (Button) findViewById(R.id.btn_right);
//        btn_right.setText("注册");
//        btn_right.setVisibility(View.VISIBLE);
////        btn_right.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent i = new Intent();
////                i.setClass(mContext, Act003Register.class);
////                i.putExtra("b_reg", true);
////                startActivityForResult(i, 0x003);
////            }
////        });
//        nameET.addTextChangedListener(textWatcher);
//        pwdET.addTextChangedListener(textWatcher);
//        btn_login.setEnabled(false);
//        btn_login.setOnClickListener(this);
//
//        // 找回密码
//        addTVForgetview();
    }

    /**
     * 22222
     */
    @Override
    public void initView() {

    }

    /**
     * 3333333
     */
    @Override
    public void initPresenter() {

    }

    private void addTVForgetview() {
//        tv_forget_pwd = (TextView) findViewById(R.id.tv_forget_pwd);
//        tv_forget_pwd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent();
//                i.setClass(mContext, Act003Register.class);
//                i.putExtra("b_reg", false);
//                startActivityForResult(i, 0x004);
//            }
//        });
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
        btn_login.setEnabled(!TextUtils.isEmpty(nameET.getText().toString()) && !TextUtils.isEmpty(pwdET.getText().toString()));
    }

    private boolean checkData() {
//        String name = nameET.getText().toString().trim();
//        String pwd = pwdET.getText().toString().trim();
//        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
//            AlertUtil.showToast(getBaseContext(), getString(R.string.error_empty_txt));
//            return false;
//        }
//        if (!StringUtil.isMobile(nameET.getText().toString().trim())) {
//            AlertUtil.showToast(mContext, "请输入正确的手机号");
//            return false;
//        }
        return true;
    }

    private void login() {

//        //  登录
//        JSONObject jsonObject = new JSONObject();
//        try {
//            String Sha1md5 = MD5Util.Sha1MD5EncodeNoPhone(mContext);
//            jsonObject.put("signValue", Sha1md5);
//            jsonObject.put("phone", nameET.getText().toString().trim());
//            jsonObject.put("password", MD5Util.MD5(pwdET.getText().toString().trim()));
//            jsonObject.put("token",ConfigUtil.Instance().GetConfigValue(ConfigUtil.C_Local_Xg_token));
////            jsonObject.put("token","123456");
//            logx.d("Token = %s", ConfigUtil.Instance().GetConfigValue(ConfigUtil.C_Local_Xg_token));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
//        entity.setContentEncoding("UTF-8");
//        logx.d(HttpUrlConstant.user_login);
//        NetRestClient.Instance().client.post(mContext,
//                HttpUrlConstant.user_login, entity, "application/json", new TextHttpResponseHandler("UTF-8") {
//                    @Override
//                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                        if (s == null) {
//                            return;
//                        }
//                        try {
//                            JSONObject object = new JSONObject(s);
//                            AlertUtil.showToast(mContext, object.optString("msg"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onSuccess(int i, Header[] headers, String s) {
//                        try {
//                            JSONObject object = new JSONObject(s);
//                            if (object.optInt("code") == 200) {
//                                AlertUtil.showToast(mContext, object.optString("msg"));
//                                ConfigUtil.Instance().SetConfigValue(ConfigUtil.C_USER_ID, object.optString("userid"));
//                                ConfigUtil.Instance().SetConfigValue(ConfigUtil.C_USER_ROLE, object.optString("role"));
//                                ConfigUtil.Instance().SetConfigValue(ConfigUtil.C_USER_PASSWORD,MD5Util.MD5(pwdET.getText().toString().trim()));
//                                getUserInfo(object.optInt("userid"));
//
//                                finish();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
    }

    //  获取用户信息
    private void getUserInfo(final int userId) {
//        String Sha1md5 = MD5Util.Sha1MD5EncodeNoPhone(mContext);
//        String reqUrl = HttpUrlConstant.user_get_info + "/" + Sha1md5 + "/" + userId;
//        Log.d("reqUrl", reqUrl);
//        NetRestClient.Instance().client.get(mContext, reqUrl, null, new TextHttpResponseHandler("UTF-8") {
//            @Override
//            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                if (s == null) {
//                    return;
//                }
//                try {
//                    JSONObject object = new JSONObject(s);
//                    AlertUtil.showToast(mContext, object.optString("msg"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onSuccess(int i, Header[] headers, String s) {
//                try {
//                    if (s != null) {
//                        JSONObject object = new JSONObject(s);
//                        if (object.optInt("code") == 200) {
//                            JSONObject userInfo = object.optJSONObject("userinfo");
//                            SharedPreferences.Editor editor = PreferencesUtil.getSharedPreference(mContext).edit();
//                            editor.putString(ConfigUtil.C_USER_NAME, userInfo.optString("username"));
//                            editor.putString(ConfigUtil.C_USER_PHONE, userInfo.optString("phone"));
//                            editor.putString(ConfigUtil.C_USER_IMAGE, userInfo.optString("image"));
//                            editor.putString(ConfigUtil.C_REAL_NAME, userInfo.optString("name"));
//                            editor.putString(ConfigUtil.C_USER_SEX, userInfo.optString("sex"));
//                            editor.putString(ConfigUtil.C_USER_BIRTH, userInfo.optString("birthday"));
//                            editor.putString(ConfigUtil.C_USER_ADDRESS, userInfo.optString("address"));
//                            editor.putString(ConfigUtil.C_USER_PLRANGE, userInfo.optString("plrange"));
//                            editor.putString(ConfigUtil.C_USER_PRICE,userInfo.optString("qprice"));
//                            editor.putString(ConfigUtil.C_USER_SIGN, userInfo.optString("sign"));
//                            editor.putInt(ConfigUtil.C_USER_AUTH, userInfo.optInt("isauth"));
//                            editor.putString(ConfigUtil.C_USER_RANK, userInfo.optString("rank"));
//                            editor.putString(ConfigUtil.C_USER_ATTENTION, userInfo.optString("attention"));
//                            editor.putString(ConfigUtil.C_USER_FSNUM, userInfo.optString("fsnum"));
//                            editor.putString(ConfigUtil.C_USER_EVALUATE, userInfo.optString("evaluate"));
//                            editor.putString(ConfigUtil.C_USER_EMAIL,userInfo.optString("email"));
//                            editor.putString(ConfigUtil.C_USER_SCHOOL,userInfo.optString("school"));
//                            editor.putString(ConfigUtil.C_USER_COMPANY,userInfo.optString("company"));
//                            editor.putString(ConfigUtil.C_USER_OCCUPATION,userInfo.optString("occupation"));
//                            editor.putString(ConfigUtil.C_USER_BACKGROUND,userInfo.optString("background"));
//                            editor.putString(ConfigUtil.C_USER_PRAISE,userInfo.optString("praise"));
//
//                            JSONArray useropus = object.optJSONArray("useropus");
//                            editor.putString(ConfigUtil.C_USER_OPUS, useropus.toString());
//                            editor.commit();
//                            //通知各个页面登录成功，方便处理数据
//                            Intent intent = new Intent("LoginSuccess");
//                            sendBroadcast(intent);
//
//                            finish();
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_login:
//                if (checkData()) {
//                    // 键盘收起来
//                    InputMethodManager imm = (InputMethodManager) getSystemService(mContext.INPUT_METHOD_SERVICE);
//                    if (imm != null) {
//                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
//                    }
//                    logx.d("Token = %s", ConfigUtil.Instance().GetConfigValue(ConfigUtil.C_Local_Xg_token));
////                    if (ConfigUtil.Instance().GetConfigValue(ConfigUtil.C_Local_Xg_token).equals("0")){
////                        registerXG();
////                    }else{
//                        login();
////                    }
//
//                }
//                break;
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x003:
                    finish();
                    break;
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        InputMethodManager imm = (InputMethodManager) getSystemService(mContext.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
