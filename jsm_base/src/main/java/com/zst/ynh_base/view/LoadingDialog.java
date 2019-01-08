package com.zst.ynh_base.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.zst.ynh_base.R;
import com.zst.ynh_base.util.ImageLoaderUtils;

public class LoadingDialog extends Dialog{
    private ImageView loading;
    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.progress_dialog);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_loading_layout);
        loading= this.findViewById(R.id.loading);
        ImageLoaderUtils.loadRes(getContext(),R.drawable.loading,loading);
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
