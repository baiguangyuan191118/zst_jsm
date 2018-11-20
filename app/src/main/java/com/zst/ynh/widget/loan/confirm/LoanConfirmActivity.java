package com.zst.ynh.widget.loan.confirm;

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
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.DepositOpenInfoVBean;
import com.zst.ynh.bean.LoanConfirmBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.event.StringEvent;
import com.zst.ynh.view.BottomDialog;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private BottomDialog bottomDialog;
    private int position;

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
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
            layoutContain.removeAllViews();
            Space space = new Space(this);
            space.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10));
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

                        }
                    });
                }
                layoutContain.addView(view);
            }
            layoutContain.addView(space);
            for (LoanConfirmBean.ItemBean.OrderParamsBean.List2Bean list1Bean : loanConfirmBean.item.order_params.list2) {
                View view = View.inflate(this, R.layout.item_loan_confirm_layout, null);
                ((TextView) view.findViewById(R.id.tv_title)).setText(list1Bean.name);
                ((TextView) view.findViewById(R.id.tv_value)).setText(list1Bean.value);
                layoutContain.addView(view);
            }
            layoutContain.addView(space);
            for (LoanConfirmBean.ItemBean.OrderParamsBean.List3Bean list1Bean : loanConfirmBean.item.order_params.list3) {
                View view = View.inflate(this, R.layout.item_loan_confirm_layout, null);
                if (loanConfirmBean.item.order_params.list3.indexOf(list1Bean) != 0 && loanConfirmBean.item.order_params.list3.indexOf(list1Bean) != 2) {
                    ((TextView) view.findViewById(R.id.tv_title)).setText(list1Bean.name);
                    ((TextView) view.findViewById(R.id.tv_value)).setText(list1Bean.value);
                    layoutContain.addView(view);
                }
            }
            layoutContain.addView(space);
            //添加下方的协议
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
    }


    @OnClick({R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (tvUseOfLoan.getText().toString().trim().equals("请选择")) {
                    ToastUtils.showShort("请选择借款用途");
                } else if (loanConfirmBean.item.real_pay_pwd_status != 1) {//代表没有设置过交易密码 去设置
                   ARouter.getInstance().build(ArouterUtil.SET_PAY_PASSWORD).navigation();
                } else {
                    // TODO: 2018/10/25 输入交易密码 要去借款了就
                }
                break;
        }
    }

    /**
     * 用途的设值
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StringEvent messageEvent){
        if (TextUtils.isEmpty(messageEvent.getMessage())){
            tvUseOfLoan.setText("请选择");
        }else{
            tvUseOfLoan.setText(messageEvent.getMessage());
            position=messageEvent.getValue();
        }
    }

    @Override
    public void getDepositOpenInfo(DepositOpenInfoVBean depositOpenInfoVBean) {
        //存管处理
    }

}
