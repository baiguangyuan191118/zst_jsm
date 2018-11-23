package com.zst.ynh.widget.person.certification.bindbank;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.BankBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.Constant;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.event.StringEvent;
import com.zst.ynh.utils.DialogUtil;
import com.zst.ynh.view.BankListDialog;
import com.zst.ynh.view.CardEditText;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/*
* 绑定银行卡
* */
@Route(path = ArouterUtil.BIND_BANK_CARD)
@Layout(R.layout.activity_bind_bank_card_layout)
public class BindBankCardActivity extends BaseActivity implements IBindBankCardView {
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.et_bankcard_num)
    CardEditText etBankcardNum;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @Autowired(name = BundleKey.ISCHANGE)
    boolean isChange;
    private BindBankCardPresent bindBankCardPresent;
    private CountDownTimer timer;
    private BankListDialog bankListDialog;
    private BankBean bankBean;
    //这是判断是否从认证页面来的 true:下方按钮显示下一步； false:下方按钮显示保存
    private boolean isFromToCertification;
    private int bankID;

    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    @Override
    public void sendSMSSuccess() {
        tvSendCode.setEnabled(false);
        ToastUtils.showShort("验证码已发送");
        countTime();
    }


    @Override
    public void loadContent() {
        loadContentView();
    }

    @Override
    public void loadLoading() {
        loadLoadingView();
    }

    @Override
    public void loadError() {
        loadErrorView();
    }

    @Override
    public void getBankListData(BankBean response) {
        this.bankBean = response;
    }

    @Override
    public void addBankCardSuccess() {
        if (isFromToCertification) {
            ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL, Constant.getTargetUrl()).navigation();
        }
    }
    /**
     * 选择的赋值
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StringEvent messageEvent) {
        if (!TextUtils.isEmpty(messageEvent.getMessage())){
            tvBankName.setText(messageEvent.getMessage());
            bankID=messageEvent.getValue();
        }

    }

    @Override
    public void onRetry() {
        bindBankCardPresent.getBankList();
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        mTitleBar.setTitle("绑定银行卡");
        bindBankCardPresent = new BindBankCardPresent();
        bindBankCardPresent.attach(this);
        bindBankCardPresent.getBankList();
        tvUserName.setText(SPUtils.getInstance().getString(SPkey.REAL_NAME));
        isFromToCertification = Constant.isIsStep();
        if (isFromToCertification) {
            btnSubmit.setText("下一步");
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


    @OnClick({R.id.tv_send_code, R.id.btn_submit, R.id.tv_bank_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code:
                if (TextUtils.isEmpty(etPhoneNum.getText().toString().trim())) {
                    ToastUtils.showShort("手机号不能为空");
                } else if (!RegexUtils.isMobileSimple(etPhoneNum.getText().toString().trim())) {
                    ToastUtils.showShort("请输入正确的手机号");
                } else {
                    bindBankCardPresent.sendBankSMS(etPhoneNum.getText().toString().trim(), bankID+"");
                }
                break;
            case R.id.btn_submit:
                if (TextUtils.isEmpty(etPhoneNum.getText().toString().trim())) {
                    ToastUtils.showShort("手机号不能为空");
                } else if (!RegexUtils.isMobileSimple(etPhoneNum.getText().toString().trim())) {
                    ToastUtils.showShort("请输入正确的手机号");
                } else if (TextUtils.isEmpty(etBankcardNum.getText().toString().trim())) {
                    ToastUtils.showShort("银行卡号不能为空");
                } else if (TextUtils.isEmpty(tvBankName.getText().toString().trim())) {
                    ToastUtils.showShort("请选择银行");
                } else if (TextUtils.isEmpty(etVerifyCode.getText().toString().trim())) {
                    ToastUtils.showShort("验证码不能为空");
                } else {
                    if (isChange)
                        bindBankCardPresent.addBankCard(bankID+"", etBankcardNum.getText().toString().trim().replaceAll(" ", ""), etPhoneNum.getText().toString().trim(), etVerifyCode.getText().toString().trim());
                    else
                        bindBankCardPresent.changeBankCard(bankID+"", etBankcardNum.getText().toString().trim().replaceAll(" ", ""), etPhoneNum.getText().toString().trim(), etVerifyCode.getText().toString().trim());
                }
                break;
            case R.id.tv_bank_name:
                bankListDialog = new BankListDialog.Builder(this, bankBean.item, bankBean.tips).create();
                bankListDialog.show();
                break;
        }
    }

    /**
     * 倒计时
     */
    private void countTime() {
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (tvSendCode != null) {
                    tvSendCode.setText(millisUntilFinished / 1000 + "秒");
                }
            }

            @Override
            public void onFinish() {
                if (tvSendCode != null) {
                    tvSendCode.setEnabled(true);
                    tvSendCode.setText("重新发送");
                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bindBankCardPresent != null)
            bindBankCardPresent.detach();
        DialogUtil.hideDialog(bankListDialog);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
