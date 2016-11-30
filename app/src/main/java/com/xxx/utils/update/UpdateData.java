package com.xxx.utils.update;

import java.io.Serializable;

/**
 * Model get-set
 * Created by xieqq on 2015-10-19 .
 */
public class UpdateData implements Serializable {
    /* "apkFileURL": "http://oss.kuaxue.com/app/2015-09-06/admin/49e7a53e-8de2-4bd2-bc3d-7b54e7a893d6.apk",
            "appName": "老师帮帮忙",
            "configType": "android",
            "id": 1,
            "inactive": false,
            "notes": "ddd",
            "pid": "com.kuaxue.laoshibang",
            "size": 15581,
            "versionCode": 1,
            "versionName": "1.0",
            "visibility": true*/
    private int id;
    private String appname;
    private String configType;
    private String apkFileURL;
    private boolean inactive;
    private String notes;
    private String pid;
    private int size;
    private String versionName;
    private int versionCode;
    private boolean visibility;
    private boolean forceUpdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getApkFileURL() {
        return apkFileURL;
    }

    public void setApkFileURL(String apkFileURL) {
        this.apkFileURL = apkFileURL;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
}
