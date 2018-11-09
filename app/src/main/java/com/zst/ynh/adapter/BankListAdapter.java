package com.zst.ynh.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.zst.ynh.R;
import com.zst.ynh.bean.BankBean;
import com.zst.ynh.core.bitmap.ImageLoaderUtils;
import com.zst.ynh_base.adapter.listview.CommonAdapter;
import com.zst.ynh_base.adapter.listview.ViewHolder;

import java.util.List;

public class BankListAdapter extends CommonAdapter<BankBean.BankItem> {
    private Context context;
    public BankListAdapter(Context context, int layoutId, List<BankBean.BankItem> datas) {
        super(context, layoutId, datas);
        this.context=context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, BankBean.BankItem item, int position) {
        ImageLoaderUtils.loadUrl(context,item.url,(ImageView) viewHolder.getView(R.id.iv_bank_icon));
        viewHolder.setText(R.id.tv_bank_name,item.bank_name);
    }
}
