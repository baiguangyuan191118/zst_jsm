package com.zst.jsm_base.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zst.jsm_base.R;

public class LoadingDialog extends Dialog{
    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.progress_dialog);
        setContentView(R.layout.base_loading_layout);
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
