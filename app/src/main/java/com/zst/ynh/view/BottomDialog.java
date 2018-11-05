package com.zst.ynh.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zst.ynh.R;
import com.zst.ynh.bean.LoanConfirmBean;
import com.zst.ynh.event.StringEvent;

import org.greenrobot.eventbus.EventBus;

public class BottomDialog extends Dialog {
    private TextView tv_cancel;
    private TextView tv_confirm;
    private NumberPickerView textConfigNumberPicker;
    private LoanConfirmBean loanConfirmBean;
    private String[] array;
    private Context context;


    public void setList( LoanConfirmBean loanConfirmBean) {
        this.loanConfirmBean = loanConfirmBean;
    }

    public BottomDialog(@NonNull Context context) {
        super(context);
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
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_confirm = findViewById(R.id.tv_confirm);
        textConfigNumberPicker = findViewById(R.id.number_picker);
        EventBus.getDefault().register(this);
        initNumberPicker();
        setOnclick();
    }

    private void initNumberPicker() {
        array=new String[loanConfirmBean.item.loan_use.size()];
        for (int i = 0; i < loanConfirmBean.item.loan_use.size(); i++) {
            array[i] = loanConfirmBean.item.loan_use.get(i).name;
        }
//        array=list.toArray(new String[list.size()]);
        //设置需要显示的内容数组
        textConfigNumberPicker.refreshByNewDisplayedValues(array);
    }

    private void setOnclick() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().unregister(this);
                dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectDate = array[textConfigNumberPicker.getValue()];
                EventBus.getDefault().post(new StringEvent(selectDate,textConfigNumberPicker.getValue()));
                EventBus.getDefault().unregister(this);
                dismiss();
            }
        });
    }

}
