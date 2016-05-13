package com.example.taher.localarea;

/**
 * Created by root on 4/25/16.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageTabAdapter extends FragmentPagerAdapter {
    private int numberOfTabs = 3;

    public PageTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return numberOfTabs;
    }

}
