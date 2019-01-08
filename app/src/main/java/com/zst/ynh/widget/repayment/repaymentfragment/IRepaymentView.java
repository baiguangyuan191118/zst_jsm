package com.zst.ynh.widget.repayment.repaymentfragment;

import com.zst.ynh.bean.OtherPlatformRepayInfoBean;
import com.zst.ynh.bean.PaymentStyleBean;
import com.zst.ynh.bean.HistoryOrderInfoBean;
import com.zst.ynh.bean.YnhRepayInfoBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IRepaymentView extends IBaseView {

    void getYnhRepaymentSuccess(YnhRepayInfoBean response);
    void getYnhRepaymentError(int code, String errorMSG);


    void getOtherRepaymentSuccess(OtherPlatformRepayInfoBean otherPlatformRepayInfoBean);
    void getOtherRepaymentError(int code,String errorMsg);

    void getYnhOrdersSuccess(HistoryOrderInfoBean historyOrderInfoBean);
    void getYnhOrdersError(int code,String errorMsg);

    void getOtherOrdersSuccess(HistoryOrderInfoBean historyOrderInfoBean);
    void getOtherOrderError(int code,String errorMsg);

    void getDetailsSuccess(PaymentStyleBean response);
}
