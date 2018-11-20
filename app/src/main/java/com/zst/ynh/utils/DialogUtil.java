package com.zst.ynh.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.widget.person.settings.SettingsActivity;
import com.zst.ynh_base.view.AlertDialog;

public class DialogUtil {
    public static void hideDialog(Dialog dialog){
        if (dialog!=null && dialog.isShowing()){
            dialog.dismiss();
            dialog=null;
        }
    }

    public static void showDialogToCertitication(Context context){
        new AlertDialog(context).builder()
                .setCancelable(false)
                .setMsg("亲，请先填写个人信息哦~")
                .setPositiveBold().setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ArouterUtil.TO_CERTIFICATION).navigation();
            }
        }).show();
    }

}
