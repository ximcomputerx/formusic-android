package com.ximcomputerx.formusic.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.ximcomputerx.formusic.application.ForMusicApplication;

import es.dmoral.toasty.Toasty;

/**
 * @AUTHOR HACKER
 */
public class ToastUtil {

    public static void showShort(Context context, String info) {
        if (null != context) {
            //Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
            Toasty.normal(context, info, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLong(Context context, String info) {
        if (null != context) {
            //Toast.makeText(context, info, Toast.LENGTH_LONG).show();
            Toasty.normal(context, info, Toast.LENGTH_LONG).show();
        }
    }

    public static void showTime(Context context, String info, int time) {
        if (null != context) {
            //Toast.makeText(context, info, time).show();
            Toasty.normal(context, info, time).show();
        }
    }

    public static void showLooperToast(Context context, String msg) {
        try {
            Looper.prepare();
            //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Toasty.normal(context, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        } catch (Exception e) {

        }
    }

    public static void showToast(String msg) {
        //Toast.makeText(ForMusicApplication.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        Toasty.normal(ForMusicApplication.getInstance().getApplicationContext(), msg).show();
    }

}
