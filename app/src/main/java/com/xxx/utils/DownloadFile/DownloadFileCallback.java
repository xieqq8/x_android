package com.xxx.utils.DownloadFile;

import com.xxx.xbase.XxException;

/**
 * 下载回调
 * @author xieqq
 *
 */
public interface DownloadFileCallback {
//	void downloadSuccess(Object obj);//下载成功
//	void downloadError(Exception e, String msg);//下载失败

	void handleProcess(long var1, long var3, String var5);

	void handleException(XxException var1, int var2);

	void handleStatus(String var1, int var2);

	void handleCancel(String var1);
}
