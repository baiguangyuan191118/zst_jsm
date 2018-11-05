package com.zst.ynh.widget.person.login.pwd;

import com.zst.ynh.bean.LoginBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface ILoginByPwdView extends IBaseView {
    void toLoginByPwd(LoginBean loginBean);
    void skipForgetPWD(String response);
}
