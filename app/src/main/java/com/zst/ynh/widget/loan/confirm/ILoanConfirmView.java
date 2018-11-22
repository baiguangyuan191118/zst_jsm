package com.zst.ynh.widget.loan.confirm;

import com.zst.ynh.bean.ApplyLoanBean;
import com.zst.ynh.bean.DepositOpenInfoVBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface ILoanConfirmView extends IBaseView {
    void getDepositOpenInfo(DepositOpenInfoVBean depositOpenInfoVBean);
    void applyLoanSuccess(ApplyLoanBean response);
    void applyLoanFailed(int code, String errorMSG);
}
