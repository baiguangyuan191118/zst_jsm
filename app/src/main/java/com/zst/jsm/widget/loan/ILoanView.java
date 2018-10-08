package com.zst.jsm.widget.loan;

import com.zst.jsm.bean.LoanBean;
import com.zst.jsm_base.mvp.view.IBaseView;

public interface ILoanView extends IBaseView{
    void getAppIndexData(LoanBean loanBean);
}
