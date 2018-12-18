package com.zst.ynh.widget.splash;

import com.zst.ynh.bean.TabListBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface SplashView extends IBaseView {
    void getTabListSuccess(TabListBean response);
}
