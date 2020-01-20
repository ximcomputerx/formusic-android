package com.ximcomputerx.formusic.utils;

import android.content.res.Resources;

import com.ximcomputerx.formusic.application.MusicApplication;

/**
 * Created by jingbin on 2016/11/22.
 * 获取原生资源
 */
public class CommonUtils {


    private static Resources getResoure() {
        return MusicApplication.getInstance().getResources();
    }

    public static String[] getStringArray(int resid) {
        return getResoure().getStringArray(resid);
    }

    public static String getString(int resid) {
        return getResoure().getString(resid);
    }

    public static float getDimens(int resId) {
        return getResoure().getDimension(resId);
    }

}
