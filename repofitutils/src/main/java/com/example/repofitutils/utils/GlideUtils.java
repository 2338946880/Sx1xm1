package com.example.repofitutils.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.repofitutils.R;

public class GlideUtils {
    private GlideUtils(){}
    private static GlideUtils glideUtils;
    public static GlideUtils getInstance(){
        if (glideUtils==null){
            synchronized (GlideUtils.class){
                if (glideUtils==null){
                    glideUtils = new GlideUtils();
                }
            }
        }
        return glideUtils;
    }

    /**
     * 图片
     * @param context
     * @param url
     * @param imageView
     */
    public void loadNormal(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .skipMemoryCache(true)//关闭内存缓存
                .placeholder(R.drawable.logo2)//图片加载出来前，显示的图片
                .error(R.drawable.ic_launcher_background)//图片加载失败后，显示的图片
                //.transform(new CenterCrop())
                .into(imageView);
    }

    /**
     * 圆形图片
     * @param context
     * @param url
     * @param imageView
     */
    public void loadCircle(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .skipMemoryCache(true)//关闭内存缓存
                .transform(new CircleCrop())
                .into(imageView);
    }

    /**
     * 圆角图片
     * @param context
     * @param url
     * @param imageView
     */
    public void loadRounded(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .skipMemoryCache(true)//关闭内存缓存
                .transform(new CenterCrop(),new RoundedCorners(20))
                .into(imageView);
    }}
