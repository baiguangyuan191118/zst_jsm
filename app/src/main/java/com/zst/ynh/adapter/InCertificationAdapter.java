package com.zst.ynh.adapter;


import android.content.Context;
import com.zst.ynh.bean.InCertificationBean;
import com.zst.ynh.widget.person.certification.incertification.itemView.HeadViewHolder;
import com.zst.ynh.widget.person.certification.incertification.itemView.TitleViewHolder;
import com.zst.ynh.widget.person.certification.incertification.itemView.itemViewHolder;
import com.zst.ynh_base.adapter.recycleview.MultiItemTypeAdapter;

import java.util.List;

public class InCertificationAdapter extends MultiItemTypeAdapter<InCertificationBean.ItemBean.ListBean> {

    public InCertificationAdapter(Context context, List<InCertificationBean.ItemBean.ListBean> datas,InCertificationBean inCertificationBean) {
        super(context, datas);
        addItemViewDelegate(new HeadViewHolder(inCertificationBean));
        addItemViewDelegate(new itemViewHolder());
        addItemViewDelegate(new TitleViewHolder());
    }
}
