package com.zst.ynh.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.zst.ynh.R;
import com.zst.ynh.bean.MyBankBean;
import com.zst.ynh_base.adapter.recycleview.CommonAdapter;
import com.zst.ynh_base.adapter.recycleview.base.ViewHolder;
import com.zst.ynh_base.util.ImageLoaderUtils;

import java.util.List;

public class MyBankListAdapter extends CommonAdapter<MyBankBean.Bank> {
    private Context context;

    public MyBankListAdapter(Context context, int layoutId, List<MyBankBean.Bank> datas) {
        super(context, layoutId, datas);
        this.context=context;
    }

    @Override
    protected void convert(ViewHolder holder, MyBankBean.Bank bank, int position) {
        ImageLoaderUtils.loadUrl(context,bank.bank_logo,(ImageView) holder.getView(R.id.iv_card_logo));
        holder.setText(R.id.tv_bank_name,bank.bank_name);
        holder.setText(R.id.card_num,bank.card_no);
        if (1 == bank.tag) {
           holder.getView(R.id.iv_exception).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.iv_exception).setVisibility(View.GONE);
        }
        if (1 == bank.is_main_card) {
            holder.getView(R.id.tv_main_card_flag).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_main_card_flag,"当前主卡");
        } else {
            holder.getView(R.id.tv_main_card_flag).setVisibility(View.GONE);
        }
    }
}
