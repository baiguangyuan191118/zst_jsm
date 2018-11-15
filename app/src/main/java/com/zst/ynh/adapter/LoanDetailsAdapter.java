package com.zst.ynh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zst.ynh.R;
import com.zst.ynh.bean.LoanDetailBean;
import com.zst.ynh.bean.LoanRecordBean;
import com.zst.ynh.core.bitmap.ImageLoaderUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoanDetailsAdapter extends RecyclerView.Adapter<LoanDetailsAdapter.LoadDetailsViewHolder> {


    private List<LoanDetailBean.PaymentMethodBean> data;
    private Context context;
    public LoanDetailsAdapter(List<LoanDetailBean.PaymentMethodBean> data){
        this.data=data;
    }

    @Override
    public LoadDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view=LayoutInflater.from(context).inflate(R.layout.loan_details_item,parent,false);
        return new LoadDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LoadDetailsViewHolder holder, int position) {
        final LoanDetailBean.PaymentMethodBean paymentMethodBean=data.get(position);
        ImageLoaderUtils.loadUrl(context,paymentMethodBean.getIconUrl(),holder.icon);
        holder.paymentStyle.setText(paymentMethodBean.getName());
        if (paymentMethodBean.isRecommend()){
            holder.sign.setVisibility(View.VISIBLE);
        }else {
            holder.sign.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.OnItemClick(paymentMethodBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class LoadDetailsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_payment_style_icon)
        ImageView icon;
        @BindView(R.id.tv_payment_style)
        TextView paymentStyle;
        @BindView(R.id.tv_payment_style_sign)
        TextView sign;
        @BindView(R.id.iv_payment_style_arrow)
        ImageView arrow;
        @BindView(R.id.tv_payment_style_tip)
        TextView tip;

        public LoadDetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(LoanDetailBean.PaymentMethodBean paymentMethodBean);
    }

    private LoanDetailsAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(LoanDetailsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
