package com.zst.ynh.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class LoanRecordPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> tabFragments;
    private List<String> tabtitle;

    public LoanRecordPagerAdapter(FragmentManager fm,List<Fragment> tabFragments,List<String> tabtitle) {
        super(fm);
        this.tabFragments=tabFragments;
        this.tabtitle=tabtitle;
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position);
    }

    @Override
    public int getCount() {
        return tabFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitle.get(position);
    }
}
