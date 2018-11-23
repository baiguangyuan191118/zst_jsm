package com.zst.ynh.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.zst.ynh.R;
import com.zst.ynh.bean.RepayInfoBean;
import com.zst.ynh.utils.CalendarManager;
import com.zst.ynh_base.adapter.recycleview.CommonAdapter;
import com.zst.ynh_base.adapter.recycleview.base.ViewHolder;

import java.util.List;

public class RepaymentAdapter extends CommonAdapter<RepayInfoBean.DataBean.ItemBean.ListBean> {
    private List<RepayInfoBean.DataBean.ItemBean.ListBean> datas;
    public RepaymentAdapter(Context context, int layoutId, List<RepayInfoBean.DataBean.ItemBean.ListBean> datas) {
        super(context, layoutId, datas);
        this.datas=datas;
    }

    @Override
    protected void convert(ViewHolder holder, RepayInfoBean.DataBean.ItemBean.ListBean listBean, int position) {
        if (position == datas.size() - 1) {
            holder.getView(R.id.line).setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_repay_money,listBean.repay_money+"元");
        holder.setText(R.id.tv_repay_desc,listBean.repay_desc);
        holder.setText(R.id.tv_status,Html.fromHtml(listBean.status_zh));
        if (listBean.status == 1) {
            if (CalendarManager.INSTANCE.checkAndAddCalendarAccount(mContext) != -1) {
                CalendarManager.INSTANCE.addCalEvent(mContext, listBean.repay_time, "91来钱快还款提醒" + "第" + listBean.current_period + "期");
            }
        }
    }
}
