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
import com.zst.ynh_base.mvp.view.BaseFragment;
import com.zst.ynh_base.util.Layout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Layout(R.layout.fragment_multi_repayment)
public class MultiRepaymentFragment extends BaseFragment {

    private static final String tag=MultiRepaymentFragment.class.getSimpleName();

    @BindView(R.id.tablayout_loanrecord)
    TabLayout tabLayout;
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
        addTab(getResources().getString(R.string.app_name));
        addTab("其他记录");
        tabLayout.addOnTabSelectedListener(onTabSelectedListener);
        fragmentList.add(ynhLoanRecordFragment);
        fragmentList.add(repaymentListFragment);
        loanRecordPagerAdapter = new LoanRecordPagerAdapter(getChildFragmentManager(), fragmentList,tabTitle);
        if(!isLazyload){
           onLazyLoad();
        }
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener=new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            for (int i=0;i<tabLayout.getTabCount();i++){
                View view = tabLayout.getTabAt(i).getCustomView();
                if(view == null){
                    return;
                }
                TextView text = (TextView) view.findViewById(R.id.tab_item_text);
                View indicator = view.findViewById(R.id.tab_item_indicator);
                if(i == tab.getPosition()){ // 选中状态
                    text.setTextColor(getResources().getColor(R.color.them_color));
                    indicator.setBackgroundColor(getResources().getColor(R.color.them_color));
                    indicator.setVisibility(View.VISIBLE);
                }else{// 未选中状态
                    text.setTextColor(Color.BLACK);
                    indicator.setVisibility(View.INVISIBLE);
                }
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    /**
     * 添加tab
     * @param tab
     */
    public void addTab(String tab){
        tabTitle.add(tab);
        View customView = getTabView(getContext(),tab,ConvertUtils.dp2px(30),ConvertUtils.dp2px(2),ConvertUtils.sp2px(14));
        customTabList.add(customView);
        tabLayout.addTab(tabLayout.newTab().setCustomView(customView));
    }


    /**
     * 获取Tab 显示的内容
     *
     * @param context
     * @param
     * @return
     */
    public static View getTabView(Context context, String text, int indicatorWidth, int indicatorHeight, int textSize) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item_layout, null);
        TextView tabText = (TextView) view.findViewById(R.id.tab_item_text);
        if(indicatorWidth>0){
            View indicator = view.findViewById(R.id.tab_item_indicator);
            ViewGroup.LayoutParams layoutParams = indicator.getLayoutParams();
            layoutParams.width  = indicatorWidth;
            layoutParams.height = indicatorHeight;
            indicator.setLayoutParams(layoutParams);
        }
        tabText.setTextSize(textSize);
        tabText.setText(text);
        return view;
    }


    @Override
    public void onDestroyView() {
        Log.d(tag,"onDestroyView");
        super.onDestroyView();
        fragmentList.clear();
    }
}
