package com.zst.ynh.widget.repayment.repaymentfragment;

import android.app.Dialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zst.ynh.R;
import com.zst.ynh.adapter.RepaymentAdapter;
import com.zst.ynh.bean.PaymentStyleBean;
import com.zst.ynh.bean.RepayInfoBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.DialogUtil;
import com.zst.ynh_base.adapter.recycleview.MultiItemTypeAdapter;
import com.zst.ynh_base.mvp.view.BaseLazyFragment;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.BaseDialog;

import java.util.List;

import butterknife.BindView;

@Layout(R.layout.fragment_repayment)
public class RepaymentFragment extends BaseLazyFragment implements IRepaymentView {
    @BindView(R.id.tv_repay_title)
    TextView tvRepayTitle;
    @BindView(R.id.tv_amount_money)
    TextView tvAmountMoney;
    @BindView(R.id.tv_late_account)
    TextView tvLateAccount;
    @BindView(R.id.tv_total_period)
    TextView tvTotalPeriod;
    @BindView(R.id.installment_recycler_view)
    RecyclerView installmentRecyclerView;
    @BindView(R.id.ll_repay_list_layout)
    LinearLayout llRepayListLayout;
    @BindView(R.id.iv_status_bac)
    ImageView ivStatusBac;
    @BindView(R.id.tv_status_desc)
    TextView tvStatusDesc;
    @BindView(R.id.btn_status_next)
    Button btnStatusNext;
    @BindView(R.id.ll_repay_status_layout)
    LinearLayout llRepayStatusLayout;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout RefreshLayout;
    private RepaymentAdapter adapter;
    private RepayInfoBean repayInfoBean;
    private RepaymentPresent repaymentPresent;
    private Dialog loanDialog;

    public void autoFresh() {
        if(RefreshLayout!=null){
            RefreshLayout.autoRefresh();
        }
    }

    public static RepaymentFragment newInstance() {
        RepaymentFragment fragment = new RepaymentFragment();
        return fragment;
    }

    @Override
    protected void onRetry() {
        repaymentPresent.getRepayInfo();
    }

    @Override
    protected void initView() {
        repaymentPresent = new RepaymentPresent();
        repaymentPresent.attach(this);
        installmentRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        RefreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void onLazyLoad() {
        RefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull com.scwang.smartrefresh.layout.api.RefreshLayout refreshLayout) {
                if (repaymentPresent != null) {
                    repaymentPresent.getRepayInfo();
                }
            }
        });

        getPermission();
    }


    private void getPermission() {
        if (XXPermissions.isHasPermission(getActivity(), Permission.Group.CALENDAR)) {
            if (!TextUtils.isEmpty(SPUtils.getInstance().getString(SPkey.USER_SESSIONID)))
            RefreshLayout.autoRefresh();
        } else {
            XXPermissions.with(getActivity())
                    .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    .permission(Permission.Group.CALENDAR)
                    .request(new OnPermission() {

                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (isAll) {
                                repaymentPresent.getRepayInfo();
                            } else {
                                ToastUtils.showShort("为了您能正常使用，请授权");
                            }
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if (quick) {
                                ToastUtils.showShort("被永久拒绝授权，请手动授予权限");
                                //如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.gotoPermissionSettings(getActivity());
                            } else {
                                ToastUtils.showShort("获取权限失败");
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (repaymentPresent != null) {
            repaymentPresent.detach();
        }
    }


    @Override
    public void loadRefresh() {
//        RefreshLayout.autoRefresh();
    }

    @Override
    public void hideRefresh() {
        RefreshLayout.finishRefresh();
    }

    @Override
    public void getRepayInfoSuccess(RepayInfoBean repayInfoBean) {
        this.repayInfoBean = repayInfoBean;
        int status = repayInfoBean.data.item.banner.status;
        initAdapter();
        if (showLoanRefused()){
            return;
        }
        //status: 1:申请中 2：驳回 ；3：未申请 ;4:已还款
        // button :1:认证完成  5:认证中
        if ((repayInfoBean.data.item.list == null || repayInfoBean.data.item.list.size() == 0) &&
                (status == 4 || status == 3 || status == 0)) {//没有数据 ，马上申请/再次申请
            showNoDataView(4 == status);
        } else if (status == 1) {//贷款审核中
            showCheckDataView();
        } else if (status == 2) {//驳回
            showPoorCreditView();
        } else {
            RepayInfoBean.DataBean.ItemBean.TotalDataBean totalData = repayInfoBean.data.item.total_data;
            if (totalData != null) {
                showInstallmentView(totalData);
            }
        }
    }

    /**
     * 导流被拒的情况
     */
    private boolean showLoanRefused(){
        if (repayInfoBean.data.risk_status.status == 1) {//代表审核不通过 不能够借款，这个时候要弹窗并进行导流
            llRepayStatusLayout.setVisibility(View.VISIBLE);
            llRepayListLayout.setVisibility(View.GONE);
            ivStatusBac.setImageResource(R.mipmap.borrow_failure);
            if (repayInfoBean.data.risk_status.status == 1) {//代表审核不通过 不能够借款，点击下班确定按钮并进行导流
                tvStatusDesc.setText("很遗憾，您的信用评分不足\n本次借款未能通过,请查看其他平台");
                btnStatusNext.setText("确定");
            }
            btnStatusNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL, repayInfoBean.data.risk_status.register_url).navigation();
                }
            });
            return true;
        }
        return false;
    }
    @Override
    public void getPaymentStyleData(PaymentStyleBean paymentStyleBean) {
        ARouter.getInstance().build(ArouterUtil.PAYMENT_STYLE).withSerializable(BundleKey.PAYMENTSTYLEBEAN, paymentStyleBean).navigation();
    }

    @Override
    public void getPaymentStyleDataFail(String errorMsg) {
        ToastUtils.showShort(errorMsg);
        repaymentPresent.getRepayInfo();
    }

    /**
     * '
     * 设置adapter
     */
    private void initAdapter() {
        adapter = new RepaymentAdapter(getActivity(), R.layout.item_repayment_layout, repayInfoBean.data.item.list);
        installmentRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //0是待确认打款，2是已还款
                if (repayInfoBean.data.item.list.get(position).status != 0 && repayInfoBean.data.item.list.get(position).status != 2) {
                    repaymentPresent.getPaymentStyleData(repayInfoBean.data.item.list.get(position).repay_id);
                } else {
                    ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL, repayInfoBean.data.item.detail_url)
                            .withBoolean(BundleKey.WEB_SET_SESSION, true).navigation();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    /**
     * 有借款的页面显示
     *
     * @param totalData
     */
    private void showInstallmentView(RepayInfoBean.DataBean.ItemBean.TotalDataBean totalData) {
        llRepayStatusLayout.setVisibility(View.GONE);
        llRepayListLayout.setVisibility(View.VISIBLE);
        tvRepayTitle.setText(totalData.order_title);
        tvAmountMoney.setText(totalData.money_amount + "元");
        tvLateAccount.setText(totalData.late_money + "元");
        tvTotalPeriod.setText(totalData.total_period);
    }

    /*
     * 信用分不足
     * */
    private void showPoorCreditView() {
        llRepayStatusLayout.setVisibility(View.VISIBLE);
        llRepayListLayout.setVisibility(View.GONE);
        ivStatusBac.setImageResource(R.mipmap.borrow_failure);
        if (repayInfoBean.data.risk_status.status == 1) {//代表审核不通过 不能够借款，点击下班确定按钮并进行导流
            tvStatusDesc.setText(repayInfoBean.data.risk_status.message);
            btnStatusNext.setText("确定");
        }
        btnStatusNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL, repayInfoBean.data.risk_status.register_url).navigation();
            }
        });
    }
    /*
     * 审核中
     * */

    private void showCheckDataView() {
        llRepayStatusLayout.setVisibility(View.VISIBLE);
        llRepayListLayout.setVisibility(View.GONE);
        ivStatusBac.setImageResource(R.mipmap.borrow_examine);
        //  审核状态对应不同展示文案
        if (!TextUtils.isEmpty(repayInfoBean.data.item.banner.message)) {
            String str = repayInfoBean.data.item.banner.message;
            if (str.contains("申请中") || str.contains("审核中")) {
                int start = str.indexOf("在");
                int end = str.indexOf("\n");
                String text1 = str.substring(0, start + 1);
                String text2 = str.substring(start + 1, end);
                String text3 = str.substring(end, str.length());
                tvStatusDesc.setText(new SpanUtils().append(text1).append(text2).setBold().setForegroundColor(Color.RED).append(text3).create());
            } else if (str.contains("急速打款")) {
                int start = str.indexOf("在");
                int end = str.lastIndexOf("\n");
                String text1 = str.substring(0, start + 1);
                String text2 = str.substring(start + 1, end);
                String text3 = str.substring(end, str.length());
                tvStatusDesc.setText(new SpanUtils().append(text1).append(text2).setBold().setForegroundColor(Color.RED).append(text3).create());
            } else {
                tvStatusDesc.setText(str);
            }
        } else {
            tvStatusDesc.setText("您的借款正在极速申请中\n请耐心等待审核结果");
        }
        btnStatusNext.setText("查看详情");
        btnStatusNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withBoolean(BundleKey.WEB_SET_SESSION, true).withString(BundleKey.URL, repayInfoBean.data.item.detail_url).navigation();
            }
        });
    }

    /**
     * 没有贷款的情况
     *
     * @param loanComplete
     */
    private void showNoDataView(boolean loanComplete) {
        llRepayStatusLayout.setVisibility(View.VISIBLE);
        llRepayListLayout.setVisibility(View.GONE);
        ivStatusBac.setImageResource(R.drawable.no_record);
        tvStatusDesc.setText(loanComplete ? "您的账单已经还清啦～" : "您目前还没有待还款的账单哦~");
        btnStatusNext.setText(loanComplete ? "再次申请" : "马上申请");
        btnStatusNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repayInfoBean.data.item.banner.button == 1) {//再次申请
                    ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED,BundleKey.MAIN_LOAN).navigation();
                } else {//验证中心
                    ARouter.getInstance().build(ArouterUtil.TO_CERTIFICATION).navigation();
                }
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
    public void onDestroyView() {
        super.onDestroyView();
        if (repaymentPresent != null) {
            repaymentPresent.detach();
            repaymentPresent = null;
        }
    }


}
