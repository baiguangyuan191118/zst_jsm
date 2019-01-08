package com.zst.ynh.widget.splash;

import com.zst.ynh.bean.TabListBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

public class SplashPresent extends BasePresent<SplashView> {
    public void getTabList(){
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
