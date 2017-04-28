package com.wzgiceman.rxretrofitlibrary.retrofit_rx.database.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by kuaX on 2017/3/23.
 */
@Entity
public class ConfigInfo {
    @Id
    private String name;

    private String value;

    @Generated(hash = 1241974810)
    public ConfigInfo(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Generated(hash = 724259026)
    public ConfigInfo() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
