package com.zst.ynh.widget.loan.confirm;

import com.zst.ynh.bean.ApplyLoanBean;
import com.zst.ynh.bean.DepositOpenInfoVBean;
import com.zst.ynh.bean.LoanConfirmBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;


public class LoanConfirmPresent extends BasePresent<ILoanConfirmView> {
    /**
     * 存管的口子（目前没用到 先写上）
     */
    public void loanConfirm() {
        mView.showLoading();
        httpManager.executeGet(ApiUrl.DEPOSIT_OPEN, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<DepositOpenInfoVBean>() {

            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(DepositOpenInfoVBean response) {
                mView.getDepositOpenInfo(response);
            }
        });
    }


    public void applyLoan(LoanConfirmBean.ItemBean bean,String password,String loanuseValue) {
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("period",bean.period+"");
        map.put("protocol_msg",bean.protocol_msg);
        map.put("real_pay_pwd_status",bean.real_pay_pwd_status+"");
        map.put("loan_use_value",loanuseValue);
        map.put("verify_loan_pass",bean.verify_loan_pass+"");
        map.put("card_type","0");
        map.put("card_no",bean.card_no);
        map.put("extra_tips",bean.extra_tips);
        map.put("hide_counter_fee","0");
        map.put("money",bean.money);
        map.put("protocol_url",bean.protocol_url);
        map.put("pay_password",password);
        map.put("bank_name",bean.bank_name);
        map.put("repayment_way_value",bean.repayment_way.value+"");

        httpManager.executePostJson(ApiUrl.APPLY_LOAN, map, new HttpManager.ResponseCallBack<ApplyLoanBean>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.applyLoanFailed(code,errorMSG);
            }

            @Override
            public void onSuccess(ApplyLoanBean response) {
                mView.applyLoanSuccess(response);
            }
        });
    }


}
