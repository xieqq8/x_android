package com.xxx.mvvmdemo.bean;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

/**
 * Created by kuaX on 2017/4/27.
 */

public class Food {
    private String description;
    private String img;
    private String keywords;
    private String summary;
//    public ObservableField<String> description = new ObservableField<>();

    public Food() {
    }

    public Food(String description, String img, String keywords, String summary) {
        this.description = description;
        this.img = img;
        this.keywords = keywords;
        this.summary = summary;
    }

//    @BindingAdapter({"bind:img","bind:error"})
//    @BindingAdapter({"img","error"})
    @BindingAdapter("img")      //@BindingAdapter("bind:img")
    public static void loadInternetImage(ImageView iv, String img) {
//        Picasso.with(iv.getContext()).load(img).into(iv);
        Glide.with(iv.getContext()).load(img).skipMemoryCache(true).into(iv); // 这个快

    }

    @BindingAdapter("img2")      //@BindingAdapter("bind:img")
    public static void loadInternetImage2(ImageView iv, String img) {
        Picasso.with(iv.getContext()).load(img).into(iv); // 这个好、快
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void onItemClick(View view) {
        Toast.makeText(view.getContext(), getDescription(), Toast.LENGTH_SHORT).show();
    }
}
