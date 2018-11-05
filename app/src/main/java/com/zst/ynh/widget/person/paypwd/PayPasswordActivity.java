package com.zst.ynh.widget.person.paypwd;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.view.keyboard.KeyboardNumberUtil;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.BaseDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Layout(R.layout.activity_pay_pwd_layout)
public class PayPasswordActivity extends BaseActivity implements IPayPasswordView {
    @BindView(R.id.tv_dec)
    TextView tvDec;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_pwd_key1)
    TextView tvPwdKey1;
    @BindView(R.id.tv_pwd_key2)
    TextView tvPwdKey2;
    @BindView(R.id.tv_pwd_key3)
    TextView tvPwdKey3;
    @BindView(R.id.tv_pwd_key4)
    TextView tvPwdKey4;
    @BindView(R.id.tv_pwd_key5)
    TextView tvPwdKey5;
    @BindView(R.id.tv_pwd_key6)
    TextView tvPwdKey6;
    @BindView(R.id.llCustomerKb)
    View llCustomerKb;
    @BindView(R.id.ll_keyboard)
    LinearLayout llKeyboard;
    private PayPasswordPresent payPasswordPresent;

    private KeyboardNumberUtil mKeyboardNumberUtil;
    private List<TextView> inputKeys ;
    private List<String> pwds;
    //确认交易密码
    private List<String> confirmPwds ;
    //确认密码
    private boolean isConfirm = false;
    private int index = 0;
    private BaseDialog successDialog;
    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        loadContentView();
        payPasswordPresent=new PayPasswordPresent();
        payPasswordPresent.attach(this);
        inputKeys=new ArrayList<>();
        pwds=new ArrayList<>();
        confirmPwds=new ArrayList<>();
        inputKeys.add(tvPwdKey1);
        inputKeys.add(tvPwdKey2);
        inputKeys.add(tvPwdKey3);
        inputKeys.add(tvPwdKey4);
        inputKeys.add(tvPwdKey5);
        inputKeys.add(tvPwdKey6);
        mKeyboardNumberUtil = new KeyboardNumberUtil(this, llCustomerKb, KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE.NUMBER, tvPwdKey1);
        llKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mKeyboardNumberUtil.isKeyboardShow()) {
                    return;
                }
                mKeyboardNumberUtil.showKeyboard();
            }
        });
        addListener();
    }

    private void addListener() {
        mKeyboardNumberUtil.setmKeyboardClickListenr(new KeyboardNumberUtil.KeyboardNumberClickListener() {
            @Override
            public void postHideEt() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void handleHideEt(View relatedEt, int hideDistance) {
            }

            @Override
            public void clickDelete() {
                if (index == 0)
                    return;
                inputKeys.get(index - 1).setVisibility(View.INVISIBLE);
                if (!isConfirm) {
                    pwds.remove(index - 1);
                } else {
                    confirmPwds.remove(index - 1);

                }
                index--;
            }

            @Override
            public void click(String clickStr) {
                if (!isConfirm) {
                    if (index >= inputKeys.size()) {
                        return;
                    }
                    pwds.add(clickStr);
                    if (index == inputKeys.size() - 1) {
                        inputKeys.get(index).setVisibility(View.VISIBLE);
                        changeConfirmPwdStatus();
                    }
                } else {
                    if (index >= inputKeys.size()) {
                        return;
                    }
                    confirmPwds.add(clickStr);
                    if (index == inputKeys.size() - 1) {
                        inputKeys.get(index).setVisibility(View.VISIBLE);
                        savePayPwd();
                    }
                }
                inputKeys.get(index).setVisibility(View.VISIBLE);
                index++;
            }

            @Override
            public void clear() {
                for (int i = 0; i < index; i++) {
                    inputKeys.get(i).setText("");
                }
                if (!isConfirm) {
                    pwds.clear();
                } else {
                    confirmPwds.clear();
                }
                index = 0;
            }
        });
    }
    /**
     * 确认密码页面的显示
     */
    private void changeConfirmPwdStatus() {
        isConfirm = true;
        for (int i = 0; i < inputKeys.size(); i++)
            inputKeys.get(i).setVisibility(View.INVISIBLE);
        tvDec.setText("确认交易密码");
        tvTip.setText("请确认六位交易密码");
        index = 0;
    }


    /**
     * 请求网络设置密码
     */
    private void savePayPwd() {
        String payPwd = getInputPwd(pwds);
        String confirmPwd = getInputPwd(confirmPwds);
        if (payPwd.equals(confirmPwd)) {
            payPasswordPresent.setPwd(confirmPwd);
        } else {
            ToastUtils.showShort("两次密码不一致,请重新输入");
            clearInputInfo();
        }
    }


    /***********
     * 清除输入
     */
    private void clearInputInfo() {
        isConfirm = false;
        for (int i = 0; i < inputKeys.size(); i++)
            inputKeys.get(i).setVisibility(View.INVISIBLE);
        tvDec.setText("设置交易密码");
        tvTip.setText("请设置六位交易密码");
        index = 0;
        pwds.clear();
        confirmPwds.clear();
    }


    /*********
     * 获取交易密码
     * @return
     */
    private String getInputPwd(List<String> passwords) {
        StringBuffer pwdBuffer = new StringBuffer();

        for (int i = 0; i < passwords.size(); i++)
            pwdBuffer=pwdBuffer.append(passwords.get(i));

        return pwdBuffer.toString().trim();
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
    protected void onDestroy() {
        super.onDestroy();
        if (payPasswordPresent!=null)
            payPasswordPresent.detach();
    }
    private void destroyDialog(){
        if (successDialog!=null && successDialog.isShowing()){
            successDialog.dismiss();
            successDialog=null;
        }
    }
    @Override
    public void setPayPwdSuccess() {
        successDialog=new BaseDialog.Builder(this).setContent1("设置密码成功").setBtnLeftBackgroundColor(getResources().getColor(R.color.them_color)).setBtnLeftColor(Color.WHITE)
                .setLeftOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        destroyDialog();
                        finish();
                    }
                }).create();
    }
}
