package com.zst.ynh.widget.loan.Home;

import com.alibaba.fastjson.JSON;
import com.zst.ynh.bean.LoanBean;
import com.zst.ynh.bean.LoanConfirmBean;
import com.zst.ynh.bean.PopularLoanBean;
import com.zst.ynh.bean.TokenStatusBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class LoanPresent extends BasePresent<ILoanView> {
    /**
     * 首页详情
     */
    public void getIndexData() {

        httpManager.get(ApiUrl.APP_INDEX, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {

            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code,String errorMSG) {
                mView.getAppIndexDataFailed(code,errorMSG);
            }

            @Override
            public void onSuccess(String response) {
                mView.getAppIndexData(JSON.parseObject(response,LoanBean.class));
            }
        });

    }

    public void getPopularLoanData(){
        httpManager.get(ApiUrl.APP_POPULAR_LOAN, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.getPopularLoanFailed(code,errorMSG);
            }

            @Override
            public void onSuccess(String response) {
                mView.getPopularLoanSuccess(JSON.parseObject(response,PopularLoanBean.class));
            }
        });
    }

    public void getMarketSatus(){
        httpManager.get(ApiUrl.APP_MARKET_STATUS, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(int code, String errorMSG) {

            }

            @Override
            public void onSuccess(String response) {
                mView.getMarketStatus(response);
            }
        });
    }

    /**
     * 贷款申请确认接口
     */
    public void loanConfirm(String money,String period,String cardType){
        mView.showProgressLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("money",money);
        map.put("period",period);
        map.put("card_type",cardType);
        httpManager.executePostJson(ApiUrl.LOAN_CONFIRM, map, new HttpManager.ResponseCallBack<LoanConfirmBean>() {

            @Override
            public void onCompleted() {
                mView.hideProgressLoading();
            }

            @Override
            public void onError(int code,String errorMSG) {
                mView.getLoanConfirmFail(code,errorMSG);
            }

            @Override
            public void onSuccess(LoanConfirmBean response) {
                mView.getLoanConfirmData(response);
            }
        });
    }

    public void getTokenStatus(String platcode){
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("to_platform_code",platcode);
        httpManager.executePostString(ApiUrl.GET_TOKEN_STATUS, map, new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onSuccess(String response) {
                mView.getTokenStatusSuccess(response);
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.getTokenStatusFailed(code,errorMSG);
            }
        });
    }
}
