package com.zst.ynh.widget.person.settings.gesture;

import com.zst.ynh.bean.GestureLockInfo;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.widget.person.settings.SettingsPresent;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

public class GestureSettingPresent extends BasePresent<IGestureSettingsView> {

    public void getGestureSettingInfo(){
        mView.showLoading();
        httpManager.executeGet(ApiUrl.GESTURE_LOCK_INFO, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<GestureLockInfo>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onSuccess(GestureLockInfo response) {
                mView.getGestureLockInfoSuccess(response);
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.getGestureLockInfoError(code,errorMSG);
            }
        });
    }
}
