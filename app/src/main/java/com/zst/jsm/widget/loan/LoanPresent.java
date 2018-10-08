package com.zst.jsm.widget.loan;

import com.zst.jsm.bean.LoanBean;
import com.zst.jsm.config.ApiUrl;
import com.zst.jsm_base.mvp.present.BasePresent;
import com.zst.jsm_base.net.BaseParams;
import com.zst.jsm_base.net.HttpManager;

public class LoanPresent extends BasePresent<ILoanView> {
    public void getIndexData() {
        mView.showLoadingView();
        httpManager.executeGet(ApiUrl.APP_INDEX, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<LoanBean>() {

            @Override
            public void onCompleted() {
                mView.hideLoadingView();
            }

            @Override
            public void onError(String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(LoanBean response) {
                mView.getAppIndexData(response);
            }
        });
    }
}
