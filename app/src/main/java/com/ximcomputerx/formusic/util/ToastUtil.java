package com.ximcomputerx.formusic.util;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.application.ForMusicApplication;

/**
 * @AUTHOR HACKER
 */
public class ToastUtil {
    public static void showShort(Context context, String info) {
        if (null != context) {
            Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLong(Context context, String info) {
        if (null != context) {
            Toast.makeText(context, info, Toast.LENGTH_LONG).show();
        }
    }

    public static void showTime(Context context, String info, int time) {
        if (null != context) {
            Toast.makeText(context, info, time).show();
        }
    }

    public static void showLooperToast(Context context, String info) {
        try {
            Looper.prepare();
            Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showShortToast(String info) {
        Toast toast = new Toast(ForMusicApplication.getInstance().getInstance());
        View view = View.inflate(ForMusicApplication.getInstance().getInstance(), R.layout.toast_custom, null);
        ((TextView) view.findViewById(R.id.tv_content)).setText(info);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showLongToast(String info) {
        Toast toast = new Toast(ForMusicApplication.getInstance().getInstance());
        View view = View.inflate(ForMusicApplication.getInstance().getInstance(), R.layout.toast_custom, null);
        ((TextView) view.findViewById(R.id.tv_content)).setText(info);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
