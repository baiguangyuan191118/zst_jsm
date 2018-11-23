package com.zst.ynh.widget.person.loanrecord;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.adapter.LoanDetailsAdapter;
import com.zst.ynh.bean.LoanDetailBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.mvp.view.IBaseView;
import com.zst.ynh_base.util.Layout;

import java.util.List;

import butterknife.BindView;

/*
* 还款页面
*
* */
@Layout(R.layout.activity_loan_details)
@Route(path=ArouterUtil.LOAN_DETAILS)
public class LoanDetailsActivity extends BaseActivity{

    @BindView(R.id.tv_loan_money)
    TextView loanMoney;

    @BindView(R.id.tv_latefee)
    TextView latefee;

    @BindView(R.id.tv_final_money)
    TextView finalMoney;

    @BindView(R.id.loan_details_recycler)
    RecyclerView recyclerView;


    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
       loadContentView();
       LoanDetailBean loanDetailBean= (LoanDetailBean) getIntent().getSerializableExtra(BundleKey.LOAN_DETAIL);
       mTitleBar.setTitle("账单明细");
       loanMoney.setText(loanDetailBean.getLoanAmount()+"元");
       latefee.setText(loanDetailBean.getLateFee()+"元");
       finalMoney.setText(loanDetailBean.getAmountPayable()+"元");

        List<LoanDetailBean.PaymentMethodBean> paymentMethodBeanList = loanDetailBean.getPaymentMethod();

        LoanDetailsAdapter loanDetailsAdapter=new LoanDetailsAdapter(paymentMethodBeanList);

        loanDetailsAdapter.setOnItemClickListener(new LoanDetailsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(LoanDetailBean.PaymentMethodBean paymentMethodBean) {
                if (paymentMethodBean.getType() == 1) {//如果是推荐就代表是支付宝
                    if (checkAliPayInstalled(LoanDetailsActivity.this)) {
                        ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL,paymentMethodBean.getUrl()).navigation();
                    } else {
                        ToastUtils.showShort("请先安装支付宝");
                    }
                } else {
                    ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL,paymentMethodBean.getUrl()).navigation();
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(loanDetailsAdapter);

    }


    public static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }


}
