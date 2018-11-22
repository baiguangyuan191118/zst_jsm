package com.zst.ynh.widget.repayment.paystyle;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.adapter.PaymentStyleAdapter;
import com.zst.ynh.bean.PaymentStyleBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import butterknife.BindView;

@Route(path = ArouterUtil.PAYMENT_STYLE)
@Layout(R.layout.activity_pay_style_layout)
public class PayStyleActivity extends BaseActivity {
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

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        mTitleBar.setTitle("账单明细");
        setData();

    }

    private void setData() {
        tvLoanMoney.setText(paymentStyleBean.data.loanAmount + "元");
        tvInterestMoney.setText(paymentStyleBean.data.lateFee + "元");
        tvFinalMoney.setText(paymentStyleBean.data.amountPayable + "元");
        paymentStyleAdapter = new PaymentStyleAdapter(this, R.layout.item_payment_type, paymentStyleBean.data.paymentMethod);
        listView.setAdapter(paymentStyleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (paymentStyleBean != null) {
                    if (paymentStyleBean.data.paymentMethod.get(position).type == 1) {//如果是推荐就代表是支付宝
                        if (checkAliPayInstalled(PayStyleActivity.this)) {
                            ARouter.getInstance().build(ArouterUtil.ABOUT_US).withString(BundleKey.URL, paymentStyleBean.data.paymentMethod.get(position).url);
                        } else {
                            ToastUtils.showShort("请先安装支付宝");
                        }
                    } else {
                        ARouter.getInstance().build(ArouterUtil.ABOUT_US).withString(BundleKey.URL, paymentStyleBean.data.paymentMethod.get(position).url);
                    }
                }
            }
        });
    }

    private static boolean checkAliPayInstalled(Context context) {

        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

}
