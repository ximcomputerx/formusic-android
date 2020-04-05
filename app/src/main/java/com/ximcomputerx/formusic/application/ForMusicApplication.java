package com.ximcomputerx.formusic.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.ximcomputerx.formusic.service.PlayService;

import org.litepal.LitePal;

/**
 * @AUTHOR HACKER
 */
public class ForMusicApplication extends Application {
    private static ForMusicApplication instance;

    private RefWatcher refWatcher;

    public synchronized static ForMusicApplication getInstance() {
        return instance;
    }

    public ForMusicApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 数据库框架
        LitePal.initialize(this);
        // 注册滑动
        registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
        // 内存泄漏
        refWatcher = LeakCanary.install(this);
        // 音乐服务
        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    public static RefWatcher getRefWatcher(Context context) {
        ForMusicApplication zhanBaoApplication = (ForMusicApplication) context.getApplicationContext();
        return zhanBaoApplication.refWatcher;
    }

}