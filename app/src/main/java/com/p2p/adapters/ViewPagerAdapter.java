package com.p2p.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.p2p.fragments.NewsTabFragment;
import com.p2p.fragments.QuotationTabFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public static final int NUM_PAGER = 2;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new QuotationTabFragment();

            case 1:
                return new NewsTabFragment();

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return NUM_PAGER;
    }
}
