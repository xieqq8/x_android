package com.wzgiceman.rxretrofitlibrary.retrofit_rx.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.database.Entity.ConfigInfo;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.database.Entity.ConfigInfoDao;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.database.Entity.DaoMaster;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.database.Entity.DaoSession;

import java.util.List;


/**
 * 配置信息单例类
 * Created by xieqq on 2015-09-24 .
 */
public class ConfigUtil {
//    public static final String WX_APP_ID = "wxf8c494e4e419cd18";
//    public static final String WX_APP_SIGN = "0a1b7889762d608deae3f484b3c52601";
//    public static final String WX_APP_SECRET = "60bd81a03befae976b3841879cbedb9a";
//
//    public static final String QQ_APP_ID = "1105625070";
//    public static final String QQ_APP_KEY = "oUcdFCAiW6X0Eo3v";
//
//    public static final String WEIBO_APP_ID = "428833581";
//    public static final String WEIBO_APP_SECRET = "13875f5871a3f0c7ea7a8165329c5a77";

    public static final String CC_USER = "0C728A4805962911";
    public static final String CC_API_KEY = "DcI2zyLMbbfPtVJ8KGOMjU2nFO0NVbLz";
    //    public static final String C_ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String DB_NAME = "kx_vl_db";

    //
//    public static final String C_Local_Xg_token = "user_local_xg_token";
//
//    public static final String C_USER_ID = "user_id";
    public static final String C_USER_NAME = "user_name";
    public static final String C_USER_GRADE = "user_grade";

    public static final String C_weike_url = "weiku_url";
    public static final String C_mingshi_url = "mingshi_url";

    //    public static final String C_REAL_NAME = "real_name";
//    public static final String C_USER_PASSWORD = "user_password";
//    public static final String C_USER_ROLE = "user_role";
//    public static final String C_USER_PHONE = "user_phone";
//    public static final String C_USER_IMAGE = "user_image";
//    public static final String C_USER_SEX = "user_sex";
//    public static final String C_USER_BIRTH = "user_birthday";
//    public static final String C_USER_PLRANGE = "user_plrange";
//    public static final String C_USER_PRICE = "user_price";
//    public static final String C_USER_ADDRESS = "user_address";
//    public static final String C_USER_SIGN = "user_sign";
//    public static final String C_USER_AUTH = "user_isauth";
//    public static final String C_USER_OPUS = "user_opus";
//    public static final String C_USER_RANK = "user_rank";
//    public static final String C_USER_ATTENTION = "user_attention";
//    public static final String C_USER_FSNUM = "user_fsnum";
//    public static final String C_USER_EVALUATE = "user_evaluate";
//    public static final String C_USER_EMAIL = "user_email";
//    public static final String C_USER_SCHOOL = "user_school";
//    public static final String C_USER_COMPANY = "user_company";
//    public static final String C_USER_OCCUPATION = "user_occupation";
//    public static final String C_USER_BACKGROUND = "user_background";
//    public static final String C_USER_PRAISE = "user_praise";
//
//    public static final String IMAGE_PATH = Environment.getExternalStorageDirectory() + "/artspace/";
//    public static final String HEAD_PATH = IMAGE_PATH + "avatar.jpg";
//    public static final String PORTRAIT_PATH = IMAGE_PATH + "portrait.jpg";
//    public static final String USER_OPUS_PATH = IMAGE_PATH + "opus.jpg";
//    public static final String CARD_THUMBNAILS_PATH = Environment.getExternalStorageDirectory() + "/artspace/thumbnails";
////>>>>>>> cf51a4df02bf830dcd2479bcf52d260d641b2016
//
//    // 录制语音，语音聊天  后缀用 .sas
//    public static final String SOUND_PATH = Environment.getExternalStorageDirectory() + "/artspace/as_sound";
//    public static final String Chat_IMAGE_PATH = Environment.getExternalStorageDirectory() + "/artspace/as_image";
//
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Cursor cursor;
    private ConfigInfoDao configDao;
    private Context appContext = null;
    private static ConfigUtil inst;

    private ConfigUtil() {
    }
    private ConfigUtil(Context context) {
        this.appContext = context;
    }
//
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
//
//    public boolean isLogin() {
//        if (GetConfigValue("user_id").equals("0"))
//            return false;
//        else
//            return true;
//    }
//
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
            return null;
        }
    }

    /**
     * 删除配置表参数值
     */
    public void delConfigValue() {
        configDao.deleteAll();
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
//
//    /**
//     * 得到下拉刷新时间
//     *
//     * @param refreshType
//     * @return
//     */
//    public String getRefreshTime(String refreshType) {
//        try {
//            return DateUtil.parseSpaceTime(Long.parseLong(GetConfigValue(refreshType)));
//        } catch (NumberFormatException e) {
//            return ""; // 之前没有
//        } finally {
//        }
//
//    }
}
