package com.zst.ynh.adapter;

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

public class ContentPagerAdapter extends LazyFragmentPagerAdapter {
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

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tab_layout, null);
        ImageView iv_tab_icon = v.findViewById(R.id.iv_tab_icon);
        TextView tv_tab_text = v.findViewById(R.id.tv_tab_text);
        iv_tab_icon.setBackgroundResource(tabIcon[position]);
        tv_tab_text.setText(tabTitle[position]);
        if (position == 0) {
            tv_tab_text.setTextColor(v.getResources().getColor(R.color.color_999999));
        }
        return v;
    }

    @Override
    protected Fragment getItem(ViewGroup container, int position) {
        return tabFragments.get(position);
    }
}
