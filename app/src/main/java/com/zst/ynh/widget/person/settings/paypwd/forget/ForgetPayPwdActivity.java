package com.zst.ynh.widget.person.settings.paypwd.forget;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.ForgetPwdCodeBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.utils.KeyboardUtil;
import com.zst.ynh.utils.StringUtil;
import com.zst.ynh.view.ClearEditText;
import com.zst.ynh.view.keyboard.KeyboardNumberUtil;
import com.zst.ynh.widget.person.settings.SettingsActivity;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ArouterUtil.FORGET_PAY_PASSWORD)
@Layout(R.layout.activity_forget_pay_pwd)
public class ForgetPayPwdActivity extends BaseActivity implements IForgetPayPwdView {

    @BindView(R.id.et_phone_number)
    ClearEditText et_phone_number;
    @BindView(R.id.et_real_name)
    ClearEditText et_real_name;
    @BindView(R.id.et_idcard_num)
    ClearEditText et_idcard_num;
    @BindView(R.id.et_verification)
    EditText et_verification;

    @BindView(R.id.tv_verification)
    TextView tvVerification;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.llCustomerKb)
    View llCustomerKb;

    private String phone;
    private String verifyCode;
    private static final int INTERVAL = 1;
    private CountDownTimer timer;
    private ForgetPayPwdPresent forgetPayPwdPresent;



    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        mTitleBar.setTitle("");
        forgetPayPwdPresent = new ForgetPayPwdPresent();
        forgetPayPwdPresent.attach(this);
        et_phone_number.addTextChangedListener(textWatcher);
        et_real_name.addTextChangedListener(textWatcher);
        et_idcard_num.addTextChangedListener(textWatcher);
        et_verification.addTextChangedListener(textWatcher);

        et_idcard_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if(!KeyboardUtil.isKeyboardShow()){
                        KeyboardUtil.showKeyboard(ForgetPayPwdActivity.this, llCustomerKb, KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE.ID_CARD, et_idcard_num);
                    }
                }else{
                    KeyboardUtil.hideKeyboard();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (forgetPayPwdPresent != null) {
            forgetPayPwdPresent.detach();
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            if (check())
                tvSubmit.setEnabled(true);
            else
                tvSubmit.setEnabled(false);
        }
    };

    private boolean check() {
        if (StringUtil.isBlankEdit(et_phone_number)) {
            return false;
        }
        if (StringUtil.isBlankEdit(et_real_name)) {
            return false;
        }
        if (StringUtil.isBlankEdit(et_idcard_num)) {
            return false;
        }
        if (StringUtil.isBlankEdit(et_verification)) {
            return false;
        }
        return true;
    }


    @OnClick({R.id.tv_verification, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_verification:
                if (StringUtil.isBlankEdit(et_phone_number)) {
                    ToastUtils.showShort("请输入手机号");
                    return;
                }
                if (!RegexUtils.isMobileExact(et_phone_number.getText().toString())) {
                    ToastUtils.showShort("请输入正确的手机号");
                    return;
                }
                phone = et_phone_number.getText().toString();
                tvVerification.setText("正在发送");
                forgetPayPwdPresent.sendSMS(phone, "find_pay_pwd");
                break;
            case R.id.tv_submit:

                KeyboardUtil.hideKeyboard();
                verifyCode = et_verification.getText().toString().trim();
                if (!RegexUtils.isMobileExact(et_phone_number.getText().toString())) {
                    ToastUtils.showShort("请输入正确的手机号");
                } else if (et_idcard_num.getText().length() != 15 && et_idcard_num.getText().length() != 18) {
                    ToastUtils.showShort("请输入正确的身份证号");
                } else if (et_verification.getText().length() < 6) {
                    ToastUtils.showShort("验证码输入不正确");
                } else {
                    phone = et_phone_number.getText().toString();
                    forgetPayPwdPresent.findPwd(phone, et_real_name.getText().toString(), et_idcard_num.getText().toString(), verifyCode);
                }

                break;
        }
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
                if (tvVerification != null) {
                    tvVerification.setText(millisUntilFinished / 1000 + "秒");
                }
            }

            @Override
            public void onFinish() {
                if (tvVerification != null) {
                    tvVerification.setEnabled(true);
                    tvVerification.setText("重新发送");
                }
            }
        }.start();
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
    public void findPwdSuccess() {
//        Intent i = getIntent();
//        i.putExtra(BundleKey.IS_SET_PAY_PWD, true);
//        i.putExtra(BundleKey.PAY_PWD_TYPE, 1);
//        i.putExtra(BundleKey.PAY_PWD_PHONE, phone);
//        i.putExtra(BundleKey.PAY_PWD_VERIFYCODE, verifyCode);
//        setResult(SettingsActivity.TAG_RESULT_CODE_FOREGET_PAY_PWD, i);
//        this.finish();
        ARouter.getInstance().build(ArouterUtil.UPDATE_TRADE_PASSWORD).withBoolean(BundleKey.IS_SET_PAY_PWD,true)
                .withInt(BundleKey.PAY_PWD_TYPE,1).withString(BundleKey.PAY_PWD_PHONE,phone)
                .withString(BundleKey.PAY_PWD_VERIFYCODE,verifyCode).navigation(this);
        finish();
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
