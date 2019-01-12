package com.zst.ynh.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.SpanUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.HistoryOrderInfoBean;
import com.zst.ynh_base.adapter.recycleview.CommonAdapter;
import com.zst.ynh_base.adapter.recycleview.base.ViewHolder;
import com.zst.ynh_base.util.ImageLoaderUtils;

import java.util.List;

public class RepayOrderAdapter extends CommonAdapter<HistoryOrderInfoBean.OrderItem> {

    public RepayOrderAdapter(Context context, int layoutId, List<HistoryOrderInfoBean.OrderItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final HistoryOrderInfoBean.OrderItem item, int position) {

        ImageLoaderUtils.loadUrl(mContext, item.logo, (ImageView) holder.getView(R.id.img_app_logo));
        holder.setText(R.id.tv_app_name, item.platform);
        if(item.is_repay){
            holder.setText(R.id.tv_status, item.status_text);
            holder.setText(R.id.tv_expert,"还款日" +item.repayment_date);
            holder.getView(R.id.ll_repay).setVisibility(View.VISIBLE);
            holder.getView(R.id.ll_repay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.OnRepayBtnClick(v,item);
                    }
                }
            });
        }else{
            holder.setText(R.id.tv_status,"");
            holder.setText(R.id.tv_expert, item.status_text);
            holder.getView(R.id.ll_repay).setVisibility(View.GONE);
        }

        SpanUtils spanUtils=new SpanUtils();
        spanUtils.append(item.period).setFontSize(25,true)
                .append(" ")
                .append("天").setFontSize(13,true);

        holder.setText(R.id.tv_loan_date, spanUtils.create());
        SpanUtils spanUtils1=new SpanUtils();
        spanUtils1.append(item.money).setFontSize(25,true)
                .append(" ")
                .append("元").setFontSize(13,true);
        holder.setText(R.id.tv_loan_amount,spanUtils1.create());

    }

    private RepayBtnClickListener listener;
    public void setRepayBtnClickListener(RepayBtnClickListener repayBtnClickListener){
        this.listener=repayBtnClickListener;
    }

    public interface RepayBtnClickListener{
        void OnRepayBtnClick(View v,HistoryOrderInfoBean.OrderItem item);
    }
}
