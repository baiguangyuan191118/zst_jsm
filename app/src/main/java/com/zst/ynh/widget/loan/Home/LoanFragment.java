package com.zst.ynh.widget.loan.Home;

import android.app.Dialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xmarqueeview.XMarqueeView;
import com.zst.ynh.JsmApplication;
import com.zst.ynh.R;
import com.zst.ynh.adapter.MarqueeViewAdapter;
import com.zst.ynh.bean.LoanBean;
import com.zst.ynh.bean.LoanConfirmBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.core.bitmap.ImageLoaderUtils;
import com.zst.ynh.event.StringEvent;
import com.zst.ynh.main.MainActivity;
import com.zst.ynh.utils.DialogUtil;
import com.zst.ynh.view.StatementDialog;
import com.zst.ynh_base.lazyviewpager.LazyFragmentPagerAdapter;
import com.zst.ynh_base.mvp.view.BaseFragment;
import com.zst.ynh_base.mvp.view.BaseLazyFragment;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.BannerLayout;
import com.zst.ynh_base.view.BaseDialog;
import com.zst.ynh_base.view.HomeSeekBar;
import com.zst.ynh_base.view.TitleBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Layout(R.layout.loan_fragment_layout)
public class LoanFragment extends BaseLazyFragment implements ILoanView {
    @BindView(R.id.banner)
    BannerLayout banner;
    @BindView(R.id.upview2)
    XMarqueeView upview2;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.tv_money_title)
    TextView tvMoneyTitle;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.hsb_selected_money)
    HomeSeekBar hsbSelectedMoney;
    @BindView(R.id.tv_start_money)
    TextView tvStartMoney;
    @BindView(R.id.tv_end_money)
    TextView tvEndMoney;
    @BindView(R.id.tv_interest_per_month_label)
    TextView tvInterestPerMonthLabel;
    @BindView(R.id.iv_interest_pre_month_tips)
    ImageView ivInterestPreMonthTips;
    @BindView(R.id.tv_interest_per_month)
    TextView tvInterestPerMonth;
    @BindView(R.id.tv_jkqx)
    TextView tvJkqx;
    @BindView(R.id.tv_loan_period)
    TextView tvLoanPeriod;
    @BindView(R.id.btn_application)
    Button btnApplication;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout RefreshLayout;
    @BindView(R.id.loan_titlebar)
    TitleBar titleBar;

    private LoanPresent loanPresent;
    private int loanMoney;
    private LoanBean loanBean;
    private StatementDialog statementDialog;
    private int maxMoney;
    private Dialog loanDialog;
    private LoanConfirmBean loanConfirmBean;
    private MarqueeViewAdapter marqueeViewAdapter;
    private boolean isInit=false;
    private boolean isFresh=false;

    public static LoanFragment newInstance() {
        LoanFragment fragment = new LoanFragment();
        return fragment;
    }

    public void setFresh(boolean fresh) {
        isFresh = fresh;
    }

    public void autoFresh() {
        if(isInit && isFresh){
            RefreshLayout.autoRefresh();
            isFresh=false;
        }
    }

    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void onRetry() {

    }

    @Override
    protected void initView() {
        LogUtils.d("initView");
        loadContentView();
        titleBar.setTitle(R.string.app_name);
        titleBar.setBackgroundColor(Color.WHITE);
        titleBar.setTitleColor(Color.BLACK);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setActionTextColor(Color.WHITE);
        loanPresent = new LoanPresent();
        loanPresent.attach(this);
        RefreshLayout.setEnableLoadMore(false);
        isInit = true;
    }

    @Override
    public void onLazyLoad() {
        RefreshLayout.autoRefresh();
        RefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull com.scwang.smartrefresh.layout.api.RefreshLayout refreshLayout) {
                if (loanPresent != null) {
                    loanPresent.getIndexData();
                }
            }
        });
    }

    @Override
    public void getAppIndexData(LoanBean loanBean) {
        this.loanBean = loanBean;
        setTitleBar(titleBar);
        setBannerData();
        setAdvertisingData();
        setLoanData();
        setButtonStyle();
    }

    public void setTitleBar(TitleBar titleBar) {

        if (loanBean != null) {
            MainActivity activity = (MainActivity) getActivity();
            activity.getmTitleBar().setVisibility(View.GONE);
            View rightlayout = LayoutInflater.from(this.getContext()).inflate(R.layout.view_message, null);
            TextView messageNo = rightlayout.findViewById(R.id.tv_message_no);
            ImageView message = rightlayout.findViewById(R.id.iv_message);
            if (loanBean.message.message_no == 0) {
                messageNo.setVisibility(View.INVISIBLE);
            } else {
                messageNo.setVisibility(View.VISIBLE);
                if (loanBean.message.message_no > 99) {
                    messageNo.setText("99");
                } else {
                    messageNo.setText("" + loanBean.message.message_no);
                }
            }

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(loanBean.message.message_no==0){
                        ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL, loanBean.message.message_url).withBoolean(BundleKey.WEB_SET_SESSION, true).navigation();
                    }else{
                        ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL, loanBean.message.message_url).withBoolean(BundleKey.MAIN_FRESH,true).withBoolean(BundleKey.WEB_SET_SESSION, true).navigation();
                    }

                }
            });
            titleBar.removeAllActions();
            titleBar.addRightLayout(rightlayout);
        }


    }

    @Override
    public void getLoanConfirmData(final LoanConfirmBean loanConfirmBean) {
        this.loanConfirmBean = loanConfirmBean;

        if (loanConfirmBean.item.dialog_credit_expired != null) {//要弹窗 说明认证已经过期了
            loanDialog = new BaseDialog.Builder(getActivity()).setContent1(loanConfirmBean.item.dialog_credit_expired.title).setBtnLeftText("继续借款")
                    .setBtnRightText("去完善").setBtnRightBackgroundColor(JsmApplication.getContext().getResources().getColor(R.color.them_color)).setBtnRightColor(Color.WHITE)
                    .setLeftOnClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouter.getInstance().build(ArouterUtil.LOAN_CONFIRM).withSerializable(BundleKey.LOAN_CONFIRM, loanConfirmBean).navigation();
                            DialogUtil.hideDialog(loanDialog);
                        }
                    }).setRightOnClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ARouter.getInstance().build(ArouterUtil.CERTIFICATION_CENTER).navigation();
                            DialogUtil.hideDialog(loanDialog);
                        }
                    }).create();
            loanDialog.show();
        } else {//直接跳到申请确认页面
            ARouter.getInstance().build(ArouterUtil.LOAN_CONFIRM).withSerializable(BundleKey.LOAN_CONFIRM, loanConfirmBean).navigation();
        }
    }

    @Override
    public void getLoanConfirmFail(int code, String errorMSG) {
        switch (code) {
            case 1005:
                loanDialog = new BaseDialog.Builder(getActivity()).setContent1(errorMSG).setBtnLeftText("去认证")
                        .setBtnLeftBackgroundColor(JsmApplication.getContext().getResources().getColor(R.color.them_color)).setBtnLeftColor(Color.WHITE)
                        .setLeftOnClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ARouter.getInstance().build(ArouterUtil.LOAN_CONFIRM).withSerializable(BundleKey.LOAN_CONFIRM, loanConfirmBean).navigation();
                                DialogUtil.hideDialog(loanDialog);
                            }
                        }).create();
                loanDialog.show();
                break;
            case -1:
                loanDialog = new BaseDialog.Builder(getActivity()).setContent1(errorMSG).setBtnLeftText("知道了")
                        .setBtnLeftBackgroundColor(JsmApplication.getContext().getResources().getColor(R.color.them_color)).setBtnLeftColor(Color.WHITE)
                        .setLeftOnClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DialogUtil.hideDialog(loanDialog);
                            }
                        }).create();
                loanDialog.show();
                break;
            default:
                ToastUtils.showShort(errorMSG);
                break;
        }
    }

    @Override
    public void showProgressLoading() {
        showLoadingView();
    }

    @Override
    public void hideProgressLoading() {
        hideLoadingView();
    }

    /**
     * 设置下方按钮
     */
    private void setButtonStyle() {
        switch (loanBean.amount_button) {
            case 0:
            case 1:
            case 2:
            case 5:
                btnApplication.setEnabled(true);
                btnApplication.setText("极速申请");
                break;
            default:
                btnApplication.setEnabled(false);
                btnApplication.setText("今日额度已抢完");
                break;
        }
    }

    /**
     * 设置借款金额
     */
    private void setLoanData() {
        hsbSelectedMoney.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                List<Integer> amounts = loanBean.amounts;
                if (amounts == null || amounts.isEmpty()) {
                    return;
                }

                int progressIndex = (progress + 50) / 100;
                if (progressIndex >= amounts.size()) {
                    progressIndex = amounts.size() - 1;
                }
                loanMoney = amounts.get(progressIndex) / 100;
                //字体的改变
                tvMoney.setText(loanMoney + ".00");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //设置距离
                hsbSelectedMoney.setProgress((hsbSelectedMoney.getProgress() + 50) / 100 * 100);
            }
        });
        //最小金额
        tvStartMoney.setText(loanBean.amounts.get(0) / 100 + "元");
        //最大金额
        tvEndMoney.setText(loanBean.amounts.get(loanBean.amounts.size() - 1) / 100 + "元");
        //利率
        tvInterestPerMonth.setText(loanBean.service_fee.interest_rate * 100 + "%");
        //天数
        tvLoanPeriod.setText(loanBean.period_num.get(0).pv);

        if (loanBean.amounts == null || loanBean.amounts.isEmpty()) {
            maxMoney = 0;
        } else if (loanBean.amounts.size() == 1) {
            maxMoney = 0;
        } else {
            maxMoney = (loanBean.amounts.size() - 1) * 100;
        }

        hsbSelectedMoney.setMax(maxMoney);
        hsbSelectedMoney.setProgress(maxMoney);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(StringEvent messageEvent) {
        tvLoanPeriod.setText(messageEvent.getMessage());
    }

    /**
     * 设置广告位数据
     */
    private void setAdvertisingData() {
        if (marqueeViewAdapter == null) {
            marqueeViewAdapter = new MarqueeViewAdapter(loanBean.user_loan_log_list, JsmApplication.getContext());
            upview2.setAdapter(marqueeViewAdapter);
        } else {
            marqueeViewAdapter.setData(loanBean.user_loan_log_list);
        }
    }

    /**
     * 设置banner数据
     */
    private void setBannerData() {
        banner.setImageLoader(new ImageLoaderUtils());
        final List<String> urls = new ArrayList<>();
        for (LoanBean.ItemBean item : loanBean.item) {
            urls.add(item.img_url);
        }
        banner.setViewUrls(urls);

        //添加监听事件
        // TODO: 2018/10/20  banner 的点击事件还没写  需要webActivity整完了才能添加
        banner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LoanBean.ItemBean bean = loanBean.item.get(position);

                if (!TextUtils.isEmpty(bean.skip_code)) {

                    if(bean.skip_code.equals("101")){//跳转到home
                        RefreshLayout.autoRefresh();
                    }else if(bean.skip_code.equals("108")){//跳转到h5 webview
                        ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL,bean.active_url).withBoolean(BundleKey.WEB_SET_SESSION,true).navigation();
                    }
                }


            }


        });
    }

    /**
     * 添加一个判断看是否可以贷款
     *
     * @return
     */
    private boolean checkCanLoan() {
        if (loanMoney <= 0) {
            ToastUtils.showShort("借款金额错误");
            return false;
        }
        if (loanMoney <= 0) {
            ToastUtils.showShort("借款期限错误");
            return false;
        }
        return true;
    }

    @OnClick({R.id.iv_interest_pre_month_tips, R.id.btn_application})
    public void onClick(View view) {
        switch (view.getId()) {
            //问号
            case R.id.iv_interest_pre_month_tips:
                if (null != loanBean && !TextUtils.isEmpty(loanBean.service_fee.interest_rate_des)) {
                    statementDialog = new StatementDialog(getActivity()).builder()
                            .setCancelable(false)
                            .setMsg(loanBean.service_fee.interest_rate_des);

                } else {
                    statementDialog = new StatementDialog(getActivity()).builder()
                            .setCancelable(false)
                            .setMsg("月利率与投资人的资金成本相关，此处展示仅作为参考");
                }
                statementDialog.show();
                break;
            //申请的点击
            case R.id.btn_application:
                switch (loanBean.amount_button) {
                    case 0://去登陆
                        ARouter.getInstance().build(ArouterUtil.LOGIN).navigation();
                        break;
                    case 1://去借款
                        if (checkCanLoan()) {
                            loanPresent.loanConfirm(loanMoney + "", loanBean.period_num.get(0).pk, "1");
                        }
                        break;
                    case 2://认证中（5步走完 审核中）
                        ARouter.getInstance().build(ArouterUtil.CERTIFICATION_CENTER).navigation();
                        break;
                    case 5://去认证（5步认证）
                        ARouter.getInstance().build(ArouterUtil.TO_CERTIFICATION).navigation();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    @Override
    public void showLoading() {
    }


    @Override
    public void hideLoading() {
        RefreshLayout.finishRefresh();
    }

    @Override
    public void ToastErrorMessage(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (statementDialog != null && statementDialog.isShowing()) {
            statementDialog.dissMiss();
        }
        isInit = false;
        DialogUtil.hideDialog(loanDialog);
        DialogUtil.hideDialog(loanDialog);
        if (loanPresent != null)
            loanPresent.detach();
    }

}
