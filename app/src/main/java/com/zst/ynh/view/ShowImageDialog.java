package com.zst.ynh.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zst.ynh.R;
import com.zst.ynh.core.bitmap.ImageLoaderUtils;

public class ShowImageDialog extends Dialog {

    private ImageView close;
    private ImageView img;

    public ShowImageDialog(@NonNull Context context,String path) {
        super(context);
        setContentView(R.layout.dialog_show_image);
        initView();
        WindowManager.LayoutParams params=new WindowManager.LayoutParams();
        params.gravity=Gravity.TOP;
        params.width=WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(params);
        this.getWindow().setBackgroundDrawableResource(R.color.transparent);
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ImageLoaderUtils.loadUrl(context,path,img);
    }

    private void initView() {
        close=findViewById(R.id.close);
        img=findViewById(R.id.img_show);
;    }
}
