package com.zst.ynh_base.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.zst.ynh_base.R;

public class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static class Builder {
        private TextView tv_title;
        private TextView tv_content1;
        private TextView tv_content2;
        private View view1;
        private View view2;
        private Button btn_left;
        private Button btn_right;
        private Context context;
        private LinearLayout ll_button;
        private String title;
        private String content1;
        private String content2;
        private String btn_left_text;
        private String btn_right_text;
        private int titleColor;
        private int contentColor;
        private int btn_left_color;
        private int btn_right_color;
        private int btn_left_background_color;
        private int btn_right_background_color;
        private View.OnClickListener leftOCL;
        private View.OnClickListener rightOCL;
        private boolean isCancelable=true;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent1(String content1) {
            this.content1 = content1;
            return this;
        }

        public Builder setContent2(String content2) {
            this.content2 = content2;
            return this;
        }

        public Builder setBtnLeftText(String btnLeftText) {
            this.btn_left_text = btnLeftText;
            return this;
        }

        public Builder setBtnRightText(String btnRightText) {
            this.btn_right_text = btnRightText;
            return this;
        }

        public Builder setTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder setContentColot(int contentColor) {
            this.contentColor = contentColor;
            return this;
        }

        public Builder setBtnLeftColor(int btnLeftColor) {
            this.btn_left_color = btnLeftColor;
            return this;
        }

        public Builder setBtnRightColor(int btnRightColor) {
            this.btn_right_color = btnRightColor;
            return this;
        }
        public Builder setBtnLeftBackgroundColor(int btnLeftBackgroundColor) {
            this.btn_left_background_color = btnLeftBackgroundColor;
            return this;
        }

        public Builder setBtnRightBackgroundColor(int btnRightBackgroundColor) {
            this.btn_right_background_color = btnRightBackgroundColor;
            return this;
        }
        public Builder setLeftOnClick(View.OnClickListener left) {
            this.leftOCL = left;
            return this;
        }

        public Builder setRightOnClick(View.OnClickListener right) {
            this.rightOCL = right;
            return this;
        }

        public Builder setCancelable(boolean isCancelable){
            this.isCancelable=isCancelable;
            return this;
        }


        public BaseDialog create() {
            BaseDialog baseDialog = new BaseDialog(context, R.style.AlertDialogStyle);
            View view = View.inflate(context, R.layout.dialog_base_layout, null);
            tv_title = view.findViewById(R.id.tv_title);
            tv_content1 = view.findViewById(R.id.tv_content1);
            tv_content2 = view.findViewById(R.id.tv_content2);
            view1 = view.findViewById(R.id.view1);
            view2 = view.findViewById(R.id.view2);
            btn_left = view.findViewById(R.id.btn_left);
            btn_right = view.findViewById(R.id.btn_right);
            ll_button=view.findViewById(R.id.ll_button);
            baseDialog.setContentView(view);
            baseDialog.setCanceledOnTouchOutside(false);
            //设置屏幕的宽度
            Window window = baseDialog.getWindow();
            android.view.WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = (int) (ScreenUtils.getScreenWidth() * 0.8);
            attributes.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(attributes);
            //view的设置与判断
            if (TextUtils.isEmpty(title)) {
                tv_title.setVisibility(View.GONE);
            } else {
                tv_title.setText(title);
                tv_title.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(content1)) {
                tv_content1.setVisibility(View.GONE);
            } else {
                tv_content1.setText(content1);
                tv_content1.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(content2)) {
                tv_content2.setVisibility(View.GONE);
            } else {
                tv_content2.setText(content2);
                tv_content2.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(content1) && TextUtils.isEmpty(content2)) {
                view1.setVisibility(View.GONE);
            } else {
                view1.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(btn_left_text)) {
                btn_left.setVisibility(View.GONE);
            } else {
                btn_left.setText(btn_left_text);
                btn_left.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(btn_right_text)) {
                btn_right.setVisibility(View.GONE);
            } else {
                btn_right.setText(btn_right_text);
                btn_right.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(btn_left_text) || TextUtils.isEmpty(btn_right_text)) {
                view2.setVisibility(View.GONE);
            } else {
                view2.setVisibility(View.VISIBLE);
            }
            if (titleColor != 0) {
                tv_title.setTextColor(titleColor);
            }
            if (contentColor != 0) {
                tv_content1.setTextColor(contentColor);
                tv_content2.setTextColor(contentColor);
            }
            if (btn_left_color != 0) {
                btn_left.setTextColor(btn_left_color);
            }
            if (btn_right_color != 0) {
                btn_right.setTextColor(btn_right_color);
            }
            if (btn_left_background_color != 0) {
                btn_left.setBackgroundColor(btn_left_background_color);
            }
            if (btn_right_background_color != 0) {
                btn_right.setBackgroundColor(btn_right_background_color);
            }
            if (!TextUtils.isEmpty(btn_left_text)) {
                btn_left.setOnClickListener(leftOCL);
            }
            if (!TextUtils.isEmpty(btn_right_text)) {
                btn_right.setOnClickListener(rightOCL);
            }
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(30,0,30,0);
            if (btn_right.getVisibility()==View.GONE){
                btn_left.setLayoutParams(layoutParams);
            }
            if (btn_left.getVisibility()==View.GONE){
                btn_right.setLayoutParams(layoutParams);
            }
            baseDialog.setCancelable(isCancelable);
            return baseDialog;
        }
    }
}
