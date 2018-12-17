package com.zst.ynh.widget.person.login.pwd;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.LoginBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.KeyboardUtil;
import com.zst.ynh.utils.TextWatcherUtil;
import com.zst.ynh.utils.UpdateHeaderUtils;
import com.zst.ynh.view.ClearEditText;
import com.zst.ynh.view.EyeEditText;
import com.zst.ynh.view.keyboard.KeyboardNumberUtil;
import com.zst.ynh.widget.person.login.LoginActivity;
import com.zst.ynh_base.mvp.view.BaseFragment;
import com.zst.ynh_base.util.Layout;

import butterknife.BindView;
import butterknife.OnClick;

@Layout(R.layout.activity_login_pwd_layout)
public class LoginByPwdFragment extends BaseFragment implements ILoginByPwdView {


    @BindView(R.id.et_phone_number)
    ClearEditText etPhoneNumber;
    @BindView(R.id.et_pwd)
    EyeEditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_forget_pwd)
    Button btnForgetPwd;
    @BindView(R.id.btn_to_register)
    Button btnToRegister;
    @BindView(R.id.llCustomerKb)
    View llCustomerKb;
    private LoginByPwdPresent loginByPwdPresent;

    public static LoginByPwdFragment newInstance() {
        LoginByPwdFragment fragment = new LoginByPwdFragment();
        return fragment;
    }

    @Override
    protected void onRetry() {

    }

    @Override
    protected void initView() {
        loadContentView();
        loginByPwdPresent = new LoginByPwdPresent();
        loginByPwdPresent.attach(this);
        llCustomerKb.bringToFront();
        etPhoneNumber.setText(LoginActivity.phoneNumber);
        etPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (etPhoneNumber != null)
                        etPhoneNumber.requestFocus();
                    KeyboardUtil.showKeyboard(getActivity(), llCustomerKb, KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE.NUMBER, etPhoneNumber);
                } else {
                    if (etPhoneNumber != null)
                        etPhoneNumber.clearFocus();
                }
            }
        });
        etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (etPwd != null)
                        etPwd.requestFocus();
                    KeyboardUtil.hideKeyboard();
                    KeyboardUtils.showSoftInput(getActivity());
                } else {
                    if (etPwd != null)
                        etPwd.clearFocus();
                }
            }
        });

        TextWatcherUtil.isButtonEnable(etPhoneNumber, etPwd, btnLogin);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (etPhoneNumber != null) {
            if (!isVisibleToUser) {
                LoginActivity.phoneNumber = etPhoneNumber.getText().toString().trim();
            } else {
                etPhoneNumber.setText(LoginActivity.phoneNumber);
            }
        }

    }


    @OnClick({R.id.btn_login, R.id.btn_forget_pwd, R.id.btn_to_register, R.id.et_phone_number, R.id.et_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String phone = etPhoneNumber.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShort("手机号不能为空");
                } else if (!RegexUtils.isMobileSimple(phone)) {
                    ToastUtils.showShort("请输入正确的手机号");
                } else if (pwd.length() < 6 || pwd.length() > 16) {
                    ToastUtils.showShort("密码必须为6~16字符");
                } else {
                    loginByPwdPresent.toLoginByPWD(phone, pwd);
                }
                break;
            case R.id.btn_forget_pwd:
                if (TextUtils.isEmpty(etPhoneNumber.getText().toString().trim())) {
                    ToastUtils.showShort("手机号不能为空");
                } else if (!RegexUtils.isMobileSimple(etPhoneNumber.getText().toString().trim())) {
                    ToastUtils.showShort("请输入正确的手机号");
                } else {
                    loginByPwdPresent.sendForgetSMS(etPhoneNumber.getText().toString().trim());

                }
                break;
            case R.id.btn_to_register:
                ARouter.getInstance().build(ArouterUtil.REGISTER_PHONE).navigation();
                break;
            case R.id.et_phone_number:
                if (!KeyboardUtil.isKeyboardShow()) {
                    KeyboardUtil.showKeyboard(getActivity(), llCustomerKb, KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE.NUMBER, etPhoneNumber);
                }
                break;
            case R.id.et_pwd:
                KeyboardUtil.hideKeyboard();
                KeyboardUtils.showSoftInput(getActivity());
                break;
        }
    }

    @Override
    public void toLoginByPwd(LoginBean loginBean) {
        //登录成功之后更新消息头
        UpdateHeaderUtils.updateHeader(loginBean.item.sessionid);
        SPUtils.getInstance().put(SPkey.REAL_NAME, loginBean.item.realname);
        SPUtils.getInstance().put(SPkey.USER_PHONE, loginBean.item.username);
        SPUtils.getInstance().put(SPkey.USER_SESSIONID, loginBean.item.sessionid);
        SPUtils.getInstance().put(SPkey.USER_SPECIAL, loginBean.item.special);
        SPUtils.getInstance().put(SPkey.UID, loginBean.item.uid + "");
        ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED, BundleKey.MAIN_LOAN).withBoolean(BundleKey.MAIN_FRESH, true).navigation();
        this.getActivity().finish();
    }

    @Override
    public void skipForgetPWD(String response) {
        boolean isReal = JSON.parseObject(response).getJSONObject("data").getBoolean("real_verify_status");
        ARouter.getInstance().build(ArouterUtil.FORGET_PWD)
                .withString(BundleKey.PHONE, etPhoneNumber.getText().toString().trim())
                .withBoolean(BundleKey.ISREAL, isReal).navigation();
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
    public void onDestroyView() {
        super.onDestroyView();
        if (loginByPwdPresent != null)
            loginByPwdPresent.detach();
    }
}
