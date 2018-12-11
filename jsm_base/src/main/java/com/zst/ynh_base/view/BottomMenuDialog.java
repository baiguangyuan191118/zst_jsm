package com.zst.ynh_base.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zst.ynh_base.R;

public class BottomMenuDialog extends Dialog {
    public BottomMenuDialog(@NonNull Context context) {
        super(context);
    }

    public BottomMenuDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * button数量必须与要填写的数组数量对应
     */
    public static class Builder {
        private LinearLayout ll_group;
        private Button btn_close;
        private int buttonNumber=1;//默认有一个
        private Context context;
        private String[] BtnTitle;
        private int[] btnTitleColor;//颜色与title必须与添加的标题数目相同要不然会报错
        private View.OnClickListener leftOCL;
        private View.OnClickListener rightOCL;
        private boolean isCancelable=true;
        private IonItemClickListener ionItemClickListener;
        private View.OnClickListener cancelListener;

        public Builder(Context context) {
            this.context = context;
        }

        public BottomMenuDialog.Builder setBtnTitle(String[] BtnTitle) {
            this.BtnTitle = BtnTitle;
            return this;
        }
        public BottomMenuDialog.Builder setBtnTitleColor(int[] btnTitleColor) {
            this.btnTitleColor = btnTitleColor;
            return this;
        }
        public BottomMenuDialog.Builder setButtonNumber(int buttonNumber) {
            this.buttonNumber = buttonNumber;
            return this;
        }
        public BottomMenuDialog.Builder setOnClickWithPosition(IonItemClickListener ionItemClickListener) {
            this.ionItemClickListener=ionItemClickListener;
            return this;
        }
        public BottomMenuDialog.Builder setCancelListener(View.OnClickListener cancelListener) {
            this.cancelListener=cancelListener;
            return this;
        }
        public BottomMenuDialog.Builder setCancelable(boolean isCancelable){
            this.isCancelable=isCancelable;
            return this;
        }


        public BottomMenuDialog create() {
            BottomMenuDialog BottomMenuDialog = new BottomMenuDialog(context, R.style.AlertDialogStyle);
            View view = View.inflate(context, R.layout.dialog_bottom_menu, null);
            ll_group = view.findViewById(R.id.ll_group);
            btn_close = view.findViewById(R.id.btn_close);
            BottomMenuDialog.setContentView(view);
            BottomMenuDialog.setCanceledOnTouchOutside(false);
            BottomMenuDialog.show();
            //设置屏幕的宽度
            Window window = BottomMenuDialog.getWindow();
            android.view.WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity=Gravity.BOTTOM;
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(attributes);
            //view的设置与判断
            ll_group.removeAllViews();
            for( int i=0;i<buttonNumber;i++){
                final int index=i;
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                Button button=new Button(context);
                button.setText(BtnTitle[i]);
                button.setTextSize(16);
                if (btnTitleColor!=null){
                    button.setTextColor(context.getResources().getColor(btnTitleColor[i]));
                }else {
                    button.setTextColor(context.getResources().getColor(R.color.them_color));
                }
                button.setLayoutParams(layoutParams);
                button.setBackground(null);
                ll_group.addView(button);
                View cutView=new View(context);
                cutView.setBackgroundColor(context.getResources().getColor(R.color.dividing_color));
                LinearLayout.LayoutParams cutLayoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
                cutView.setLayoutParams(cutLayoutParams);
                if (i==buttonNumber-1){
                    ll_group.removeView(cutView);
                }else{
                    ll_group.addView(cutView);
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ionItemClickListener.onItemClickListener(index);
                    }
                });
            }

            if (cancelListener!=null){
                btn_close.setOnClickListener(cancelListener);
            }
            BottomMenuDialog.setCancelable(isCancelable);
            return BottomMenuDialog;
        }
    }
   public interface IonItemClickListener{
        void onItemClickListener(int position);
    }
}
