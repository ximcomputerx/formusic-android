package com.ximcomputerx.formusic.util;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Field;

public class ToolbarUtils {
    public static TextView getToolbarTitleView(Toolbar toolbar) {

        try {

            Field field = toolbar.getClass().getDeclaredField("mTitleTextView");
            field.setAccessible(true);

            Object object = field.get(toolbar);
            if (object !=null) {
                TextView mTitleTextView = (TextView) object;
                return mTitleTextView;
            }
            //Ln.d("toolbarUtils :" + "mTitleTextView do not find");
        } catch (IllegalAccessException e) {
           // Ln.d("toolbarUtils :" + "IllegalAccessException");
        } catch (NoSuchFieldException e) {
            //Ln.d(e);
        }catch (Exception e) {
            //Ln.d("toolbarUtils :" + "paser error");
        }
        return null;
    }

    public static void setMarqueeForToolbarTitleView(final Toolbar toolbar) {
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                TextView mTitleTextView = getToolbarTitleView(toolbar);
                if (mTitleTextView == null) {
                    //Ln.d("toolbarUtils :" + "mTitleTextView is null");
                    return;
                }
                mTitleTextView.setHorizontallyScrolling(true);
                mTitleTextView.setMarqueeRepeatLimit(-1);
                mTitleTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                mTitleTextView.setSelected(true);
                //Ln.d("toolbarUtils :" + "mTitleTextView set successfully ");
            }
        });

    }
}