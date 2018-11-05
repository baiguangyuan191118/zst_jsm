package com.zst.ynh.widget.person.register.password;

import com.zst.ynh.bean.LoginBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IRegisterPwdView extends IBaseView {
    void registerSuccess(LoginBean loginBean);
    void sendSMSSuccess();
}
