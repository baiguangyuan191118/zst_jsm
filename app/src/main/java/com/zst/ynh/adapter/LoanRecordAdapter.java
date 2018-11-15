package com.zst.ynh.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zst.ynh.R;
import com.zst.ynh.bean.LoanRecordBean;
import com.zst.ynh.bean.MineBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoanRecordAdapter extends RecyclerView.Adapter<LoanRecordAdapter.LoanRecordViewHolder> {

    private List<LoanRecordBean.LoanRecordListBean> data;

    public LoanRecordAdapter(List<LoanRecordBean.LoanRecordListBean> data){
        this.data=data;
    }

    @Override
    public LoanRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.loanrecord_item,parent,false);
        return new LoanRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LoanRecordViewHolder holder, int position) {
        final LoanRecordBean.LoanRecordListBean listBean= data.get(position);
        if(listBean!=null){
            holder.title.setText(listBean.getTitle());
            holder.time.setText(listBean.getTime());
            holder.status.setText(Html.fromHtml(listBean.getText()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(listBean);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class LoanRecordViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_loanrecord_title)
        TextView title;

        @BindView(R.id.tv_loanrecord_time)
        TextView time;

        @BindView(R.id.tv_loanrecord_status)
        TextView status;

        public LoanRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(LoanRecordBean.LoanRecordListBean loanRecordListBean);
    }

    private LoanRecordAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(LoanRecordAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
