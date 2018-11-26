package com.zst.ynh.widget.person.loanrecord;

import com.zst.ynh.bean.LoanRecordBean;
import com.zst.ynh.bean.PaymentStyleBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface ILoanRecordView extends IBaseView {

    void showLoanRecord(LoanRecordBean loanRecordBean);

    void getLoanRecordError(int code, String errorMSG);

    void getloanDetailsSuccess(PaymentStyleBean response);

}
