package com.zst.ynh.widget.repayment.repaymentfragment;

import com.alibaba.fastjson.JSON;
import com.zst.ynh.bean.HistoryOrderInfoBean;
import com.zst.ynh.bean.OtherPlatformRepayInfoBean;
import com.zst.ynh.bean.PaymentStyleBean;
import com.zst.ynh.bean.YnhRepayInfoBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class RepaymentPresent extends BasePresent<IRepaymentView> {

    /*
    * 由你花还款界面的信息
    * credit/web/credit-loan/get-pay-order
    * */
    public void getYnhRepaymentInfo(){
        httpManager.executeGet(ApiUrl.YNH_REPAYMENT_INFO, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<YnhRepayInfoBean>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
               mView.getYnhRepaymentError(code,errorMSG);
            }

            @Override
            public void onSuccess(YnhRepayInfoBean response) {
                mView.getYnhRepaymentSuccess(response);
            }
        });
    }

    /*
    * 其他平台还款信息
    * */
    public void getOtherRepaymentInfo(){

        httpManager.executeGet(ApiUrl.OTHER_REPAYMENT_INFO, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<OtherPlatformRepayInfoBean>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.getOtherRepaymentError(code,errorMSG);
            }

            @Override
            public void onSuccess(OtherPlatformRepayInfoBean response) {
                mView.getOtherRepaymentSuccess(response);
            }
        });

    }

    /*
    * 由你花的历史订单
    * */
    public void getYnhOrders(){
        httpManager.executeGet(ApiUrl.YNH_HISTORY_ORDERS, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<HistoryOrderInfoBean>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.getYnhOrdersError(code,errorMSG);
            }

            @Override
            public void onSuccess(HistoryOrderInfoBean response) {
                mView.getYnhOrdersSuccess(response);
            }
        });
    }

    /*
    * 其他平台的历史订单
    * */
    public void getOtherOrders(){
        httpManager.executeGet(ApiUrl.OTHER_HISTORY_ORDERS, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<HistoryOrderInfoBean>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.getOtherOrderError(code,errorMSG);
            }

            @Override
            public void onSuccess(HistoryOrderInfoBean response) {
                mView.getOtherOrdersSuccess(response);
            }
        });
    }


    /*
    * 还款详情
    * */
    public void getRepayDetail(String id,String platformCode){//详情
        mView.showLoading();
        Map<String,String> map= BaseParams.getBaseParams();
        map.put("repaymentId",id);
        map.put("platformCode",platformCode);
        httpManager.executePostJson(ApiUrl.GET_MY_ZST_LOAN, map, new HttpManager.ResponseCallBack<PaymentStyleBean>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(PaymentStyleBean response) {
                mView.getDetailsSuccess(response);
            }
        });
    }

}
