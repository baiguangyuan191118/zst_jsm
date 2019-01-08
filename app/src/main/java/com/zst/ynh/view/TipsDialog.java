package com.zst.ynh.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.zst.ynh.R;

public class TipsDialog extends Dialog {

    private TextView submit;
    private TextView tvTitle;
    private TextView tvTips;
    private TextView tvProtocol;
    private ImageView close;
    private ImageView bg;
    private String title, tips, buttonText;
    private boolean closeHidden;
    private int[] padding;

    public TipsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams params=new WindowManager.LayoutParams();
        params.gravity=Gravity.CENTER;
        params.width= (int) (ScreenUtils.getScreenWidth()*0.8);
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(params);

        setContentView(R.layout.base_tips_dialog);
        submit =  this.findViewById(R.id.tv_submit);
        tvTitle =  this.findViewById(R.id.tv_title);
        tvTips =  this.findViewById(R.id.tv_tips);
        tvProtocol =  this.findViewById(R.id.tv_protocol);
        close =  this.findViewById(R.id.iv_close);
        if (closeHidden) {
            close.setVisibility(View.GONE);
        } else {
            close.setVisibility(View.VISIBLE);
        }
        bg = this.findViewById(R.id.bg);
        if (resourceId > 0) {
            bg.setBackgroundResource(resourceId);
        } else {
            bg.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(tips)) {
            tvTips.setText(tips);
            if (padding.length == 4) {
                tvTips.setPadding(padding[0], padding[1], padding[2], padding[3]);
            }

        } else {
            tvTips.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(buttonText)) {
            submit.setText(buttonText);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickCallBack) {
                    TipsDialog.this.dismiss();
                    clickCallBack.click();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != closeCallBack) {
                    closeCallBack.close();
                }
                TipsDialog.this.dismiss();
            }
        });
    }


    public TextView getAddtioncalInfo() {
        return tvProtocol;
    }

    private ClickCallBack callBack;

    public void setClickCallBack(ClickCallBack callBack) {
        this.callBack = callBack;
    }

    public interface ClickCallBack {
        /**
         * 点击按钮回调
         */
        void click();
    }

    public void setCloseCallBack(CloseCallBack closeCallBack) {
        this.closeCallBack = closeCallBack;
    }

    private CloseCallBack closeCallBack;

    public interface CloseCallBack {
        /**
         * 点击右上角X回调
         */
        void close();
    }

    private ClickCallBack clickCallBack;
    private int resourceId;

    public void setContent(String title, String tips, String buttonText, ClickCallBack clickCallBack, int resourceId, boolean closeHidden, int... padding) {
        this.title = title;
        this.tips = tips;
        this.buttonText = buttonText;
        this.clickCallBack = clickCallBack;
        this.resourceId = resourceId;
        this.closeHidden = closeHidden;
        this.padding = padding;
    }

}
