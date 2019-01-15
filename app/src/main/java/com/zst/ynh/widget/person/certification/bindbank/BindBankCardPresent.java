package com.zst.ynh.widget.person.certification.bindbank;

import com.alibaba.fastjson.JSON;
import com.zst.ynh.bean.BankBean;
import com.zst.ynh.bean.DepositOpenInfoVBean;
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
    public void sendBankSMS(final String phoneNumber,final String bank_id) {
        mView.showLoading();
        httpManager.get(ApiUrl.GET_RANDOM, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {

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
                map.put("phone", phoneNumber);
                map.put("bank_id", bank_id);
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
        httpManager.executePostJson(ApiUrl.GET_BANK_LIST, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<BankBean>() {
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
                mView.loadContent();
            }
        });
    }
    /**
     * 添加银行卡
     */
    public void addBankCard(String bankId,String cardNum,String phone,String code){
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("bank_id",bankId);
        map.put("card_no",cardNum);
        map.put("phone",phone);
        map.put("code",code);
        httpManager.executePostString(ApiUrl.ADD_BANK_CARD, map, new HttpManager.ResponseCallBack<String>() {
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
    /**
     * 更换银行卡
     */
    public void changeBankCard(String bankId,String cardNum,String phone,String code){
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("bank_id",bankId);
        map.put("card_no",cardNum);
        map.put("phone",phone);
        map.put("code",code);
        httpManager.executePostString(ApiUrl.CHANGE_CARD, map, new HttpManager.ResponseCallBack<String>() {
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
