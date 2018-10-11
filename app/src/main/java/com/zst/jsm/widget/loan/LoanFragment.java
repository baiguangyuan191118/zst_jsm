package com.zst.jsm.widget.loan;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.stx.xmarqueeview.XMarqueeView;
import com.zst.jsm.JsmApplication;
import com.zst.jsm.R;
import com.zst.jsm.adapter.MarqueeViewAdapter;
import com.zst.jsm.bean.LoanBean;
import com.zst.jsm.core.bitmap.ImageLoaderUtils;
import com.zst.jsm.event.StringEvent;
import com.zst.jsm.view.BottomDialog;
import com.zst.jsm.view.RefreshHeadView;
import com.zst.jsm.view.StatementDialog;
import com.zst.jsm_base.mvp.view.BaseFragment;
import com.zst.jsm_base.util.Layout;
import com.zst.jsm_base.view.BannerLayout;
import com.zst.jsm_base.view.HomeSeekBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Layout(R.layout.loan_fragment_layout)
public class LoanFragment extends BaseFragment implements ILoanView {
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
    @BindView(R.id.tv_rent_btn)
    Button tvRentBtn;
    @BindView(R.id.iv_float)
    ImageView ivFloat;
    @BindView(R.id.refresh_view)
    TwinklingRefreshLayout refreshView;

    private LoanPresent loanPresent;
    private int loanMoney;
    private LoanBean loanBean;
    private StatementDialog statementDialog;
    private BottomDialog bottomDialog;

    public static LoanFragment newInstance() {
        LoanFragment fragment = new LoanFragment();
        return fragment;
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
        loadContentView();
        loanPresent = new LoanPresent();
        loanPresent.attach(this);
        loanPresent.getIndexData();
        refreshView.setEnableLoadmore(false);//禁止加载更多
        refreshView.setHeaderView(new RefreshHeadView(getContext()));//设置刷新样式
    }

    @Override
    public void getAppIndexData(LoanBean loanBean) {
        this.loanBean=loanBean;
        setBannerData();
        setAdvertisingData();
        setLoanData();
    }

    /**
     * 设置借款金额
     */
    private void setLoanData() {
        //上方的金额与滑动条
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
                loanMoney = amounts.get(progressIndex)/ 100;
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
        tvStartMoney.setText(loanBean.unused_amount/100+"元");
        //最大金额
        tvEndMoney.setText(loanBean.amounts_max/100+"元");
        //利率
        tvInterestPerMonthLabel.setText(loanBean.service_fee.interest_rate*100+"%");
        //天数
        tvLoanPeriod.setText(loanBean.period_num.get(0).pv);
        bottomDialog=new BottomDialog(getActivity());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(StringEvent messageEvent) {
        tvLoanPeriod.setText(messageEvent.getMessage());
    }
    /**
     * 设置广告位数据
     */
    private void setAdvertisingData() {
        MarqueeViewAdapter marqueeViewAdapter=new MarqueeViewAdapter(loanBean.user_loan_log_list,JsmApplication.getContext());
        upview2.setAdapter(marqueeViewAdapter);
    }

    /**
     * 设置banner数据
     */
    private void setBannerData() {
        banner.setImageLoader(new ImageLoaderUtils());
        final List<String> urls = new ArrayList<>();
        for (LoanBean.ItemBean item : loanBean.item ) {
            urls.add(item.img_url);
        }
        banner.setViewUrls(urls);

        //添加监听事件
        banner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LoanBean.ItemBean bean =  loanBean.item.get(position);

                if (!TextUtils.isEmpty(bean.skip_code)) {
//                    if (SchemeTool.TAG_JUMP_H5.equals(bean.skip_code)) {
//                        SchemeTool.jump(Uri.parse(bean.active_url), getActivity());
//                    } else {
//                        SchemeTool.jump(Uri.parse(SchemeTool.MyScheme + "://?skip_code=" + bean.skip_code), getActivity());
//                    }
                }


            }


        });
    }

    @OnClick({R.id.iv_interest_pre_month_tips,R.id.tv_loan_period})
    public void onClick(View view){
        switch (view.getId()){
            //问号
            case R.id.iv_interest_pre_month_tips:
                if (null !=loanBean  && !TextUtils.isEmpty(loanBean.service_fee.interest_rate_des)) {
                    statementDialog=new StatementDialog(getActivity()).builder()
                            .setCancelable(false)
                            .setMsg(loanBean.service_fee.interest_rate_des);

                } else {
                    statementDialog=new StatementDialog(getActivity()).builder()
                            .setCancelable(false)
                            .setMsg("月利率与投资人的资金成本相关，此处展示仅作为参考");
                }
                statementDialog.show();
                break;
                //天数点击
                case R.id.tv_loan_period:
                    break;
        }
    }

    @Override
    public void showLoading() {
        refreshView.startRefresh();
    }


    @Override
    public void hideLoading() {
        refreshView.finishRefreshing();
    }

    @Override
    public void ToastErrorMessage(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (statementDialog.isShowing()){
            statementDialog.dissMiss();
        }
    }
}
