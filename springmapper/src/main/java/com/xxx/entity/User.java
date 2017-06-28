package com.xxx.entity;

import java.util.Date;

public class User {
    // 属性名称和数据库字段名称保持一致
    private Integer id;
    // 姓名
    private String username;
    // 性别
    private String sex;
    // 地址
    private String address;
    // 生日
    private Date birthday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", sex=" + sex
                + ", address=" + address + ", birthday=" + birthday + "]";
    }

}