package com.zst.ynh.widget.person.settings.loginpwd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.LoginBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.NoDoubleClickListener;
import com.zst.ynh.utils.StringUtil;
import com.zst.ynh.view.ClearEditText;
import com.zst.ynh.widget.person.login.pwd.ILoginByPwdView;
import com.zst.ynh.widget.person.login.pwd.LoginByPwdPresent;
import com.zst.ynh.widget.person.settings.ISettingsView;
import com.zst.ynh.widget.person.settings.SettingsPresent;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.AlertDialog;

import butterknife.BindView;

/*
 * 修改登录密码
 * */
@Route(path = ArouterUtil.UPDATE_LOGIN_PASSWORD)
@Layout(R.layout.activity_update_pwd)
public class UpdatePwdActivity extends BaseActivity implements IUpdatePwdView ,ISettingsView ,ILoginByPwdView {

    @BindView(R.id.tv_user_phone)
    TextView userPhone;

    @BindView(R.id.et_old_pwd)
    ClearEditText oldPwd;

    @BindView(R.id.et_new_pwd)
    ClearEditText newPwd;

    @BindView(R.id.et_new_pwd_again)
    ClearEditText newPwdAgin;

    @BindView(R.id.tv_complete)
    TextView complete;
    @BindView(R.id.tv_forget)
    TextView forget;

    private UpdatePwdPresent updatePwdPresent;
    private SettingsPresent settingsPresent;
    private LoginByPwdPresent loginByPwdPresent;

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {

        userPhone.setText(StringUtil.changeMobile(SPUtils.getInstance().getString(SPkey.USER_PHONE)));
        mTitleBar.setTitle("");

        oldPwd.addTextChangedListener(watcher);
        newPwd.addTextChangedListener(watcher);
        newPwdAgin.addTextChangedListener(watcher);
        complete.setOnClickListener(noDoubleClickListener);
        forget.setOnClickListener(noDoubleClickListener);

        updatePwdPresent = new UpdatePwdPresent();
        updatePwdPresent.attach(this);

        settingsPresent=new SettingsPresent();
        settingsPresent.attach(this);

        loginByPwdPresent=new LoginByPwdPresent();
        loginByPwdPresent.attach(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(updatePwdPresent!=null){
            updatePwdPresent.detach();
        }
        if(settingsPresent!=null){
            settingsPresent.detach();
        }
    }

    NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            switch (view.getId()) {
                case R.id.tv_complete:
                    resetPwdRequest();
                    break;
                case R.id.tv_forget:
                    loginByPwdPresent.sendForgetSMS(SPUtils.getInstance().getString(SPkey.USER_PHONE));
                    break;
            }
        }
    };

    /******
     * 修改密码
     */
    private void resetPwdRequest() {

        String pwd = newPwd.getText().toString().trim();
        String confirmPwd = newPwdAgin.getText().toString().trim();
        String oldPwdStr = oldPwd.getText().toString().trim();
        if (StringUtil.isBlank(oldPwdStr)) {
            ToastUtils.showShort("请输入原密码");
            return;
        }

        if (StringUtil.isBlank(pwd) || StringUtil.isBlank(confirmPwd)) {
            ToastUtils.showShort("请输入新密码");
            return;
        }

        if (oldPwd.length() < 6 || oldPwd.length() > 16) {
            ToastUtils.showShort("旧密码必须为6~16字符");
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 16) {
            ToastUtils.showShort("新密码必须为6~16字符");
            return;
        }
        if (confirmPwd.length() < 6 || confirmPwd.length() > 16) {
            ToastUtils.showShort("新密码必须为6~16字符");
            return;
        }
        if (!pwd.equals(confirmPwd)) {
            ToastUtils.showShort("新密码两次输入不一致");
            return;
        }
        updatePwdPresent.updateLoginPassword(oldPwdStr, pwd);

    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String pwd = newPwd.getText().toString().trim();
            String confirmPwd = newPwdAgin.getText().toString().trim();
            String oldPwdstr = oldPwd.getText().toString().trim();
            complete.setEnabled(pwd.length() >= 6 && oldPwdstr.length() >= 6 && confirmPwd.length() >= 6);
        }
    };

    @Override
    public void updateLoginPwdSuccess(String response) {
        new AlertDialog(UpdatePwdActivity.this).builder()
                .setCancelable(false)
                .setMsg("登录密码修改成功")
                .setPositiveBold().setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               settingsPresent.logout();
            }
        }).show();
    }

    @Override
    public void updateLoginPwdError(int code, String errorMSG) {
        new AlertDialog(UpdatePwdActivity.this).builder()
                .setCancelable(false)
                .setMsg(errorMSG)
                .setPositiveBold().setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
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

    @Override
    public void LogoutSuccess(String response) {

        @SuppressLint("WrongConstant") int flag=Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK;
        ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED,"0").withFlags(flag).navigation();

    }

    @Override
    public void toLoginByPwd(LoginBean loginBean) {

    }

    @Override
    public void skipForgetPWD(String response) {
        boolean isReal = JSON.parseObject(response).getJSONObject("data").getBoolean("real_verify_status");
        String username=SPUtils.getInstance().getString(SPkey.USER_PHONE);
        ARouter.getInstance().build(ArouterUtil.FORGET_PWD)
                .withString(BundleKey.PHONE,username)
                .withBoolean(BundleKey.ISREAL, isReal).navigation();
    }
}
