package com.xxx.xbase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.xxx.utils.LogX;

/**
 * 基类acitivity
 * 所有的activity都要继承这个
 * @author xieqq
 */
public class BaseActivity extends Activity {
	protected LogX logx = LogX.getLogger();
	// 定义的context
	protected Context mContext = null;
	protected Activity mActivity = null;
	/**
	 * 初始化
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mContext = this;
		mActivity = this;
		super.onCreate(savedInstanceState);
	}

	@Override
	public void finish() {
		super.finish();
	}

	/**
	 * 返回键
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
