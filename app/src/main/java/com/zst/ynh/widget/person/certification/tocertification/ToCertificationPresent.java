package com.zst.ynh.widget.person.certification.tocertification;

import com.zst.ynh.bean.CertificationGuideBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;


public class ToCertificationPresent extends BasePresent<IToCertificationView> {
    /**
     * 获取认证走到了哪一步
     */
    public void getCertificationGuide(){
        mView.showLoading();
        httpManager.executePostJson(ApiUrl.CERTIFICATION_GUIDE, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<CertificationGuideBean>() {

            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code,String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(CertificationGuideBean response) {
                mView.getCertificationType(response);
            }
        });
    }
}
