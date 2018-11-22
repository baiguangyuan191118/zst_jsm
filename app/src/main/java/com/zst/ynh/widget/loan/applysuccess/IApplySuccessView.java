package com.zst.ynh.widget.loan.applysuccess;

import com.zst.ynh.bean.ApplySuccessBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IApplySuccessView extends IBaseView {

    void getApplySuccess(ApplySuccessBean response);
    void getApplyFailed(int code, String errorMSG);

}
