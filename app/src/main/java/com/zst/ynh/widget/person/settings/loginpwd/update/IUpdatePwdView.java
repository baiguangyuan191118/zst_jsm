package com.zst.ynh.widget.person.settings.loginpwd.update;

import com.zst.ynh_base.mvp.view.IBaseView;

public interface IUpdatePwdView extends IBaseView {
    void updateLoginPwdSuccess(String response);

    void updateLoginPwdError(int code, String errorMSG);
}
