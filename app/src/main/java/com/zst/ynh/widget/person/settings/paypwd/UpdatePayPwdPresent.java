package com.zst.ynh.widget.person.settings.paypwd;

import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class UpdatePayPwdPresent extends BasePresent<IUpdatePayPwdView> {

    public void updateTradePwdPresent(String oldpwd,String pwd){
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("old_pwd",oldpwd);
        map.put("new_pwd",pwd);
        httpManager.executePostString(ApiUrl.UPDATE_TRADE_PWD,map, new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.updatePayPwdError(code,errorMSG);
            }

            @Override
            public void onSuccess(String response) {
                mView.updatePayPwdSuccess(response);
            }
        });
    }

    public void setPayPwd(String pwd){
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("password",pwd);
        httpManager.executePostString(ApiUrl.SET_PAY_PASSWORD, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.setPayPwdError(code,errorMSG);
            }

            @Override
            public void onSuccess(String response) {
                mView.setPayPwdSuccess(response);
            }
        });
    }

    public void resetPayPwd(String phone,String code,String pwd){
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("password",pwd);
        map.put("phone",phone);
        map.put("code",code);
        httpManager.executePostString(ApiUrl.RESET_PAY_PASSWORD, map, new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.setPayPwdError(code,errorMSG);
            }

            @Override
            public void onSuccess(String response) {
                mView.setPayPwdSuccess(response);
            }
        });
    }
}
