/** 
 * HaoJiXing Teacher Q&A
 * copyright(C)2013- Acorn International
 *
 * packeage：com.ozing.calltearcher.mobile.phone.utils.PreferencesUtil.java
 * create：2013年9月23日下午3:40:28
 */
package com.xxx.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author jixiaolong<micro4code@163.com>
 * @code: 015261
 */
public class PreferencesUtil {
	public static final String PREF_FILE_NAME = "xx_art_space";
//	public static final String PREF_KEY_ACCESS_TOKEN = "";
//    public static final String PREF_KEY_USERNAME = "";

	public static SharedPreferences getSharedPreference(Context mContext){
		return mContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
	}
	
	public static void putInt(String key, int value, Context mContext){
		getSharedPreference(mContext).edit().putInt(key, value).commit();
	}

	public static int getInt(String key, int defValue, Context mContext){
		return getSharedPreference(mContext).getInt(key, defValue);
	}

	public static String getString(String key, String defValue, Context mContext){
		return getSharedPreference(mContext).getString(key, defValue);
	}

	public static void putString(String key, String value, Context mContext){
		getSharedPreference(mContext).edit().putString(key, value).commit();
	}

	public static String[] getPreferences(String[] keys, Context mContext){
		SharedPreferences sf = getSharedPreference(mContext);
		String[] values = new String[keys.length];
		for(int i=0;i<keys.length;i++){
			values[i] = sf.getString(keys[i], "");
		}
		return values;
	}

}
