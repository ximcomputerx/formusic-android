package com.ximcomputerx.formusic.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.ximcomputerx.formusic.model.ApkDownloadInfo;
import com.yidd365.yiddcustomer.R;
import com.yidd365.yiddcustomer.config.Constant;
import com.yidd365.yiddcustomer.models.ApkDownloadInfo;
import com.yidd365.yiddcustomer.utils.LogUtil;
import com.yidd365.yiddcustomer.utils.NotifyUtil;
import com.yidd365.yiddcustomer.utils.StringUtils;

import java.io.File;

/**
 * Created by Neo on 15/7/14.
 */
public class ApkUpdateService extends Service {

    /**
     * 日志标识符
     */
    private final static String TAG = ApkUpdateService.class.getSimpleName();

    private ApkDownloadInfo downloadFile = null;
    private NotifyUtil notifyUtil = null;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        downloadFile = (ApkDownloadInfo) intent
                .getSerializableExtra(Constant.ApkUpdate.APK_AUTO_UPLOAD_PARAM_OBJ);
        if (downloadFile != null) {
            Intent contentIntent = new Intent();
            contentIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent rightPendIntent = PendingIntent.getActivity(this,
                    Constant.UPDATE_NOTIFY, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            String ticker = "您有一条更新通知";
            notifyUtil = new NotifyUtil(this, Constant.UPDATE_NOTIFY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notifyUtil.notify_progress(rightPendIntent, R.mipmap.ic_notification_logo, ticker, "发现新版本", "正在下载中");
            } else {
                notifyUtil.notify_progress(rightPendIntent, R.mipmap.ic_launcher, ticker, "发现新版本", "正在下载中");
            }
            startDownload();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownload() {
        // 删除旧安装包
        File oldFile = new File(downloadFile.getOldLocalFile());
        if (oldFile.exists()) {
            oldFile.delete();
            LogUtil.i(TAG,
                    "删除上一版本安装包：" + downloadFile.getOldLocalFile());
        }

        // 文件已存在直接返回执行安装操作
        File localFile = new File(downloadFile.getLocalFile());
        if (localFile.exists()) {
            LogUtil.i(TAG,
                    "新版本安装包已存在：" + downloadFile.getOldLocalFile()
                            + "，执行安装操作");
        }
        FileDownloader.getImpl().create(downloadFile.getRemoteFile()).setPath(downloadFile.getLocalFile()).setListener(fileDownloadListener).start();
    }

    FileDownloadListener fileDownloadListener = new FileDownloadListener() {
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            int progress = 0;
            if (totalBytes != 0) {
                progress = (int) (((soFarBytes * 100) / (float) totalBytes));
                notifyUtil.update_progress(progress);
            } else {
                notifyUtil.update_error(Constant.TASK_NOTIFY);
            }
        }

        @Override
        protected void blockComplete(BaseDownloadTask task) {
            notifyUtil.clear(Constant.TASK_NOTIFY);
            install(downloadFile.getLocalFile());
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            notifyUtil.update_complete(Constant.TASK_NOTIFY);
            // 安装应用程序
            install(downloadFile.getLocalFile());
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            notifyUtil.update_error(Constant.TASK_NOTIFY);
        }

        @Override
        protected void warn(BaseDownloadTask task) {
            notifyUtil.update_error(Constant.TASK_NOTIFY);
        }
    };

    /**
     * 调用打包安装程序安装应用
     *
     * @param apkFileName
     */
    public void install(String apkFileName) {
        if (StringUtils.isEmpty(apkFileName)) {
            LogUtil.e(TAG, "安装程序文件名为空");
            return;
        }
        try {
            File apkFile = new File(apkFileName);
            Uri uri = Uri.fromFile(apkFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            LogUtil.e(TAG, "安装文件不存在或有错误已停止执行");
            LogUtil.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

}