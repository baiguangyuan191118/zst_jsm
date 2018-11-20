package com.zst.ynh.widget.person.settings;

import android.content.Intent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.gesturelock.GestureLockLayout;
import com.zst.ynh.R;
import com.zst.ynh.bean.GestureLockInfo;
import com.zst.ynh.bean.MineBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.DialogUtil;
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

    /**
     * 关闭手势解锁的ReQuestCode
     */
    private static final int TAG_REQUEST_CODE = 0x100;
    /**
     * 关闭手势解锁成功RESULT_CODE
     */
    public static final int TAG_RESULT_CODE_SUCCESS = TAG_REQUEST_CODE + 1;

    /*
    * 其他号码登录
    * */
    public static final int TAG_RESULT_CODE_LOGOUT=TAG_REQUEST_CODE+2;
    /*
    * 设置支付密码成功
    * */
    public static final int TAG_RESULT_CODE_SET_PAY_PWD=TAG_REQUEST_CODE+3;

    /*
    * 忘记密码
    * */
    public static final int TAG_RESULT_CODE_FOREGET_PAY_PWD=TAG_REQUEST_CODE+4;


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

        gesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                boolean isChecked=checkBox.isChecked();
                if(isChecked){//设置
                    ARouter.getInstance().build(ArouterUtil.GESTURE_SET).withInt(BundleKey.GESTURE_MODE,GestureLockLayout.RESET_MODE).navigation();
                }else{//关闭
                    ARouter.getInstance().build(ArouterUtil.GESTURE_SET).withInt(BundleKey.GESTURE_MODE,GestureLockLayout.VERIFY_MODE).navigation(SettingsActivity.this, TAG_REQUEST_CODE);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (TAG_REQUEST_CODE == requestCode) {
            if(TAG_RESULT_CODE_SUCCESS == resultCode){
                String key = SPUtils.getInstance().getString(SPkey.USER_PHONE);
                if (!StringUtil.isBlank(key)) {
                    SPUtils.getInstance().put(key, "");
                }
            }
           if(TAG_RESULT_CODE_LOGOUT ==resultCode){
               ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED, "0").navigation();
               ARouter.getInstance().build(ArouterUtil.LOGIN).navigation();
               finish();
           }

           if(TAG_RESULT_CODE_SET_PAY_PWD==resultCode){
               trade.setText("修改交易密码");
           }
        }
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

                    ARouter.getInstance().build(ArouterUtil.UPDATE_LOGIN_PASSWORD).navigation();

                    break;
                case R.id.layout_modification_trade_password:

                    if (mineItemBean != null && mineItemBean.getVerify_info() != null && mineItemBean.getVerify_info().getReal_verify_status() == 1) {
                        boolean isSetPayPwd=mineItemBean.getVerify_info().getReal_pay_pwd_status()==1?false:true;
                        ARouter.getInstance().build(ArouterUtil.UPDATE_TRADE_PASSWORD).withBoolean(BundleKey.IS_SET_PAY_PWD,isSetPayPwd).navigation();
                    } else {
                        DialogUtil.showDialogToCertitication(SettingsActivity.this);
                    }

                    break;
                case R.id.tv_exit:
                    logoutConfirm();
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        String key = SPUtils.getInstance().getString(SPkey.USER_PHONE);
        boolean isOpen = !StringUtil.isBlank(SPUtils.getInstance().getString(key));
        gesture.setChecked(isOpen);
    }

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
