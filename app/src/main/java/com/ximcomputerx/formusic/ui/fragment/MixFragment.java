package com.ximcomputerx.formusic.ui.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.base.BaseFragment;
import com.ximcomputerx.formusic.ui.adapter.FragmentMixAdapter;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * @AUTHOR HACKER
 */
public class MixFragment extends BaseFragment {

    @Bind(R.id.psts_indicator)
    protected NavigationTabStrip psts_indicator;
    @Bind(R.id.viewPager)
    protected ViewPager viewPager;

    private String[] indicators = {"流行", "摇滚", "电子", "华语", "民谣", "怀旧"};
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private FragmentMixAdapter listFragmentAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_main_mix;
    }

    @Override
    protected void initView(View contentView) {
        viewPager.setOffscreenPageLimit(6);

        // 设置Tab标题文字的大小,传入的是sp
        // psts_indicator.setTextSize(14);
        // 设置选中Tab文字的颜色
        // psts_indicator.setSelectedTextColor(getResources().getColor(R.color.red));
        // 设置正常Tab文字的颜色
        // psts_indicator.setTextColor(getResources().getColor(R.color.black));
        // 设置Tab文字的左右间距,传入的是dp
        // psts_indicator.setTabPaddingLeftRight(25);
        // 是否支持动画渐变(颜色渐变和文字大小渐变)
        // psts_indicator.setFadeEnabled(false);
        // 设置最大缩放,是正常状态的0.3倍
        // psts_indicator.setZoomMax(0.3F);
        psts_indicator.setTitles(indicators);
    }

    @Override
    protected void initData() {
        fragments.add(new MixFragment1());
        fragments.add(new MixFragment2());
        fragments.add(new MixFragment3());
        fragments.add(new MixFragment4());
        fragments.add(new MixFragment5());
        fragments.add(new MixFragment6());
        listFragmentAdapter = new FragmentMixAdapter(getActivity().getSupportFragmentManager(), fragments, indicators);
        viewPager.setAdapter(listFragmentAdapter);
        psts_indicator.setViewPager(viewPager);
    }
}
