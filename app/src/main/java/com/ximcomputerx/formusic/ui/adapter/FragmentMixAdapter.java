package com.ximcomputerx.formusic.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @CREATED HACKER
 */
public class FragmentMixAdapter extends FragmentPagerAdapter {
    private String[] indicators;
    private ArrayList<Fragment> fragments;

    public FragmentMixAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] indicators) {
        super(fm);
        this.fragments = fragments;
        this.indicators = indicators;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return indicators[position];
    }
}
