package com.zst.ynh.widget.repayment.repaymentfragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.zst.ynh.R;
import com.zst.ynh.adapter.RepaymentAdapter;
import com.zst.ynh.bean.PaymentStyleBean;
import com.zst.ynh.bean.RepayInfoBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.widget.person.certification.identity.IdentityCertificationActivity;
import com.zst.ynh_base.adapter.recycleview.MultiItemTypeAdapter;
import com.zst.ynh_base.lazyviewpager.LazyFragmentPagerAdapter;
import com.zst.ynh_base.mvp.view.BaseFragment;
import com.zst.ynh_base.util.Layout;

import java.util.List;

import butterknife.BindView;

@Layout(R.layout.fragment_repayment)
public class RepaymentFragment extends BaseFragment implements IRepaymentView, LazyFragmentPagerAdapter.Laziable {
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
    private RepaymentAdapter adapter;
    private RepayInfoBean repayInfoBean;
    private RepaymentPresent repaymentPresent;


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
        getPermission();
    }

    private void getPermission() {
        if (XXPermissions.isHasPermission(getActivity(), Permission.Group.CALENDAR)) {
            repaymentPresent.getRepayInfo();
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
    public void loadLoading() {
        loadLoadingView();
    }

    @Override
    public void LoadError() {
        loadErrorView();
    }

    @Override
    public void loadContent() {
        loadContentView();
    }

    @Override
    public void getRepayInfoSuccess(RepayInfoBean repayInfoBean) {
        this.repayInfoBean = repayInfoBean;
        int status = repayInfoBean.data.item.banner.status;
        initAdapter();
        //status: 1:申请中 2：驳回 ；3：未申请 ;4:已还款
        // button :1:认证完成  5:认证中
        if ((repayInfoBean.data.item.list == null || repayInfoBean.data.item.list.size() == 0) &&
                (status == 4 || status == 3 || status == 0)) {//没有数据 ，马上申请/再次申请
            showNoDataView(4 == status);
        } else if (status == 1) {//贷款审核中
            showCheckDataView();
        } else if (status == 2) {//驳回
            String can_loan_time = repayInfoBean.data.item.banner.can_loan_time;//再次可借款的时间
            showPoorCreditView(can_loan_time);
        } else {
            RepayInfoBean.DataBean.ItemBean.TotalDataBean totalData = repayInfoBean.data.item.total_data;
            if (totalData != null) {
                showInstallmentView(totalData);
            }
        }
    }

    @Override
    public void getPaymentStyleData(PaymentStyleBean paymentStyleBean) {
        ARouter.getInstance().build(ArouterUtil.PAYMENT_STYLE).withSerializable(BundleKey.PAYMENTSTYLEBEAN,paymentStyleBean).navigation();
    }

    @Override
    public void getPaymentStyleDataFail(String errorMsg) {
        ToastUtils.showShort(errorMsg);
        repaymentPresent.getRepayInfo();
    }

    /**'
     * 设置adapter
     */
    private void initAdapter(){
        adapter = new RepaymentAdapter(getActivity(), R.layout.item_repayment_layout, repayInfoBean.data.item.list);
        installmentRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //0是待确认打款，2是已还款
                if (repayInfoBean.data.item.list.get(position).status != 0 && repayInfoBean.data.item.list.get(position).status!= 2) {
                    repaymentPresent.getPaymentStyleData(repayInfoBean.data.item.list.get(position).repay_id);
                } else {
                    ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL,repayInfoBean.data.item.detail_url)
                            .withBoolean(BundleKey.WEB_SET_SESSION,true).navigation();
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
    private void showPoorCreditView(String can_loan_time) {
        llRepayStatusLayout.setVisibility(View.VISIBLE);
        llRepayListLayout.setVisibility(View.GONE);
        ivStatusBac.setImageResource(R.mipmap.borrow_failure);
        if (can_loan_time.equals("0")) {
            tvStatusDesc.setText("很遗憾，您的信用评分不足\n本次借款未能通过");
            btnStatusNext.setText("再次申请");
            btnStatusNext.setEnabled(true);
        } else if (can_loan_time.equals("1")) {
            tvStatusDesc.setText("很遗憾，您的信用评分不足，无法借款");
            btnStatusNext.setVisibility(View.GONE);
        } else {
            tvStatusDesc.setText("很遗憾，您的信用评分不足\n请" + can_loan_time + "天后再尝试");
            btnStatusNext.setText("再次申请（" + can_loan_time + "天）");
            btnStatusNext.setEnabled(false);
        }

        btnStatusNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED, "0").navigation();
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
                if (repayInfoBean.data.item.banner.button == 1) {//
                    ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED, "0").navigation();
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
