package com.ximcomputerx.formusic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ximcomputerx.formusic.config.Actions;
import com.ximcomputerx.formusic.notice.NoticeManager;
import com.ximcomputerx.formusic.play.MediaSessionManager;
import com.ximcomputerx.formusic.play.PlayManager;
import com.ximcomputerx.formusic.utils.LogUtil;

/**
 * @CREATED HACKER
 */
public class PlayService extends Service {
    private static final String TAG = "PlayService";

    /**
     * 可以通过binder获取service实例
     */
    public class PlayBinder extends Binder {
        public PlayService getService() {
            return PlayService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayBinder();
    }

    /**
     * 在第一次创建服务是调用，多次执行startservice()不会重复调用此方法
     */
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, "onCreate():" + getClass().getSimpleName());
        PlayManager.getInstance().init(this);
        MediaSessionManager.getInstance().init(this);
        NoticeManager.getInstance().init(this);
        // TODO
        //QuitTimer.get().init(this);
    }

    /**
     * 多次执行startservice()，此方法会被多次调用
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case Actions.ACTION_STOP:
                    stop();
                    break;
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        LogUtil.i(TAG, "onDestroy():" + getClass().getSimpleName());
        super.onDestroy();
    }

    public static void startCommand(Context context, String action) {
        Intent intent = new Intent(context, PlayService.class);
        intent.setAction(action);
        context.startService(intent);
    }

    /**
     * 停止播放音乐
     */
    private void stop() {
        PlayManager.getInstance().stopPlayer();
        //NoticeManager.getInstance().cancelAll();
        // TODO
        //QuitTimer.get().stop();
    }

}
