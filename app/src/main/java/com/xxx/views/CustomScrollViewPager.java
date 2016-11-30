package com.xxx.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义滑动ViewPager， 默认不滑动 滑动viewpager不响应
 * @author xieqq
 *
 */
@SuppressLint("NewApi")
public class CustomScrollViewPager extends ViewPager {
	private boolean HAS_TOUCH_MODE = true;

	public CustomScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomScrollViewPager(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (HAS_TOUCH_MODE)
			return super.onInterceptHoverEvent(ev);
		else	//返回false让当前布局的子布局去响应事件
			return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (HAS_TOUCH_MODE)
			return super.onTouchEvent(ev);
		else	//返回false让当前布局的子布局去响应事件
			return false;
	}
}
