package com.ximcomputerx.formusic.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.leakcanary.RefWatcher;
import com.wang.avi.AVLoadingIndicatorView;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.application.ForMusicApplication;
import com.ximcomputerx.formusic.network.ApiWrapper;
import com.ximcomputerx.formusic.utils.ActivityManagerUtil;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * @AUTHOR HACKER
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static String TAG = "BaseActivity";

    protected static Handler handler = new Handler();

    protected Context context;

    protected Intent intent;

    protected boolean flag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TAG = this.getClass().getSimpleName();
        ActivityManagerUtil.create().addActivity(this);
        this.setContentView(this.setLayoutId());

        ButterKnife.bind(this);
        initTitleBar();
        initView();
        initData();
    }

    protected abstract int setLayoutId();

    protected void initTitleBar() {
    }

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ActivityManagerUtil.create().finishActivity(this);
        System.gc();

        ButterKnife.unbind(this);
        RefWatcher refWatcher = ForMusicApplication.getRefWatcher(this);
        refWatcher.watch(this);

        super.onDestroy();
    }

    @Override
    public void finish() {
        System.gc();
        super.finish();
    }

    public ForMusicApplication getApp() {
        return (ForMusicApplication) getApplication();
    }

    private WeakReference<Dialog> netDialog;
    private AVLoadingIndicatorView avl_load;
    public void showNetDialog() {
        if (netDialog == null || netDialog.get() == null) {
            Dialog dialog = new Dialog(context, R.style.NormalDialog);
            RelativeLayout layout = (RelativeLayout) View.inflate(context, R.layout.item_progress_dialog, null);
            avl_load = layout.findViewById(R.id.avl_load);
            avl_load.show();
            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            netDialog = new WeakReference<Dialog>(dialog);
        } else {
            if (!netDialog.get().isShowing()) {
                avl_load.show();
                netDialog.get().show();
            }
        }
    }

    public void closeNetDialog() {
        if (netDialog != null && netDialog.get() != null && netDialog.get().isShowing()) {
            netDialog.get().dismiss();
            avl_load.show();
        }
    }


    public ApiWrapper getApiWrapper(boolean show) {
        if (show) {
            showNetDialog();
        }
        return ApiWrapper.getInstance();
    }
}
