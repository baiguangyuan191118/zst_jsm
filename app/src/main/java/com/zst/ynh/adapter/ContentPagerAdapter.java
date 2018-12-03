package com.zst.ynh.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zst.ynh.R;
import com.zst.ynh_base.lazyviewpager.LazyFragmentPagerAdapter;

import java.util.List;

public class ContentPagerAdapter extends FragmentPagerAdapter  {
    private List<Fragment> tabFragments;
    private int[] tabTitle;
    private int[] tabIcon;
    private Context context;
    public ContentPagerAdapter(FragmentManager fm, List<Fragment> tabFragments,int[] tabTitle,int[] tabIcon,Context context) {
        super(fm);
        this.tabFragments=tabFragments;
        this.tabTitle=tabTitle;
        this.tabIcon=tabIcon;
        this.context=context;
    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }

    @SuppressLint("ResourceAsColor")
    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tab_layout, null);
        ImageView iv_tab_icon = v.findViewById(R.id.iv_tab_icon);
        TextView tv_tab_text = v.findViewById(R.id.tv_tab_text);
        iv_tab_icon.setImageResource(tabIcon[position]);
        tv_tab_text.setText(tabTitle[position]);
        return v;
    }


    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position);
    }
}
