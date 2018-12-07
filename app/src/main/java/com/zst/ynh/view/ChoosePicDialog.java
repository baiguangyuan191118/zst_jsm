package com.zst.ynh.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zst.ynh.R;

public class ChoosePicDialog extends Dialog {

    private Button takepic;
    private Button close;
    private LinearLayout content;

    public ChoosePicDialog(@NonNull Context context) {
        super(context);
    }

    public ChoosePicDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_pic);
        initView();
        WindowManager.LayoutParams params=new WindowManager.LayoutParams();
        params.gravity=Gravity.TOP;
        params.width=WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(params);
        this.getWindow().setBackgroundDrawableResource(R.color.transparent);
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
    }

    private void initView() {
        content=this.findViewById(R.id.dialog_content);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoosePicDialog.this.dismiss();
            }
        });
        takepic=this.findViewById(R.id.btn_take_pic);
        close=this.findViewById(R.id.btn_close);
        takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listner!=null){
                    listner.takepic();
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoosePicDialog.this.dismiss();
            }
        });
    }

    public interface TakePicListener{
        void takepic();
    }

    private ChoosePicDialog.TakePicListener listner;

    public void setTakePicListener(ChoosePicDialog.TakePicListener listner){
        this.listner=listner;
    }
}
