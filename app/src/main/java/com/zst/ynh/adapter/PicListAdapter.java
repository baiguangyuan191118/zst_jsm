package com.zst.ynh.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.UploadPicBean;
import com.zst.ynh_base.adapter.recycleview.CommonAdapter;
import com.zst.ynh_base.adapter.recycleview.base.ViewHolder;
import com.zst.ynh_base.util.ImageLoaderUtils;

import java.util.List;

import static com.zst.ynh.widget.person.certification.work.WorkCard.WorkCardUpdateActivity.Type_Add;
import static com.zst.ynh.widget.person.certification.work.WorkCard.WorkCardUpdateActivity.Type_None;
import static com.zst.ynh.widget.person.certification.work.WorkCard.WorkCardUpdateActivity.Type_TakePhoto;
import static com.zst.ynh.widget.person.certification.work.WorkCard.WorkCardUpdateActivity.Type_UploadFailed;
import static com.zst.ynh.widget.person.certification.work.WorkCard.WorkCardUpdateActivity.Type_Uploaded;
import static com.zst.ynh.widget.person.certification.work.WorkCard.WorkCardUpdateActivity.Type_Uploading;

public class PicListAdapter extends CommonAdapter<UploadPicBean> {


    private Context context;
    private int progress;


    public PicListAdapter(Context context, int layoutId, List<UploadPicBean> datas, int progress) {
        super(context, layoutId, datas);
        this.context = context;
        this.progress = progress;
    }

    @Override
    public void onViewHolderCreated(ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
    }

    int width = (ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(40)) / 3;

    @Override
    protected void convert(final ViewHolder holder, final UploadPicBean dataBean, final int position) {


        FrameLayout frameLayout = holder.getView(R.id.fl_content);
        LinearLayout progresslayout=holder.getView(R.id.layout_progress);
        ProgressBar progressBar = holder.getView(R.id.progressbar);
        TextView tv_progress=holder.getView(R.id.tv_progress);
        final ImageView delete = holder.getView(R.id.iv_delete);
        ImageView choose = holder.getView(R.id.iv_chose);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, width);
        frameLayout.setLayoutParams(layoutParams);
        holder.itemView.setTag(position);
        delete.setTag(position);
        switch (dataBean.type) {
            case Type_None:
                progresslayout.setVisibility(View.GONE);
                delete.setVisibility(View.VISIBLE);
                break;
            case Type_Add:
                ImageLoaderUtils.loadRes(context, R.mipmap.add_photo, choose);
                progresslayout.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                // choose.setLayoutParams(new FrameLayout.LayoutParams(100,100));
                int px = ConvertUtils.dp2px(35);
                choose.setPadding(px, px, px, px);
                break;
            case Type_TakePhoto:
                ImageLoaderUtils.loadRes(context, R.mipmap.camera, choose);
                progresslayout.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                //   choose.setLayoutParams(new FrameLayout.LayoutParams(100,100));
                int px1 = ConvertUtils.dp2px(35);
                choose.setPadding(px1, px1, px1, px1);
                break;
            case Type_Uploaded:
                progresslayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tv_progress.setTextSize(16);
                tv_progress.setText("已完成");
                delete.setVisibility(View.GONE);
                break;
            case Type_UploadFailed:
                progresslayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tv_progress.setTextSize(16);
                tv_progress.setText("上传失败");
                delete.setVisibility(View.VISIBLE);
                break;
            case Type_Uploading:
                progresslayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(dataBean.progress);
                tv_progress.setTextSize(12);
                tv_progress.setText("当前进度"+dataBean.progress+"%");
                delete.setVisibility(View.GONE);
                break;
        }

        if (!StringUtils.isEmpty(dataBean.url)) {
            choose.setPadding(1, 1, 1, 1);
            ImageLoaderUtils.loadUrl(context, dataBean.url, choose);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position= (int) v.getTag();
                    listener.onItemClick( mDatas.get(position));
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position= (int) v.getTag();
                    listener.deleteItemListener(mDatas.get(position));
                }
            }
        });

    }


    private PicClickListener listener;

    public void setPicClickListener(PicClickListener listener) {
        this.listener = listener;
    }

    public interface PicClickListener {
        void onItemClick(UploadPicBean dataBean);

        void deleteItemListener(UploadPicBean dataBean);
    }

}
