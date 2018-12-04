package com.zst.ynh.widget.person.certification.banklist;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zst.ynh.R;
import com.zst.ynh.adapter.MyBankListAdapter;
import com.zst.ynh.bean.MyBankBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh_base.adapter.recycleview.HeaderAndFooterWrapper;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;

import butterknife.BindView;

@Route(path = ArouterUtil.BANK_LIST)
@Layout(R.layout.activity_my_bank_list_layout)
public class BankListActivity extends BaseActivity implements IBankListView, OnRefreshListener {
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_view)
    SmartRefreshLayout refreshView;
    private BankListPresent bankListPresent;
    private MyBankListAdapter myBankListAdapter;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private View footerView;
    private TextView tvTips;
    private Button btnAdd;

    @Override
    public void getBankListData(MyBankBean myBankBean) {
        //设置adapter数据
        if (myBankListAdapter == null)
            myBankListAdapter = new MyBankListAdapter(this, R.layout.item_my_bank_list_item, myBankBean.card_list);
        headerAndFooterWrapper = new HeaderAndFooterWrapper(myBankListAdapter);
        headerAndFooterWrapper.addFootView(footerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(headerAndFooterWrapper);
        headerAndFooterWrapper.notifyDataSetChanged();

        //设置上方的消息提醒
        if (myBankBean.is_show_notice == 1 && !TextUtils.isEmpty(myBankBean.notice_msg)) {
            tvNotice.setVisibility(View.VISIBLE);
            tvNotice.setText(myBankBean.notice_msg);
        } else {
            tvNotice.setVisibility(View.GONE);
        }
        //设置下方的footer数据
        if(!TextUtils.isEmpty(myBankBean.tips)){
            tvTips.setText(myBankBean.tips);
        }

    }

    @Override
    public void loadContent() {
        if (refreshView.getState()==RefreshState.Refreshing){

        }else{
            loadContentView();
        }
    }

    @Override
    public void loadError() {
        loadErrorView();
    }

    @Override
    public void loadLoading() {
        if (refreshView.getState()==RefreshState.Refreshing){

        }else{
            loadLoadingView();
        }

    }

    @Override
    public void getIsAddCard() {
        ARouter.getInstance().build(ArouterUtil.BIND_BANK_CARD).withBoolean(BundleKey.ISCHANGE,true).navigation();
    }

    @Override
    public void onRetry() {
        bankListPresent.getBankList();
    }

    @Override
    public void initView() {
        mTitleBar.setTitle("我的银行卡");
        bankListPresent = new BankListPresent();
        bankListPresent.attach(this);
        bankListPresent.getBankList();
        refreshView.setEnableLoadMore(false);
        refreshView.setOnRefreshListener(this);
        footerView=LayoutInflater.from(this).inflate(R.layout.activity_my_bank_list_footer_layout,null);
        tvTips=footerView.findViewById(R.id.tv_tips);
        btnAdd=footerView.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankListPresent.isAddCard();

            }
        });
    }

    @Override
    public void showLoading() {
        showLoadingView();
    }

    @Override
    public void hideLoading() {
        hideLoadingView();
    }

    @Override
    public void ToastErrorMessage(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bankListPresent.detach();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        bankListPresent.getBankList();
    }
}
