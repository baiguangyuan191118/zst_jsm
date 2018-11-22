package com.zst.ynh.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zst.ynh.R;
import com.zst.ynh.bean.PaymentStyleBean;
import com.zst.ynh.core.bitmap.ImageLoaderUtils;
import com.zst.ynh_base.adapter.listview.CommonAdapter;
import com.zst.ynh_base.adapter.listview.ViewHolder;

import java.util.List;

public class PaymentStyleAdapter extends CommonAdapter<PaymentStyleBean.DataBean.PaymentMethodBean> {
    private Context context;
    public PaymentStyleAdapter(Context context, int layoutId, List<PaymentStyleBean.DataBean.PaymentMethodBean> datas) {
        super(context, layoutId, datas);
        this.context=context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, PaymentStyleBean.DataBean.PaymentMethodBean item, int position) {
        ImageLoaderUtils.loadUrl(context,item.iconUrl,(ImageView) viewHolder.getView(R.id.iv_payment_style_icon));
        viewHolder.setText(R.id.tv_payment_style,item.name);
        if (item.isRecommend){
            viewHolder.getView(R.id.tv_payment_style_sign).setVisibility(View.VISIBLE);
        }else {
            viewHolder.getView(R.id.tv_payment_style_sign).setVisibility(View.GONE);
        }
    }
}
