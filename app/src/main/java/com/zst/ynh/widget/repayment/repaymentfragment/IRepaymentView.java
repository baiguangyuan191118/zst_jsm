package com.zst.ynh.widget.repayment.repaymentfragment;

import com.zst.ynh.bean.PaymentStyleBean;
import com.zst.ynh.bean.RepayInfoBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IRepaymentView extends IBaseView {
    void loadRefresh();
    void hideRefresh();
    void getRepayInfoSuccess(RepayInfoBean repayInfoBean);
    void getPaymentStyleData(PaymentStyleBean paymentStyleBean);
    void getPaymentStyleDataFail(String errorMsg);
}
