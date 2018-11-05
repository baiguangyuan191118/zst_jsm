package com.zst.ynh.widget.person.login.pwd;

import com.alibaba.fastjson.JSON;
import com.zst.ynh.bean.LoginBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.Constant;
import com.zst.ynh.utils.MD5Util;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class LoginByPwdPresent extends BasePresent<ILoginByPwdView> {
    public void toLoginByPWD(String phoneNumber,String pwd){
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("username",phoneNumber);
        map.put("password",pwd);
        httpManager.executePostJson(ApiUrl.LOGIN_BY_PWD, map, new HttpManager.ResponseCallBack<LoginBean>() {

            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code,String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(LoginBean response) {
                mView.toLoginByPwd(response);
            }
        });
    }
    /**
     * 发送验证码
     * @param phoneNumber
     */
    public void sendForgetSMS(final String phoneNumber) {
        mView.showLoading();
        Map<String, String> map = BaseParams.getBaseParams();
        map.put("phone", phoneNumber);
        map.put("type", "find_pwd");
        httpManager.get(ApiUrl.GET_RANDOM, map, new HttpManager.ResponseCallBack<String>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.hideLoading();
            }

            @Override
            public void onSuccess(String response) {
                String random = (String) JSON.parseObject(response).getJSONObject("data").get("random");
                Map<String, String> map = BaseParams.getBaseParams();
                map.put("random", random);
                map.put("sign", MD5Util.getMD5String(phoneNumber + random + Constant.GETCODE_KEY));
                httpManager.executePostString(ApiUrl.FORGET_BY_SMS, map, new HttpManager.ResponseCallBack<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(int code, String errorMSG) {
                        mView.ToastErrorMessage(errorMSG);
                        mView.hideLoading();
                    }

                    @Override
                    public void onSuccess(String response) {
                        mView.hideLoading();
                        mView.skipForgetPWD(response);
                    }
                });
            }
        });
    }
}
