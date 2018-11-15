package com.zst.ynh.widget.person.settings;

import android.content.Intent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.MineBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.NoDoubleClickListener;
import com.zst.ynh.utils.StringUtil;
import com.zst.ynh.widget.web.BaseWebActivity;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.util.VersionUtil;
import com.zst.ynh_base.view.AlertDialog;
import com.zst.ynh_base.view.BaseDialog;

import butterknife.BindView;

@Route(path = ArouterUtil.SETTINGS)
@Layout(R.layout.activity_settings)
public class SettingsActivity extends BaseActivity implements ISettingsView {

    @BindView(R.id.layout_about_my)
    RelativeLayout aboutUs;

    @BindView(R.id.layout_modification_login_password)
    RelativeLayout modifLoginPsw;

    @BindView(R.id.layout_modification_trade_password)
    RelativeLayout modifTradePsw;

    @BindView(R.id.tv_exit)
    TextView exit;

    @BindView(R.id.tv_trad)
    TextView trade;

    @BindView(R.id.tv_version)
    TextView version;

    @BindView(R.id.ck_gesture)
    CheckBox gesture;

    private MineBean.MineItemBean mineItemBean;

    private SettingsPresent settingsPresent;

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {

        settingsPresent = new SettingsPresent();
        settingsPresent.attach(this);
        mTitleBar.setTitle("设置");
        mineItemBean = (MineBean.MineItemBean) getIntent().getSerializableExtra(BundleKey.SETTING);
        version.setText("V" + VersionUtil.getLocalVersionName(this));
        if (mineItemBean != null && mineItemBean.getVerify_info() != null && mineItemBean.getVerify_info().getReal_pay_pwd_status() == 1) {
            trade.setText("修改交易密码");
        } else {
            trade.setText("设置交易密码");
        }

        String key = SPUtils.getInstance().getString(SPkey.USER_PHONE);
        boolean isOpen = !StringUtil.isBlank(SPUtils.getInstance().getString(key));
        gesture.setChecked(isOpen);

        aboutUs.setOnClickListener(onClickListener);
        modifLoginPsw.setOnClickListener(onClickListener);
        modifTradePsw.setOnClickListener(onClickListener);
        exit.setOnClickListener(onClickListener);

        gesture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (settingsPresent != null) {
            settingsPresent.detach();
        }
    }

    NoDoubleClickListener onClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            switch (view.getId()) {
                case R.id.layout_about_my:

                    ARouter.getInstance().build(ArouterUtil.ABOUT_US).withString(BundleKey.URL, ApiUrl.ABOUT_US).navigation();

                    break;
                case R.id.layout_modification_login_password:
                    break;
                case R.id.layout_modification_trade_password:
                    break;
                case R.id.tv_exit:
                    logoutConfirm();
                    break;
            }
        }
    };

    /*********************
     * 退出确认
     */
    private void logoutConfirm() {
        new AlertDialog(this).builder()
                .setCancelable(false)
                .setMsg("是否确定退出?")
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).setPositiveButton("退出", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               settingsPresent.logout();
            }
        }).show();
    }

    @Override
    public void LogoutSuccess(String response) {
        //清除用户数据
        //SPUtils.getInstance().put(SPkey.USER_PHONE,"");
        SPUtils.getInstance().put(SPkey.USER_SESSIONID, "");
        SPUtils.getInstance().put(SPkey.REAL_NAME, "");
        SPUtils.getInstance().put(SPkey.UID, "");
        //清楚cookie
        CookieSyncManager.createInstance(this);
        CookieManager cm = CookieManager.getInstance();
        cm.removeAllCookie();
        CookieSyncManager.getInstance().sync();
        //跳转页面到main
        ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED, "0").navigation();
    }


    @Override
    public void showLoading() {
        showLoadingView();
    }

    @Override
    public void hideLoading() {
        hideLoadingView();
    }

    @Override
    public void ToastErrorMessage(String msg) {
        ToastUtils.showShort(msg);
    }
}
