package com.zst.ynh.widget.person.loanrecord;

import com.alibaba.fastjson.JSON;
import com.zst.ynh.bean.LoanRecordBean;
import com.zst.ynh.bean.PaymentStyleBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class LoanRecordPresent extends BasePresent<ILoanRecordView> {

    public void getLoadRecordInfo(int page,int pageSize){
       // mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("page",page+"");
        map.put("pagsize",pageSize+"");
        httpManager.executePostJson(ApiUrl.LOAD_RECORD, map, new HttpManager.ResponseCallBack<LoanRecordBean>() {
            @Override
            public void onCompleted() {
               mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
               mView.getLoanRecordError(code,errorMSG);
            }

            @Override
            public void onSuccess(LoanRecordBean response) {
                mView.showLoanRecord(response);
            }
        });
    }

    public void getLoanDetail(String id){
        mView.showLoading();
        Map<String,String> map= BaseParams.getBaseParams();
        map.put("repaymentId",id);
        httpManager.executePostString(ApiUrl.GET_MY_ZST_LOAN, map, new HttpManager.ResponseCallBack<String>() {
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
                mView.getloanDetailsSuccess(JSON.parseObject(response,PaymentStyleBean.class));
            }
        });
    }

}
