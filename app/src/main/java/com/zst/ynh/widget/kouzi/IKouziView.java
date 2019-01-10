package com.zst.ynh.widget.kouzi;

import com.zst.ynh_base.mvp.view.IBaseView;

public interface IKouziView extends IBaseView {
    void isSuperLoan(String response);
    void isSuperLoanFailed(int code,String errorMSG);
}
