package com.xxx.utils.DownloadFile;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import com.xxx.fudao.R;
import com.xxx.utils.LogX;
import com.xxx.utils.StorageUtil;
import com.xxx.utils.update.UpdateManager;
import com.xxx.xbase.XxException;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 文件下载的service
 * 下载APK安装的，做成文件下载需要简单调整下
 * 会给到通知栏
 * @author
 */
public class DownloadFileService extends Service {
	private DownloadFileUtils downloadFileUtils;//文件下载工具类
	private String filePath;//保存在本地的路径
	private NotificationManager notificationManager;//状态栏通知管理类
	private Notification notification;//状态栏通知
	private RemoteViews remoteViews;//状态栏通知显示的view
	private final int notificationID = 1;//通知的id
	private final int updateProgress = 1;//更新状态栏的下载进度
	private final int downloadSuccess = 2;//下载成功
	private final int downloadError = 3;//下载失败
	private Timer timer;//定时器，用于更新下载进度
	private TimerTask task;//定时器执行的任务

	private Context mContext = this;

	// 下载的线程数，有的服务器不支持多线程，设为1
	public static int DOWN_THREAD_COUNT=1;
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}

	@Override
	public void onCreate() {
		LogX.getLogger().d("开始oncreate");
		init();
	}
	
	private void init(){
		filePath = StorageUtil.getSelfCache(mContext);
		LogX.getLogger().d("开始下载，初始化:" + filePath);

		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification();
		// 点击后就会自动消失
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.icon = R.mipmap.ic_launcher;//设置通知消息的图标
		notification.tickerText = "下载中...";//设置通知消息的标题
		remoteViews = new RemoteViews(getPackageName(), R.layout.down_notification);
		remoteViews.setImageViewResource(R.id.IconIV, R.mipmap.ic_launcher);

		timer = new Timer();
		task = new TimerTask() {			
			@Override
			public void run() {
				handler.sendEmptyMessage(updateProgress);
			}
		};
	}

//	private PendingIntent pre_PendingIntent(){
//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.setDataAndType(Uri.fromFile(new File(filePath, getResources().getString(R.string.app_name))),
//				"application/vnd.android.package-archive");
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		//去掉通知栏
//		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//		return pendingIntent;
//    }

	/**
	 * 更新apk包
	 */
	void updateApk() {
		// mUri)调用内置浏览器查看后如何获得内置浏览器的返回数据
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(filePath, getResources().getString(R.string.app_name))),
				"application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivity(intent);

	}

	private void SendBroadMSg(String type,int progress){
		Intent msgIntent = new Intent(type);
		msgIntent.putExtra("pro", progress);
		mContext.sendBroadcast(msgIntent);
//
////		LogX.getLogger().d("SendBroadMSg");
//		EventBus.getDefault().post(new EventUpdateProgress(type, progress));
	}

	private String updateUrl="";

	private static boolean bstart = false;
	// START_STICKY：如果service进程被kill掉，保留service的状态为开始状态，
	// 但不保留递送的intent对象。随后系统会尝试重新创建service
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogX.getLogger().d("onStartCommand--%d--%d",flags,startId);
		if (intent == null){
			LogX.getLogger().d("intent is null");
			notificationManager.cancel(notificationID); // 自动消失
			return super.onStartCommand(intent, flags, startId);
		} else {
			try {
				updateUrl = intent.getStringExtra("updateurl");
			} catch (Exception e) {
				LogX.getLogger().d("intent getStringExtra Exception");
				return super.onStartCommand(intent, flags, startId);
			}
		}
		bstart = false;

		isForceUpdate = intent.getBooleanExtra("isForceUpdate",false);
//		if (isForceUpdate) {// 强制更新弹出下载框
//		}

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				LogX.getLogger().d("DownloadFileUtils:" + updateUrl);
				// 初始化
				downloadFileUtils = new DownloadFileUtils(updateUrl, filePath, mContext.getString(R.string.app_name), DOWN_THREAD_COUNT, callback);
				// 开始下载
				downloadFileUtils.downloadFile();
			}
		}).start();
		// 延迟500毫秒，每600毫秒发送一次进度
		timer.schedule(task, 500, 1000);
//		int nRet =super.onStartCommand(intent, flags, startId);
//
//		LogX.getLogger().d("onStartCommand--ret--%d",nRet);

		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		LogX.getLogger().d(" is onDestory...");
		super.onDestroy();
	}

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			try {
				LogX.getLogger().d("msg.what = %d",msg.what);
				if (msg.what == updateProgress) {//更新下载进度
					if(!bstart) {
						bstart = true;
						SendBroadMSg(UpdateManager.update_start, 0);
					}
                    // 文件总大小
                    long fileSize = downloadFileUtils.getFileSize();
                    // 己下载完成数
                    long totalReadSize = downloadFileUtils.getTotalReadSize();

//    				LogX.getLogger().d("filesize %f, totalReadSize %f", (float)fileSize, (float)totalReadSize);
					if(totalReadSize > 0){
                        float size = (float) totalReadSize * 100 / (float) fileSize;
                        DecimalFormat format = new DecimalFormat("0.00");
                        String progress = format.format(size);
                        remoteViews.setTextViewText(R.id.progressTv, "已下载" + progress + "%");
                        remoteViews.setProgressBar(R.id.progressBar, 100, (int) size, false);
                        notification.contentView = remoteViews;
                        notificationManager.notify(notificationID, notification);

                        SendBroadMSg(UpdateManager.update_progress, (int) size);
    //					if(progressDlg!=null)
    //						progressDlg.setProgress((int)size);
                    }
                } else if (msg.what == downloadSuccess) {//下载完成
                    remoteViews.setTextViewText(R.id.progressTv, "下载完成。");
                    remoteViews.setProgressBar(R.id.progressBar, 100, 100, false);
                    notification.contentView = remoteViews;

                    notificationManager.notify(notificationID, notification);
                    notificationManager.cancel(notificationID); // 自动消失
                    // 下载完成后提示
                    updateApk();
                    if(timer != null && task != null){
                        timer.cancel();
                        task.cancel();
                        timer = null;
                        task = null;
                    }
                    // 添加通知栏点击安装   // 点击后通知栏消失没有做
    //				remoteViews.setOnClickPendingIntent(R.id.llInstall, pre_PendingIntent());
                    stopService(new Intent(getApplicationContext(),DownloadFileService.class));//stop service

                    SendBroadMSg(UpdateManager.update_finish, 0);

                } else if (msg.what == downloadError) {//下载失败
                    if(timer != null && task != null){
                        timer.cancel();
                        task.cancel();
                        timer = null;
                        task = null;
                    }
                    notificationManager.cancel(notificationID);
                    stopService(new Intent(getApplicationContext(), DownloadFileService.class));//stop service

                    SendBroadMSg(UpdateManager.update_finish, -1);
                }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	};


//	/**
//	 * 显示软件下载对话框
//	 */
//	private ProgressDialog progressDlg = null;
	private boolean isForceUpdate =false;
	/**
	 * 下载回调
	 */
	DownloadFileCallback callback = new DownloadFileCallback() {
		@SuppressWarnings("deprecation")
		@Override
		public void handleProcess(long start, long end, String videoId) {

		}

		@Override
		public void handleException(XxException exception, int status) {
			LogX.getLogger().d("down callback error:" + exception.getMessage());
			handler.sendEmptyMessage(downloadError);
		}

		@Override
		public void handleStatus(String videoId, int status) {
			switch (status) {
				case DownloadFileUtils.WAIT: // 等待
					break;
				case DownloadFileUtils.DOWNLOAD: // 下载中
					break;
				case DownloadFileUtils.PAUSE:	// 暂停
					break;
				case DownloadFileUtils.FINISH:	// 完成
					LogX.getLogger().d("down callback:" + downloadSuccess);
					handler.sendEmptyMessage(downloadSuccess);

					break;

			}
		}

		@Override
		public void handleCancel(String videoId) {
			// 取消
		}
	};
	

}