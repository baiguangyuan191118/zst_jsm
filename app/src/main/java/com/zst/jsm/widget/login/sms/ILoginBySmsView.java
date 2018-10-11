package com.zst.jsm.widget.login.sms;

import com.zst.jsm.bean.LoginBean;
import com.zst.jsm_base.mvp.view.IBaseView;

public interface ILoginBySmsView extends IBaseView {
    void getLoginData(LoginBean loginBean);
}
