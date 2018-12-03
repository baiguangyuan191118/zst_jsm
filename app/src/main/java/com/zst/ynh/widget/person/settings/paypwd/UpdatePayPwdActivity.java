package com.zst.ynh.widget.person.settings.paypwd;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.EventValue;
import com.zst.ynh.event.StringEvent;
import com.zst.ynh.utils.WeakHandler;
import com.zst.ynh.view.keyboard.KeyboardNumberUtil;
import com.zst.ynh.widget.person.settings.SettingsActivity;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.AlertDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/*
* 修改密码，重置密码，设置密码
* */
@Route(path = ArouterUtil.UPDATE_TRADE_PASSWORD)
@Layout(R.layout.activity_update_trade_pwd)
public class UpdatePayPwdActivity extends BaseActivity implements IUpdatePayPwdView {

    @Autowired(name = BundleKey.IS_SET_PAY_PWD)
    boolean isSetPwd=false;//true:设置密码 false：修改密码

    @BindView(R.id.llCustomerKb)
    View llCustomerKb;
    KeyboardNumberUtil mKeyboardNumberUtil;
    @BindView(R.id.ed_pwd_key1)
    TextView ed_pwd_key1;
    @BindView(R.id.ed_pwd_key2)
    TextView ed_pwd_key2;
    @BindView(R.id.ed_pwd_key3)
    TextView ed_pwd_key3;
    @BindView(R.id.ed_pwd_key4)
    TextView ed_pwd_key4;
    @BindView(R.id.ed_pwd_key5)
    TextView ed_pwd_key5;
    @BindView(R.id.ed_pwd_key6)
    TextView ed_pwd_key6;
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    @BindView(R.id.tv_forget)
    TextView tv_forget;
    @BindView(R.id.tv_dec)
    TextView tv_dec;

    @BindView(R.id.fl_content)
    FrameLayout inputContent;

    private List<TextView> inputKeys = new ArrayList<TextView>();

    private List<String> oldPwds = new ArrayList<>();//旧密码
    private List<String> pwds = new ArrayList<String>();//新密码
    //确认交易密码
    private List<String> confirmPwds = new ArrayList<String>();//确认密码

    private int type = 0;//修改密码 0：旧密码 1：新密码 2：确认密码
    private int index = 0;
    private WeakHandler weakHandler=new WeakHandler();
    private UpdatePayPwdPresent updatePayPwdPresent;

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        mTitleBar.setTitle("");
        clearInputInfo();
        inputKeys.add(ed_pwd_key1);
        inputKeys.add(ed_pwd_key2);
        inputKeys.add(ed_pwd_key3);
        inputKeys.add(ed_pwd_key4);
        inputKeys.add(ed_pwd_key5);
        inputKeys.add(ed_pwd_key6);
        mKeyboardNumberUtil = new KeyboardNumberUtil(this, llCustomerKb, KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE.NUMBER, ed_pwd_key1);
        weakHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                mKeyboardNumberUtil.showKeyboard();
            }
        }, 800);

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
                if (type == 0) {
                    oldPwds.remove(index - 1);
                } else if (type == 1) {
                    pwds.remove(index - 1);
                } else {
                    confirmPwds.remove(index - 1);
                }

                index--;
            }

            @Override
            public void click(String clickStr) {

                if (type == 0) {
                    if (index >= inputKeys.size()) {
                        return;
                    }
                    oldPwds.add(clickStr);
                    if (index == inputKeys.size() - 1) {
                        inputKeys.get(index).setVisibility(View.VISIBLE);
                        weakHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                changeNewPwdStatus();
                            }
                        }, 500);
                    }
                }
                if (type == 1) {
                    if (index >= inputKeys.size()) {
                        return;
                    }

                    pwds.add(clickStr);
                    if (index == inputKeys.size() - 1) {
                        inputKeys.get(index).setVisibility(View.VISIBLE);
                        weakHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                changeConfirmPwdStatus();
                            }
                        }, 500);
                    }
                }
                if (type == 2) {
                    if (index >= inputKeys.size()) {
                        return;
                    }
                    confirmPwds.add(clickStr);
                    if (index == inputKeys.size() - 1) {
                        inputKeys.get(index).setVisibility(View.VISIBLE);
                        weakHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                savePayPwd();
                            }
                        }, 500);
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
                if (type == 0) {
                    oldPwds.clear();
                }
                if (type == 1) {
                    pwds.clear();
                } else {
                    confirmPwds.clear();
                }

                index = 0;

            }
        });

        if(updatePayPwdPresent ==null){
            updatePayPwdPresent =new UpdatePayPwdPresent();
            updatePayPwdPresent.attach(this);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(updatePayPwdPresent !=null){
            updatePayPwdPresent.detach();
        }
    }

    @OnClick({R.id.tv_forget, R.id.fl_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget:
                ARouter.getInstance().build(ArouterUtil.FORGET_PAY_PASSWORD).navigation(this,SettingsActivity.TAG_RESULT_CODE_FOREGET_PAY_PWD);
                break;
            case R.id.fl_content:
                if (mKeyboardNumberUtil.isKeyboardShow()) {
                    return;
                }
                mKeyboardNumberUtil.showKeyboard();
                break;
        }
    }


    //切换到输入新密码状态
    private void changeNewPwdStatus() {
        type = 1;
        for (int i = 0; i < inputKeys.size(); i++)
            inputKeys.get(i).setVisibility(View.INVISIBLE);
        tv_dec.setText("新交易密码");
        tv_tip.setText("请输入六位交易密码");
        index = 0;
    }

    //切换到输入确认密码状态
    private void changeConfirmPwdStatus() {
        type = 2;
        for (int i = 0; i < inputKeys.size(); i++)
            inputKeys.get(i).setVisibility(View.INVISIBLE);
        tv_dec.setText("确认交易密码");
        tv_tip.setText("请确认六位交易密码");
        index = 0;
    }


    private void savePayPwd() {
        String payPwd = getInputPwd(pwds);
        String confirmPwd = getInputPwd(confirmPwds);
        String oldPwd = getInputPwd(oldPwds);
        if (payPwd.equals(confirmPwd)) {
            savePayPwd(oldPwd, confirmPwd);
        } else {
            ToastUtils.showShort("两次密码不一致,请重新输入");
            clearInputInfo();
        }
    }

    /***********
     * 设置交易密码
     */
    private void savePayPwd(String oldpwd, String pwd) {

        if(isSetPwd){
            if (getIntent().getIntExtra(BundleKey.PAY_PWD_TYPE, 0) == 1) {//重置密码 from ForgetPayPwdActivity
                updatePayPwdPresent.resetPayPwd(getIntent().getStringExtra(BundleKey.PAY_PWD_PHONE),
                        getIntent().getStringExtra(BundleKey.PAY_PWD_VERIFYCODE),pwd);
            } else {//设置密码
                updatePayPwdPresent.setPayPwd(pwd);
            }
        }else{
            updatePayPwdPresent.updateTradePwdPresent(oldpwd,pwd);
        }

    }

    /***********
     * 初始状态
     */
    private void clearInputInfo() {
        if(isSetPwd){
            type = 1;
            tv_dec.setText("设置交易密码");
            tv_tip.setText("请设置六位交易密码");
            tv_forget.setVisibility(View.GONE);
        }else{
            type = 0;
            tv_dec.setText("修改交易密码");
            tv_tip.setText("请输入旧的六位交易密码");
        }

        for (int i = 0; i < inputKeys.size(); i++)
            inputKeys.get(i).setVisibility(View.INVISIBLE);

        for (int i = 0; i < inputKeys.size(); i++)
            inputKeys.get(i).setVisibility(View.INVISIBLE);

        index = 0;
        oldPwds.clear();
        pwds.clear();
        confirmPwds.clear();
    }

    /*********
     * 获取交易密码
     *
     * @return
     */
    private String getInputPwd(List<String> passwords) {
        String payPwd = "";

        for (int i = 0; i < passwords.size(); i++)
            payPwd = payPwd + passwords.get(i);

        return payPwd.trim();
    }





    @Override
    public void updatePayPwdSuccess(String response) {
        new AlertDialog(this).builder()
                .setCancelable(false)
                .setMsg("交易密码修改成功")
                .setPositiveBold().setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).show();
    }

    @Override
    public void updatePayPwdError(int code, String errorMSG) {
        if(code!=-2){
            new AlertDialog(this).builder()
                    .setCancelable(false)
                    .setMsg(errorMSG)
                    .setPositiveBold().setPositiveButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearInputInfo();
                }
            }).show();
        }
    }

    @Override
    public void setPayPwdError(int code, String errorMSG) {
        clearInputInfo();
    }

    @Override
    public void setPayPwdSuccess(String response) {
        new AlertDialog(UpdatePayPwdActivity.this).builder()
                .setCancelable(false)
                .setMsg("交易密码设置成功")
                .setPositiveBold().setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new StringEvent("",EventValue.UPDATE_PAYPWD));
                //返回设置界面，显示修改交易密码
                setResult(SettingsActivity.TAG_RESULT_CODE_SET_PAY_PWD);
                finish();
            }
        }).show();
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
