package com.zst.ynh.widget.person.loanrecord;

import com.zst.ynh.bean.LoanDetailBean;
import com.zst.ynh.bean.LoanRecordBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface ILoanRecordView extends IBaseView {

    void showLoanRecord(LoanRecordBean loanRecordBean);

    void getLoanRecordError(int code, String errorMSG);

    void getLoanDetailsError(int code, String errorMSG);

    void getloanDetailsSuccess(LoanDetailBean response);

}
