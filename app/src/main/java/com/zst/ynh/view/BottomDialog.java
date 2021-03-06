package com.zst.ynh.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zst.ynh.R;
import com.zst.ynh.bean.LoanConfirmBean;
import com.zst.ynh.event.StringEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class BottomDialog extends Dialog {
    private TextView tv_cancel;
    private TextView tv_confirm;
    private NumberPickerView textConfigNumberPicker;
    private String[] array;
    private Context context;
    private int type;//在设置联系人的页面  1，紧急联系人  2.常用联系人


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public void setData(String[] array ) {
        this.array = array;
    }

    public BottomDialog(@NonNull Context context) {
        this(context, R.style.BottomDialog);
        this.context = context;
    }

    public BottomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_numberpick_layout);
        setCancelable(false);//点击外部不可dismiss
        setCanceledOnTouchOutside(false);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth(); //设置dialog的宽度为当前手机屏幕的宽度
        getWindow().setAttributes(p);

        tv_cancel = findViewById(R.id.tv_cancel);
        tv_confirm = findViewById(R.id.tv_confirm);
        textConfigNumberPicker = findViewById(R.id.number_picker);
        initNumberPicker();
        setOnclick();
    }

    private void initNumberPicker() {
//        array=list.toArray(new String[list.size()]);
        //设置需要显示的内容数组
        if (array!=null && array.length>0)
        textConfigNumberPicker.refreshByNewDisplayedValues(array);
    }

    public void setPosition(int position){
        textConfigNumberPicker.setValue(position);
    }

    public void  notifyDataChanged(){
        if (textConfigNumberPicker!=null){
            initNumberPicker();
        }
    }
    private void setOnclick() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectDate = array[textConfigNumberPicker.getValue()];
                EventBus.getDefault().post(new StringEvent(selectDate,textConfigNumberPicker.getValue()));
                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
