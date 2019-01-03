package com.zst.ynh.adapter;

import android.content.Context;

import com.zst.ynh.R;
import com.zst.ynh.bean.PaymentStyleBean;
import com.zst.ynh_base.adapter.listview.CommonAdapter;
import com.zst.ynh_base.adapter.listview.ViewHolder;

import java.util.List;

public class SelectCouponAdapter extends CommonAdapter<PaymentStyleBean.DataBean.Coupon> {
    private Context context;
    public SelectCouponAdapter(Context context, int layoutId, List<PaymentStyleBean.DataBean.Coupon> datas) {
        super(context, layoutId, datas);
        this.context=context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, PaymentStyleBean.DataBean.Coupon item, int position) {
        viewHolder.setText(R.id.tv_coupon_money,item.coupon_amount+"");
        viewHolder.setText(R.id.tv_load_money,item.limit_text);
        viewHolder.setText(R.id.tv_exper_time,item.expire_start+"~"+item.expire_end);
        if(item.status.equals("1")){
            viewHolder.getView(R.id.ll_coupon_status).setBackgroundResource(R.mipmap.coupon_left_valid);
        }else{
            viewHolder.getView(R.id.ll_coupon_status).setBackgroundResource(R.mipmap.coupon_left_invalid);
        }
    }
}
