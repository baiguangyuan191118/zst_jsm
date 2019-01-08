package com.zst.ynh.widget.loan.Home;

import com.zst.ynh.bean.LoanBean;
import com.zst.ynh.bean.LoanConfirmBean;
import com.zst.ynh.bean.PopularLoanBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface ILoanView extends IBaseView{
    void getAppIndexData(LoanBean loanBean);
    void getAppIndexDataFailed(int code, String errorMSG);


    void getLoanConfirmData(LoanConfirmBean loanConfirmBean);
    void getLoanConfirmFail(int code,String errorMSG);


    void showProgressLoading();
    void hideProgressLoading();

    void getPopularLoanSuccess(PopularLoanBean popularLoanBean);
    void getPopularLoanFailed(int code,String errorMsg);

    void getMarketStatus(String response);
}
