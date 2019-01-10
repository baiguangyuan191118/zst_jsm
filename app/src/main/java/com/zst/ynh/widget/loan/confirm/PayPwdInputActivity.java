package com.zst.ynh.widget.loan.confirm;

import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.ApplyLoanBean;
import com.zst.ynh.bean.DepositOpenInfoVBean;
import com.zst.ynh.bean.LoanConfirmBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.view.keyboard.KeyboardNumberUtil;
import com.zst.ynh.view.keyboard.PwdInputController;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.AlertDialog;

import butterknife.BindView;

@Route(path = ArouterUtil.PAY_PWD_INPUT)
@Layout(R.layout.activity_pay_pwd_input)
public class PayPwdInputActivity extends BaseActivity implements ILoanConfirmView {


    @BindView(R.id.rl_content)
    RelativeLayout rl_content;
    @BindView(R.id.iv_close)
    ImageView close;
    @BindView(R.id.tv_tip)
    TextView tips;
    @BindView(R.id.tv_pay_type)
    TextView payType;
    @BindView(R.id.tv_money)
    TextView money;

    @BindView(R.id.llCustomerKb)
    LinearLayout keybord;

    private LoanConfirmBean.ItemBean data;
    private String platfrom;
    @BindView( R.id.input_controller)
    PwdInputController pwdInputController;

    private LoanConfirmPresent loanConfirmPresent;
    private AlertDialog errorDailog;//其它错误
    private AlertDialog pwdErrorDialog;//支付错误

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        mTitleBar.setVisibility(View.GONE);
        setTitle(null);
        WindowManager.LayoutParams params=new WindowManager.LayoutParams();
        params.gravity=Gravity.TOP;
        params.width=WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(params);
        loanConfirmPresent = new LoanConfirmPresent();
        loanConfirmPresent.attach(this);
        initViews();
    }

    private void initViews() {

        payType.setText("借款金额");
        data = (LoanConfirmBean.ItemBean) getIntent().getSerializableExtra(BundleKey.PAY_PWD_INPUT_DATA);
        platfrom=getIntent().getStringExtra(BundleKey.PLATFORM);
        money.setText(data.money);
        pwdInputController.initKeyBoard(keybord, KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE.NUMBER);
        pwdInputController.showKeyBoard();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pwdInputController.setOnPwdInputEvent(new PwdInputController.OnPwdInputEvent() {
            @Override
            public void inputComplete(String pwd) {
                if (StringUtils.isEmpty(platfrom)) {//由你花
                    loanConfirmPresent.applyLoan(data, pwd, data.select_loanuse+"");
                } else {//贷超
                    loanConfirmPresent.applyPlatformLoan(data.period + "", data.money, pwd, platfrom);
                }

            }
        });
    }

    @Override
    public void getDepositOpenInfo(DepositOpenInfoVBean depositOpenInfoVBean) {

    }

    @Override
    public void applyLoanSuccess(ApplyLoanBean response) {
        ARouter.getInstance().build(ArouterUtil.APPLY_LOAN_SUCCESS).withInt(BundleKey.ORDER_ID, response.getItem().getOrder_id()).withString(BundleKey.PLATFORM,platfrom).navigation();
    }

    @Override
    public void applyLoanFailed(int code, final String errorMSG) {

        rl_content.setVisibility(View.GONE);
        if (code == 3) {//支付密码错误
            if (pwdErrorDialog == null) {
                pwdErrorDialog = new AlertDialog(this).builder().setCancelable(false).setMsg(errorMSG)
                        .setNegativeButton("忘记密码", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ARouter.getInstance().build(ArouterUtil.FORGET_PAY_PASSWORD).navigation();
                                finish();
                            }
                        }).setPositiveBold().setPositiveButton("重新输入", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                reset();
                            }
                        });
            }
            pwdErrorDialog.show();
        } else {//其他错误
            if (errorDailog == null) {
                errorDailog = new AlertDialog(this).builder().setCancelable(false).setMsg(errorMSG)
                        .setNegativeButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
            }
            errorDailog.show();

        }
    }

    public void reset() {
        rl_content.setVisibility(View.VISIBLE);
        pwdInputController.clear();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void ToastErrorMessage(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loanConfirmPresent!=null){
            loanConfirmPresent.detach();
        }

        if(pwdErrorDialog!=null){
            pwdErrorDialog=null;
        }
        if(errorDailog!=null){
            errorDailog=null;
        }

    }
}