package com.zst.ynh.utils;

import android.app.Dialog;

public class DialogUtil {
    public static void hideDialog(Dialog dialog){
        if (dialog!=null && dialog.isShowing()){
            dialog.dismiss();
            dialog=null;
        }
    }
}
