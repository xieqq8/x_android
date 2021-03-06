package com.kuaxue.demo;

import android.app.Application;
import android.content.Context;

import com.kuaxue.database.ConfigUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.kuaxue.demo.netparse.NetRestClient;
import com.xxx.utils.LogX;
import com.xxx.utils.StorageUtil;

import java.io.File;

/**
 * 公共application对象
 * 存储与整个应用相关的公共变量
 * 公共的一些初始化
 * Created by xieqq on 2015-10-09 .
 */
public class FDApplication extends Application {
    public FDApplication() {
        super();
    }

    @Override
    public void onCreate() {
        // 缓存、上传、下载
        // 创建默认的ImageLoader配置参数
//        ImageLoaderConfiguration config = ImageLoaderConfiguration
//                .createDefault(this);
//        ①减少配置之中线程池的大小，(.threadPoolSize).推荐1-5；
//        ②使用.bitmapConfig(Bitmap.config.RGB_565)代替ARGB_8888;
//        ③使用.imageScaleType(ImageScaleType.IN_SAMPLE_INT)或者 try.imageScaleType(ImageScaleType.EXACTLY)；
//        ④避免使用RoundedBitmapDisplayer.他会创建新的ARGB_8888格式的Bitmap对象；
//        ⑤使用.memoryCache(new WeakMemoryCache())，不要使用.cacheInMemory();

//        LruMemoryCache 开源框架默认的内存缓存类，缓存的是bitmap的强引用
//        UsingFreqLimitedMemoryCache（如果缓存的图片总量超过限定值，先删除使用频率最小的bitmap）
//
//        LRULimitedMemoryCache（这个也是使用的lru算法，和LruMemoryCache不同的是，他缓存的是bitmap的弱引用）
//        FIFOLimitedMemoryCache（先进先出的缓存策略，当超过设定值，先删除最先加入缓存的bitmap）
//        LargestLimitedMemoryCache(当超过缓存限定值，先删除最大的bitmap对象)
//        LimitedAgeMemoryCache（当 bitmap加入缓存中的时间超过我们设定的值，将其删除）
//        LogX.getLogger().d("aaaaaaaaaaaaaaaaaaa");

        // 文件数据
//        File cacheDir = new File(StorageUtil.getDataFloder() +"/AA");
        Context mContext = this.getApplicationContext();
        File cacheDir = StorageUtils.getCacheDirectory(this.getApplicationContext());
//        LogX.getLogger().d("StorageUtils: %s",cacheDir.getAbsolutePath());
        LogX.getLogger().d("StorageUtil getSelfCache: %s", StorageUtil.getSelfCache(mContext));
//        LogX.getLogger().d("StorageUtil getSdCards: ");
//        StorageUtil.getSdCards(mContext);
//        LogX.getLogger().d("StorageUtil getDevMountList: ");
//        StorageUtil.getDevMountList();
//        LogX.getLogger().d("StorageUtil getExternalSdCardPath: %s", StorageUtil.getExternalSdCardPath());
//        LogX.getLogger().d("StorageUtil getDataFloder: %s", StorageUtil.getDataFloder());


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getApplicationContext())
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 内存缓存文件的最大长宽
//                .diskCacheExtraOptions(480, 800, null) // 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个
//                .taskExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
//                .taskExecutorForCachedImages(AsyncTask.THREAD_POOL_EXECUTOR) //                .httpConnectTimeout(5000)
                .threadPoolSize(3) // default  线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 1)  // default 设置当前线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)    // 内存缓存的最大值
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default 磁盘缓存
                .diskCacheSize(30 * 1024 * 1024)       // 磁盘缓存大小
                .diskCacheFileCount(300)        // 可以缓存的文件数量
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this.getApplicationContext(), 5 * 1000, 30 * 1000)) // default // connectTimeout (5 s), readTimeout (30 s)
                .imageDecoder(new BaseImageDecoder(false)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
//                .writeDebugLogs() // 日志是否打开
                .build();
        ImageLoader.getInstance().init(config);
//        ImageLoader.getInstance().clearMemoryCache();
//        ImageLoader.getInstance().clearDiskCache();
        // 网络请求头配置
        NetRestClient.Instance().setAppContext(this.getApplicationContext());
        NetRestClient.Instance().HeaderConfig();// 服务端解析头

        // 配置设置初始化
        ConfigUtil.Instance().setAppContext(this.getApplicationContext());

//        // 测试消息
//        TelephonyManager tm = (TelephonyManager) this.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//        LogX.getLogger().d("imei:" + tm.getDeviceId());//如果是GSM网络，返回IMEI；如果是CDMA网络，返回MEID
//        LogX.getLogger().d("imsi:" + tm.getSimSerialNumber()); // 返回SIM卡的序列号(IMEI)
//        LogX.getLogger().d("Line1Number:" + tm.getLine1Number()); // 返回SIM卡电话号码
//
//        LogX.getLogger().d("NetworkType:" + tm.getNetworkType());  //  获取网络类型 在中国，联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信的3G为EVDO
//        LogX.getLogger().d("version:" + Build.VERSION.RELEASE);// 4.4.2 android型号
//        LogX.getLogger().d("brand:" + Build.BRAND);//Sony
//        LogX.getLogger().d("device-sn:" + Build.SERIAL);//CB5A23QDW7
//        LogX.getLogger().d("device-model:" + Build.MODEL);// 型号L50t
//        //        logx.d("PRODUCT:" + Build.PRODUCT);//L50t
//        //        logx.d("DEVICE:" + Build.DEVICE);//L50t
//        LogX.getLogger().d("BOARD:" + Build.BOARD);//MSM8974  骁龙800 MSM8974（LTE)是高通2013年推出的Snapdragon 800系列产品
//        LogX.getLogger().d("HARDWARE:" + Build.HARDWARE);//qcom高通
        super.onCreate();
    }
}

