package com.zst.ynh.widget.person.certification.banklist;

import com.alibaba.fastjson.JSON;
import com.zst.ynh.bean.DepositOpenInfoVBean;
import com.zst.ynh.bean.InCertificationBean;
import com.zst.ynh.bean.MyBankBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.Constant;
import com.zst.ynh.utils.MD5Util;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class BankListPresent extends BasePresent<IBankListView> {
    /**
     * 获取银行卡列表
     */
    public void getBankList() {
        mView.loadLoading();
        httpManager.executePostJson(ApiUrl.CARD_LIST, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<MyBankBean>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.loadError();
            }

            @Override
            public void onSuccess(MyBankBean response) {
                mView.getBankListData(response);
                mView.loadContent();
            }
        });
    }
    /**
     * 检查是否可以绑卡
     */
    public void isAddCard() {
        mView.showLoading();
        httpManager.executePostString(ApiUrl.CHANGE_CARD_CHECK, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {

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
                mView.getIsAddCard();
            }
        });
    }

    /**
     * 设主卡的时候先获取验证码 在进行接口设置
     * @param phoneNumber
     * @param bankId
     */
    public void sendSMS(final String phoneNumber,final String bankId){
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
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
                map.put("phone",phoneNumber);
                map.put("bank_id", bankId);
                httpManager.executePostString(ApiUrl.GET_BANK_CODE,map, new HttpManager.ResponseCallBack<String>() {
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
                        mView.sendSMSSuccess();
                    }
                });
            }
        });
    }
    /**
     * 解绑银行卡
     */
    public void unbindCard(String cardId) {
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("card_id",cardId);
        httpManager.executePostString(ApiUrl.UNBIND_CARD,map , new HttpManager.ResponseCallBack<String>() {

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
                mView.unbindCard();
            }
        });
    }
    /**
     * 设为主卡
     */
    public void setMasterCard(String cardId,String code) {
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("card_id",cardId);
        map.put("code",code);
        httpManager.executePostString(ApiUrl.BIND_MASTER_CARD,map , new HttpManager.ResponseCallBack<String>() {

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
                mView.setMasterCard();
            }
        });
    }

}
