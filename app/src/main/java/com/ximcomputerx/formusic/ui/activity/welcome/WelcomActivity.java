package com.ximcomputerx.formusic.ui.activity.welcome;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.ui.activity.main.MainActivity;
import com.ximcomputerx.formusic.base.BaseActivity;
import com.ximcomputerx.formusic.config.Variable;
import com.ximcomputerx.formusic.utils.ToastUtil;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @AUTHER HACKER
 */
public class WelcomActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    public static final int LOCATION_PERMISSION = 10000;
    public static final int STORAGE_PERMISSION = 20000;
    public static final int CAMERA_PERMISSION = 30000;
    public static final int RECORD_AUDIO = 40000;

    private LocationClient locationClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.flag = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).fullScreen(true).hideBar(BarHide.FLAG_HIDE_BAR).init();
        //hideBottomUIMenu();
    }

    @Override
    protected void initData() {
        checkPermissions();

        enter();
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
        requestLocationPermission();
        requestCameraPermission();
        requestRecodeAudioPermission();
        requestExternalStoragePermission();
    }

    /**
     * 位置权限
     */
    @AfterPermissionGranted(LOCATION_PERMISSION)
    private void requestLocationPermission() {
        // Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            EasyPermissions.requestPermissions(this, "", LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
        } else {
            startLocation();
        }
    }

    /**
     * 相机权限
     */
    @AfterPermissionGranted(CAMERA_PERMISSION)
    public void requestCameraPermission() {
        // Manifest.permission.CAMERA
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            EasyPermissions.requestPermissions(this, "", CAMERA_PERMISSION, Manifest.permission.CAMERA, Manifest.permission.CAMERA);
        }
    }

    /**
     * 麦克风权限
     */
    @AfterPermissionGranted(RECORD_AUDIO)
    public void requestRecodeAudioPermission() {
        // Manifest.permission.RECORD_AUDIO
        if (EasyPermissions.hasPermissions(this, Manifest.permission.RECORD_AUDIO)) {
            EasyPermissions.requestPermissions(this, "", CAMERA_PERMISSION, Manifest.permission.RECORD_AUDIO, Manifest.permission.RECORD_AUDIO);
        }
    }

    /**
     * 存储卡读写权限
     */
    @AfterPermissionGranted(STORAGE_PERMISSION)
    public void requestExternalStoragePermission() {
        // Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, "", STORAGE_PERMISSION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * 权限申请系统回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发到easypermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 申请成功是回调
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    /**
     * 申请拒绝是回调
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

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

                    ToastUtil.showToast("定位成功");

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