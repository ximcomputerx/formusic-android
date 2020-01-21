package com.ximcomputerx.formusic.ui.activity.setting;

import android.view.View;
import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class ScanActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    protected ImageView iv_back;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .navigationBarColor(R.color.white)
                .navigationBarDarkIcon(true).init();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_back})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
