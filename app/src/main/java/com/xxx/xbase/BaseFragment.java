package com.xxx.xbase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.xxx.utils.LogX;

/**
 * 基类fragment 所有的Fragment都要继承
 * 
 * @author xieqq
 * 
 */
public class BaseFragment extends Fragment {
	protected LogX logx = LogX.getLogger();

	protected View view = null;
	protected Context mContext = null;
	protected Activity mActivity = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mContext = this.getActivity();
		mActivity = this.getActivity();
		super.onCreate(savedInstanceState);
	}

	public void removeSelfFromParent(View view) {
		if (view != null) {
			ViewParent parent = view.getParent();
			if (parent != null && parent instanceof ViewGroup) {
				// 左邊的對象是否是右邊的實例
				ViewGroup group = (ViewGroup) parent;
				group.removeView(view);
			}
		}
	}
	@Override
	public void onDestroyView() {
		//移除当前视图，防止重复加载相同视图使得程序闪退
		removeSelfFromParent(view);
		super.onDestroyView();
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
