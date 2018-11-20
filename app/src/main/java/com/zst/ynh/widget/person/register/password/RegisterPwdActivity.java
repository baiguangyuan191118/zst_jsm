package com.zst.ynh.widget.person.register.password;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.LoginBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.KeyboardUtil;
import com.zst.ynh.utils.NoLineClickSpan;
import com.zst.ynh.utils.UpdateHeaderUtils;
import com.zst.ynh.view.EyeEditText;
import com.zst.ynh.view.keyboard.KeyboardNumberUtil;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ArouterUtil.REGISTER_PWD)
@Layout(R.layout.activity_register_pwd_layout)
public class RegisterPwdActivity extends BaseActivity implements IRegisterPwdView {
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.btn_verification)
    Button btnVerification;
    @BindView(R.id.et_password)
    EyeEditText etPassword;
    @BindView(R.id.tv_register_protocol)
    TextView tvRegisterProtocol;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.llCustomerKb)
    View llCustomerKb;
    @Autowired(name = BundleKey.PHONE)
    String phoneNumber;
    private RegisterPwdPresent registerPwdPresent;
    private  CountDownTimer timer;

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        loadContentView();
        registerPwdPresent = new RegisterPwdPresent();
        registerPwdPresent.attach(this);
        addTextWatch();
        initProtocol();
        //验证码是上个页面发送的  所以这里上来就要倒计时
        btnVerification.setEnabled(false);
        countTime();
    }

    private void initProtocol(){
        String protocol=tvRegisterProtocol.getText().toString();
        String protocolUsual=protocol.substring(0,protocol.indexOf("《"));
        String protocolClick=protocol.substring(protocol.indexOf("《"),protocol.length());
        tvRegisterProtocol.setMovementMethod(LinkMovementMethod.getInstance());
        tvRegisterProtocol.setText(new SpanUtils().append(protocolUsual).append(protocolClick)
                .setForegroundColor(getResources().getColor(R.color.them_color)).setClickSpan(new NoLineClickSpan(new NoLineClickSpan.onSpanClick() {
                    @Override
                    public void onSpanClick() {
                        ARouter.getInstance().build(ArouterUtil.WEB).withString(BundleKey.URL,ApiUrl.REGISTER_PROTOCOL).navigation();
                    }
                })).create());
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

    private void addTextWatch() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnSubmit.setEnabled(etSmsCode.length() == 6 && etPassword.length() >= 6);
            }
        };
        etSmsCode.addTextChangedListener(watcher);
        etPassword.addTextChangedListener(watcher);
    }

    @Override
    public void registerSuccess(LoginBean loginBean) {
        //登录成功之后更新消息头
        UpdateHeaderUtils.updateHeader(loginBean.item.sessionid);
        SPUtils.getInstance().put(SPkey.REAL_NAME, loginBean.item.realname);
        SPUtils.getInstance().put(SPkey.USER_PHONE, loginBean.item.username);
        SPUtils.getInstance().put( SPkey.USER_SESSIONID, loginBean.item.sessionid);
        SPUtils.getInstance().put( SPkey.USER_SPECIAL, loginBean.item.special);
        SPUtils.getInstance().put(SPkey.UID, loginBean.item.uid+"");
        ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED,"0").navigation();
    }

    @Override
    public void sendSMSSuccess() {
        btnVerification.setEnabled(false);
        ToastUtils.showShort("验证码已发送");
        countTime();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (registerPwdPresent != null)
            registerPwdPresent.detach();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    @OnClick({R.id.btn_verification, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_verification:
                btnVerification.setEnabled(false);
                btnVerification.setText("正在发送");
                registerPwdPresent.sendSMS(phoneNumber);
                break;
            case R.id.btn_submit:
                if (etPassword.length()>16){
                    ToastUtils.showShort("登录密码需由6~16字符组成");
                }else{
                    registerPwdPresent.registerPwd(phoneNumber,etSmsCode.getText().toString().trim(),etPassword.getText().toString().trim());
                }
                break;
            case R.id.et_sms_code:
                KeyboardUtils.hideSoftInput(this);
                if (!KeyboardUtil.isKeyboardShow()){
                    KeyboardUtil.showKeyboard(this,llCustomerKb, KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE.NUMBER, etSmsCode);
                }
                break;
            case R.id.et_password:
                KeyboardUtils.hideSoftInput(this);
                if (!KeyboardUtil.isKeyboardShow()){
                    KeyboardUtil.showKeyboard(this,llCustomerKb, KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE.NUMBER, etPassword);
                }
                break;
        }
    }
    /**
     * 倒计时
     */
    private void countTime(){
        timer=new CountDownTimer(60*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(this!=null && btnVerification!=null){
                    btnVerification.setText(millisUntilFinished/1000+"秒");

                }
            }

            @Override
            public void onFinish() {
                if(this!=null && btnVerification!=null){
                    btnVerification.setEnabled(true);
                    btnVerification.setText("重新发送");
                }
            }
        }.start();
    }
}
