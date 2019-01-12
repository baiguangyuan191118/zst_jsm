package com.zst.ynh_base.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.zst.ynh_base.R;
import com.zst.ynh_base.util.ImageLoaderUtils;

public class LoadingDialog extends Dialog{
    private ImageView loading;
    private int height;

    public LoadingDialog(@NonNull Context context,int height) {
        super(context, R.style.progress_dialog);
        this.height=height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_loading_layout);
        WindowManager.LayoutParams params=new WindowManager.LayoutParams();
        params.gravity=Gravity.CENTER;
        params.width=WindowManager.LayoutParams.MATCH_PARENT;
        params.height=height;
        this.getWindow().setAttributes(params);
        this.getWindow().setBackgroundDrawableResource(R.color.transparent);
        loading= this.findViewById(R.id.loading);
        ImageLoaderUtils.loadRes(getContext(),R.drawable.loading,loading);
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
