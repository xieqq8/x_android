package xxx.com.dbutil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import xxx.com.dbutil.entity.ConfigInfo;
import xxx.com.dbutil.greendao.ConfigInfoDao;
import xxx.com.dbutil.greendao.DaoMaster;
import xxx.com.dbutil.greendao.DaoSession;


/**
 * 配置信息单例类
 * Created by xieqq on 2015-09-24 .
 */
public class ConfigUtil {


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
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(appContext, CommonDB.DB_NAME, null);
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
}
