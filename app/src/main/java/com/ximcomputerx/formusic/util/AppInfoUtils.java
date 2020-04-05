package com.ximcomputerx.formusic.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/**
 * Created by Neo on 15/7/14.
 */
public class AppInfoUtils {
    /**
     * Retrieve launcher activity name of the application from the context
     *
     * @param context The context of the application package.
     * @return launcher activity name of this application. From the
     * "android:name" attribute.
     */
    public static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        // To limit the components this Intent will resolve to, by setting an
        // explicit package name.
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // All Application must have 1 Activity at least.
        // Launcher activity must be found!
        ResolveInfo info = packageManager
                .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        // get a ResolveInfo containing ACTION_MAIN, CATEGORY_LAUNCHER
        // if there is no Activity which has filtered by CATEGORY_DEFAULT
        if (info == null) {
            info = packageManager.resolveActivity(intent, 0);
        }
        //////////////////////另一种实现方式//////////////////////
        // ComponentName componentName = context.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName()).getComponent();
        // return componentName.getClassName();
        //////////////////////另一种实现方式//////////////////////
        return info.activityInfo.name;
    }



    /**
     * 获取当前应用版本号
     *
     * @return
     * @throws Exception
     */
    public static String getVersionCode(Context mContext) {
        String version = "";
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = mContext.getPackageManager();
            // getPack()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            version = packInfo.versionCode + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取当前应用版本号
     *
     * @return
     * @throws Exception
     */
    public static String getVersionName(Context mContext) {
        String version = "";
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = mContext.getPackageManager();
            // getPack()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            version = packInfo.versionName + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 检查当前版本号
     *
     * @param context
     * @param versionCode
     * @return
     */
    public static boolean checkVersion(Context context, String versionCode) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            if (Integer.parseInt(versionCode) > version) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}