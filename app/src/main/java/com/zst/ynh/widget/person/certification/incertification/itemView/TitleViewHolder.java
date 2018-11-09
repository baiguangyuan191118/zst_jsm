package com.zst.ynh.widget.person.certification.incertification.itemView;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zst.ynh.R;
import com.zst.ynh.bean.InCertificationBean;
import com.zst.ynh_base.adapter.recycleview.base.ItemViewDelegate;
import com.zst.ynh_base.adapter.recycleview.base.ViewHolder;

public class TitleViewHolder implements ItemViewDelegate<InCertificationBean.ItemBean.ListBean> {
    private TextView tvTitle, tvSubtitle;

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_title_recycle_layout;
    }

    @Override
    public boolean isForViewType(InCertificationBean.ItemBean.ListBean item, int position) {
        if (item.type == 0 || item.type == 2)
            return true;
        return false;
    }

    @Override
    public void convert(ViewHolder holder, InCertificationBean.ItemBean.ListBean listBean, int position) {
        tvTitle = holder.getView(R.id.title);
        tvSubtitle = holder.getView(R.id.content);
        setTitleData(listBean);
    }
    /**
     * 设置标题
     * @param
     */
    public void setTitleData(InCertificationBean.ItemBean.ListBean listBean){
        if (!TextUtils.isEmpty(listBean.title)) {
            tvTitle.setText(Html.fromHtml(listBean.title));
        }
    }
}
