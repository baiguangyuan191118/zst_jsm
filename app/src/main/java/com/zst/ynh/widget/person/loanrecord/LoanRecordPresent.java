package com.zst.ynh.widget.person.loanrecord;

import com.zst.ynh.bean.LoanDetailBean;
import com.zst.ynh.bean.LoanRecordBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class LoanRecordPresent extends BasePresent<ILoanRecordView> {

    public void getLoadRecordInfo(int page,int pageSize){
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        httpManager.executeGet(ApiUrl.LOAD_RECORD, map, new HttpManager.ResponseCallBack<LoanRecordBean>() {
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
        httpManager.executeGet(ApiUrl.GET_MY_ZST_LOAN, map, new HttpManager.ResponseCallBack<LoanDetailBean>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(LoanDetailBean response) {
                mView.getloanDetailsSuccess(response);
            }
        });
    }

}