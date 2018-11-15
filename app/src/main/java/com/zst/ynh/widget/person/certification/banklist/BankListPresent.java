package com.zst.ynh.widget.person.certification.banklist;

import com.zst.ynh.bean.InCertificationBean;
import com.zst.ynh.bean.MyBankBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

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
}
