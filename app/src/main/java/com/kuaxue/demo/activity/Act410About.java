package com.kuaxue.demo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xxx.fudao.R;
import com.xxx.utils.update.UpdateManager;
import com.xxx.xbase.BaseActivityWithSlideBack;

/**
 * 关于
 * Created by xieqq on 2015-10-15 .
 */
public class Act410About extends BaseActivityWithSlideBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initTitle();
    }

    private void initTitle() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.self_about));
        TextView version_name_tv = (TextView) findViewById(R.id.version_name_tv);

        version_name_tv.setText("XXX " + UpdateManager.getVersionName(mContext) + " 版");
        // 标题返回
        addTitleBtnBack();
        // 用户协议
        addBtnAgreement();
        // 用户评价
        addTvPingjia();
        // 用户分享
        addTvShare();
    }

    /**
     * 用户协议
     */
    private void addBtnAgreement() {
        TextView use_agreement_tv = (TextView) findViewById(R.id.use_agreement_tv);
        use_agreement_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, Act411UserAgreement.class);
                startActivity(i);
            }
        });

    }

    /**
     * 用户评价
     */
    private void addTvPingjia() {
        TextView use_pingjia = (TextView) findViewById(R.id.use_pingjia);
        use_pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(mContext,mContext.getString(R.string.self_about_pingjia_none), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 用户分享
     * 分享给一个下载地址  系统自带的(或者用友盟的,单写）
     */
    private void addTvShare() {
        TextView use_share = (TextView) findViewById(R.id.use_share);
        use_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 分享
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/*");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "123"); // "123" 分享的内容
                    startActivity(sendIntent);
                } catch (Exception e) {
                    Toast.makeText(mContext, mContext.getString(R.string.self_about_pingjia_none), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
