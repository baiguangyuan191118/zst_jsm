package com.zst.ynh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.zst.ynh.R;
import com.zst.ynh.bean.MineBean;
import com.zst.ynh.core.bitmap.ImageLoaderUtils;
import com.zst.ynh.utils.StringUtil;


import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class PersonFragmentItemAdapter extends RecyclerView.Adapter<PersonFragmentItemAdapter.PersonFragmentItemViewHolder> {

    private List<MineBean.MoreItem> data;
    private Context context;

    public PersonFragmentItemAdapter( List<MineBean.MoreItem> data) {
        this.data = data;
    }

    @Override
    public PersonFragmentItemAdapter.PersonFragmentItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.person_fragment_item, parent, false);
        return new PersonFragmentItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonFragmentItemAdapter.PersonFragmentItemViewHolder holder, int position) {

        final MineBean.MoreItem moreItem = data.get(position);
        if (!StringUtil.isBlank(moreItem.getLogo())) {
            ImageLoaderUtils.loadUrl(context, moreItem.getLogo(), holder.tip);
        } else {
            ImageLoaderUtils.loadRes(context, moreItem.getRes(), holder.tip);
        }

        holder.title.setText(moreItem.getTitle());

        if (!StringUtil.isBlank(moreItem.getSubtitle())) {
            holder.tag.setVisibility(View.VISIBLE);
            holder.tag.setText(moreItem.getSubtitle());
        }

        if (moreItem.getIs_overdue()==1){
            holder.dot.setVisibility(View.VISIBLE);
        }else{
            holder.dot.setVisibility(View.GONE);
        }

        if (position == data.size() - 1) {
            holder.divider.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.OnItemClick(moreItem);
                }
            }
        });

    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(MineBean.MoreItem moreItem);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class PersonFragmentItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_tip_more_item)
        ImageView tip;
        @BindView(R.id.tv_title_more_item)
        TextView title;
        @BindView(R.id.tv_tag_more_item)
        TextView tag;
        @BindView(R.id.iv_red_dot_more_item)
        ImageView dot;
        @BindView(R.id.iv_enter_more_item)
        ImageView enter;

        @BindView(R.id.divider_more_item)
        View divider;

        private PersonFragmentItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
