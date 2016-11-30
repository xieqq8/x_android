package com.xxx.xbase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.xxx.fudao.R;
import com.xxx.views.SwipeBackLayout;

/**
 * 基类acitivity
 * 1、SwipeBackLayout滑动返回
 * 2、继承这个返回有动画
 * @author xieqq
 */
public class BaseActivityWithSlideBack extends BaseActivity {

	protected SwipeBackLayout layout;

	/**
	 * 初始化
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.swipe_base, null);
		layout.attachToActivity(this);
	}

	/**
	 * 返回
	 */
	protected void addTitleBtnBack(){
		ImageView iv_left = (ImageView)findViewById(R.id.iv_left);
		iv_left.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	@Override
	public void finish() {
		super.finish();
		// 退出的动画
		overridePendingTransition(0, R.anim.base_slide_right_out);
	}

	@Override
	public void onBackPressed() {
//		ActivityManager.getAppManager().finishActivity(this);
		super.onBackPressed();
		logx.d("onBackPressed");
	}

//	public void startActivity(Intent intent) {
//		super.startActivity(intent);
//		// 一个activity进入的动画，一个activity退出的动画
//		overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
//	}
}
