package com.zst.ynh.widget.loan.confirm;

import com.zst.ynh.bean.DepositOpenInfoVBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;


public class LoanConfirmPresent extends BasePresent<ILoanConfirmView> {
    /**
     * 存管的口子（目前没用到 先写上）
     */
    public void loanConfirm(){
        mView.showLoading();
        httpManager.executeGet(ApiUrl.DEPOSIT_OPEN, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<DepositOpenInfoVBean>() {

            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code,String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(DepositOpenInfoVBean response) {
                mView.getDepositOpenInfo(response);
            }
        });
    }
}
