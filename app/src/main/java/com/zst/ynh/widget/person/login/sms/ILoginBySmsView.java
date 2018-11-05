package com.zst.ynh.widget.person.login.sms;

import com.zst.ynh.bean.LoginBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface ILoginBySmsView extends IBaseView {
    void getLoginData(LoginBean loginBean);
    void sendSMSSuccess();
    void sendSMSFail();
}
