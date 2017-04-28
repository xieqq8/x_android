package com.xxx.mvvmdemo.bean;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

/**
 * Created by kuaX on 2017/4/21.
 */

public class UserLoginBean {
    public ObservableField<String> mFirstName = new ObservableField<>();
    public final ObservableField<String> mPassword = new ObservableField<>();

    public String userface;

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    @BindingAdapter("userface_img")
    public static void getInternetImage(ImageView iv, String userface) {
        // 图片加载
        Glide.with(iv.getContext()).load(userface).skipMemoryCache(true).into(iv); // 这个快
        Toast.makeText(iv.getContext(),"Glide Picasso" + userface, Toast.LENGTH_SHORT).show();
//        Picasso.with(iv.getContext()).load(userface).into(iv); // 不加载GIF用这个??
    }
//    public UserLoginBean(String firstName, String password) {
//        this. mFirstName = firstName;
//        this. mPassword = password;
//    }

//    public String getmFirstName() {
//        return mFirstName;
//    }
//
//    public void setmFirstName(String mFirstName) {
//        this.mFirstName = mFirstName;
//    }
//
//    public String getmPassword() {
//        return mPassword;
//    }
//
//    public void setmPassword(String mPassword) {
//        this.mPassword = mPassword;
//    }
}
