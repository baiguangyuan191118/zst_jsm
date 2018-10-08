package com.zst.jsm.widget.loan;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.stx.xmarqueeview.XMarqueeView;
import com.zst.jsm.R;
import com.zst.jsm.bean.LoanBean;
import com.zst.jsm.core.bitmap.ImageLoaderUtils;
import com.zst.jsm.view.RefreshHeadView;
import com.zst.jsm_base.mvp.view.BaseFragment;
import com.zst.jsm_base.util.Layout;
import com.zst.jsm_base.view.BannerLayout;
import com.zst.jsm_base.view.HomeSeekBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

    public static LoanFragment newInstance() {
        LoanFragment fragment = new LoanFragment();
        return fragment;
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
        setBannerData(loanBean);
    }

    /**
     * 设置banner数据
     */
    private void setBannerData(final LoanBean loanBean) {
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
                    if (SchemeTool.TAG_JUMP_H5.equals(bean.getSkip_code())) {
                        SchemeTool.jump(Uri.parse(bean.getActive_url()), activity);
                    } else {
                        SchemeTool.jump(Uri.parse(SchemeTool.MyScheme + "://?skip_code=" + bean.getSkip_code()), activity);
                    }
                }


            }


        });
    }


    @Override
    public void showLoadingView() {
        refreshView.startRefresh();
    }


    @Override
    public void hideLoadingView() {
        refreshView.finishRefreshing();
    }

    @Override
    public void ToastErrorMessage(String msg) {
        ToastUtils.showShort(msg);
    }

}
