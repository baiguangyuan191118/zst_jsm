package com.zst.jsm.widget.login;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zst.jsm.R;
import com.zst.jsm.config.ArouterUtil;
import com.zst.jsm.widget.login.pwd.LoginByPwdFragment;
import com.zst.jsm.widget.login.sms.LoginBySmsFragment;
import com.zst.jsm_base.mvp.view.BaseActivity;
import com.zst.jsm_base.util.Layout;

import java.lang.reflect.Field;

import butterknife.BindView;

@Route(path = ArouterUtil.LOGIN)
@Layout(R.layout.activity_login_layout)
public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_tab_layout)
    TabLayout loginTabLayout;
    @BindView(R.id.login_view_pager)
    ViewPager loginViewPager;
    private FragmentAdapter adapter;

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        loadContentView();
        mTitleBar.setTitle("登录");
        initAndSetupView();
    }
    /**
     * 初始化对应控件
     */
    private void initAndSetupView() {
        setIndicator(loginTabLayout, 40, 40);
        adapter = new FragmentAdapter(getSupportFragmentManager());
        loginViewPager.setAdapter(adapter);
        //两者关联
        loginTabLayout.setupWithViewPager(loginViewPager);
        loginViewPager.setCurrentItem(0);
    }
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
    /**
     * ViewPager 适配器
     */
    private static class FragmentAdapter extends FragmentStatePagerAdapter {

        private String[] title = {"快捷登录", "账号密码登录"};

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {

            if (0 == position) {
                //快捷登陆
                LoginBySmsFragment fragment = LoginBySmsFragment.newInstance();
                return fragment;
            } else {
                //账号密码登陆
                LoginByPwdFragment fragment = LoginByPwdFragment.newInstance();
                return fragment;
            }
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

}
