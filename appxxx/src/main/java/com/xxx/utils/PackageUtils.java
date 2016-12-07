package com.xxx.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.io.File;

/**
 * Created by kuaX on 2016/12/5.
 */

public class PackageUtils {
    public static final String TAG = "PackageUtils";

    private PackageUtils() {
        throw new AssertionError();
    }

    public static PackageInfo getPackageInfo(Context context){
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
//            LogUtils.e(e.getLocalizedMessage());
        }
        return  new PackageInfo();
    }


}
