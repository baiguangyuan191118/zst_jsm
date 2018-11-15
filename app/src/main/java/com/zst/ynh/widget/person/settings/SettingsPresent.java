package com.zst.ynh.widget.person.settings;

import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

public class SettingsPresent extends BasePresent<ISettingsView> {

    void logout(){
        mView.showLoading();
        httpManager.get(ApiUrl.LOGOUT, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(String response) {
                mView.LogoutSuccess(response);
            }
        });
    }
}
