package com.xxx.utils.DownloadFile;

import com.xxx.utils.LogX;
import com.xxx.xbase.XxErrorCode;
import com.xxx.xbase.XxException;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程下载 工具类
 * @author xieqq
 *
 */
public class DownloadFileUtils {
	private int status;

	public static final int WAIT = 100;
	public static final int DOWNLOAD = 200;
	public static final int PAUSE = 300;
	public static final int FINISH = 400;

	private String url;//下载地址
	private long fileSize;//下载的文件大小
	private long totalReadSize = 0;//已读取的文件大小
	private long block;//每条线程下载的长度
	private int nThreadCount = DownloadFileService.DOWN_THREAD_COUNT;//下载的线程数 固定2
	private final int threadPoolNum = 5;//线程池的大小
	private String fileName;//存储在本地的文件名称
	private String filePath;//存储的路径
	private HttpURLConnection urlConnection;
	private RandomAccessFile randomAccessFile;//根据指定位置写入数据
	private URL uri;
	private DownloadFileCallback callback;//下载的回调接口
	private ExecutorService executorService;//固定大小的线程池
//	private volatile boolean error = false;//全局变量，使用volatile同步，下载产生异常时改变
	private File[] tempFiles;//保存thread的下载进度缓存文件的集合
	public DownloadFileUtils(String url,String filePath,String fileName,int threadCount,DownloadFileCallback callback){
		this.url = url;
		this.filePath = filePath;
		this.fileName = fileName;
		this.nThreadCount = threadCount;
		this.callback = callback;
		tempFiles = new File[threadCount];
	}
	
	public long getFileSize() {
		return fileSize;
	}

	private DownloadThread downloadThread[] = new DownloadThread[DownloadFileService.DOWN_THREAD_COUNT];
	public long getTotalReadSize() {
		totalReadSize = 0;
		for (int i =0; i< nThreadCount; i++){
//			LogX.getLogger().d("thread %d download %d",i,(int)downloadThread[i].getTotalReadSize());
			totalReadSize += downloadThread[i].getTotalReadSize();
		}
		return totalReadSize;
	}
	/**
	 * 文件下载
	 * @return true 下载成功 false 下载失败
	 */
	public boolean downloadFile(){
		try {
			uri = new URL(url);
			urlConnection = (HttpURLConnection) uri.openConnection();
//			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("User-Agent", "NetFox");

			urlConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			urlConnection.setConnectTimeout(5 * 60 * 1000);// 设置连接超时
			urlConnection.setReadTimeout(60 * 1000);//设置数据读取超时
			urlConnection.setAllowUserInteraction(true);// 允许用户交互

			int code = urlConnection.getResponseCode();
			LogX.getLogger().d("responsecode %d",code);
			if(code == HttpURLConnection.HTTP_OK){
				fileSize = urlConnection.getContentLength();//获取文件的长度
				block = fileSize / nThreadCount + 1;//为了避免文件长度缺失每条线程下载长度增加1
				File file = new File(filePath,fileName);
				if(!file.getParentFile().exists())
					file.getParentFile().mkdirs();
				executorService = Executors.newFixedThreadPool(threadPoolNum);
				CountDownLatch countDownLatch = new CountDownLatch(nThreadCount);//线程计数器
				for(int i = 0; i < nThreadCount; i++){
					long startPosition = i * block;//每条线程的开始读取位置
					long endPosition = (i+1) * block - 1;//每条线程的读取结束位置

					LogX.getLogger().d("%s thread %d down start %d end %d",file.toString(), i, (int)startPosition, (int)endPosition);
					randomAccessFile = new RandomAccessFile(file, "rwd");

					downloadThread[i] = new DownloadThread(i+1, startPosition, endPosition, randomAccessFile,countDownLatch);
					downloadThread[i].setRunParm(fileName
							, filePath
							, tempFiles
							, nThreadCount
							, uri
							, callback);
					LogX.getLogger().d("tempfiles:" + tempFiles.toString());
					executorService.execute(downloadThread[i]);
				}
				LogX.getLogger().d("wait for download......");

				countDownLatch.await();//阻塞线程,直到countDownLatch线程数为零

				LogX.getLogger().d("download end");
				for(int i = 0; i < nThreadCount; i++){
					if(tempFiles[i] != null && tempFiles[i].exists())
						tempFiles[i].delete();
				}
				executorService.shutdown();
				callback.handleStatus(url,FINISH);//下载成功时的回调
				return true;
			}
		} catch (Exception e) {

			callback.handleException(new XxException(XxErrorCode.NETWORK_ERROR,
					new String[]{"下载失败，ErrorCode: " + XxErrorCode.NETWORK_ERROR.name()}), this.status);
			return false;
		}
		return false;
	}
//	private void a(ErrorCode var1) {
//		if(!this.u) {
//			if(++this.t > this.s || this.status != 200 && this.status != 100) {
//				this.status = 300;
//				if(this.k != null) {
//					this.k.handleException(new DreamwinException(var1, new String[]{"下载失败，ErrorCode: " + var1.name()}), this.status);
//					this.g();
//				}
//			} else {
//				this.c();
//			}
//		}
//	}
}
