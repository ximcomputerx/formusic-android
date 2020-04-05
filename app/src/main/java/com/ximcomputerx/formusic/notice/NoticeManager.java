package com.ximcomputerx.formusic.notice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.application.ForMusicApplication;
import com.ximcomputerx.formusic.config.Constant;
import com.ximcomputerx.formusic.model.MusicInfo;
import com.ximcomputerx.formusic.receiver.StatusBarReceiver;
import com.ximcomputerx.formusic.service.PlayService;
import com.ximcomputerx.formusic.ui.activity.MainActivity;

/**
 * 通知栏管理器
 */
public class NoticeManager {
    private static final int NOTIFICATION_ID = 0x111;
    private PlayService playService;
    private NotificationManager notificationManager;
    private RemoteViews remoteViews;

    /**
     * 单实例
     * @return
     */
    public static NoticeManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static NoticeManager instance = new NoticeManager();
    }

    private NoticeManager() {

    }

    /**
     * 初始化通知栏
     * @param playService
     */
    public void init(PlayService playService) {
        this.playService = playService;
        notificationManager = (NotificationManager) playService.getSystemService(Context.NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(ForMusicApplication.getInstance().getPackageName(), R.layout.notification_music);
    }

    /**
     * 显示音乐播放信息
     * @param music
     */
    public void showPlay(final MusicInfo music) {
        if (music == null) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(ForMusicApplication.getInstance().getResources(), R.mipmap.play_page_default_cover);
        playService.startForeground(NOTIFICATION_ID, buildNotification(playService, music, true, bitmap));
        Glide.with(ForMusicApplication.getInstance())
                .load(music.getCoverPath())
                .into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                playService.startForeground(NOTIFICATION_ID, buildNotification(playService, music, true, drawableToBitmap(resource)));
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
        //playService.startForeground(NOTIFICATION_ID, buildNotification(playService, music, true));
    }

    /**
     * 显示音乐停止信息
     * @param music
     */
    public void showPause(final MusicInfo music) {
        if (music == null) {
            return;
        }
        //playService.stopForeground(false);
        Bitmap bitmap = BitmapFactory.decodeResource(ForMusicApplication.getInstance().getResources(), R.mipmap.play_page_default_cover);
        notificationManager.notify(NOTIFICATION_ID, buildNotification(playService, music, false, bitmap));
        Glide.with(ForMusicApplication.getInstance())
                .load(music.getCoverPath())
                .into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                notificationManager.notify(NOTIFICATION_ID, buildNotification(playService, music, false, drawableToBitmap(resource)));
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });

    }

    public void cancelAll() {
        notificationManager.cancelAll();
    }

    /**
     * 构建通知栏
     * @param context
     * @param music
     * @param isPlaying
     * @return
     */
    private Notification buildNotification(Context context, MusicInfo music, boolean isPlaying, Bitmap bitmap) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constant.EXTRA_NOTIFICATION, true);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmaps = BitmapFactory.decodeResource(context.getResources(), R.mipmap.play_page_default_cover);

        // 构建通知栏
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.play_page_default_cover)
                .setLargeIcon(bitmaps)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCustomContentView(getRemoteViews(context, music, isPlaying, bitmap));

        //增加一個渠道，ID不重复即可
        String CHANNEL_ID = "123456";
        String CHANNEL_NAME = "听音";
        String DESCRIPTION = "听音";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //修改安卓8.1以上系统报错
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_MIN);
            notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
            notificationChannel.setShowBadge(false);//是否显示角标
            notificationChannel.setShowBadge(false);//是否显示角标
            notificationChannel.setDescription(DESCRIPTION);//是否显示角标
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            NotificationManager manager = (NotificationManager) playService.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
            builder.setChannelId(CHANNEL_ID);
        }
        return builder.build();
    }

    /**
     * 获取自定义布局
     * @param context
     * @param music
     * @param isPlaying
     * @return
     */
    private RemoteViews getRemoteViews(Context context, MusicInfo music, boolean isPlaying, Bitmap bitmap) {
        String title = music.getTitle();
        String subtitle = music.getArtist();

        //实例化RemoteViews对象，传入当前应用的包名和自定义的通知样式布局
        //RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_music);
        /*if (cover != null) {
            remoteViews.setImageViewBitmap(R.id.iv_icon, cover);
        } else {
            remoteViews.setImageViewResource(R.id.iv_icon, R.mipmap.ic_launcher);
        }*/
        /*Glide.with(playService)
                .asBitmap()
                .load(music.getCoverPath())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        //ToastUtil.showShortToast(playService, "加载成功。。。。");
                        remoteViews.setImageViewBitmap(R.id.iv_icon, resource);
                    }
                });*/
        remoteViews.setImageViewBitmap(R.id.iv_icon, bitmap);
        //remoteViews.setImageViewResource(R.id.iv_icon, R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.tv_title, title);
        remoteViews.setTextViewText(R.id.tv_subtitle, subtitle);

        // 布局点击事件
        Intent playIntent = new Intent(StatusBarReceiver.ACTION_STATUS_BAR);
        playIntent.setComponent(new ComponentName("com.ximcomputerx.formusic", "com.ximcomputerx.formusic.receiver.StatusBarReceiver"));
        playIntent.putExtra(StatusBarReceiver.EXTRA, StatusBarReceiver.EXTRA_PLAY_PAUSE);

        PendingIntent playPendingIntent = PendingIntent.getBroadcast(context, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setImageViewResource(R.id.iv_play_pause, getPlayIconRes(isPlaying));
        //为按钮设置点击事件
        remoteViews.setOnClickPendingIntent(R.id.iv_play_pause, playPendingIntent);

        Intent nextIntent = new Intent(StatusBarReceiver.ACTION_STATUS_BAR);
        nextIntent.setComponent(new ComponentName("com.ximcomputerx.formusic", "com.ximcomputerx.formusic.receiver.StatusBarReceiver"));
        nextIntent.putExtra(StatusBarReceiver.EXTRA, StatusBarReceiver.EXTRA_NEXT);

        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, 1, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setImageViewResource(R.id.iv_next, getNextIconRes());
        //为按钮设置点击事件
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

    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
}
