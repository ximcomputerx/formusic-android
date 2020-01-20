package com.ximcomputerx.formusic.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public abstract class BaseDialog extends Dialog implements View.OnClickListener {

    protected Context mContext;

    /**
     * 默认的handler
     */
    protected static Handler handler = new Handler();

    public BaseDialog(Context context) {
        super(context);
        mContext = context;
    }

    public BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initData();
        setCanceledOnTouchOutside(true);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                EventBus.getDefault().unregister(this);
                ButterKnife.unbind(this);
            }
        });
    }

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 设置对话框是正常宽度还是全屏
     *
     * @param isFullScreen <li>true:全屏<li>false:正常宽度
     */
    public void setFullScreen(boolean isFullScreen) {
        if (isFullScreen) {
            WindowManager manager = ((Activity)mContext).getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
            int width = outMetrics.widthPixels;
            //int height = outMetrics.heightPixels;

            WindowManager.LayoutParams p = getWindow().getAttributes();
            //p.height = (int) (height * 0.6);
            p.width = (int) ((width) * 1);
            this.getWindow().setAttributes(p);
        }
    }

    /**
     * 设置Dialog动画
     *
     * @param resId 资源id
     */
    public void setAnimation(int resId) {
        // 得到对话框
        Window window = getWindow();
        // 设置窗口弹出动画
        window.setWindowAnimations(resId);
    }

    /**
     * 设置对话框位置
     *
     * @param position Gravity.XXX
     */
    public void setGravity(int position) {
        Window window = getWindow();
        window.setGravity(position);
    }

    @Override
    public void onClick(View v) {
    }

}
