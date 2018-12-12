package com.zst.ynh.widget.person.settings.paypwd.forget;

import com.zst.ynh.bean.ForgetPwdCodeBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IForgetPayPwdView extends IBaseView {
    void sendSMSSuccess();
    void sendSMSFail();
    void findPwdSuccess();
}
