package com.zst.ynh.widget.repayment;

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
import com.zst.ynh.R;
import com.zst.ynh.adapter.InstallmentAdapter;
import com.zst.ynh.bean.RepayInfoBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh_base.lazyviewpager.LazyFragmentPagerAdapter;
import com.zst.ynh_base.mvp.view.BaseFragment;
import com.zst.ynh_base.util.Layout;

import butterknife.BindView;

@Layout(R.layout.fragment_repayment)
public class RepaymentFragment extends BaseFragment implements IRepaymentView, LazyFragmentPagerAdapter.Laziable {

    public static RepaymentFragment newInstance() {
        RepaymentFragment fragment = new RepaymentFragment();
        return fragment;
    }

    private RepaymentPresent repaymentPresent;

    //无数据 布局
    @BindView(R.id.ll_retry)
    LinearLayout noDataLayout;
    @BindView(R.id.iv_error)
    ImageView noDataImage;
    @BindView(R.id.tv_error_msg)
    TextView noDataMsg;
    @BindView(R.id.btn_on_retry)
    Button noDataBtn;

    //审核中 ,驳回
    @BindView(R.id.ll_check)
    LinearLayout checkLayout;
    @BindView(R.id.check_icon)
    ImageView checkIcon;
    @BindView(R.id.check_desc)
    TextView checkDesciption;
    @BindView(R.id.btn_check_next)
    TextView checkNext;
    @BindView(R.id.txt_friendly_help)
    TextView friendlyHelp;

    /*
     *
     * */
    @BindView(R.id.installment)
    LinearLayout installmentLayout;
    @BindView(R.id.repay_title)
    TextView repayTitle;
    @BindView(R.id.amount_money)
    TextView amountMoney;
    @BindView(R.id.late_account)
    TextView lateMoney;
    @BindView(R.id.total_period)
    TextView totalPeriod;
    @BindView(R.id.installment_recycler_view)
    RecyclerView recyclerView;
    private InstallmentAdapter adapter;

    private RepayInfoBean repayInfoBean;

    @Override
    protected void onRetry() {

    }

    @Override
    protected void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter=new InstallmentAdapter();
        repaymentPresent = new RepaymentPresent();
        repaymentPresent.attach(this);
        repaymentPresent.getRepayInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (repaymentPresent != null) {
            repaymentPresent.detach();
        }
    }


    @Override
    public void getRepayInfoSuccess(RepayInfoBean repayInfoBean) {
        this.repayInfoBean = repayInfoBean;
        int status = repayInfoBean.item.banner.status;

        if ((repayInfoBean.item.list == null || repayInfoBean.item.list.size() == 0) &&
                (status == 4 || status == 3 || status == 0)) {//没有数据 ，马上申请/再次申请
            showNodataView(4 == status);
        } else if (status == 1) {//贷款审核中
            showCheckDataView();
        } else if (status == 2) {//驳回
            String can_loan_time = repayInfoBean.item.banner.can_loan_time;//再次可借款的时间
            showPoorCreditView(can_loan_time);
        } else {
            RepayInfoBean.RepaymentItemBean.TotalData totalData = repayInfoBean.item.total_data;
            if (totalData != null) {
                showInstallmentView(totalData);
            } else {

            }
        }
    }

    private void showInstallmentView(RepayInfoBean.RepaymentItemBean.TotalData totalData) {
        noDataLayout.setVisibility(View.GONE);
        checkLayout.setVisibility(View.GONE);
        installmentLayout.setVisibility(View.VISIBLE);
        repayTitle.setText(totalData.order_title);
        amountMoney.setText(totalData.money_amount+"元");
        lateMoney.setText(totalData.late_money+"元");
        totalPeriod.setText(totalData.total_period);
    }

    /*
     * 信用分不足
     * */
    private void showPoorCreditView(String can_loan_time) {
        noDataLayout.setVisibility(View.GONE);
        checkLayout.setVisibility(View.VISIBLE);
        installmentLayout.setVisibility(View.GONE);
        checkIcon.setImageResource(R.mipmap.borrow_failure);
        if (can_loan_time.equals("0")) {
            checkDesciption.setText("很遗憾，您的信用评分不足\n本次借款未能通过");
            checkNext.setText("再次申请");
            checkNext.setEnabled(true);
        } else if (can_loan_time.equals("1")) {
            checkDesciption.setText("很遗憾，您的信用评分不足，无法借款");
            checkNext.setVisibility(View.GONE);
        } else {
            checkDesciption.setText("很遗憾，您的信用评分不足\n请" + can_loan_time + "天后再尝试");
            checkNext.setText("再次申请（" + can_loan_time + "天）");
            checkNext.setEnabled(false);
        }

        checkNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED, "0").navigation();
            }
        });

        String export_title = repayInfoBean.item.banner.export_title;
        if (!TextUtils.isEmpty(export_title)) {
            friendlyHelp.setVisibility(View.VISIBLE);
            friendlyHelp.setText(export_title);
            friendlyHelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL, repayInfoBean.item.banner.export_url).navigation();
                }
            });
        } else {
            friendlyHelp.setVisibility(View.GONE);
        }

    }

    /*
     * 审核中
     * */
    private void showCheckDataView() {
        checkLayout.setVisibility(View.VISIBLE);
        noDataLayout.setVisibility(View.GONE);
        installmentLayout.setVisibility(View.GONE);
        checkIcon.setImageResource(R.mipmap.borrow_examine);
        //  审核状态对应不同展示文案
        if (!TextUtils.isEmpty(repayInfoBean.item.banner.message)) {
            String str = repayInfoBean.item.banner.message;
            if (str.contains("申请中") || str.contains("审核中")) {
                int start = str.indexOf("在");
                int end = str.indexOf("\n");
                String text1 = str.substring(0, start + 1);
                String text2 = str.substring(start + 1, end);
                String text3 = str.substring(end, str.length());
                checkDesciption.setText(new SpanUtils().append(text1).append(text2).setBold().setForegroundColor(Color.RED).append(text3).create());
            } else if (str.contains("急速打款")) {
                int start = str.indexOf("在");
                int end = str.lastIndexOf("\n");
                String text1 = str.substring(0, start + 1);
                String text2 = str.substring(start + 1, end);
                String text3 = str.substring(end, str.length());
                checkDesciption.setText(new SpanUtils().append(text1).append(text2).setBold().setForegroundColor(Color.RED).append(text3).create());
            } else {
                checkDesciption.setText(str);
            }
        } else {
            checkDesciption.setText("您的借款正在极速申请中\n请耐心等待审核结果");
        }
        checkNext.setText("查看详情");
        friendlyHelp.setVisibility(View.GONE);
        checkNext.setEnabled(true);
        checkNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withBoolean(BundleKey.WEB_SET_SESSION, true).withString(BundleKey.URL, repayInfoBean.item.detail_url).navigation();
            }
        });
    }

    private void showNodataView(boolean loanComplete) {
        noDataLayout.setVisibility(View.VISIBLE);
    /*    ll_repay.setVisibility(View.GONE);
        installment.setVisibility(View.GONE);
        ll_refuse.setVisibility(View.GONE);
        ll_check.setVisibility(View.GONE);*/
        noDataImage.setImageResource(R.drawable.no_record);
        noDataMsg.setText(loanComplete ? "您的账单已经还清啦～" : "您目前还没有待还款的账单哦~");
        noDataBtn.setText(loanComplete ? "再次申请" : "马上申请");
        noDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repayInfoBean.item.banner.button == 1) {//
                    ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED, "0").navigation();
                } else {//验证中心
                    ARouter.getInstance().build(ArouterUtil.TO_CERTIFICATION).navigation();
                }
            }
        });
    }

    @Override
    public void getRepayInfoFailed(int code, String errorMSG) {

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
}
