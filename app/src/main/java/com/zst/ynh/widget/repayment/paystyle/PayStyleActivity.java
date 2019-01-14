package com.zst.ynh.widget.repayment.paystyle;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.adapter.PaymentStyleAdapter;
import com.zst.ynh.base.UMBaseActivity;
import com.zst.ynh.bean.PaymentStyleBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.utils.StringUtil;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import butterknife.BindView;

@Route(path = ArouterUtil.PAYMENT_STYLE)
@Layout(R.layout.activity_pay_style_layout)
public class PayStyleActivity extends UMBaseActivity implements IPayStyleView {
    @BindView(R.id.tv_loan_money)
    TextView tvLoanMoney;
    @BindView(R.id.tv_interest_money)
    TextView tvInterestMoney;
    @BindView(R.id.tv_final_money)
    TextView tvFinalMoney;
    @BindView(R.id.listView)
    ListView listView;
    @Autowired(name = BundleKey.PAYMENTSTYLEBEAN)
    PaymentStyleBean paymentStyleBean;
    private PaymentStyleAdapter paymentStyleAdapter;

    @BindView(R.id.fl_coupon)
    FrameLayout fl_coupon;
    @BindView(R.id.tv_coupon)
    TextView tv_coupon;
    private PaymentStyleBean.Coupon selectCoupon;

    private PayStylePresent payStylePresent;

    private int payType;

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        mTitleBar.setTitle("账单明细");
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setData();
        payStylePresent=new PayStylePresent();
        payStylePresent.attach(this);
        fl_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ArouterUtil.SELECT_COUPON).withSerializable(BundleKey.COUPON_LIST,paymentStyleBean).navigation(PayStyleActivity.this,0);
            }
        });
    }

    private void setData() {
        if (paymentStyleBean!=null) {
            tvLoanMoney.setText(StringUtil.formatDecimal2(paymentStyleBean.loanAmount) + "元");
            tvInterestMoney.setText(StringUtil.formatDecimal2(paymentStyleBean.lateFee) + "元");
            tvFinalMoney.setText(StringUtil.formatDecimal2(paymentStyleBean.amountPayable) + "元");
            if(paymentStyleBean.couponCount==0){
                tv_coupon.setText("暂无可用");
                tv_coupon.setTextColor(Color.GRAY);
            }else{
                tv_coupon.setText(paymentStyleBean.couponCount+"张可用");
                tv_coupon.setTextColor(getResources().getColor(R.color.color_f0170236));
            }
            paymentStyleAdapter = new PaymentStyleAdapter(this, R.layout.item_payment_type, paymentStyleBean.paymentMethod);
            listView.setAdapter(paymentStyleAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (paymentStyleBean != null) {
                        if (paymentStyleBean.paymentMethod.get(position).type == 1) {//如果是推荐就代表是支付宝
                            if (!checkAliPayInstalled(PayStyleActivity.this)) {
                                ToastUtils.showShort("请先安装支付宝");
                                return;
                            }
                        }
                        String coupon_id=null;
                        if(selectCoupon!=null){
                           coupon_id=selectCoupon.user_coupon_id;
                        }
                        payType=paymentStyleBean.paymentMethod.get(position).type;
                        payStylePresent.getPayUrl(paymentStyleBean.repaymentId,paymentStyleBean.paymentMethod.get(position).type+"",coupon_id,paymentStyleBean.platformCode);

                    }
                }
            });
        }
    }

    private static boolean checkAliPayInstalled(Context context) {

        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            selectCoupon= (PaymentStyleBean.Coupon) data.getSerializableExtra(BundleKey.SELECT_COUPON);
            if(selectCoupon!=null){
                float couponAmount= Float.parseFloat(selectCoupon.coupon_amount);
                float amountpayable=paymentStyleBean.amountPayable-couponAmount;
                tvFinalMoney.setText(StringUtil.formatDecimal2(amountpayable)+"元");
                tv_coupon.setText("-"+StringUtil.formatDecimal2(couponAmount)+"元");
                tv_coupon.setTextColor(getResources().getColor(R.color.color_f0170236));
            }
        }else{
            selectCoupon=null;
            tv_coupon.setText("不使用优惠券");
            tv_coupon.setTextColor(Color.GRAY);
            tvFinalMoney.setText(StringUtil.formatDecimal2(paymentStyleBean.amountPayable)+"元");
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    public void getPayUrlSuccess(String url) {
        if(payType==1){//支付宝
            ARouter.getInstance().build(ArouterUtil.REPAYMENT_WEBVIEW).withBoolean(BundleKey.WEB_SET_SESSION,true).withString(BundleKey.URL, url).navigation();
        }else{//其他
            ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL,url).withBoolean(BundleKey.WEB_SET_SESSION,true).navigation();
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
}
