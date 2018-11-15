package com.zst.ynh.widget.person.certification.banklist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zst.ynh.R;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;

import butterknife.BindView;

/*
* 我的银行卡
* */
@Route(path=ArouterUtil.BANK_LIST)
@Layout(R.layout.activity_bank_list)
public class BankListActivity extends BaseActivity {


    @BindView(R.id.bank_list_refreshlayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.bank_list_recyclerView)
    RecyclerView recyclerView;

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
}
