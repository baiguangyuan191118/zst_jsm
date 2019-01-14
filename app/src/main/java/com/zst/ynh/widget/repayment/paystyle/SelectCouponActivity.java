package com.zst.ynh.widget.repayment.paystyle;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.adapter.SelectCouponAdapter;
import com.zst.ynh.base.UMBaseActivity;
import com.zst.ynh.bean.PaymentStyleBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.TitleBar;

import java.util.List;

import butterknife.BindView;

@Route(path = ArouterUtil.SELECT_COUPON)
@Layout(R.layout.activity_select_coupon)
public class SelectCouponActivity extends UMBaseActivity {

    @BindView(R.id.tv_unuse_coupon)
    TextView tv_unuse_coupon;
    @BindView(R.id.ll_no_coupon)
    ConstraintLayout ll_no_coupon;
    @BindView(R.id.listView_coupon)
    ListView listView_coupon;
    private SelectCouponAdapter selectCouponAdapter;
    private List<PaymentStyleBean.Coupon> couponList;

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        initTitle();
        setData();
        tv_unuse_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });
    }

    private void setData() {
        PaymentStyleBean paymentStyleBean = (PaymentStyleBean) getIntent().getSerializableExtra(BundleKey.COUPON_LIST);
        couponList=paymentStyleBean.couponList;
        if(paymentStyleBean.couponCount==0){
            ll_no_coupon.setVisibility(View.VISIBLE);
            listView_coupon.setVisibility(View.GONE);
        }else{
            ll_no_coupon.setVisibility(View.GONE);
            listView_coupon.setVisibility(View.VISIBLE);
            selectCouponAdapter=new SelectCouponAdapter(this,R.layout.select_coupon_item,couponList);
            listView_coupon.setAdapter(selectCouponAdapter);
            listView_coupon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PaymentStyleBean.Coupon coupon=couponList.get(position);
                    if(coupon.status.equals("1")){
                        Intent i=new Intent();
                        i.putExtra(BundleKey.SELECT_COUPON,coupon);
                        setResult(0,i);
                        finish();
                    }else{
                        ToastUtils.showShort("您不满足该券的使用条件");
                    }

                }
            });
        }
    }

    private void initTitle() {
        mTitleBar.setTitle("优惠券");
        mTitleBar.setLeftImageResource(R.drawable.system_back);
        mTitleBar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCouponActivity.this.finish();
            }
        });
        mTitleBar.setActionTextColor(R.color.theme_color);
        TitleBar.TextAction right = new TitleBar.TextAction("使用说明") {
            @Override
            public void performAction(View view) {
                ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL, ApiUrl.COUPON_RULE).navigation();
            }
        };
        mTitleBar.addAction(right);
    }
}
