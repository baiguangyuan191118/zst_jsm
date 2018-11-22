package com.zst.ynh.widget.repayment;

import com.zst.ynh.bean.RepayInfoBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

public class RepaymentPresent extends BasePresent<IRepaymentView> {


    public void getRepayInfo(){
        mView.showLoading();
        httpManager.executeGet(ApiUrl.REPAY_INFO, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<RepayInfoBean>() {
            @Override
            public void onCompleted() {
             mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);

            }

            @Override
            public void onSuccess(RepayInfoBean response) {
                mView.getRepayInfoSuccess(response);
            }
        });
    }
}
