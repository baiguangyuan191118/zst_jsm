package com.zst.ynh.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.LoanConfirmBean;
import com.zst.ynh.view.keyboard.KeyboardNumberUtil;
import com.zst.ynh.view.keyboard.PwdInputController;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.view.TitleBar;

import org.w3c.dom.Text;

import butterknife.BindView;

public class PayPwdInputDialog extends Dialog {

    private Context context;
    private ImageView close;
    private TextView tips;
    private TextView payType;
    private TextView money;
    private PwdInputController pwdInputController;
    private LinearLayout keybord;
    private LoanConfirmBean loanConfirmBean;

    public PayPwdInputDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    public PayPwdInputDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
    }


    public void setContent(LoanConfirmBean loanConfirmBean){
        this.loanConfirmBean=loanConfirmBean;
    }

    public void reset(){
        pwdInputController.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay_pwd_input);
        initViews();
        WindowManager.LayoutParams params=new WindowManager.LayoutParams();
        params.gravity=Gravity.TOP;
        params.width=WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(params);
        this.getWindow().setBackgroundDrawableResource(R.color.transparent);
    }


    public interface InputCompleteListener{
        void inputComplete(String pwd);
    }

    public InputCompleteListener listner;

    public void setInputCompleteListener(InputCompleteListener listner){
        this.listner=listner;
    }


    private void initViews() {
        close=this.findViewById(R.id.iv_close);
        tips=this.findViewById(R.id.tv_tip);
        payType=this.findViewById(R.id.tv_pay_type);
        money=this.findViewById(R.id.tv_money);
        keybord=this.findViewById(R.id.llCustomerKb);
        pwdInputController=this.findViewById(R.id.input_controller);
        payType.setText("借款金额");
        money.setText(loanConfirmBean.item.money);
        pwdInputController.initKeyBoard(keybord,KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE.NUMBER);
        pwdInputController.showKeyBoard();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPwdInputDialog.this.dismiss();
            }
        });
        pwdInputController.setOnPwdInputEvent(new PwdInputController.OnPwdInputEvent() {
            @Override
            public void inputComplete(String pwd) {
                if(listner!=null){
                    listner.inputComplete(pwd);
                }
            }
        });
    }


    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
        pwdInputController.hideKeyBoard();
    }
}
