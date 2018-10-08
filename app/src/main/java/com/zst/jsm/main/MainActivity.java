package com.zst.jsm.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.zst.jsm.R;
import com.zst.jsm.adapter.ContentPagerAdapter;
import com.zst.jsm.widget.loan.LoanFragment;
import com.zst.jsm.widget.person.PersonFragment;
import com.zst.jsm.widget.repayment.RepaymentFragment;
import com.zst.jsm_base.mvp.view.BaseActivity;
import com.zst.jsm_base.util.Layout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


@Layout(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;

    private int[] tabTitle = {R.string.menu_loan, R.string.menu_repay, R.string.menu_mine};
    private int[] tabIcon = {R.mipmap.menu_loan_pressed, R.mipmap.menu_repay_normal, R.mipmap.menu_mine_normal};
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    private LoanFragment loanFragment;
    private PersonFragment personFragmentl;
    private RepaymentFragment repaymentFragment;


    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        loadContentView();
        initFragment();
        initTab();
        addTabListener();
        initTitle();
    }

    private void initTitle() {
        mTitleBar.setLeftImageResource(0);
    }

    private void initFragment(){
        tabFragments=new ArrayList<>();
        loanFragment=LoanFragment.newInstance();
        personFragmentl=PersonFragment.newInstance();
        repaymentFragment=RepaymentFragment.newInstance();
        tabFragments.add(loanFragment);
        tabFragments.add(repaymentFragment);
        tabFragments.add(personFragmentl);
    }



    private void initTab() {
        tlTab.setTabMode(TabLayout.MODE_FIXED);
        tlTab.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(tlTab, 10);
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager(), tabFragments, tabTitle, tabIcon, this);
        vpContent.setAdapter(contentAdapter);
        tlTab.setupWithViewPager(vpContent);
        for (int i = 0; i < tlTab.getTabCount(); i++) {
            tlTab.getTabAt(i).setCustomView(contentAdapter.getTabView(i));
        }
        tlTab.getTabAt(0).getCustomView().setSelected(true);
    }

    private void addTabListener() {
        tlTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.getCustomView().findViewById(R.id.iv_tab_icon).setBackgroundResource(R.mipmap.menu_loan_pressed);
                        break;
                    case 1:
                        tab.getCustomView().findViewById(R.id.iv_tab_icon).setBackgroundResource(R.mipmap.menu_repay_pressed);
                        break;
                    case 2:
                        tab.getCustomView().findViewById(R.id.iv_tab_icon).setBackgroundResource(R.mipmap.menu_mine_pressed);
                        break;
                }
                TextView textView = tab.getCustomView().findViewById(R.id.tv_tab_text);
                textView.setTextColor(getResources().getColor(R.color.them_color));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.getCustomView().findViewById(R.id.iv_tab_icon).setBackgroundResource(R.mipmap.menu_loan_normal);
                        break;
                    case 1:
                        tab.getCustomView().findViewById(R.id.iv_tab_icon).setBackgroundResource(R.mipmap.menu_repay_normal);
                        break;
                    case 2:
                        tab.getCustomView().findViewById(R.id.iv_tab_icon).setBackgroundResource(R.mipmap.menu_mine_normal);
                        break;
                }
                TextView textView = tab.getCustomView().findViewById(R.id.tv_tab_text);
                textView.setTextColor(getResources().getColor(R.color.color_999999));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
