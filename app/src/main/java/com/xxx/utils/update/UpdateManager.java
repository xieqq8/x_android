package com.xxx.utils.update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.xxx.fudao.R;
import com.kuaxue.demo.netparse.HttpUrlConstant;
import com.kuaxue.demo.netparse.NetRestClient;
import com.xxx.utils.AlertUtil;
import com.xxx.utils.DownloadFile.DownloadFileService;
import com.xxx.utils.LogX;

import org.apache.http.Header;
import org.json.JSONException;

/**
 * 软件更新  百度的需要加百度的更新SDK
 * @author XQQ
 */
public class UpdateManager {
    /* 下载中 */
    private static final int U_START = 1;
    /* 下载结束 */
    private static final int U_PROGRESS = 2;
    /* 强制应用退出 */
    private static final int U_FINISH = 3;
    /* 保存解析的XML信息 */
//    private HashMap<String, String> mHashMap = null;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    //    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    private Context mContext;

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    @Override
    protected void finalize() throws Throwable {

        unRegisterUpdateApp();
        // 反注册EventBus
//        if (EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().unregister(this);

        super.finalize();
    }

    /**
     * 检测软件更新
     *
     * @param bCheck 是否是手动检测 , 每次启动自动检测 true 会有提示正在检测已经是最新， false 静默
     */
    public void checkUpdate(boolean bCheck) {
        // 获取当前软件版本
        int versionCode = getVersionCode(mContext);
        // 比较本地与网络的版本
        CompareNetVersionCode(versionCode, bCheck);
    }

    private UpdateData updateData;
    ProgressDialog progressDialog;

    /**
     * 得到服务器的最新版本号
     *
     * @param localVersionCode
     * @param bCheck           true 会有提示正在检测已经是最新， false 静默
     */
    private void CompareNetVersionCode(final int localVersionCode, final boolean bCheck) {
        RequestParams params = new RequestParams();
        params.put("currentVersionCode", localVersionCode + "");
        NetRestClient.Instance().client.get(HttpUrlConstant.UPDATE, params, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                if (bCheck) {
                    // 如果是手动点的版本检测
                    progressDialog = AlertUtil.showLoadingDialog(mContext, "正在检测最新版本");
                }
                super.onStart();
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                // 检查网络
                // 提示网络连接还是超时的问题
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                UpdateParser up = new UpdateParser();
                try {
                    updateData = up.parse(s);
                    LogX.getLogger().d("%s \n localver %d, remotever %d", s, localVersionCode, updateData.getVersionCode());
                    if (updateData != null && updateData.getVersionCode() > localVersionCode) {
                        showNoticeDialog();
                    } else {
                        if (bCheck) {
                            progressDialog.dismiss();
                            Toast.makeText(mContext, "已经是最新版本", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinish() {
                // 结束
                super.onFinish();
                if (bCheck) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }
        });
    }

    /**
     * 获取软件当前版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取软件版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionCode = "";
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    boolean isForceUpdate = false;

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {

        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("软件更新");
        builder.setMessage(updateData.getNotes());
        // 更新
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 显示下载对话框
                Intent downIntent = new Intent(mContext, DownloadFileService.class);
                // 更新路径
                downIntent.putExtra("updateurl", updateData.getApkFileURL());
//                downIntent.putExtra("updateurl", "http://i6.topit.me/6/5d/45/1131907198420455d6o.jpg");
                // 是否强制更新
                downIntent.putExtra("isForceUpdate", updateData.isForceUpdate());
//                if(updateData.isForceUpdate()) {
                // 下载进度反馈
                registerUpdateApp();
//                // 用eventbus代替broadcast
//                EventBus.getDefault().register(mContext);
//                }
                mContext.startService(downIntent);
            }
        });

        //
        isForceUpdate = updateData.isForceUpdate();
        if (isForceUpdate) {
            // 强制更新，点别的地方不可消失
            builder.setCancelable(false);
            // 显示下载进度对话框 // 当前页有下载框 不下载完不让用~
        } else { // 非强制更新，可点稍后更新
            // 稍后更新
            builder.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            // 点别的地方可以消失
            builder.setCancelable(true);
        }

        // 更新框显示
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

//    /**
//     * 事件的处理在和事件的发送在相同的进程
//     *
//     * @param event
//     */
//    public void onEvent(EventUpdateProgress event) {
//        LogX.getLogger().d("onEvent");
//
//    }
//
//    /**
//     * 事件的处理会在UI线程中执行
//     *
//     * @param event
//     */
//    public void onEventBackground(EventUpdateProgress event) {
//        LogX.getLogger().d("onEventBackground");
//
//    }
//
//    /**
//     * 事件的处理会在一个后台线程中执行
//     *
//     * @param event
//     */
//    public void onEventAsync(EventUpdateProgress event) {
//        LogX.getLogger().d("onEventAsync");
//
//    }
//
//    /**
//     * 事件处理会在单独的线程中执行，主要用于在后台线程中执行耗时操作
//     *
//     * @param event
//     */
//    public void onEventMainThread(EventUpdateProgress event) {
//
//        LogX.getLogger().d("type = progress = ");
//        if (event.getmMsg().equals(update_start)) {
//            showDownloadDialog();
//        } else if (event.getmMsg().equals(update_progress)) {
//
//            if (progressDlg != null)
//                progressDlg.setProgress(event.getnProgress());
//            else
//                LogX.getLogger().d("progressDlg is null");
//
//        } else if (event.getmMsg().equals(update_finish)) {
//            if (progressDlg != null)
//                progressDlg.dismiss();
//            unRegisterUpdateApp();
//            // 反注册EventBus
////            EventBus.getDefault().unregister(mContext);
//        }
//    }

    public static final String update_start = "com.xx.update_start";
    public static final String update_progress = "com.xx.updateprogress";
    public static final String update_finish = "com.xx.updatefinish";

//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case U_START:
//                    showDownloadDialog();
//                    break;
//                case U_PROGRESS:
//                    int progress = msg.arg1;
//                    if(progressDlg!=null)
//                        progressDlg.setProgress(progress);
//                    break;
//                case U_FINISH:
//                    if(progressDlg!=null)
//                        progressDlg.dismiss();
//            }
//        }
//    };
    /**
     * 用广播接收进度
     */
    private BroadcastReceiver progressReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogX.getLogger().d("BroadcastReceiver action %s", intent.getAction());

            if (intent.getAction().equals(update_start)) {
//                mHandler.sendEmptyMessage(U_START);
                // 下载显示
                showDownloadDialog();
            } else if (intent.getAction().equals(update_progress)) {
                int progress = intent.getIntExtra("pro", 0);
//                Message msg = new Message();
//                msg.arg1 = progress;
//                msg.what = U_PROGRESS;
//                mHandler.sendMessage(msg);
                if (progressDlg != null)
                    progressDlg.setProgress(progress);
            } else if (intent.getAction().equals(update_finish)) {
//                mHandler.sendEmptyMessage(U_FINISH);
                if (progressDlg != null)
                    progressDlg.dismiss();
                unRegisterUpdateApp(); // 广播注销掉
                // 反注册EventBus
//                EventBus.getDefault().unregister(mContext);
            }
        }
    };

    /**
     * 注册更新服务broadcast
     */
    private void registerUpdateApp() {
        LogX.getLogger().d("registerUpdatePay");
        IntentFilter filter = new IntentFilter();
//        filter.addCategory(); category 和 action
        filter.addAction(update_start);
        filter.addAction(update_progress);
        filter.addAction(update_finish);
        mContext.registerReceiver(progressReceiver, filter);
    }

    /**
     * 反注册更新服务broadcast
     */
    private void unRegisterUpdateApp() {
        try {
            LogX.getLogger().d("unRegisterUpdatePay");
            mContext.unregisterReceiver(progressReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    /**
//     * 显示软件下载对话框
//     */
    private ProgressDialog progressDlg = null;

    private void showDownloadDialog() {
        try {
            progressDlg = new ProgressDialog(mContext);
            progressDlg.setTitle("正在更新");
            progressDlg.setIcon(R.mipmap.ic_launcher);
            progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

            if (!isForceUpdate) {
                // 取消更新 // 非强制更新可以点取消
                progressDlg.setButton(DialogInterface.BUTTON_NEGATIVE,
                        "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                if (which == DialogInterface.BUTTON_NEGATIVE) {
                                    progressDlg.dismiss();
                                    cancelUpdate = true;
                                }
                            }
                        });
                progressDlg.setCancelable(true);
            } else {
                progressDlg.setCancelable(false);
            }
            progressDlg.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
