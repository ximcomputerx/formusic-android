package com.ximcomputerx.formusic.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.ximcomputerx.formusic.service.PlayService;

import org.litepal.LitePal;

/**
 * @AUTHOR HACKER
 */
public class ForMusicApplication extends Application {
    private static ForMusicApplication instance;

    private String token;

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

        LitePal.initialize(this);

        // leakcanary install
        refWatcher = LeakCanary.install(this);

        // 创建服务
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