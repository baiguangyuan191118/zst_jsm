package com.zst.ynh.widget.person.login.resetpwd;

import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class ResetPwdPresent extends BasePresent<IRestPwdView> {
    public void resetPWD(String phoneNumber,String code,String newPwd){
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("phone",phoneNumber);
        map.put("code",code);
        map.put("password",newPwd);
        httpManager.executePostString(ApiUrl.RESET_PWD, map, new HttpManager.ResponseCallBack<String>() {

            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code,String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(String response) {
                mView.resetPwdSuccess();
            }
        });
    }
}
