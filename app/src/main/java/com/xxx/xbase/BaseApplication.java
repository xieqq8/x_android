package com.xxx.xbase;

import android.app.Application;

public class BaseApplication extends Application{
	
	/** 全局Context，原理是因为Application类是应用最先运行的，所以在我们的代码调用时，该值已经被赋值过了 */
	private static BaseApplication mInstance;
	
	@Override
	public void onCreate() {
		super.onCreate();
//		ImageLoadUtil.initImageLoadUtil(getApplicationContext()); // app初始化的时候可以使用
	}
	
	public static BaseApplication getApplication() {
		return mInstance;
	}
}
