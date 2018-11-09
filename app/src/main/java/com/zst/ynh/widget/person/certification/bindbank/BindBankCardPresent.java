package com.zst.ynh.widget.person.certification.bindbank;

import com.alibaba.fastjson.JSON;
import com.zst.ynh.bean.BankBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.Constant;
import com.zst.ynh.utils.MD5Util;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;
import java.util.Map;

public class BindBankCardPresent extends BasePresent<IBindBankCardView> {
    /**
     * 发送验证码
     * @param phoneNumber
     */
    public void sendBankSMS(final String phoneNumber) {
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
                httpManager.executePostString(ApiUrl.GET_BANK_CODE, map, new HttpManager.ResponseCallBack<String>() {
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
                        mView.sendSMSSuccess();
                    }
                });
            }
        });
    }

    /**
     * 获取银行卡列表
     */
    public void getBankList(){
        mView.loadLoading();
        httpManager.executePostJson(ApiUrl.GET_BANK_CODE, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<BankBean>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.loadError();
            }
            @Override
            public void onSuccess(BankBean response) {
                mView.getBankListData(response);
                mView.loadError();
            }
        });
    }
    /**
     * 添加银行卡
     */
    public void addBankCard(){
        mView.showLoading();
        httpManager.executePostString(ApiUrl.ADD_BANK_CARD, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {
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
                mView.addBankCardSuccess();
            }
        });
    }
}
