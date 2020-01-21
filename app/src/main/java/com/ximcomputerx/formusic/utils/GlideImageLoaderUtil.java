package com.ximcomputerx.formusic.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.ximcomputerx.formusic.application.ForMusicApplication;

/**
 * @AUTHOR HACKER
 */
public class GlideImageLoaderUtil {
    public static void displayImageInActivity(Activity activity, String path, ImageView imageView) {
        Glide.with(activity)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void displayImageInFragment(Fragment fragment, String path, ImageView imageView) {
        Glide.with(fragment)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void displayImage(String path, ImageView imageView) {
        Glide.with(ForMusicApplication.getInstance().getApplicationContext())
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void displayImage(String path, ImageView imageView, int resourceId) {
        Glide.with(ForMusicApplication.getInstance().getApplicationContext())
                .load(path)
                .placeholder(resourceId)
                .error(resourceId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void displayImage(Context context, String path, ImageView imageView, Drawable defaultDrawable, int width, int height) {
        Glide.with(context)
                .load(path)
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void displayRoundImage(String path, ImageView imageView, int resourceId) {
        Glide.with(ForMusicApplication.getInstance().getApplicationContext())
                .load(path)
                //.dontAnimate()
                .transition(DrawableTransitionOptions.withCrossFade())
                .thumbnail(Glide.with(ForMusicApplication.getInstance().getApplicationContext()).load(resourceId)
                        .transform(new CenterCrop(), new GlideRoundTransform(ForMusicApplication.getInstance().getApplicationContext(), 10)))
                .transform(new CenterCrop(), new GlideRoundTransform(ForMusicApplication.getInstance().getApplicationContext(), 10))
                //.placeholder(resourceId)
                //.error(resourceId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void displayRoundImage(Integer resource, ImageView imageView, int resourceId) {
        Glide.with(ForMusicApplication.getInstance().getApplicationContext())
                .load(resourceId)
                //.dontAnimate()
                .transition(DrawableTransitionOptions.withCrossFade())
                .thumbnail(Glide.with(ForMusicApplication.getInstance().getApplicationContext()).load(resourceId)
                        .transform(new CenterCrop(), new GlideRoundTransform(ForMusicApplication.getInstance().getApplicationContext(), 10)))
                .transform(new CenterCrop(), new GlideRoundTransform(ForMusicApplication.getInstance().getApplicationContext(), 10))
                //.placeholder(resourceId)
                //.error(resourceId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(imageView);
    }
}