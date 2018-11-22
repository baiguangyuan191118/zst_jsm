package com.zst.ynh.widget.repayment;

import com.zst.ynh.bean.RepayInfoBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IRepaymentView extends IBaseView {
    void getRepayInfoSuccess(RepayInfoBean repayInfoBean);
    void getRepayInfoFailed(int code, String errorMSG);
}
