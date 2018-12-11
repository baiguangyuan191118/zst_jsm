package com.zst.ynh.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zst.ynh.R;
import com.zst.ynh.view.keyboard.KeyboardNumberUtil;

public class InputSMSCardDialog extends Dialog {
    PwdInputController input_controller;
    private View llCustomerKb;
    private ImageView close;
    private TextView tvSendSMS;
    private long countdownTime = 90000L;
    private long countDownInterval = 1000L;
    private CountDownTimer timer = null;
    private VerifyCallBack callBack;
    private SendSMSCodeVerifyCallBack sendSMSCodeVerifyCallBack;

    public InputSMSCardDialog(@NonNull Context context) {
        super(context);
    }

    public InputSMSCardDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_sms_code_master_card);
        setCanceledOnTouchOutside(false);
        input_controller = findViewById(R.id.input_controller);
        llCustomerKb = findViewById(R.id.llCustomerKb);
        tvSendSMS = findViewById(R.id.tv_send_sms_code);
        input_controller.initKeyBoard(llCustomerKb, KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE.NUMBER);
        input_controller.showKeyBoard();
        input_controller.setOnPwdInputEvent(new PwdInputController.OnPwdInputEvent() {

            @Override
            public void inputComplete(String pwd) {
                dismiss();
                if (null != callBack) {
                    callBack.verify(pwd);
                }
            }
        });
        tvSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSendSMS.setEnabled(false);
                if (null != timer) {
                    timer.cancel();
                }
                timer = new CountDownTimer(countdownTime, countDownInterval) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        tvSendSMS.setText((millisUntilFinished / 1000) + "s");
                    }

                    @Override
                    public void onFinish() {
                        tvSendSMS.setEnabled(true);
                        tvSendSMS.setText("重新获取");
                    }
                };
                timer.start();
                if (null != sendSMSCodeVerifyCallBack) {
                    sendSMSCodeVerifyCallBack.sendCode();
                }
            }
        });
        close = (ImageView) findViewById(R.id.iv_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void reset() {
        if (null != timer) {
            timer.cancel();
        }
        tvSendSMS.setEnabled(true);
        tvSendSMS.setText("重新获取");
    }

    public void start() {
        if (null != timer) {
            timer.cancel();
        }
        timer = new CountDownTimer(countdownTime, countDownInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
                tvSendSMS.setText((millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                tvSendSMS.setEnabled(true);
                tvSendSMS.setText("重新获取");
            }
        };
        timer.start();
    }

    public void setVerifyCallBack(VerifyCallBack callBack) {
        this.callBack = callBack;
    }
    public interface VerifyCallBack {
        void verify(String code);

    }

    public void setSendSMSCodeVerifyCallBack(SendSMSCodeVerifyCallBack sendSMSCodeVerifyCallBack) {
        this.sendSMSCodeVerifyCallBack = sendSMSCodeVerifyCallBack;
    }

    public interface SendSMSCodeVerifyCallBack {
        void sendCode();
    }
}
