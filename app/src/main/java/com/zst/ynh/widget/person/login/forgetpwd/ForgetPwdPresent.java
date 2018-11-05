package com.zst.ynh.widget.person.login.forgetpwd;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.EncryptUtils;
import com.zst.ynh.bean.ForgetPwdCodeBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.Constant;
import com.zst.ynh.utils.MD5Util;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class ForgetPwdPresent extends BasePresent<IForgetPwdView> {
    public void sendSMS(final String phoneNumber){
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("phone",phoneNumber);
        httpManager.get(ApiUrl.GET_RANDOM, map, new HttpManager.ResponseCallBack<String>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(int code,String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.hideLoading();
            }

            @Override
            public void onSuccess(String response) {
                String random= (String) JSON.parseObject(response).getJSONObject("data").get("random");
                Map<String,String> map=BaseParams.getBaseParams();
                map.put("random",random);
                map.put("sign",MD5Util.getMD5String(phoneNumber+random+Constant.GETCODE_KEY));
                httpManager.executePostJson(ApiUrl.FORGET_BY_SMS,map, new HttpManager.ResponseCallBack<ForgetPwdCodeBean>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(int code,String errorMSG) {
                        mView.ToastErrorMessage(errorMSG);
                    }

                    @Override
                    public void onSuccess(ForgetPwdCodeBean response) {
                        mView.sendSMSSuccess(response.real_verify_status);
                    }
                });
            }
        });
    }
    public void findPWD(final String phoneNumber,String code,boolean isRealVerifyStatus ,String realName,String idCard){
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("phone",phoneNumber);
        map.put("code",code);
        map.put("type","find_pwd");
        if (isRealVerifyStatus){
            map.put("realname",realName);
            map.put("id_card",idCard);
        }
        httpManager.executePostString(ApiUrl.FORGET_PWD, map, new HttpManager.ResponseCallBack<String>() {

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
                mView.findPwdSuccess();
            }
        });
    }

}
