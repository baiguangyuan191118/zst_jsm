package com.zst.ynh.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.zst.ynh.R;
import com.zst.ynh.bean.PicItemBean;
import com.zst.ynh.core.bitmap.ImageLoaderUtils;
import com.zst.ynh_base.adapter.recycleview.CommonAdapter;
import com.zst.ynh_base.adapter.recycleview.base.ViewHolder;

import java.util.List;

public class PicListAdapter extends CommonAdapter<PicItemBean.DataBeanX.ItemBean.DataBean> {
    private Context context;
    private int progress;

    public PicListAdapter(Context context, int layoutId, List<PicItemBean.DataBeanX.ItemBean.DataBean> datas,int progress) {
        super(context, layoutId, datas);
        this.context = context;
        this.progress=progress;
    }

    @Override
    protected void convert(ViewHolder holder, PicItemBean.DataBeanX.ItemBean.DataBean dataBean, final int position) {
        if (getDatas().size() == 1) {
            ImageLoaderUtils.loadRes(context, R.mipmap.camera, (ImageView) holder.getView(R.id.iv_chose));
        } else {
            if (getDatas().size() - 1 == position) {
                if (getDatas().size() < 3)
                    ImageLoaderUtils.loadRes(context, R.mipmap.add_photo, (ImageView) holder.getView(R.id.iv_chose));
            } else {
                ImageLoaderUtils.loadUrl(context, dataBean.url, (ImageView) holder.getView(R.id.iv_chose));
                if (dataBean.type == PicItemBean.Type_Uploaded || dataBean.type == PicItemBean.Type_UploadFailed || dataBean.type == PicItemBean.Type_Uploading)
                    holder.getView(R.id.progress_upload_pic).setVisibility(View.VISIBLE);
                else
                    holder.getView(R.id.progress_upload_pic).setVisibility(View.GONE);
                if (dataBean.type == PicItemBean.Type_UploadFailed)
                    holder.getView(R.id.iv_delete).setVisibility(View.VISIBLE);
                else
                    holder.getView(R.id.iv_delete).setVisibility(View.GONE);
            }
            holder.getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDatas().remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
