package com.xxx.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by lousx on 2017/2/23.
 */

public class GlideConfiguration implements GlideModule {
    @Override
    public void applyOptions(final Context context, GlideBuilder glideBuilder) {
        int diskCacheSize = 1024 * 1024 * 30;//最多可以缓存多少字节的数据
        int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存
        glideBuilder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "art", diskCacheSize));
        // 自定义内存和图片池大小
        glideBuilder.setMemoryCache(new LruResourceCache(memorySize));
        glideBuilder.setBitmapPool(new LruBitmapPool(memorySize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
