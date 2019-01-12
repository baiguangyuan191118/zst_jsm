package com.zst.ynh.widget.person.login.sms;

import android.os.CountDownTimer;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.LoginBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.config.UMClicEventID;
import com.zst.ynh.config.UMClickEvent;
import com.zst.ynh.utils.KeyboardUtil;
import com.zst.ynh.utils.NoDoubleClickListener;
import com.zst.ynh.utils.NoLineClickSpan;
import com.zst.ynh.utils.TextWatcherUtil;
import com.zst.ynh.utils.UpdateHeaderUtils;
import com.zst.ynh.view.ClearEditText;
import com.zst.ynh.view.keyboard.KeyboardNumberUtil;
import com.zst.ynh.widget.person.login.LoginActivity;
import com.zst.ynh_base.mvp.view.BaseFragment;
import com.zst.ynh_base.util.Layout;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

@Layout(R.layout.activity_login_sms_layout)
public class LoginBySmsFragment extends BaseFragment implements ILoginBySmsView {
    @BindView(R.id.et_phone_number)
    ClearEditText etPhoneNumber;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.tv_verification)
    TextView tvVerification;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register_protocol)
    TextView btnRegisterProtocol;
    @BindView(R.id.llCustomerKb)
    View llCustomerKb;
    @BindColor(R.color.them_color)
    int spanColor;
    private LoginBySmsPresent loginBySmsPresent;
    private CountDownTimer timer;

    public static LoginBySmsFragment newInstance() {
        LoginBySmsFragment fragment = new LoginBySmsFragment();
        return fragment;
    }

    @Override
    protected void onRetry() {

    }

    @Override
    protected void initView() {
        LogUtils.d("initView");
        loadContentView();
        loginBySmsPresent = new LoginBySmsPresent();
        loginBySmsPresent.attach(this);
        KeyboardUtils.hideSoftInput(getActivity());
        TextWatcherUtil.isButtonEnable(etPhoneNumber, etSmsCode, btnLogin);
        setOnclick();
        setProtocolStyle();
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
        etSmsCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (etSmsCode != null)
                        etSmsCode.requestFocus();
                    KeyboardUtil.showKeyboard(getActivity(), llCustomerKb, KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE.NUMBER, etSmsCode);
                } else {
                    if (etSmsCode != null)
                        etSmsCode.clearFocus();
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(etPhoneNumber!=null){
            if (!isVisibleToUser) {
                LoginActivity.phoneNumber=etPhoneNumber.getText().toString().trim();
                KeyboardUtil.hideKeyboard();
            } else{
                etPhoneNumber.setText(LoginActivity.phoneNumber);
            }
        }

    }

    /**
     * 设置下方协议的颜色与内容
     */
    private void setProtocolStyle() {
        String protocol = btnRegisterProtocol.getText().toString();
        String protocolUsual = protocol.substring(0, protocol.indexOf("《"));
        String protocolClick = protocol.substring(protocol.indexOf("《"), protocol.length());
        btnRegisterProtocol.setMovementMethod(LinkMovementMethod.getInstance());
        btnRegisterProtocol.setText(new SpanUtils().append(protocolUsual).append(protocolClick).setForegroundColor(spanColor)
                .setClickSpan(new NoLineClickSpan(new NoLineClickSpan.onSpanClick() {
                    @Override
                    public void onSpanClick() {
                        ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL, ApiUrl.REGISTER_PROTOCOL).navigation();
                    }
                })).create());
    }

    @Override
    public void getLoginData(LoginBean loginBean) {
        UMClickEvent.getInstance().onClick(getActivity(),UMClicEventID.UM_EVENT_LOGIN_SMS,"短信快捷登录");
        //登录成功之后更新消息头
        UpdateHeaderUtils.updateHeader(loginBean.item.sessionid);
        SPUtils.getInstance().put(SPkey.REAL_NAME, loginBean.item.realname);
        SPUtils.getInstance().put(SPkey.USER_PHONE, loginBean.item.username);
        SPUtils.getInstance().put(SPkey.USER_SESSIONID, loginBean.item.sessionid);
        SPUtils.getInstance().put(SPkey.USER_SPECIAL, loginBean.item.special);
        SPUtils.getInstance().put(SPkey.UID, loginBean.item.uid+"");
        ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED,BundleKey.MAIN_LOAN).withBoolean(BundleKey.MAIN_FRESH,true).navigation();
        this.getActivity().finish();
    }

    @Override
    public void sendSMSSuccess() {
        tvVerification.setEnabled(false);
        ToastUtils.showShort("验证码已发送");
        countTime();
    }

    @Override
    public void sendSMSFail() {
        tvVerification.setEnabled(true);
        tvVerification.setText("重新发送");
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


    @OnClick({R.id.et_phone_number, R.id.et_sms_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_phone_number:
                if (!KeyboardUtil.isKeyboardShow())
                    KeyboardUtil.showKeyboard(getActivity(), llCustomerKb, KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE.NUMBER, etPhoneNumber);
                break;
            case R.id.et_sms_code:
                if (!KeyboardUtil.isKeyboardShow())
                    KeyboardUtil.showKeyboard(getActivity(), llCustomerKb, KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE.NUMBER, etSmsCode);
                break;
        }
    }

    private void setOnclick() {
        NoDoubleClickListener listener = new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_login:
                        //登录
                        String phone = etPhoneNumber.getText().toString().trim();
                        String code = etSmsCode.getText().toString().trim();
                        if (TextUtils.isEmpty(phone)) {
                            ToastUtils.showShort("手机号不能为空");
                        } else if (!RegexUtils.isMobileSimple(phone)) {
                            ToastUtils.showShort("请输入正确的手机号");
                        } else if (TextUtils.isEmpty(code)) {
                            ToastUtils.showShort("验证码不能为空");
                        } else {
                            loginBySmsPresent.toLoginBySMS(phone, code);
                        }
                        break;

                    case R.id.tv_verification:
                        if (TextUtils.isEmpty(etPhoneNumber.getText().toString().trim())) {
                            ToastUtils.showShort("手机号不能为空");
                        } else {
                            //获取验证码
                            tvVerification.setText("正在发送");
                            loginBySmsPresent.sendSMS(etPhoneNumber.getText().toString().trim());
                        }
                        break;

                    default:
                        break;
                }
            }
        };
        btnLogin.setOnClickListener(listener);
        tvVerification.setOnClickListener(listener);
    }

    /**
     * 倒计时
     */
    private void countTime() {
        if(timer!=null) {
            timer.cancel(); //防止new出多个导致时间跳动加速
            timer=null;
        }
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (getActivity() != null && tvVerification != null) {
                    tvVerification.setText(millisUntilFinished / 1000 + "秒");
                }
            }
            @Override
            public void onFinish() {
                if (getActivity() != null && tvVerification != null) {
                    tvVerification.setEnabled(true);
                    tvVerification.setText("重新发送");
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (loginBySmsPresent != null)
            loginBySmsPresent.detach();
    }
}
