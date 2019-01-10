package com.zst.ynh.widget.person.settings.loginpwd.forget;

import com.zst.ynh_base.mvp.view.IBaseView;

public interface IForgetPwdView extends IBaseView {
    void sendSMSSuccess(boolean isRealVerifyStatus);
    void findPwdSuccess();
}
