package com.zst.ynh.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by yangxiangjie on 2017/9/21.
 * TextWatcher监听工具类
 */

public class TextWatcherUtil {

    /**
     *手机号码长度
     */
    private static final int PHONE_LENGTH = 11;
    /**
     *验证码和密码最小长度
     */
    private static final int CODE_OR_PASS_LENGTH = 6;

    private boolean enable;

    /**
     * 根据手机号码，验证码或密码长度判断底部按钮 是否可以点击
     *
     * @param phone     手机号
     * @param codeOrPwd 验证码或密码
     * @return true:btn可点击；否则，btn不可点击
     */
    public static void isButtonEnable(final EditText phone, final EditText codeOrPwd, final View enableView) {
        if (phone == null || codeOrPwd == null) {
            //有一个控件为null 直接返回false
            return;
        }

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String phoneStr = phone.getText().toString();
                String codeOrPwdStr = codeOrPwd.getText().toString();
                boolean enable = phoneStr.length() == PHONE_LENGTH && codeOrPwdStr.length() >= CODE_OR_PASS_LENGTH;
                enableView.setEnabled(enable);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        phone.addTextChangedListener(watcher);
        codeOrPwd.addTextChangedListener(watcher);
    }

    /**
     * 充值密码界面
     *
     * @param newPwdET       新密码
     * @param confirmedPwdET 确认密码
     * @param enableView
     * @return true:btn可点击；否则，btn不可点击
     */
    public static void isPwdButtonEnable(final EditText newPwdET, final EditText confirmedPwdET, final View enableView) {
        if (newPwdET == null || confirmedPwdET == null) {
            return;
        }
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String newPwdStr = newPwdET.getText().toString();
                String confirmedPwdStr = confirmedPwdET.getText().toString();
                boolean enable = newPwdStr.length() >= CODE_OR_PASS_LENGTH && confirmedPwdStr.length() >= CODE_OR_PASS_LENGTH;
                enableView.setEnabled(enable);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        newPwdET.addTextChangedListener(watcher);
        confirmedPwdET.addTextChangedListener(watcher);

    }


    /**
     * 输入金额保留小数点后两位
     *
     * @param editText EditText
     */
    public static void FormatAmount(final EditText editText) {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editText.addTextChangedListener(watcher);
    }
    /**
     * 去掉多余的小数点和小数点后面的0
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

}
