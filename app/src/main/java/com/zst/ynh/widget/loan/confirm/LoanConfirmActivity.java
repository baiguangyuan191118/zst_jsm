package com.zst.ynh.widget.loan.confirm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zst.ynh.R;
import com.zst.ynh.bean.ApplyLoanBean;
import com.zst.ynh.bean.DepositOpenInfoVBean;
import com.zst.ynh.bean.LoanConfirmBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.event.StringEvent;
import com.zst.ynh.view.BottomDialog;
import com.zst.ynh.view.PayPwdInputDialog;
import com.zst.ynh.view.ServiceChatgeDialog;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.AlertDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ArouterUtil.LOAN_CONFIRM)
@Layout(R.layout.activity_loan_confirm_layout)
public class LoanConfirmActivity extends BaseActivity implements ILoanConfirmView {
    @BindView(R.id.layout_contain)
    LinearLayout layoutContain;
    @BindView(R.id.txt_lend)
    TextView txtLend;
    @BindView(R.id.tv_loan_agreement)
    TextView tvLoanAgreement;
    @BindView(R.id.tv_use_of_loan)
    TextView tvUseOfLoan;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.cb_agreement)
    CheckBox cbAgreement;

    @Autowired(name = BundleKey.LOAN_CONFIRM)
    LoanConfirmBean loanConfirmBean;
    private LoanConfirmPresent loanConfirmPresent;
    private String[] agreementTitle;
    private String[] agreementUrl;
    private BottomDialog loanUseDialog;//贷款用途的dialog
    private int position;//贷款用途的位置
    private String[] loanUseArray;//贷款用途

    private PayPwdInputDialog payPwdInputDialog;//支付密码输入
    private AlertDialog errorDailog;//其它错误
    private AlertDialog pwdErrorDialog;//支付错误

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        loanConfirmPresent = new LoanConfirmPresent();
        loanConfirmPresent.attach(this);
        setData();
    }

    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    /**
     * 设置数据
     */
    private void setData() {
        if (loanConfirmBean != null) {
            //添加上方的layout
            addContentView();
            //添加下方的协议
            addArgumentView();
            //设置贷款用途数据
            setLoanUseData();
        }
    }

    private void addContentView() {
        layoutContain.removeAllViews();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(10));
        Space space = new Space(this);
        layoutContain.addView(space, layoutParams);
        for (LoanConfirmBean.ItemBean.OrderParamsBean.List1Bean list1Bean : loanConfirmBean.item.order_params.list1) {
            View view = View.inflate(this, R.layout.item_loan_confirm_layout, null);
            ((TextView) view.findViewById(R.id.tv_title)).setText(list1Bean.name);
            ((TextView) view.findViewById(R.id.tv_value)).setText(list1Bean.value);

            if (loanConfirmBean.item.order_params.list1.indexOf(list1Bean) == 2) {
                //总利息边上的按钮
                view.findViewById(R.id.iv_interest).setBackgroundResource(R.mipmap.home_attention);
                view.findViewById(R.id.iv_interest).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        serviceChargePopDialog();
                    }
                });
            }
            layoutContain.addView(view);
        }
        Space space2 = new Space(this);
        layoutContain.addView(space2, layoutParams);
        for (LoanConfirmBean.ItemBean.OrderParamsBean.List2Bean list1Bean : loanConfirmBean.item.order_params.list2) {
            View view = View.inflate(this, R.layout.item_loan_confirm_layout, null);
            ((TextView) view.findViewById(R.id.tv_title)).setText(list1Bean.name);
            ((TextView) view.findViewById(R.id.tv_value)).setText(list1Bean.value);
            layoutContain.addView(view);
        }
        Space space3 = new Space(this);
        layoutContain.addView(space3, layoutParams);
        for (LoanConfirmBean.ItemBean.OrderParamsBean.List3Bean list1Bean : loanConfirmBean.item.order_params.list3) {
            View view = View.inflate(this, R.layout.item_loan_confirm_layout, null);
            if (loanConfirmBean.item.order_params.list3.indexOf(list1Bean) != 0 && loanConfirmBean.item.order_params.list3.indexOf(list1Bean) != 2) {
                ((TextView) view.findViewById(R.id.tv_title)).setText(list1Bean.name);
                ((TextView) view.findViewById(R.id.tv_value)).setText(list1Bean.value);
                layoutContain.addView(view);
            }
        }
        Space space4 = new Space(this);
        layoutContain.addView(space4, layoutParams);
    }

    private void addArgumentView() {
        agreementTitle = new String[loanConfirmBean.item.agreement.size()];
        agreementUrl = new String[loanConfirmBean.item.agreement.size()];
        for (int i = 0; i < loanConfirmBean.item.agreement.size(); i++) {
            agreementTitle[i] = loanConfirmBean.item.agreement.get(i).title;
            agreementUrl[i] = loanConfirmBean.item.agreement.get(i).url;
        }
        //不知道具体的数量 目前采用的是笨方法
        if (agreementTitle.length == 1) {
            tvLoanAgreement.setText(new SpanUtils().append("我已阅读并提醒").append(agreementTitle[0])
                    .setForegroundColor(getResources().getColor(R.color.them_color)).setClickSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            ARouter.getInstance().build(ArouterUtil.WEB).withString(BundleKey.URL, agreementUrl[0]).navigation();
                        }
                    }).create());
        } else if (agreementTitle.length == 2) {
            tvLoanAgreement.setText(new SpanUtils().append("我已阅读并提醒").append(agreementTitle[0])
                    .setForegroundColor(getResources().getColor(R.color.them_color)).setClickSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            ARouter.getInstance().build(ArouterUtil.WEB).withString(BundleKey.URL, agreementUrl[0]).navigation();
                        }
                    }).append(agreementTitle[1]).setClickSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            ARouter.getInstance().build(ArouterUtil.WEB).withString(BundleKey.URL, agreementUrl[1]).navigation();
                        }
                    }).create());
        } else if (agreementTitle.length == 3) {
            tvLoanAgreement.setText(new SpanUtils().append("我已阅读并提醒").append(agreementTitle[0])
                    .setForegroundColor(getResources().getColor(R.color.them_color)).setClickSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            ARouter.getInstance().build(ArouterUtil.WEB).withString(BundleKey.URL, agreementUrl[0]).navigation();
                        }
                    }).append(agreementTitle[1]).setClickSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            ARouter.getInstance().build(ArouterUtil.WEB).withString(BundleKey.URL, agreementUrl[1]).navigation();
                        }
                    }).append(agreementTitle[2]).setClickSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            ARouter.getInstance().build(ArouterUtil.WEB).withString(BundleKey.URL, agreementUrl[2]).navigation();
                        }
                    }).create());
        }
        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnNext.setEnabled(isChecked ? true : false);
            }
        });

    }

    private void setLoanUseData() {
        if (loanConfirmBean.item.loan_use == null || loanConfirmBean.item.loan_use.size() == 0) {
            String data = SPUtils.getInstance().getString(SPkey.LOAN_USE);
            if (data != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<LoanConfirmBean.ItemBean.LoanUseBean>>() {
                }.getType();
                List<LoanConfirmBean.ItemBean.LoanUseBean> saveUse = gson.fromJson(data, type);
                loanConfirmBean.item.loan_use = saveUse;
            } else {
                return;
            }
        } else {
            Gson gson = new Gson();
            String json = gson.toJson(loanConfirmBean.item.loan_use);
            SPUtils.getInstance().put("loanUse", json);
        }

        loanUseArray = new String[loanConfirmBean.item.loan_use.size()];
        for (int i = 0; i < loanConfirmBean.item.loan_use.size(); i++) {
            LoanConfirmBean.ItemBean.LoanUseBean loanUse = loanConfirmBean.item.loan_use.get(i);
            loanUseArray[i] = loanUse.name;
            if (loanUse.value == 11) {
                position = loanConfirmBean.item.loan_use.indexOf(loanUse);
                tvUseOfLoan.setText(loanUse.name);
            }
        }
        loanUseDialog = new BottomDialog(this, R.style.progress_dialog);
        loanUseDialog.setData(loanUseArray);
    }

    /**
     * 服务费弹框
     */
    private void serviceChargePopDialog() {
        ServiceChatgeDialog dialog = new ServiceChatgeDialog(this);
        dialog.setContent(loanConfirmBean.item.fees);
        dialog.show();
    }

    @Override
    public void showLoading() {
        showLoadingView();
    }

    @Override
    public void hideLoading() {
        hideLoadingView();
    }

    @Override
    public void ToastErrorMessage(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loanConfirmPresent != null)
            loanConfirmPresent.detach();

        if (payPwdInputDialog != null) {
            payPwdInputDialog = null;
        }
        if (loanUseDialog != null) {
            loanUseDialog = null;
        }

        if(pwdErrorDialog!=null){
            pwdErrorDialog=null;
        }
        if(errorDailog!=null){
            errorDailog=null;
        }

    }


    @OnClick({R.id.btn_next, R.id.tv_use_of_loan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (tvUseOfLoan.getText().toString().trim().equals("请选择")) {
                    ToastUtils.showShort("请选择借款用途");
                } else if (loanConfirmBean.item.real_pay_pwd_status != 1) {//代表没有设置过交易密码 去设置
                    ARouter.getInstance().build(ArouterUtil.UPDATE_TRADE_PASSWORD).withBoolean(BundleKey.IS_SET_PAY_PWD, true).navigation();
                } else {
                    // 输入交易密码 要去借款了
                    payPwdInputDialog = new PayPwdInputDialog(this);
                    payPwdInputDialog.setContent(loanConfirmBean);
                    payPwdInputDialog.setInputCompleteListener(new PayPwdInputDialog.InputCompleteListener() {
                        @Override
                        public void inputComplete(String pwd) {
                            int loanuse = loanConfirmBean.item.loan_use.get(position).value;
                            loanConfirmPresent.applyLoan(loanConfirmBean.item, pwd, loanuse + "");
                        }
                    });
                    payPwdInputDialog.show();
                }
                break;
            case R.id.tv_use_of_loan:
                if (loanUseDialog != null) {
                    loanUseDialog.show();
                    loanUseDialog.setPosition(position);
                }
                break;
        }
    }

    /**
     * 用途的设值
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StringEvent messageEvent) {
        if (TextUtils.isEmpty(messageEvent.getMessage())) {
            tvUseOfLoan.setText("请选择");
        } else {
            tvUseOfLoan.setText(messageEvent.getMessage());
            position = messageEvent.getValue();
        }
    }

    @Override
    public void getDepositOpenInfo(DepositOpenInfoVBean depositOpenInfoVBean) {
        //存管处理
    }

    /*
     * 申请贷款成功 {"code":0,"message":"申请成功","data":{"item":{"order_id":589947}}}
     * */
    @Override
    public void applyLoanSuccess(ApplyLoanBean response) {
        payPwdInputDialog.dismiss();
        //百融防欺诈
        ARouter.getInstance().build(ArouterUtil.APPLY_LOAN_SUCCESS).withInt(BundleKey.ORDER_ID, response.getItem().getOrder_id()).navigation();

    }


    @Override
    public void applyLoanFailed(int code, String errorMSG) {
        if (payPwdInputDialog != null && payPwdInputDialog.isShowing()) {
            payPwdInputDialog.dismiss();
        }

        if (code == 3) {//支付密码错误
            if (pwdErrorDialog == null) {
                pwdErrorDialog = new AlertDialog(this).builder().setCancelable(false).setMsg(errorMSG)
                        .setNegativeButton("忘记密码", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ARouter.getInstance().build(ArouterUtil.FORGET_PAY_PASSWORD).navigation();
                            }
                        }).setPositiveBold().setPositiveButton("重新输入", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                payPwdInputDialog.reset();
                                payPwdInputDialog.show();
                            }
                        });
            }
            pwdErrorDialog.show();
        } else {//其他错误
            if(errorDailog==null){
                errorDailog=new AlertDialog(this).builder().setCancelable(false).setMsg(errorMSG)
                        .setNegativeButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
            }
            errorDailog.show();

        }
    }

}
