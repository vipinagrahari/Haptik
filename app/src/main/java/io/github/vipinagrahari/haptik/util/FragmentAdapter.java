package io.github.vipinagrahari.haptik.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vipin on 19/11/16.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    int itemCount;
    List<Fragment> fragments;
    List<String> fragmentTitles;

    public FragmentAdapter(FragmentManager fm, int itemCount) {
        super(fm);
        this.itemCount = itemCount;
        fragments = new ArrayList<>(itemCount);
        fragmentTitles = new ArrayList<>(itemCount);

    }

    public void setItem(int location, Fragment fragment, String title) {
        fragments.add(location, fragment);
        fragmentTitles.add(location, title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return itemCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
}