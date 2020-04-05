package com.ximcomputerx.formusic.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.base.BaseActivity;
import com.ximcomputerx.formusic.config.Variable;
import com.ximcomputerx.formusic.util.ToastUtil;

import java.util.List;

/**
 * @AUTHER HACKER
 */
public class WelcomActivity extends BaseActivity {
    private String[] STORAGE_PERMS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private String[] CAMERA_PERMS = {Manifest.permission.CAMERA};
    private String[] MICROPHONE_PERMS = {Manifest.permission.RECORD_AUDIO};

    private LocationClient locationClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).fullScreen(true).hideBar(BarHide.FLAG_HIDE_BAR).init();
    }

    @Override
    protected void initData() {
        checkPermissions();
    }

    private void enter() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                {
                    intent = new Intent(context, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    /**
     * 检查权限
     */
    private void checkPermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                        //Manifest.permission.CAMERA,
                        //Manifest.permission.RECORD_AUDIO
                ).withListener(new MultiplePermissionsListener() {

            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    startLocation();
                } else {
                    ToastUtil.showShortToast("请授予软件权限");
                }
                enter();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        if (locationClient == null) {
            locationClient = new LocationClient(this);
            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            option.setIsNeedAddress(true);
            option.setOpenGps(true);
            option.setCoorType("gcj02");
            locationClient.setLocOption(option);
            locationClient.registerLocationListener(new BDLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation location) {
                    if (location == null) {
                        return;
                    }
                    Variable.location = location;

                    ToastUtil.showShortToast("定位成功");

                    if (locationClient != null && locationClient.isStarted()) {
                        locationClient.stop();
                        locationClient = null;
                    }
                }

                @Override
                public void onConnectHotSpotMessage(String s, int i) {

                }
            });
        }

        if (!locationClient.isStarted()) {
            locationClient.start();
            locationClient.requestLocation();
        }
    }

}