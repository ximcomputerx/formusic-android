package com.ximcomputerx.formusic.ui.activity.setting;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.base.BaseActivity;
import com.ximcomputerx.formusic.utils.GlideImageLoaderUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {
    @Bind(R.id.iv_icon)
    protected ImageView iv_icon;
    @Bind(R.id.iv_back)
    protected ImageView iv_back;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_about;
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


    @OnClick({R.id.iv_back, R.id.ll_www, R.id.ll_open, R.id.ll_copyright, R.id.ll_version})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_www:
                //viewPager.setCurrentItem(0);
                // 分享到
                //String shareText = mTitle + " " + webView.getUrl() + " (分享自云阅 https://fir.im/cloudreader)";
                //ShareUtils.share(WebViewActivity.this, shareText);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri targetUrl = Uri.parse("http://www.formusic.me");
                intent.setData(targetUrl);
                startActivity(intent);
                break;
            case R.id.ll_open:
                //viewPager.setCurrentItem(1);
                break;
            case R.id.ll_copyright:
                //viewPager.setCurrentItem(1);
                break;
            case R.id.ll_version:
                //viewPager.setCurrentItem(1);
                break;
        }
    }
}
