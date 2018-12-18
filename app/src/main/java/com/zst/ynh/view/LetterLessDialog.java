package com.zst.ynh.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zst.ynh.R;


public class LetterLessDialog extends Dialog {
    private TextView tv_tips;
    private Button btn_submit;
    private ImageButton ibDialogClose;
    private String tip;
    private OnClckListener onclckListener;
    private Context context;

    public LetterLessDialog(@NonNull Context context) {
        super(context, R.style.AlertDialogStyle);
    }

    public LetterLessDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setOnclick(OnClckListener onclckListener) {
        this.onclckListener = onclckListener;
    }
    public void setContent(String tips) {
        this.tip=tips;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_hint_layout);
        setCanceledOnTouchOutside(false);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth(); //设置dialog的宽度为当前手机屏幕的宽度
        getWindow().setAttributes(p);
        tv_tips = findViewById(R.id.tv_tips);
        btn_submit = findViewById(R.id.btn_submit);
        ibDialogClose = findViewById(R.id.ib_dialog_close);
        ibDialogClose.setVisibility(View.VISIBLE);
        tv_tips.setText(tip);
        ibDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onclckListener != null)
                    onclckListener.onClickListener();
            }
        });
    }


    public interface OnClckListener {
        void onClickListener();
    }
}
