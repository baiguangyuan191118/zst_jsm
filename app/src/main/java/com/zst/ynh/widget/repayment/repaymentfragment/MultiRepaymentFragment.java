package com.zst.ynh.widget.repayment.repaymentfragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.adapter.LoanRecordPagerAdapter;
import com.zst.ynh.view.EnhanceTabLayout;
import com.zst.ynh_base.mvp.view.BaseFragment;
import com.zst.ynh_base.util.Layout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Layout(R.layout.fragment_multi_repayment)
public class MultiRepaymentFragment extends BaseFragment {

    private static final String tag=MultiRepaymentFragment.class.getSimpleName();

    @BindView(R.id.enhance_tab_layout)
    EnhanceTabLayout tabLayout;
    @BindView(R.id.viewpager_loanrecord)
    ViewPager viewPager;

    private LoanRecordPagerAdapter loanRecordPagerAdapter;

    private RepaymentListFragment ynhLoanRecordFragment;
    private RepaymentListFragment repaymentListFragment;

    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String> tabTitle=new ArrayList<>();
    private List<View> customTabList=new ArrayList<>();
    private int ynhType;
    private int otherType;

    @Override
    protected void onRetry() {

    }

    public int getYnhType() {
        return ynhType;
    }

    public void setYnhType(int ynhType) {
        this.ynhType = ynhType;
    }

    public int getOtherType() {
        return otherType;
    }

    public void setOtherType(int otherType) {
        this.otherType = otherType;
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        Log.d(tag,"onLazyLoad");
        if(viewPager.getAdapter()==null) {
            viewPager.setAdapter(loanRecordPagerAdapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout.getTabLayout()));
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    public void autoFresh() {
        ynhLoanRecordFragment.autoFresh();
        repaymentListFragment.autoFresh();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(tag,"setUserVisibleHint:"+isVisibleToUser);
    }

    @Override
    protected void initView() {
        Log.d(tag,"initView");
        ynhLoanRecordFragment=new RepaymentListFragment();
        ynhLoanRecordFragment.setLazyload(true);
        ynhLoanRecordFragment.setLIST_TYPE(ynhType);
        repaymentListFragment =new RepaymentListFragment();
        repaymentListFragment.setLazyload(true);
        repaymentListFragment.setLIST_TYPE(otherType);
        tabTitle.add(getResources().getString(R.string.app_name));
        tabTitle.add("其他记录");
        for(int i=0;i<tabTitle.size();i++){
            tabLayout.addTab(tabTitle.get(i));
        }

        fragmentList.add(ynhLoanRecordFragment);
        fragmentList.add(repaymentListFragment);
        loanRecordPagerAdapter = new LoanRecordPagerAdapter(getChildFragmentManager(), fragmentList);

        if(!isLazyload){
           onLazyLoad();
        }
    }


    @Override
    public void onDestroyView() {
        Log.d(tag,"onDestroyView");
        super.onDestroyView();
        fragmentList.clear();
    }
}
