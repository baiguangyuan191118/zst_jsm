package com.zst.ynh.widget.loan.applysuccess;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.zst.ynh.R;
import com.zst.ynh.bean.ApplySuccessBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ArouterUtil.APPLY_LOAN_SUCCESS)
@Layout(R.layout.activity_apply_loan_success)
public class ApplySuccessActvity extends BaseActivity implements IApplySuccessView{

    @BindView(R.id.btn_detail)
    TextView btnDetail;
    @BindView(R.id.btn_go_home)
    TextView btnGoHome;
    @BindView(R.id.loan_money)
    TextView loanMoney;
    @BindView(R.id.sum_of_interest)
    TextView sumOfInterest;
    @BindView(R.id.loan_limit_days)
    TextView loanLimitDays;
    @BindView(R.id.loan_date)
    TextView loanDate;

    @Autowired(name=BundleKey.ORDER_ID)
    int orderId;
    @Autowired(name = BundleKey.PLATFORM)
    String platform;

    private ApplySuccessPresent present;
    private ApplySuccessBean applySuccessBean;

    @Override
    public void onRetry() {
        present.getLoanSuccessInfo(orderId,platform);
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        mTitleBar.setTitle("申请成功");
        //上传白骑士token

        present=new ApplySuccessPresent();
        present.attach(this);
        present.getLoanSuccessInfo(orderId,platform);
    }

    @OnClick({R.id.btn_detail,R.id.btn_go_home})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.btn_detail:
                ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withBoolean(BundleKey.WEB_SET_SESSION,true).withString(BundleKey.URL,applySuccessBean.getDetail_url()).navigation();
                break;
            case R.id.btn_go_home:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(present!=null){
            present.detach();
        }

    }

    @Override
    public void onBackPressed() {
        ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED,BundleKey.MAIN_LOAN).withBoolean(BundleKey.MAIN_FRESH,true).navigation();
    }

    @Override
    public void getApplySuccess(ApplySuccessBean response) {

        applySuccessBean=response;
        if(!StringUtils.isEmpty(response.getMoney_amount())){
            loanMoney.setText(response.getMoney_amount()+"元");
        }
        if(!StringUtils.isEmpty(response.getCounter_fee())){
            sumOfInterest.setText(response.getCounter_fee()+"元");
        }
        if(!StringUtils.isEmpty(response.getLoan_term())){
            loanLimitDays.setText(response.getLoan_term());
        }
        if(!StringUtils.isEmpty(response.getApply_date())){
            loanDate.setText(response.getApply_date());
        }

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
        loadErrorView();
        ToastUtils.showShort(msg);
    }
}
