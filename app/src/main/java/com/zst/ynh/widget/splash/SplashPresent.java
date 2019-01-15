package com.zst.ynh.widget.splash;

import com.zst.ynh.bean.SplashAdBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

public class SplashPresent extends BasePresent<SplashView> {

    public void getAdPage() {
        httpManager.executePostJson(ApiUrl.SPLASH_AD, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<SplashAdBean>() {
            @Override
            public void onCompleted() {
                mView.getAdComplete();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.getAdFailed(code, errorMSG);
            }

            @Override
            public void onSuccess(SplashAdBean response) {
                mView.getAdSuccess(response);
            }
        });
    }

    public void getTabList() {
        httpManager.executeGet(ApiUrl.GET_ALL_TABS, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onSuccess(String response) {
                mView.getTabListSuccess(response);
            }

            @Override
            public void onError(int code, String errorMSG) {

            }
        });
    }
}
