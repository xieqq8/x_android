package com.xxx.appxxx;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.xxx.appxxx.ui.activity.Act000Welcome;
import com.xxx.appxxx.ui.activity.Act001Main;
import com.xxx.utils.LogX;
import com.xxx.utils.PackageUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.jar.*;

/**
 * 公共application对象
 * 存储与整个应用相关的公共变量
 * 公共的一些初始化
 * Created by xieqq on 2015-10-09 .
 */
public class FDApplication extends Application {
    //    public static IWXAPI api;
    private static FDApplication mInstance;

    public FDApplication() {
        super();
    }

    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;
    @Override
    public void onCreate() {
        // Android 和 Java 内存泄露检测。
//        LeakCanary.install(this);

        mInstance = this;

        initImageLoader();
        initScreenSize();

        RxRetrofitApp.init(this);

//        // 配置设置初始化
//        ConfigUtil.Instance().setAppContext(this.getApplicationContext());
//
////        // 测试消息
////        TelephonyManager tm = (TelephonyManager) this.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
////        LogX.getLogger().d("imei:" + tm.getDeviceId());//如果是GSM网络，返回IMEI；如果是CDMA网络，返回MEID
////        LogX.getLogger().d("imsi:" + tm.getSimSerialNumber()); // 返回SIM卡的序列号(IMEI)
////        LogX.getLogger().d("Line1Number:" + tm.getLine1Number()); // 返回SIM卡电话号码
////
////        LogX.getLogger().d("NetworkType:" + tm.getNetworkType());  //  获取网络类型 在中国，联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信的3G为EVDO
////        LogX.getLogger().d("version:" + Build.VERSION.RELEASE);// 4.4.2 android型号
////        LogX.getLogger().d("brand:" + Build.BRAND);//Sony
////        LogX.getLogger().d("device-sn:" + Build.SERIAL);//CB5A23QDW7
////        LogX.getLogger().d("device-model:" + Build.MODEL);// 型号L50t
////        //        logx.d("PRODUCT:" + Build.PRODUCT);//L50t
////        //        logx.d("DEVICE:" + Build.DEVICE);//L50t
////        LogX.getLogger().d("BOARD:" + Build.BOARD);//MSM8974  骁龙800 MSM8974（LTE)是高通2013年推出的Snapdragon 800系列产品
////        LogX.getLogger().d("HARDWARE:" + Build.HARDWARE);//qcom高通
        super.onCreate();

//        if (quickStart()) {
//            return;
//        }
    }

    /**
     * 初始化imageloader
     */
    private void initImageLoader() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), "imageloader/Cache");

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // 加载图片时会在内存中加载缓存
                .cacheOnDisk(true) // 加载图片时会在磁盘中加载缓存
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .threadPoolSize(3)  // default  线程池内加载的数量
                // default
                .threadPriority(Thread.NORM_PRIORITY - 2)
                // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
                // default      UnlimitedDiskCache
                .diskCache(new UnlimitedDiskCache(cacheDir))
                // default
                .diskCacheSize(30 * 1024 * 1024).diskCacheFileCount(300)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) // default
                .defaultDisplayImageOptions(defaultOptions) // default
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }

    public static Context getInstance() {
        return mInstance;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = mInstance.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mInstance.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前系统语言
     *
     * @return 当前系统语言
     */
    public static String getLanguage() {
        Locale locale = mInstance.getResources().getConfiguration().locale;
        String language = locale.getDefault().toString();
        return language;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

//        if (!quickStart() && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {//>=5.0的系统默认对dex进行oat优化
//            if (needWait(base)){
//                waitForDexopt(base);//Dex进行优化
//            }
//            MultiDex.install (this );
//        } else {
//            return;
//        }
    }

    public static final String KEY_DEX2_SHA1 = "dex2-SHA1-Digest";

    public boolean quickStart() {
        if (StringUtils.contains( getCurProcessName(this), ":mini")) {
            LogX.getLogger().d( ":mini start!");
            return true;
        }
        return false ;
    }
    //neead wait for dexopt ?
    private boolean needWait(Context context){
        String flag = get2thDexSHA1(context);
        LogX.getLogger().d( "dex2-sha1 "+flag);
        SharedPreferences sp = context.getSharedPreferences(
                PackageUtils.getPackageInfo(context). versionName, MODE_MULTI_PROCESS);
        String saveValue = sp.getString(KEY_DEX2_SHA1, "");
        return !StringUtils.equals(flag,saveValue);
    }
    /**
     * Get classes.dex file signature
     * @param context
     * @return
     */
    private String get2thDexSHA1(Context context) {
        ApplicationInfo ai = context.getApplicationInfo();
        String source = ai.sourceDir;
        try {
            JarFile jar = new JarFile(source);
            java.util.jar.Manifest mf = jar.getManifest();
            Map<String, Attributes> map = mf.getEntries();
            Attributes a = map.get("classes2.dex"); // 对这个进行优化 , 没有classess2.dex
            return a.getValue("SHA1-Digest");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    // optDex finish
    public void installFinish(Context context){
        SharedPreferences sp = context.getSharedPreferences(
                PackageUtils.getPackageInfo(context).versionName, MODE_MULTI_PROCESS);
        sp.edit().putString(KEY_DEX2_SHA1,get2thDexSHA1(context)).commit();
    }


    public static String getCurProcessName(Context context) {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context
                    .getSystemService(Context. ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                    .getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess. processName;
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return null ;
    }
    public void waitForDexopt(Context base) {
        Intent intent = new Intent();
        ComponentName componentName = new
                ComponentName( "com.xxx.appxxx", Act000Welcome.class.getName());
        intent.setComponent(componentName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        base.startActivity(intent);
        long startWait = System.currentTimeMillis ();
        long waitTime = 10 * 1000 ;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1 ) {
            waitTime = 20 * 1000 ;//实测发现某些场景下有些2.3版本有可能10s都不能完成optdex
        }
        while (needWait(base)) {
            try {
                long nowWait = System.currentTimeMillis() - startWait;
                LogX.getLogger().d( "wait ms :" + nowWait);
                if (nowWait >= waitTime) {
                    return;
                }
                Thread.sleep(200 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

