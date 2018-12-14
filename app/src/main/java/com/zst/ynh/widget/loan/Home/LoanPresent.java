package com.zst.ynh.widget.loan.Home;

import com.alibaba.fastjson.JSON;
import com.zst.ynh.bean.LoanBean;
import com.zst.ynh.bean.LoanConfirmBean;
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
        mView.showLoading();
        httpManager.get(ApiUrl.APP_INDEX, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {

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
                mView.getAppIndexData(JSON.parseObject(response,LoanBean.class));
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
}
