package com.ximcomputerx.formusic.notice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.config.Constant;
import com.ximcomputerx.formusic.model.MusicInfo;
import com.ximcomputerx.formusic.receiver.StatusBarReceiver;
import com.ximcomputerx.formusic.service.PlayService;
import com.ximcomputerx.formusic.ui.activity.main.MainActivity;

public class NoticeManager {
    private static final int NOTIFICATION_ID = 0x111;
    private PlayService playService;
    private NotificationManager notificationManager;

    public static NoticeManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static NoticeManager instance = new NoticeManager();
    }

    private NoticeManager() {

    }

    public void init(PlayService playService) {
        this.playService = playService;
        notificationManager = (NotificationManager) playService.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void showPlay(MusicInfo music) {
        if (music == null) {
            return;
        }
        playService.startForeground(NOTIFICATION_ID, buildNotification(playService, music, true));
    }

    public void showPause(MusicInfo music) {
        if (music == null) {
            return;
        }
        playService.stopForeground(false);
        notificationManager.notify(NOTIFICATION_ID, buildNotification(playService, music, false));
    }

    public void cancelAll() {
        notificationManager.cancelAll();
    }

    private Notification buildNotification(Context context, MusicInfo music, boolean isPlaying) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constant.EXTRA_NOTIFICATION, true);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notification)
                .setCustomContentView(getRemoteViews(context, music, isPlaying));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //修改安卓8.1以上系统报错
            NotificationChannel notificationChannel = new NotificationChannel("111", "134124323", NotificationManager.IMPORTANCE_MIN);
            notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
            notificationChannel.setShowBadge(false);//是否显示角标
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            NotificationManager manager = (NotificationManager) playService.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
            builder.setChannelId("111");
        }
        return builder.build();
    }

    private RemoteViews getRemoteViews(Context context, MusicInfo music, boolean isPlaying) {
        String title = music.getTitle();
        String subtitle = music.getArtist();

        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_music);
        /*if (cover != null) {
            remoteViews.setImageViewBitmap(R.id.iv_icon, cover);
        } else {
            remoteViews.setImageViewResource(R.id.iv_icon, R.mipmap.ic_launcher);
        }*/
        Glide.with(playService)
                .asBitmap()
                .load(music.getCoverPath())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        //ToastUtil.showShort(playService, "加载成功。。。。");
                        remoteViews.setImageViewBitmap(R.id.iv_icon, resource);
                    }
                });
        remoteViews.setTextViewText(R.id.tv_title, title);
        remoteViews.setTextViewText(R.id.tv_subtitle, subtitle);

        Intent playIntent = new Intent(StatusBarReceiver.ACTION_STATUS_BAR);
        playIntent.setComponent(new ComponentName("com.ximcomputerx.music", "com.ximcomputerx.music.receiver.StatusBarReceiver"));
        playIntent.putExtra(StatusBarReceiver.EXTRA, StatusBarReceiver.EXTRA_PLAY_PAUSE);

        PendingIntent playPendingIntent = PendingIntent.getBroadcast(context, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setImageViewResource(R.id.iv_play_pause, getPlayIconRes(isPlaying));
        remoteViews.setOnClickPendingIntent(R.id.iv_play_pause, playPendingIntent);

        Intent nextIntent = new Intent(StatusBarReceiver.ACTION_STATUS_BAR);
        nextIntent.setComponent(new ComponentName("com.ximcomputerx.music", "com.ximcomputerx.music.receiver.StatusBarReceiver"));
        nextIntent.putExtra(StatusBarReceiver.EXTRA, StatusBarReceiver.EXTRA_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, 1, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setImageViewResource(R.id.iv_next, getNextIconRes());
        remoteViews.setOnClickPendingIntent(R.id.iv_next, nextPendingIntent);

        return remoteViews;
    }

    private int getPlayIconRes(boolean isPlaying) {
        if (isPlaying) {
            return R.mipmap.ic_status_bar_pause_dark;
        } else {
            return R.mipmap.ic_status_bar_play_dark;
        }
    }

    private int getNextIconRes() {
        return R.mipmap.ic_status_bar_next_dark;
    }
}
