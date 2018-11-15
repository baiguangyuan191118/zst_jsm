package com.zst.ynh.widget.person.loanrecord;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zst.ynh.R;
import com.zst.ynh.adapter.LoanRecordAdapter;
import com.zst.ynh.bean.LoanDetailBean;
import com.zst.ynh.bean.LoanRecordBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.widget.web.BaseWebActivity;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
* 借款记录
* */
@Layout(R.layout.activity_load_record)
@Route(path = ArouterUtil.LOAN_RECORD)
public class LoanRecordActivity extends BaseActivity implements ILoanRecordView {


    @BindView(R.id.loarecord_refresh)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.loadrecord_recyclerview)
    RecyclerView recyclerView;

    LoanRecordPresent loanRecordPresent;

    private int pageNo=1;//第几页
    private int pageSize=10;//每页数目
    private List<LoanRecordBean.LoanRecordListBean> loanRecordListBeans;
    private LoanRecordBean loanRecordBean;
    private LoanRecordAdapter mAdapter;

    @Override
    public void onRetry() {
        if(loanRecordPresent!=null){
            pageNo=1;
            loanRecordPresent.getLoadRecordInfo(pageNo,pageSize);
        }
    }

    @Override
    public void initView() {

        loadContentView();
        initTitlebar();
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if(loanRecordPresent!=null){
                    pageNo=1;
                    loanRecordPresent.getLoadRecordInfo(pageNo,pageSize);
                }

            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(loanRecordPresent!=null){
                    pageNo=pageNo+1;
                    loanRecordPresent.getLoadRecordInfo(pageNo,pageSize);
                }
            }
        });
        loanRecordPresent=new LoanRecordPresent();
        loanRecordPresent.attach(this);
        loanRecordPresent.getLoadRecordInfo(pageNo,pageSize);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        loanRecordListBeans=new ArrayList<>();
        mAdapter=new LoanRecordAdapter(loanRecordListBeans);
        mAdapter.setOnItemClickListener(new LoanRecordAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(LoanRecordBean.LoanRecordListBean loanRecordListBean) {
                if (loanRecordListBean.isIs_repay()) {//借款页面

                    String rep_id=loanRecordListBean.getRep_id();

                    loanRecordPresent.getLoanDetail(rep_id);

                } else {
                    Intent intent = new Intent(LoanRecordActivity.this, BaseWebActivity.class);
                    intent.putExtra("title", "");
                    intent.putExtra("url", loanRecordListBean.getUrl());
                    LoanRecordActivity.this.startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(mAdapter);

    }

    private void initTitlebar() {
        mTitleBar.setTitle("借款记录");
        mTitleBar.setDividerColor(R.color.dividing_color);
        mTitleBar.setActionTextColor(R.color.theme_color);
        mTitleBar.addAction(new TitleBar.TextAction("帮助中心") {
            @Override
            public void performAction(View view) {
                ARouter.getInstance().build(ArouterUtil.HELPER_CENTER).withString(BundleKey.URL,loanRecordBean.getLink_url()).navigation();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loanRecordPresent!=null){
            loanRecordPresent.detach();
        }
    }

    @Override
    public void showLoading() {
        smartRefreshLayout.autoRefresh();
    }

    @Override
    public void hideLoading() {
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void ToastErrorMessage(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void showLoanRecord(final LoanRecordBean loanRecordBean) {

        if(loanRecordBean!=null && loanRecordBean.getItem()!=null){

            this.loanRecordBean=loanRecordBean;

            if(loanRecordBean.getItem().size()==0 && pageNo==1){
                loadNoDataView();
                return;
            }

            //添加数据
            if (pageNo == 1) {
                loanRecordListBeans.clear();
                loanRecordListBeans.addAll(loanRecordBean.getItem());
            } else {
                loanRecordListBeans.addAll(loanRecordBean.getItem());
            }

            if (loanRecordBean.getItem().size() < pageSize) {//数据小于10，说明数据全部加载完成
                smartRefreshLayout.setEnableLoadMore(false);
            } else {//数据等于10，说明还可能有数据
                smartRefreshLayout.setEnableLoadMore(true);
            }

            if (mAdapter.getItemCount() < pageSize) {
                if (pageNo > 1) {
                    pageNo = pageNo - 1;
                }
            }
            if (mAdapter.getItemCount() < 1) {//加载错误
                if (pageNo > 1) {
                    pageNo = pageNo - 1;
                }
                smartRefreshLayout.finishLoadMore(false);
            }

            mAdapter.notifyDataSetChanged();
        }else{//加载错误
            if (pageNo > 1) {
                pageNo = pageNo - 1;
            }
            smartRefreshLayout.finishLoadMore(false);
        }
    }

    @Override
    public void getLoanRecordError(int code, String errorMSG) {
        smartRefreshLayout.finishRefresh();
        if (pageNo > 1) {
            pageNo = pageNo - 1;
            loanRecordListBeans.clear();
            mAdapter.notifyDataSetChanged();
            smartRefreshLayout.finishLoadMore(false);
        }else{
            pageNo=1;
            loadErrorView();
        }

    }

    @Override
    public void getLoanDetailsError(int code, String errorMSG) {
        ToastUtils.showShort(errorMSG);
    }

    @Override
    public void getloanDetailsSuccess(LoanDetailBean response) {
        ARouter.getInstance().build(ArouterUtil.LOAN_DETAILS).withSerializable(BundleKey.LOAN_DETAIL,response).navigation();
    }

}
