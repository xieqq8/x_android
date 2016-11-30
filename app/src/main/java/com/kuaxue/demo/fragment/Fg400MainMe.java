package com.kuaxue.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xxx.fudao.R;
import com.kuaxue.demo.activity.Act410About;
import com.kuaxue.demo.activity.Act420FeedBack;
import com.kuaxue.demo.activity.Act430UserInfo;
import com.xxx.utils.update.UpdateManager;
import com.xxx.xbase.BaseFragment;

/**
 * 提问
 */
public class Fg400MainMe extends BaseFragment {

	private View rl_user_detail = null;
	/**
	 * onCreate是指创建该fragment
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
//		logx.d("onCreate ");
		Bundle args = getArguments();

		super.onCreate(savedInstanceState);
	}

	/**
	 * onCreateView是创建该fragment对应的视图
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		logx.d("onCreateView ");
		if(view == null)
			view = inflater.inflate(R.layout.fragment_main_me, null);
		return view;
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
//		logx.d("onViewCreated list ");
		super.onViewCreated(view, savedInstanceState);
		init();
	}

	private void init() {
		rl_user_detail = view.findViewById(R.id.rl_user_detail);

		addUserinfo();
		// 意见反馈
		addFeedBack();
		// 版本更新
		addCheckVersion();
		// 关于我们
		addAboutUs();
	}

	@Override
	public void onDestroyView() {
//		logx.d("onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
//		logx.d("onDestroy");
		super.onDestroy();
	}

	/**
	 * 个人信息
	 */
	private void addUserinfo(){
		rl_user_detail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, Act430UserInfo.class));
			}
		});
	}

	/**
	 * 意见反馈
	 */
	private void addFeedBack(){
		TextView tv_feedback = (TextView)view.findViewById(R.id.tv_feedback);
		tv_feedback.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(getActivity(), Act420FeedBack.class);
				startActivity(i);
			}
		});

	}
	/**
	 * 版本检测
	 */
	private void addCheckVersion(){
		RelativeLayout tv_feedback = (RelativeLayout)view.findViewById(R.id.rl_my_update);
		tv_feedback.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UpdateManager updateManager = new UpdateManager(getActivity());
				updateManager.checkUpdate(true);
			}
		});
	}
	/**
	 * 关于
	 */
	private void addAboutUs(){
		TextView tv_about = (TextView)view.findViewById(R.id.tv_about);
		tv_about.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(getActivity(), Act410About.class);
				startActivity(i);
			}
		});
	}
}
