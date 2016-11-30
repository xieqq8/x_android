package com.kuaxue.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kuaxue.database.dao.ConfigInfo;
import com.kuaxue.database.dao.ConfigInfoDao;
import com.kuaxue.database.dao.DaoMaster;
import com.kuaxue.database.dao.DaoSession;
import com.xxx.utils.DateUtil;

import java.util.List;

/**
 * 配置信息单例类
 * Created by xieqq on 2015-09-24 .
 */
public class ConfigUtil {

    public static final String C_ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String C_USERNAME = "USERNAME";
    public static final String DB_NAME = "kx_bm_db";

//    // imageLoader 加载选项
//    public static final DisplayImageOptions Image_loader_options = new DisplayImageOptions.Builder()
////            .showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
////    .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
////    .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
//            .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
//            .delayBeforeLoading(1000)  // 下载前的延迟时间
//            .cacheInMemory(true) // default  设置下载的图片是否缓存在内存中
//            .cacheOnDisk(true) // default  设置下载的图片是否缓存在SD卡中
////    .preProcessor(...)
////    .postProcessor(...)
////    .extraForDownloader(...)
//            .considerExifParams(false) // default
//            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default 设置图片以如何的编码方式显示
//            .bitmapConfig(Bitmap.Config.ARGB_8888) // default 设置图片的解码类型
////    .decodingOptions(...)  // 图片的解码设置
//            .displayer(new SimpleBitmapDisplayer()) // default  还可以设置圆角图片new RoundedBitmapDisplayer(20)
//            .handler(new Handler()) // default
//            .build();

    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Cursor cursor;
    private ConfigInfoDao configDao;
    private Context appContext = null;
    private static ConfigUtil inst;

    private ConfigUtil() {
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(appContext, DB_NAME, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        configDao = daoSession.getConfigInfoDao();
    }

    public Context getAppContext() {
        return appContext;
    }

    public static synchronized ConfigUtil Instance() {
        if (inst == null) {
            inst = new ConfigUtil();
        }
        return inst;
    }

    /**
     * 读取参数值
     *
     * @param strParm
     * @return
     */
    public String GetConfigValue(String strParm) {
        List<ConfigInfo> lst = configDao.queryBuilder().where(ConfigInfoDao.Properties.Name.eq(strParm)).list();
        if (lst.size() > 0) {
            return lst.get(0).getValue();
        } else {
            return "";
        }
    }

    /**
     * 设置参数值
     *
     * @param strParm 参数
     * @param strVar  值
     * @return
     */
    public long SetConfigValue(String strParm, String strVar) {
        ConfigInfo conInfo = new ConfigInfo();
        conInfo.setName(strParm);
        conInfo.setValue(strVar);
        return configDao.insertOrReplace(conInfo);
    }

    /**
     * 得到下拉刷新时间
     * @param refreshType
     * @return
     */
    public String getRefreshTime(String refreshType){
        try {
            return DateUtil.parseSpaceTime(Long.parseLong(GetConfigValue(refreshType)));
        } catch (NumberFormatException e) {
            return ""; // 之前没有
        } finally {
        }

    }
}
