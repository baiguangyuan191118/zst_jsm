package com.zst.jsm.widget.login.sms;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.jsm.R;
import com.zst.jsm.bean.LoginBean;
import com.zst.jsm.config.ArouterUtil;
import com.zst.jsm.config.SPkey;
import com.zst.jsm.utils.NoDoubleClickListener;
import com.zst.jsm.utils.SPUtil;
import com.zst.jsm.utils.TextWatcherUtil;
import com.zst.jsm.view.ClearEditText;
import com.zst.jsm_base.mvp.view.BaseFragment;
import com.zst.jsm_base.util.Layout;

import butterknife.BindView;

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
    private LoginBySmsPresent loginBySmsPresent;

    public static LoginBySmsFragment newInstance() {
        LoginBySmsFragment fragment = new LoginBySmsFragment();
        return fragment;
    }

    @Override
    protected void onRetry() {

    }

    @Override
    protected void initView() {
        loadContentView();
        loginBySmsPresent = new LoginBySmsPresent();
        loginBySmsPresent.attach(this);

        TextWatcherUtil.isButtonEnable(etPhoneNumber, etSmsCode, btnLogin);
        setOnclick();

        loginBySmsPresent.getIndexData();
    }

    @Override
    public void getLoginData(LoginBean loginBean) {
        SPUtil.put(getContext(), SPkey.USER_PHONE, loginBean.item.realname);
        SPUtil.put(getContext(), SPkey.USER_SESSIONID, loginBean.item.sessionid);
        SPUtil.put(getContext(), SPkey.USER_SPECLIAL, loginBean.item.special);
        ARouter.getInstance().build(ArouterUtil.MAIN).navigation();
    }

    @Override
    public void showLoading() {
        loadLoadingView();
    }

    @Override
    public void hideLoading() {
        hideLoadingView();
    }

    @Override
    public void ToastErrorMessage(String msg) {
        ToastUtils.showShort(msg);
    }

    private void setOnclick(){
        NoDoubleClickListener listener = new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_login:
                        //登录
                        break;

                    case R.id.tv_verification:
                        //获取验证码
                        break;

                    default:
                        break;
                }
            }
        };

        btnLogin.setOnClickListener(listener);
        tvVerification.setOnClickListener(listener);
    }
}
