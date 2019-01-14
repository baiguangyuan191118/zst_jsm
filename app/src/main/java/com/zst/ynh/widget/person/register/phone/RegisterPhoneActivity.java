package com.zst.ynh.widget.person.register.phone;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.base.UMBaseActivity;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.utils.DialogUtil;
import com.zst.ynh.view.ClearEditText;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ArouterUtil.REGISTER_PHONE)
@Layout(R.layout.activity_register_phone_layout)
public class RegisterPhoneActivity extends UMBaseActivity implements IRegisterPhoneView {
    @BindView(R.id.et_phone_number)
    ClearEditText etPhoneNumber;
    @BindView(R.id.btn_next)
    Button btnNext;
    private String phone;
    private RegisterPhonePresent registerPhonePresent;
    private Dialog dialog;

    @Override
    public void skipNextPWD() {
        ARouter.getInstance().build(ArouterUtil.REGISTER_PWD).withString(BundleKey.PHONE,phone).navigation();
    }

    @Override
    public void showDialog() {
        dialog= new BaseDialog.Builder(this).setContent1("您已经是老用户了 直接去登录吧").setBtnLeftText("取消").setBtnRightText("去登陆").setLeftOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.hideDialog(dialog);
            }
        }).setRightOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.hideDialog(dialog);
                ARouter.getInstance().build(ArouterUtil.LOGIN).navigation();
                finish();
            }
        }).create();
        dialog.show();
    }

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        loadContentView();
        mTitleBar.setTitle("");
        registerPhonePresent=new RegisterPhonePresent();
        registerPhonePresent.attach(this);
        addTextWatch();
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

    private void addTextWatch(){
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnNext.setEnabled(s.length() == 11);
            }
        });
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        phone=etPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            ToastUtils.showShort("手机号不能为空");
        }else if (RegexUtils.isMobileSimple(phone)){
            registerPhonePresent.sendSMS(phone);
        }else{
            ToastUtils.showShort("请输入正确的手机号");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (registerPhonePresent!=null)
        registerPhonePresent.detach();
        DialogUtil.hideDialog(dialog);
    }
}
