package com.kuaxue.demo.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xxx.fudao.R;
import com.kuaxue.demo.netparse.HttpUrlConstant;
import com.xxx.xbase.BaseActivityWithSlideBack;

/**
 * 用户协议
 * Created by xieqq on 2015-10-15 .
 */
public class Act411UserAgreement extends BaseActivityWithSlideBack {

    private WebView wv_content;
    private ProgressBar pb_loading;
    private TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_argeement);

        // 标题设置
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.self_about_argeement));

        // 返回
        addTitleBtnBack();

        // 内容设置
        wv_content = (WebView) findViewById(R.id.wv_content);

        // 进度条
        pb_loading = (ProgressBar)findViewById(R.id.pb_loading);

        addWebView();
    }

    private void addWebView() {
        wv_content.loadUrl(HttpUrlConstant.USE_ARGEEMENT);
//        wv_content.loadData(mzyb.questionContent, "text/html; charset=UTF-8", "");

        wv_content.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // 设置标题
                tv_title.setText(getResources().getString(R.string.self_about_argeement));
                pb_loading.setVisibility(View.VISIBLE); // 显示等待圈
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb_loading.setVisibility(View.GONE);    // 隐藏等待圈
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 加载失败
                Toast.makeText(mContext, "加载失败，请稍候再试", Toast.LENGTH_SHORT)
                        .show();
                tv_title.setText(getResources().getString(R.string.eror_no_web));

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }
        });
// 这个可以查看进度
//        wv_content.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//            }
//        });
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
