package com.zst.ynh.widget.person.certification.incertification;

import com.zst.ynh.bean.InCertificationBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.ArrayList;
import java.util.List;

public class InCertificationPresent extends BasePresent<IInCertificationView> {
    /**
     * 获取认证中心信息
     */
    public void getVerificationInfo() {
        mView.showLoadView();
        httpManager.executeGet(ApiUrl.VERIFICATION_INFO, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<InCertificationBean>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.showErrorView();
            }

            @Override
            public void onSuccess(InCertificationBean response) {
                mView.getCertificationData(response);
                mView.showContentView();
            }
        });
    }


}
