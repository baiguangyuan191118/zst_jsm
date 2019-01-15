package com.zst.ynh.widget.splash;

import com.zst.ynh.bean.SplashAdBean;
import com.zst.ynh.bean.TabListBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface SplashView extends IBaseView {
    void getTabListSuccess(String response);
    void getAdSuccess(SplashAdBean response);
    void getAdFailed(int code, String errorMSG);
    void getAdComplete();
}
