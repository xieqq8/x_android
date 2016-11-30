package com.xxx.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 文件存储
 *  1:getExternalStorageDirectory() 获取到外部存储的目录 一般指SDcard（/storage/sdcard0）
 *   	getExternalStorageState() 获取外部设置的当前状态 一般指SDcard，比较常用的应该是 MEDIA_MOUNTED（SDcard存在并且可以进行读写）还有其他的一些状态，可以在文档中进行查找。
 *  2: getDataDirectory() 获取到Android中的data数据目录（sd卡中的data文件夹）
 *  3:getDownloadCacheDirectory() 获取到下载的缓存目录（sd卡中的download文件夹）
 *	4:getRootDirectory()  获取到Android Root路径*/
public class StorageUtil {
	private static final int ERROR = -1;
	private static String mData = null;

	private static String APP_DATA_PATH="/kfudao";
//	getExternalStoragePublicDirectory
	/**
	 * 外部存储卡是否存在
	 */
	public static boolean externalMemoryAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 *
	 * @param path
	 *            文件夹路径如果不存在就创建
	 */
	public void isExist(String path) {
		File file = new File(path);
		// 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()) {
			file.mkdir(); //
//			file.mkdirs();// 如果有多级目录
		}
	}

	/**
	 * 得到存储路径
	 * @param context
	 * @return
	 */
	public static String getSelfCache(Context context){
		try {
			if(externalMemoryAvailable()){
				mData = Environment.getExternalStorageDirectory().getCanonicalPath();
				return mData + APP_DATA_PATH;
			} else {
				String[] sd = getSdCards(context);
				if(sd!=null){
					return sd[0]+ APP_DATA_PATH;
				}else{
					getExternalSdCardPath();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回多张存储卡路径
	 * @param context
	 * @return
	 */
	private static String[] getSdCards(Context context){
		StorageManager storageManager = (StorageManager) context.getSystemService(context.STORAGE_SERVICE);
		try {
			Class<?>[] paramClasses = {};
			Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
			getVolumePathsMethod.setAccessible(true);
			Object[] params = {};
			String[] invoke = (String[])getVolumePathsMethod.invoke(storageManager, params);

			if(invoke.length > 0 ){
				for(int i=0; i < invoke.length; i++){
					LogX.getLogger().d("getSdCards %d:%s", i, invoke[i]);
				}
				return  invoke; // 默认的存储卡
			} else {
				return  null;
			}
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return  null;
	}

	/**
	 * 得到挂载的存储结点
	 * @return
	 */
	private static ArrayList<String> getDevMountList() {
		String[] toSearch = new String[0];
		try {
			toSearch = FileUtils.readFileToString(new File("D:/a/b/cxyapi.txt")).split(" ");

			ArrayList<String> out = new ArrayList<String>();
			for (int i = 0; i < toSearch.length; i++) {
				if (toSearch[i].contains("dev_mount")) {
					if (new File(toSearch[i + 2]).exists()) {
						out.add(toSearch[i + 2]);
						LogX.getLogger().d("getDevMountList %d:%s", i, toSearch[i+2]);

					}
				}
			}
			return out;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取扩展SD卡存储目录
	 *
	 * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
	 * 否则：返回内置SD卡目录
	 *
	 * @return
	 */
	private static String getExternalSdCardPath() {

		if (externalMemoryAvailable()) {
			File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
			return sdCardFile.getAbsolutePath();
		}

		String path = null;

		File sdCardFile = null;

		ArrayList<String> devMountList = getDevMountList();

		for (String devMount : devMountList) {
			File file = new File(devMount);

			if (file.isDirectory() && file.canWrite()) {
				path = file.getAbsolutePath();

				String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
				File testWritable = new File(path, "test_" + timeStamp);

				if (testWritable.mkdirs()) {
					testWritable.delete();
				} else {
					path = null;
				}
			}
		}

		if (path != null) {
			sdCardFile = new File(path);
			return sdCardFile.getAbsolutePath();
		}

		return null;
	}
	/**
	 * 得到可存储的路径
	 *
	 */
	private static String getDataFloder() {
		if (mData != null)
			return mData;
		String state = Environment.getExternalStorageState(); // 是否有SD卡
		try {
			if (state.equals(Environment.MEDIA_MOUNTED)) {
				mData = Environment.getExternalStorageDirectory().getCanonicalPath();
				return mData;
			}
			// 判断 /udisk 是否存在 存在则 /udisk/xx/
			try {
				File f = new File("/udisk/");
				if (f.exists()) {
					mData = "/udisk/";
					return mData;
				}
			} catch (Exception e) {
			}

			mData = Environment.getDataDirectory().getCanonicalPath();
			return mData;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取手机内部剩余存储空间
	 * 
	 * @return
	 */
	public static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * 获取手机内部总的存储空间
	 * 
	 * @return
	 */
	public static long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	/**
	 * 获取SDCARD剩余存储空间
	 * 
	 * @return
	 */
	public static long getAvailableExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} else {
			return ERROR;
		}
	}

	/**
	 * 获取SDCARD总的存储空间
	 * 
	 * @return
	 */
	public static long getTotalExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} else {
			return ERROR;
		}
	}
}
