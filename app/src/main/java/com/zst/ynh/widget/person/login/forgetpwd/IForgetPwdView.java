package com.zst.ynh.widget.person.login.forgetpwd;

import com.zst.ynh_base.mvp.view.IBaseView;

public interface IForgetPwdView extends IBaseView {
    void sendSMSSuccess(boolean isRealVerifyStatus);
    void findPwdSuccess();
}
