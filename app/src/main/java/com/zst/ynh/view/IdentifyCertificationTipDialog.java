package com.zst.ynh.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.zst.ynh.R;
import com.zst.ynh.core.bitmap.ImageLoaderUtils;

public class IdentifyCertificationTipDialog extends Dialog {
    private Context context;
    public IdentifyCertificationTipDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    public IdentifyCertificationTipDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.statement_dialog);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_pic_tips_dialog);
        Window window=getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        CheckBox cb = findViewById(R.id.rb);
        Button btnGo = findViewById(R.id.btn_go);
        ImageView imageView=findViewById(R.id.iv_pic);
        ImageLoaderUtils.loadRes(context,R.mipmap.take_id_pic_tips_bg,imageView);
        if (null != listener) {
            btnGo.setOnClickListener(listener);
        }
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (null != iCbSelect) {
                    iCbSelect.setIsSelected(isChecked);
                }
            }
        });
    }

    private ICbSelect iCbSelect;
    private View.OnClickListener listener;

    public interface ICbSelect {
        void setIsSelected(boolean isSelected);
    }

    public void callBack(ICbSelect iCbSelect, View.OnClickListener listener) {
        this.iCbSelect = iCbSelect;
        this.listener = listener;
    }
}
