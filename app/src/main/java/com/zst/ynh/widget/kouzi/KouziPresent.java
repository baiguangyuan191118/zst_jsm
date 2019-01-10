package com.zst.ynh.widget.kouzi;

import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

public class KouziPresent extends BasePresent<IKouziView> {

    public void isSuperLoan(){
        httpManager.get(ApiUrl.IS_SUPER_LOAN, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.isSuperLoanFailed(code,errorMSG);
            }

            @Override
            public void onSuccess(String response) {
                mView.isSuperLoan(response);
            }
        });
    }

}
