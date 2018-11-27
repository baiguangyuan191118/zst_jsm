package com.zst.ynh.widget.person.login;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.zst.ynh.R;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.view.TablayoutLine;
import com.zst.ynh.widget.person.login.pwd.LoginByPwdFragment;
import com.zst.ynh.widget.person.login.sms.LoginBySmsFragment;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import butterknife.BindView;

@Route(path = ArouterUtil.LOGIN)
@Layout(R.layout.activity_login_layout)
public class LoginActivity extends BaseActivity implements PhoneCallback{
    @BindView(R.id.login_tab_layout)
    TabLayout loginTabLayout;
    @BindView(R.id.login_view_pager)
    ViewPager loginViewPager;
    private FragmentAdapter adapter;
    public static String phoneNumber="";

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        LogUtils.d("initView");
        loadContentView();
        initAndSetupView();
        mTitleBar.setTitle("登录");
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    /**
     * 初始化对应控件
     */
    private void initAndSetupView() {
        loginTabLayout.post(new Runnable() {
            @Override
            public void run() {
                TablayoutLine.setIndicator(loginTabLayout, 40, 40);
            }
        });
        adapter = new FragmentAdapter(getSupportFragmentManager());
        loginViewPager.setAdapter(adapter);
        //两者关联
        loginTabLayout.setupWithViewPager(loginViewPager);
        loginViewPager.setCurrentItem(0);
    }


    @Override
    public void phoneChanged(String phone) {
        this.phoneNumber=phone;
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

    @Override
    protected void onPause() {
        super.onPause();
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!TextUtils.isEmpty(getIntent().getStringExtra(BundleKey.LOGIN_FROM))){
            if (BundleKey.LOGIN_FROM_MAIN.equals(getIntent().getStringExtra(BundleKey.LOGIN_FROM))){
                LogUtils.d("initView login backpress");
                ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED,"0").withBoolean(BundleKey.MAIN_FRESH,true).navigation();
                this.finish();
            }
        }
    }
}
