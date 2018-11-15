package com.zst.ynh.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public class BankListActivityAdapter extends RecyclerView.Adapter<BankListActivityAdapter.BankListActivityViewHolder> {


    @Override
    public BankListActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BankListActivityViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class BankListActivityViewHolder extends RecyclerView.ViewHolder{

        public BankListActivityViewHolder(View itemView) {
            super(itemView);
        }
    }

}
