package com.ximcomputerx.formusic.ui.activity.setting;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.base.BaseActivity;
import com.ximcomputerx.formusic.utils.BaseTools;
import com.ximcomputerx.formusic.utils.GlideImageLoaderUtil;
import com.ximcomputerx.formusic.utils.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class IssuesActivity extends BaseActivity {
    @Bind(R.id.iv_icon)
    protected ImageView iv_icon;
    @Bind(R.id.iv_back)
    protected ImageView iv_back;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_issues;
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
        GlideImageLoaderUtil.displayRoundImage(R.mipmap.ic_launcher, iv_icon, R.mipmap.ic_launcher);
    }

    @OnClick({R.id.iv_back, R.id.ll_qq, R.id.ll_mail})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_qq:
                BaseTools.joinQQChat(this, "2022034924");
                break;
            case R.id.ll_mail:
                try {
                    Intent data = new Intent(Intent.ACTION_SENDTO);
                    data.setData(Uri.parse("mailto:ximcomputerx@gmail.com"));
                    startActivity(data);
                } catch (Exception e) {
                    ToastUtil.showToast("请先安装邮箱");
                }
                break;
        }
    }
}
