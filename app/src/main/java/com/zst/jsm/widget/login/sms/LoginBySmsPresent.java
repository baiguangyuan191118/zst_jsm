package com.zst.jsm.widget.login.sms;

import com.zst.jsm.bean.LoanBean;
import com.zst.jsm.bean.LoginBean;
import com.zst.jsm.config.ApiUrl;
import com.zst.jsm_base.mvp.present.BasePresent;
import com.zst.jsm_base.net.BaseParams;
import com.zst.jsm_base.net.HttpManager;

public class LoginBySmsPresent extends BasePresent<ILoginBySmsView> {
    public void getIndexData() {
        mView.showLoading();
        httpManager.executeGet(ApiUrl.APP_INDEX, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<LoginBean>() {

            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(LoginBean response) {
                mView.getLoginData(response);
            }
        });
    }
}
