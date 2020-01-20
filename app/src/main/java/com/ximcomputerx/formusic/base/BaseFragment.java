package com.ximcomputerx.formusic.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.squareup.leakcanary.RefWatcher;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.application.MusicApplication;
import com.ximcomputerx.formusic.network.ApiWrapper;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * @AUTHOR HACKER
 */
public abstract class BaseFragment extends Fragment {

    protected Context context;

    protected static Handler handler = new Handler();

    protected View contentView;

    protected Intent intent;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(setLayoutId(), container, false);
        ButterKnife.bind(this, contentView);
        initView(contentView);
        initData();
        return contentView;
    }

    protected abstract int setLayoutId();

    protected abstract void initView(View contentView);

    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MusicApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    public MusicApplication getApp() {
        return (MusicApplication) MusicApplication.getInstance();
    }

    private WeakReference<Dialog> netDialog;

    public void showNetDialog() {
        if (netDialog == null || netDialog.get() == null) {
            Dialog dialog = new Dialog(context, R.style.NormalDialog);
            RelativeLayout layout = (RelativeLayout) View.inflate(context, R.layout.item_progress_dialog, null);
            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            netDialog = new WeakReference<Dialog>(dialog);
        } else {
            if (!netDialog.get().isShowing()) {
                netDialog.get().show();
            }
        }
    }

    public void closeNetDialog() {
        if (netDialog != null && netDialog.get() != null && netDialog.get().isShowing()) {
            netDialog.get().dismiss();
        }
    }

    protected ApiWrapper getApiWrapper(boolean show) {
        if (show) {
            showNetDialog();
        }
        return ApiWrapper.getInstance();
    }
}