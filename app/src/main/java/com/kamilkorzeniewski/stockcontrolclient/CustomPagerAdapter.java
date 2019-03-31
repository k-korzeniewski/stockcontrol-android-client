package com.kamilkorzeniewski.stockcontrolclient;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class CustomPagerAdapter extends FragmentStatePagerAdapter {

    private List<CustomFragment> fragments;

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    public void addFragment(CustomFragment fragment){
        fragments.add(fragment);
    }
    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getPageTitle();
    }
}
