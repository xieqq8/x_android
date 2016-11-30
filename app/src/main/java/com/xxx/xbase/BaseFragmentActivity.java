package com.xxx.xbase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.xxx.utils.LogX;

/**
 * fragment是3.0以后的东西，为了在低版本中使用fragment就要用到android-support-v4.jar兼容包,
 * 而fragmentActivity就是这个兼容包里面的，它提供了操作fragment的一些方法，其功能跟3.0及以 后的版本的Activity的功能一样。
 * 
 * @author xieqq
 * 
 */
public class BaseFragmentActivity extends FragmentActivity {
	protected LogX logx = LogX.getLogger();// 日志

	protected Context mContext = null;
	protected Activity mActivity = null;

//	protected SwipeBackLayout layout;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		mContext = this;
		mActivity = this;
//		layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.swipe_base, null);
//		layout.attachToActivity(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
//		overridePendingTransition(0, R.anim.base_slide_right_out);

	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		// 一个activity进入的动画，一个activity退出的动画
//		overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);

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
