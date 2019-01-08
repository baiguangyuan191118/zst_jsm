package com.zst.ynh.widget.repayment.repaymentfragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zst.ynh.R;
import com.zst.ynh.adapter.RepayOrderAdapter;
import com.zst.ynh.bean.OtherPlatformRepayInfoBean;
import com.zst.ynh.bean.PaymentStyleBean;
import com.zst.ynh.bean.HistoryOrderInfoBean;
import com.zst.ynh.bean.RepayItemBean;
import com.zst.ynh.bean.YnhRepayInfoBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh_base.mvp.view.BaseFragment;
import com.zst.ynh_base.util.Layout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Layout(R.layout.fragment_repaymentlist)
public class RepaymentListFragment extends BaseFragment implements IRepaymentView {

    private static final String tag=RepaymentListFragment.class.getSimpleName();
    @BindView(R.id.multiply_freshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recycleView_repayment)
    RecyclerView repaymentRecyclerView;
    private RepayOrderAdapter repaymentInfoAdapter;
    private List<HistoryOrderInfoBean.OrderItem> repaymentInfoBeanList = new ArrayList<>();
    private RepaymentPresent repaymentPresent;


    private int LIST_TYPE = ListType.YNH_REPAYMENT;


    public void setLIST_TYPE(int LIST_TYPE) {
        this.LIST_TYPE = LIST_TYPE;
    }

    public int getLIST_TYPE() {
        return LIST_TYPE;
    }

    public void autoFresh() {
        if (smartRefreshLayout != null) {
            onLazyLoad();
        }
    }

    public void loadData(){
        switch (LIST_TYPE) {
            case ListType.YNH_REPAYMENT:
                repaymentPresent.getYnhRepaymentInfo();
                break;
            case ListType.YNH_ORDERS:
                repaymentPresent.getYnhOrders();
                break;
            case ListType.OTHER_REPAYMENT:
                repaymentPresent.getOtherRepaymentInfo();
                break;
            case ListType.OTHER_ORDERS:
                repaymentPresent.getOtherOrders();
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(tag,"setUserVisibleHint:"+isVisibleToUser+";type:"+getLIST_TYPE());
    }

    private void setAdapter(){
        if(repaymentInfoAdapter==null){
            repaymentInfoAdapter = new RepayOrderAdapter(this.getActivity(), R.layout.item_repayment_info, repaymentInfoBeanList);
            repaymentRecyclerView.setAdapter(repaymentInfoAdapter);
            repaymentInfoAdapter.setRepayBtnClickListener(new RepayOrderAdapter.RepayBtnClickListener() {
                @Override
                public void OnRepayBtnClick(View v, HistoryOrderInfoBean.OrderItem item) {
                    repaymentPresent.getRepayDetail(item.rep_id, item.platform_code);
                }
            });
        }else{
            repaymentInfoAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onLazyLoad() {
        Log.d(tag,"onLazyLoad"+LIST_TYPE);
        smartRefreshLayout.autoRefresh();
    }

    @Override
    protected void onRetry() {
        loadLoadingView();
        loadData();
    }


    @Override
    protected void initView() {
        Log.d(tag,"initView"+LIST_TYPE);
        loadContentView();
        repaymentPresent = new RepaymentPresent();
        repaymentPresent.attach(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        repaymentRecyclerView.setLayoutManager(layoutManager);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
        if (!isLazyload) {
            onLazyLoad();
        }
    }

    @Override
    public void loadRefresh() {

    }

    @Override
    public void hideRefresh() {
        smartRefreshLayout.finishRefresh();
    }


    @Override
    public void getYnhRepaymentSuccess(YnhRepayInfoBean response) {
        if (response.item.list.size() == 0) {
            loadNoDataView(0, "您暂时还无还款订单哦~");
        }
        repaymentInfoBeanList.clear();
        for (RepayItemBean listBean : response.item.list) {
            HistoryOrderInfoBean.OrderItem item = new HistoryOrderInfoBean.OrderItem();
            item.is_repay = true;
            item.logo = listBean.logo;
            item.money = listBean.repay_money;
            item.period = listBean.period;
            item.platform = listBean.platform;
            item.platform_code = listBean.platform_code;
            item.rep_id = listBean.repay_id;
            item.status_text = listBean.status_text;
            item.repayment_date = listBean.repayment_date;
            item.text = listBean.status_zh;
            item.time = listBean.repay_time;
            item.url = response.item.detail_url;
            item.title = "";
            repaymentInfoBeanList.add(item);
        }

        setAdapter();
    }

    @Override
    public void getYnhRepaymentError(int code, String errorMSG) {
        loadErrorView();
    }

    @Override
    public void getYnhOrdersSuccess(HistoryOrderInfoBean historyOrderInfoBean) {
        if (historyOrderInfoBean.item.size() == 0) {
            loadNoDataView(0, "您暂时还无还款订单哦~");
        }
        repaymentInfoBeanList.clear();
        repaymentInfoBeanList.addAll(historyOrderInfoBean.item);
        setAdapter();
    }

    @Override
    public void getYnhOrdersError(int code, String errorMsg) {
        loadErrorView();
    }

    @Override
    public void getOtherRepaymentSuccess(OtherPlatformRepayInfoBean otherPlatformRepayInfoBean) {
        if (otherPlatformRepayInfoBean.item.list.size() == 0) {
            loadNoDataView(0, "您暂时还无还款订单哦~");
        }
        repaymentInfoBeanList.clear();
        for(RepayItemBean listBean:otherPlatformRepayInfoBean.item.list){
            HistoryOrderInfoBean.OrderItem item = new HistoryOrderInfoBean.OrderItem();
            item.is_repay = true;
            item.logo = listBean.logo;
            item.money = listBean.repay_money;
            item.period = listBean.period;
            item.platform = listBean.platform;
            item.platform_code = listBean.platform_code;
            item.rep_id = listBean.repay_id;
            item.status_text = listBean.status_text;
            item.repayment_date = listBean.repayment_date;
            item.text = listBean.status_zh;
            item.time = listBean.repay_time;
            item.url = "";
            item.title = "";
            repaymentInfoBeanList.add(item);
        }
        setAdapter();
    }

    @Override
    public void getOtherRepaymentError(int code, String errorMsg) {
        loadErrorView();
    }

    @Override
    public void getOtherOrdersSuccess(HistoryOrderInfoBean historyOrderInfoBean) {
        if (historyOrderInfoBean.item.size() == 0) {
            loadNoDataView(0, "您暂时还无还款订单哦~");
        }
        repaymentInfoBeanList.clear();
        repaymentInfoBeanList.addAll(historyOrderInfoBean.item);
        setAdapter();
    }

    @Override
    public void getOtherOrderError(int code, String errorMsg) {
        loadErrorView();
    }

    @Override
    public void getDetailsSuccess(PaymentStyleBean response) {
        ARouter.getInstance().build(ArouterUtil.PAYMENT_STYLE).withSerializable(BundleKey.PAYMENTSTYLEBEAN, response).navigation();
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
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(tag,"onDestroyView");
        if (repaymentPresent != null) {
            repaymentPresent.detach();
        }
    }
}
