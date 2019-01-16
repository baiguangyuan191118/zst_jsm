package com.zst.ynh.widget.person.login.sms;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.EncryptUtils;
import com.zst.ynh.bean.LoginBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.Constant;
import com.zst.ynh.utils.MD5Util;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class LoginBySmsPresent extends BasePresent<ILoginBySmsView> {
    public void toLoginBySMS(String phoneNumber, String code) {
        mView.showLoading();
        Map<String, String> map = BaseParams.getBaseParams();
        map.put("username", phoneNumber);
        map.put("code", code);
        httpManager.executePostJson(ApiUrl.LOGIN_BY_SMS, map, new HttpManager.ResponseCallBack<LoginBean>() {

            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(LoginBean response) {
                mView.getLoginData(response);
            }
        });
    }

    /**
     * 嵌套请求 回头改了
     *
     * @param phoneNumber
     */
    public void sendSMS(final String phoneNumber) {
        Map<String, String> map = BaseParams.getBaseParams();
        httpManager.get(ApiUrl.GET_RANDOM, map, new HttpManager.ResponseCallBack<String>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(String response) {
                String random = (String) JSON.parseObject(response).getJSONObject("data").get("random");
                Map<String, String> map = BaseParams.getBaseParams();
                map.put("phone", phoneNumber);
                map.put("random", random);
                map.put("sign", MD5Util.getMD5String(phoneNumber + random + Constant.GETCODE_KEY));
                httpManager.executePostString(ApiUrl.GET_CODE, map, new HttpManager.ResponseCallBack<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(int code, String errorMSG) {
                        mView.ToastErrorMessage(errorMSG);
                        mView.sendSMSFail();
                    }

                    @Override
                    public void onSuccess(String response) {
                        mView.sendSMSSuccess();
                    }
                });
            }
        });
    }
}
