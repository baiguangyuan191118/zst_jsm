package com.zst.ynh.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.adapter.ContentPagerAdapter;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.widget.loan.Home.LoanFragment;
import com.zst.ynh.widget.person.mine.PersonFragment;
import com.zst.ynh.widget.repayment.repaymentfragment.RepaymentFragment;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = ArouterUtil.MAIN)
@Layout(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;

    private int[] titleName = {R.string.app_name, R.string.title_repay, R.string.menu_mine};
    private int[] tabTitle = {R.string.menu_loan, R.string.menu_repay, R.string.menu_mine};
    private int[] tabIcon = {R.mipmap.menu_loan_pressed, R.mipmap.menu_repay_normal, R.mipmap.menu_mine_normal};
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    private LoanFragment loanFragment;
    private PersonFragment personFragment;
    private RepaymentFragment repaymentFragment;
    private TitleBar.TextAction history;


    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        LogUtils.d("initView OnCreate");
        loadContentView();
        initFragment();
        initTab();
        addTabListener();
        initTitle();
    }

    private void initTitle() {
        mTitleBar.setVisibility(View.GONE);
        mTitleBar.setLeftImageResource(0);
        mTitleBar.setTitle(titleName[0]);
        mTitleBar.setActionTextColor(R.color.theme_color);
        history = new TitleBar.TextAction("历史") {
            @Override
            public void performAction(View view) {
                ARouter.getInstance().build(ArouterUtil.LOAN_RECORD).navigation();
            }
        };

    }

    private void initFragment() {
        tabFragments = new ArrayList<>();
        loanFragment = LoanFragment.newInstance();
        personFragment = PersonFragment.newInstance();
        repaymentFragment = RepaymentFragment.newInstance();
        tabFragments.add(loanFragment);
        tabFragments.add(repaymentFragment);
        tabFragments.add(personFragment);
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
        tlTab.getTabAt(0).select();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.d("initView onNewIntent");
        boolean isFresh = intent.getBooleanExtra(BundleKey.MAIN_FRESH, false);
        loanFragment.setFresh(isFresh);
        repaymentFragment.setFresh(isFresh);
        personFragment.setFresh(isFresh);

        if (!TextUtils.isEmpty(intent.getStringExtra(BundleKey.MAIN_SELECTED))) {
            if ("0".equals(intent.getStringExtra(BundleKey.MAIN_SELECTED))) {
                tlTab.getTabAt(0).select();
                loanFragment.autoFresh();
            } else if ("1".equals(intent.getStringExtra(BundleKey.MAIN_SELECTED))) {
                tlTab.getTabAt(1).select();
                repaymentFragment.autoFresh();
            } else if ("2".equals(intent.getStringExtra(BundleKey.MAIN_SELECTED))) {
                tlTab.getTabAt(2).select();
                personFragment.autoFresh();
            }

        }
    }

    private void addTabListener() {

        tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTitleBar.setTitle(titleName[tab.getPosition()]);
                switch (tab.getPosition()) {
                    case 0:
                        mTitleBar.setTitle("");
                        mTitleBar.setVisibility(View.GONE);
                        mTitleBar.setBackgroundColor(Color.WHITE);
                        mTitleBar.setTitleColor(Color.BLACK);
                        tab.getCustomView().findViewById(R.id.iv_tab_icon).setBackgroundResource(R.mipmap.menu_loan_pressed);
                        loanFragment.autoFresh();
                        break;
                    case 1:
                        if (TextUtils.isEmpty(SPUtils.getInstance().getString(SPkey.USER_SESSIONID))) {
                            ARouter.getInstance().build(ArouterUtil.LOGIN).withString(BundleKey.LOGIN_FROM, BundleKey.LOGIN_FROM_MAIN).navigation();
                            return;
                        }
                        mTitleBar.setVisibility(View.VISIBLE);
                        mTitleBar.setBackgroundColor(Color.WHITE);
                        mTitleBar.setTitleColor(Color.BLACK);
                        mTitleBar.removeAllActions();
                        mTitleBar.addAction(history);
                        repaymentFragment.autoFresh();
                        tab.getCustomView().findViewById(R.id.iv_tab_icon).setBackgroundResource(R.mipmap.menu_repay_pressed);
                        break;
                    case 2:
                        if (TextUtils.isEmpty(SPUtils.getInstance().getString(SPkey.USER_SESSIONID))) {
                            ARouter.getInstance().build(ArouterUtil.LOGIN).withString(BundleKey.LOGIN_FROM, BundleKey.LOGIN_FROM_MAIN).navigation();
                            return;
                        }
                        mTitleBar.setVisibility(View.VISIBLE);
                        mTitleBar.setTitleColor(Color.WHITE);
                        mTitleBar.setBackgroundResource(R.color.them_color);
                        mTitleBar.removeAllActions();
                        personFragment.autoFresh();
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

    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showShort("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
