package com.zst.ynh.widget.person.register.phone;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.EncryptUtils;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.Constant;
import com.zst.ynh.utils.MD5Util;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class RegisterPhonePresent extends BasePresent<IRegisterPhoneView> {
    public void sendSMS(final String phoneNumber){
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("phone",phoneNumber);
        httpManager.get(ApiUrl.GET_RANDOM, map, new HttpManager.ResponseCallBack<String>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(int code,String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(String response) {
                String random= (String) JSON.parseObject(response).getJSONObject("data").get("random");
                Map<String,String> map=BaseParams.getBaseParams();
                map.put("random",random);
                map.put("sign",MD5Util.getMD5String(phoneNumber+random+Constant.GETCODE_KEY));
                httpManager.executePostString(ApiUrl.REGISTER_BY_SMS,map, new HttpManager.ResponseCallBack<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(int code,String errorMSG) {
                        if (code==1000||code==2000){//已经是老用户直接去登录
                            mView.showDialog();
                        }else {
                            mView.ToastErrorMessage(errorMSG);
                        }
                    }

                    @Override
                    public void onSuccess(String response) {
                        mView.skipNextPWD();
                    }
                });
            }
        });
    }
}
