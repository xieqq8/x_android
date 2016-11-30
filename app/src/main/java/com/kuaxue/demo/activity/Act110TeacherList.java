package com.kuaxue.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.xxx.fudao.R;
import com.kuaxue.demo.netparse.NetRestClient;
import com.xxx.xbase.BaseActivityWithSlideBack;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 老师列表 / 课程列表示例
 */
public class Act110TeacherList extends BaseActivityWithSlideBack {
    //
    // http://api.laoshibang.kuaxue.com/question/recommendation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);
        AddBtnGet();
        AddBtnPost();
        AddBtnPut();
    }

    /**
     * put测试
     */
    private void AddBtnPut(){
        Button btnPut = (Button)findViewById(R.id.btnPut);
        btnPut.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                String url = "http://api.laoshibang.kuaxue.com/user/password";
                RequestParams params = new RequestParams();
                params.put("password", "123123");
                params.put("newPassword", "123123");

                NetRestClient.Instance().client.put(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        Toast.makeText(mContext, "Put onFailure:" + s, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    /**
     * post测试
     */
    private void AddBtnPost(){
        Button btnPost = (Button)findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {

                String url = "http://api.laoshibang.kuaxue.com/login";
                RequestParams params = new RequestParams();
                // {"username":"13439326862","password":"123123","identify":"student"}
                params.put("username", "13439326862");
                params.put("password", "123123");
                params.put("identify", "student");
                NetRestClient.Instance().client.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        Toast.makeText(mContext, "post onStart", Toast.LENGTH_LONG).show();
                        super.onStart();
                    }
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        logx.d(s);
                        Toast.makeText(mContext, "post onFailure:"+s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
                        String token = "";
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            token = jsonObject.getString("access_token");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        NetRestClient.Instance().client.addHeader("Authorization", "Bearer " + token);

                    }
                });
            }
        });
    }
    private void AddBtnGet() {
        Button btnGet = (Button) findViewById(R.id.btnGet);
        btnGet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String url1 = "http://api.laoshibang.kuaxue.com/question/recommendation";
                NetRestClient.Instance().client.get(url1, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        Toast.makeText(mContext, "onFailure:" + s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
//                        logx.d(s);  // 日志打出来异常
                        Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStart() {
                        Toast.makeText(mContext, "get onStart", Toast.LENGTH_LONG).show();
                        super.onStart();
                    }

                    @Override
                    public void onProgress(int bytesWritten, int totalSize) {
                        super.onProgress(bytesWritten, totalSize);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                    @Override
                    public void onPreProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                        super.onPreProcessResponse(instance, response);
                    }

                    @Override
                    public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                        super.onPostProcessResponse(instance, response);
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        super.onRetry(retryNo);
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                    }
                });
            }
        });

    }
}
