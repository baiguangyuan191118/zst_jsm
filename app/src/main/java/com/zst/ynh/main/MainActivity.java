package com.zst.ynh.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

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

    private long exitTime = 0;
    private int[] titleName = {R.string.app_name, R.string.title_repay, R.string.menu_mine};
    private int[] tabTitle = {R.string.menu_loan, R.string.menu_repay, R.string.menu_mine};
    private int[] tabIcon = {R.drawable.selector_menu_loan, R.drawable.selector_menu_repay, R.drawable.selector_menu_mine};
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    private LoanFragment loanFragment;
    private PersonFragment personFragment;
    private RepaymentFragment repaymentFragment;
    private View history;
    private View message;


    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        LogUtils.d("initView OnCreate");
        loadContentView();
        initFragment();
        initTab();
        initTitle();

        addTabListener();
    }

    private void initTitle() {
        mTitleBar.setLeftImageResource(0);
        mTitleBar.setActionTextColor(R.color.theme_color);
        TitleBar.TextAction action = new TitleBar.TextAction("历史") {
            @Override
            public void performAction(View view) {
                ARouter.getInstance().build(ArouterUtil.LOAN_RECORD).navigation();
            }
        };
        history = mTitleBar.addAction(action);
        message = LayoutInflater.from(this).inflate(R.layout.view_message, null);
        mTitleBar.addRightLayout(message);
        message.setVisibility(View.VISIBLE);
        history.setVisibility(View.GONE);
        loanFragment.setTitle(message);
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
        tlTab.setupWithViewPager(vpContent, true);
        for (int i = 0; i < tlTab.getTabCount(); i++) {
            tlTab.getTabAt(i).setCustomView(contentAdapter.getTabView(i));
        }
       // tlTab.getTabAt(0).select();
        mTitleBar.setTitle(titleName[0]);
    }

    private void addTabListener() {

        tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTitleBar.setTitle(titleName[tab.getPosition()]);
                switch (tab.getPosition()) {
                    case 0:
                        message.setVisibility(View.VISIBLE);
                        history.setVisibility(View.GONE);
                        mTitleBar.setTitleColor(Color.BLACK);
                        mTitleBar.setBackgroundColor(Color.WHITE);
                        break;
                    case 1:
                        if (TextUtils.isEmpty(SPUtils.getInstance().getString(SPkey.USER_SESSIONID))) {
                            ARouter.getInstance().build(ArouterUtil.LOGIN).withString(BundleKey.LOGIN_FROM, BundleKey.LOGIN_FROM_MAIN).navigation();
                            return;
                        }
                        history.setVisibility(View.VISIBLE);
                        message.setVisibility(View.GONE);
                        mTitleBar.setTitleColor(Color.BLACK);
                        mTitleBar.setBackgroundColor(Color.WHITE);
                        break;
                    case 2:
                        if (TextUtils.isEmpty(SPUtils.getInstance().getString(SPkey.USER_SESSIONID))) {
                            ARouter.getInstance().build(ArouterUtil.LOGIN).withString(BundleKey.LOGIN_FROM, BundleKey.LOGIN_FROM_MAIN).navigation();
                            return;
                        }
                        message.setVisibility(View.GONE);
                        history.setVisibility(View.GONE);
                        mTitleBar.setTitleColor(Color.WHITE);
                        mTitleBar.setBackgroundResource(R.color.them_color);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
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
