package com.zst.ynh.widget.person.settings.loginpwd.reset;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.UMClicEventID;
import com.zst.ynh.config.UMClickEvent;
import com.zst.ynh.utils.StringUtil;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ArouterUtil.RESET_PWD)
@Layout(R.layout.activity_reset_pwd_layout)
public class ResetPwdActivity extends BaseActivity implements IRestPwdView {
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_new_pwd_again)
    EditText etNewPwdAgain;
    @BindView(R.id.btn_complete)
    Button btnComplete;
    private String password;
    private String nPassword;
    private ResetPwdPresent resetPwdPresent;
    @Autowired(name = BundleKey.PHONE)
    String phone;
    @Autowired(name = BundleKey.CODE)
    String code;

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        resetPwdPresent=new ResetPwdPresent();
        resetPwdPresent.attach(this);
        mTitleBar.setTitle("");

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


    @OnClick(R.id.btn_complete)
    public void onViewClicked() {
        UMClickEvent.getInstance().onClick(this,UMClicEventID.UM_EVENT_UPDATE_PWD,"修改密码");
        password=etNewPwd.getText().toString().trim();
        nPassword=etNewPwdAgain.getText().toString().trim();
        if(TextUtils.isEmpty(password)||TextUtils.isEmpty(nPassword)){
            ToastUtils.showShort("请输入密码");
            return;
        }

        if(password.length()< 6 || password.length()>16){
            ToastUtils.showShort("密码必须为6~16字符");
            return;
        }

        if(!password.equals(nPassword)){
            ToastUtils.showShort("密码两次输入不一致");
            return;
        }

        if(!StringUtil.isPwd(password)){
            ToastUtils.showShort("新密码需由6~16位字母和数字组成");
            return;
        }

        resetPwdPresent.resetPWD(phone,code,nPassword);
    }

    @Override
    public void resetPwdSuccess() {
        ToastUtils.showShort("密码设置成功");
        ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED,BundleKey.MAIN_LOAN).navigation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resetPwdPresent!=null)
        resetPwdPresent.detach();
    }
}
