package com.zst.ynh.widget.repayment.repaymentfragment;

import com.alibaba.fastjson.JSON;
import com.zst.ynh.bean.PaymentStyleBean;
import com.zst.ynh.bean.RepayInfoBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class RepaymentPresent extends BasePresent<IRepaymentView> {
    public void getRepayInfo(){
        mView.loadLoading();
        httpManager.get(ApiUrl.REPAYMENT_INFO, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.LoadError();
            }

            @Override
            public void onSuccess(String response) {
                mView.getRepayInfoSuccess(JSON.parseObject(response,RepayInfoBean.class));
                mView.loadContent();
            }
        });
    }

    /**
     * 获取支付方式 将数据传递给下个页面 为防止借款中页面显示异常的错误
     */
    public void getPaymentStyleData(String id){
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("repaymentId",id);
        httpManager.executePostString(ApiUrl.GET_MY_ZST_LOAN, map, new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.getPaymentStyleDataFail(errorMSG);
            }

            @Override
            public void onSuccess(String response) {
                mView.getPaymentStyleData(JSON.parseObject(response,PaymentStyleBean.class));
            }
        });
    }
}
