package com.zst.ynh.widget.main;

import com.zst.ynh.bean.UpdateVersionBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

public class MainPresent extends BasePresent<MainView> {

    public void getVersionInfo(){

        httpManager.executeGet(ApiUrl.UPDATE_VERSION, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<UpdateVersionBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onSuccess(UpdateVersionBean response) {
                mView.getVersionInfo(response);
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }
        });
    }

}
