package com.zst.ynh_base.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zst.ynh_base.R;

public class LoadingDialog extends Dialog{
    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.progress_dialog);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_loading_layout);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        if (isShowing()) {
            return;
        }
        super.show();
    }
    @Override
    public void dismiss() {
        super.dismiss();
    }
}
