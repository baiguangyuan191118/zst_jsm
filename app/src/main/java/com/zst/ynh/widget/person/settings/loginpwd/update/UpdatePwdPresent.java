package com.zst.ynh.widget.person.settings.loginpwd.update;

import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class UpdatePwdPresent extends BasePresent<IUpdatePwdView> {

    public void updateLoginPassword(String old_pwd,String new_pwd){
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("old_pwd",old_pwd);
        map.put("new_pwd",new_pwd);
        httpManager.executePostString(ApiUrl.UPDATE_LOGIN_PWD, map, new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onSuccess(String response) {
                mView.updateLoginPwdSuccess(response);
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.updateLoginPwdError(code,errorMSG);
            }
        });
    }
}
