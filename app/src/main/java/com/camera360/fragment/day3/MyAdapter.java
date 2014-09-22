package com.camera360.fragment.day3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by zhouwei on 14-9-22.
 */
public class MyAdapter extends FragmentPagerAdapter {
    static final int NUM_ITEMS = 10;

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return ArrayListFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
