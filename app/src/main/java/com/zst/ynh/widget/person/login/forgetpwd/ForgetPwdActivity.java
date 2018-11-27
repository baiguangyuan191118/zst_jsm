package com.zst.ynh.widget.person.login.forgetpwd;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.EncryptUtil;
import com.zst.ynh.view.ClearEditText;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ArouterUtil.FORGET_PWD)
@Layout(R.layout.activity_forget_pwd_layout)
public class ForgetPwdActivity extends BaseActivity implements IForgetPwdView {
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.et_real_name)
    ClearEditText etRealName;
    @BindView(R.id.et_id_num)
    ClearEditText etIdNum;
    @BindView(R.id.et_verification)
    EditText etVerification;
    @BindView(R.id.btn_verification)
    Button btnVerification;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @Autowired(name = BundleKey.ISREAL)
    boolean isReal;
    @Autowired(name = BundleKey.PHONE)
    String phone;
    private ForgetPwdPresent forgetPwdPresent;
    private CountDownTimer timer;
    private boolean isRealVerifyStatus;

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        forgetPwdPresent = new ForgetPwdPresent();
        forgetPwdPresent.attach(this);
        mTitleBar.setTitle("");
        initData();
        addTextWatch();
        //刚进来就要让他倒计时
        countTime();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        tvPhoneNum.setText(EncryptUtil.changeMobile(phone));
        if (isReal) {
            etRealName.setVisibility(View.VISIBLE);
            etIdNum.setVisibility(View.VISIBLE);
        }else {
            etRealName.setVisibility(View.GONE);
            etIdNum.setVisibility(View.GONE);
        }
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


    @OnClick({R.id.btn_verification, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_verification:
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShort("手机号不能玩为空");
                } else {
                    forgetPwdPresent.sendSMS(phone);
                }
                break;
            case R.id.btn_submit:
                forgetPwdPresent.findPWD(phone, etVerification.getText().toString().trim(),
                        isRealVerifyStatus, etRealName.getText().toString().trim(), etIdNum.getText().toString().trim());
                break;
        }
    }

    @Override
    public void sendSMSSuccess(boolean isRealVerifyStatus) {
        this.isRealVerifyStatus = isRealVerifyStatus;
        ToastUtils.showShort("验证码已发送");
        btnVerification.setEnabled(false);
        countTime();
    }

    @Override
    public void findPwdSuccess() {
        ARouter.getInstance().build(ArouterUtil.RESET_PWD)
                .withString(BundleKey.PHONE,phone).withString(BundleKey.CODE,etVerification.getText().toString().trim()).navigation();
        this.finish();
    }

    /**
     * 倒计时
     */
    private void countTime() {
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (this != null && btnVerification != null) {
                    btnVerification.setText(millisUntilFinished / 1000 + "秒");
                }
            }

            @Override
            public void onFinish() {
                if (this != null && btnVerification != null) {
                    btnVerification.setEnabled(true);
                    btnVerification.setText("重新发送");
                }
            }
        }.start();
    }

    private void addTextWatch() {
        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                btnSubmit.setEnabled(btnCheckEnable());
            }
        };
        etVerification.addTextChangedListener(textWatcher);
        etRealName.addTextChangedListener(textWatcher);
        etIdNum.addTextChangedListener(textWatcher);
    }

    /**
     * 下方按钮是否可点击
     *
     * @return
     */
    private boolean btnCheckEnable() {
        if (TextUtils.isEmpty(tvPhoneNum.getText().toString().trim())) {
            return false;
        }

        if (TextUtils.isEmpty(etVerification.getText().toString().trim()) || etVerification.getText().toString().length() != 6) {
            return false;
        }
        if (!isRealVerifyStatus) {
            return true;
        } else {
            if (TextUtils.isEmpty(etRealName.getText().toString().trim())) {
                return false;
            }
            if (TextUtils.isEmpty(etIdNum.getText().toString().trim())) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (forgetPwdPresent!=null)
            forgetPwdPresent.detach();
    }
}
