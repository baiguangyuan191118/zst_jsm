package com.zst.ynh.widget.person.settings.paypwd.update;

import com.zst.ynh_base.mvp.view.IBaseView;

public interface IUpdatePayPwdView extends IBaseView {

    void updatePayPwdSuccess(String response);

    void updatePayPwdError(int code, String errorMSG);

    void setPayPwdError(int code, String errorMSG);

    void setPayPwdSuccess(String response);
}
