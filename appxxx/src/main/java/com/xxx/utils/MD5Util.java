package com.xxx.utils;

import android.content.Context;

import java.security.MessageDigest;

public class MD5Util {

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * token 1
	 * sha1(md5(date("YmdHi").'&%@$Xi768'));
	 * @param mcontext
	 * @return
     */
	public static String Sha1MD5EncodeNoPhone(Context mcontext) {
		long curTime = System.currentTimeMillis() +
				PreferencesUtil.getSharedPreference(mcontext).getLong("server_internal",0);

		String MD5source = DateUtil.formatTime(curTime, "yyyyMMddHHmm") + "&%@$Xi768";
		String Md5Out = MD5(MD5source);
		Sha1Php sha1 = new Sha1Php();
		String strRetsha1 = sha1.getDigestOfString(Md5Out.getBytes()).toLowerCase();
		return strRetsha1;
	}

	/**
	 * token 2
	 * sha1(md5(date("YmdHi").'&%@$Xi768'.$username));
	 * @param mcontext
	 * @param phoneNum		$username
     * @return
     */
	public static String MD5MD5Encode(Context mcontext, String phoneNum) {

		long curTime = System.currentTimeMillis() +
				PreferencesUtil.getSharedPreference(mcontext).getLong("server_internal",0);
		String MD5source = DateUtil.formatTime(curTime, "yyyyMMddHHmm") + "&%@$Xi768" + phoneNum;
		String Md5Out = MD5(MD5source);
		Sha1Php sha1 = new Sha1Php();

		String strRetsha1 = sha1.getDigestOfString(Md5Out.getBytes()).toLowerCase();

		return strRetsha1;
	}

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			//使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				//System.out.println((int)b);
				//将没个数(int)b进行双字节加密
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str);
		} catch (Exception e) {return null;}
	}


	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

//	http://blog.csdn.net/iefreer/article/details/7864190
//	Java对PHP服务器hmac_sha1签名认证方法的匹配实现
	private static String appendEqualSign(String s){
		int len = s.length();
		int appendNum = 8 - (int)(len/8);
		for (int n=0; n<appendNum; n++){
			s += "=";
		}
		return s;
	}

	/**
	 * @param plainText 加密字符串
	 * @return String 返回32位md5加密字符串(16位加密取substring(8,24))
	 */
	public static String getMD5Str32(String plainText) {
		// 返回字符串
		String md5Str = null;
		try {
			// 操作字符串
			StringBuffer buf = new StringBuffer();
			/**
			 * MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
			 * 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
			 *
			 * MessageDigest 对象开始被初始化。
			 * 该对象通过使用 update()方法处理数据。
			 * 任何时候都可以调用 reset()方法重置摘要。
			 * 一旦所有需要更新的数据都已经被更新了，应该调用digest()方法之一完成哈希计算。
			 *
			 * 对于给定数量的更新数据，digest 方法只能被调用一次。
			 * 在调用 digest 之后，MessageDigest 对象被重新设置成其初始状态。
			 */
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 添加要进行计算摘要的信息,使用 plainText 的 byte 数组更新摘要。
			md.update(plainText.getBytes());
			// 计算出摘要,完成哈希计算。
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				// 将整型 十进制 i 转换为16位，用十六进制参数表示的无符号整数值的字符串表示形式。
				buf.append(Integer.toHexString(i));
			}
			// 32位的加密
			md5Str = buf.toString();
			// 16位的加密
			// md5Str = buf.toString().md5Strstring(8,24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5Str;
	}
}
