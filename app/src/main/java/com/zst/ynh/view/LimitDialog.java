package com.zst.ynh.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.zst.ynh.R;


public class LimitDialog extends Dialog {
    public LimitDialog(@NonNull Context context) {
        super(context);
    }

    public LimitDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.limitDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_limit_layout);
        TextView textView=findViewById(R.id.tv_hint);
        textView.setText("个人资料更新中\n请等待需要3-5分钟");
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setDimAmount(0f);
    }
}
