package com.zst.ynh.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zst.ynh.R;
import com.zst.ynh.bean.TabListBean;
import com.zst.ynh_base.util.ImageLoaderUtils;

import java.util.List;

public class ContentPagerAdapter extends FragmentPagerAdapter  {
    private List<Fragment> tabFragments;
    private List<TabListBean.BottomNavBean>  bottomNavBeans;
    private Context context;
    public ContentPagerAdapter(FragmentManager fm, List<Fragment> tabFragments, List<TabListBean.BottomNavBean> bottomNavBeans, Context context) {
        super(fm);
        this.tabFragments=tabFragments;
        this.bottomNavBeans=bottomNavBeans;
        this.context=context;
    }

    @Override
    public int getCount() {
        return bottomNavBeans.size();
    }

    @SuppressLint("ResourceAsColor")
    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tab_layout, null);
        ImageView iv_tab_icon = v.findViewById(R.id.iv_tab_icon);
        TextView tv_tab_text = v.findViewById(R.id.tv_tab_text);
        TabListBean.BottomNavBean bottomNavBean= bottomNavBeans.get(position);
        String icon=bottomNavBean.getIcon();
        if(icon.contains("http://")){
            ImageLoaderUtils.loadUrl(context,icon,iv_tab_icon);
        }else{
            int resid=Integer.parseInt(icon);
            ImageLoaderUtils.loadRes(context,resid,iv_tab_icon);
        }
        tv_tab_text.setText(bottomNavBean.getName());
        return v;
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position);
    }
}
