package com.zst.ynh.utils;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.KeyboardUtils;
import com.zst.ynh.view.keyboard.KeyboardNumberUtil;

import java.lang.reflect.Method;

public class KeyboardUtil {
    public static KeyboardNumberUtil input_controller;


    public static boolean isKeyboardShow() {
        if (input_controller != null) {
            return input_controller.isKeyboardShow();
        } else {
            return false;
        }
    }

    public static void hideSoftKeyboard(EditText view) {
        if (android.os.Build.VERSION.SDK_INT > 10) {//4.0以上 danielinbiti
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(view, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void showKeyboard(Activity activity, View wholeView, KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE type, EditText view) {
        KeyboardUtils.hideSoftInput(activity);
        if (android.os.Build.VERSION.SDK_INT > 10) {//4.0以上 danielinbiti
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(view, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        view.requestFocus();
        input_controller = new KeyboardNumberUtil(activity, wholeView, type, view);
        input_controller.showKeyboard();
        addInputListener(view);
    }

    public static void hideKeyboard() {
        if (input_controller != null) {
            input_controller.hideKeyboard();
        }
    }

    private static void addInputListener(final EditText view) {
        input_controller.setmKeyboardClickListenr(new KeyboardNumberUtil.KeyboardNumberClickListener() {

            @Override
            public void postHideEt() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd() {
                // TODO Auto-generated method stub
            }

            @Override
            public void handleHideEt(View relatedEt, int hideDistance) {
                // TODO Auto-generated method stub
            }

            @Override
            public void clickDelete() {
                // TODO Auto-generated method stub
                if (view.length() > 0) {
                    view.setText(view.getText().subSequence(0, view.length() - 1));
                    view.setSelection(view.getText().length());
                }
            }

            @Override
            public void click(String clickStr) {
                // TODO Auto-generated method stub
                view.append(clickStr);
                view.setSelection(view.getText().length());
            }

            @Override
            public void clear() {
                // TODO Auto-generated method stub
                view.setText("");
                view.setSelection(view.getText().length());
            }
        });
    }
}
