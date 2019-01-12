package com.zst.ynh.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SpanUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.PopularLoanBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh_base.adapter.recycleview.CommonAdapter;
import com.zst.ynh_base.adapter.recycleview.base.ViewHolder;
import com.zst.ynh_base.util.ImageLoaderUtils;

import java.util.List;

public class PopularLoanAdapter extends CommonAdapter<PopularLoanBean.DataBean> {

    private Context context;

    public PopularLoanAdapter(Context context, int layoutId, List<PopularLoanBean.DataBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    public void notifyData(List<PopularLoanBean.DataBean> datas){
        mDatas=datas;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, final PopularLoanBean.DataBean popularLoanBean, final int position) {

        ImageLoaderUtils.loadUrl(context, popularLoanBean.logo, (ImageView) holder.getView(R.id.img_logo));
        int count;
        if(popularLoanBean.count!=null){
          count =Integer.parseInt(popularLoanBean.count);
        }else{
            count=0;
        }
        holder.setText(R.id.tv_count,count+"人已借");
        SpanUtils spanUtils = new SpanUtils();
        spanUtils.append(popularLoanBean.app_name).setBold();
        spanUtils.append("  ");
        spanUtils.append(popularLoanBean.quato+"元").setForegroundColor(context.getResources().getColor(R.color.them_color));
        holder.setText(R.id.tv_name, spanUtils.create());
        holder.setText(R.id.tv_description,popularLoanBean.introduction);
        holder.getView(R.id.btn_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(applyBtnListener!=null){
                    applyBtnListener.applyClick(popularLoanBean);
                }

            }
        });
    }

    public ApplyClickListener applyBtnListener;
    public interface ApplyClickListener{
        void applyClick(PopularLoanBean.DataBean popularLoanBean);
    }
    public void setApplyClickListener(ApplyClickListener applyBtnListener){
        this.applyBtnListener=applyBtnListener;
    }
}
