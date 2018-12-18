package com.zst.ynh.widget.main;

import com.zst.ynh.bean.UpdateVersionBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface MainView extends IBaseView {

    public void getVersionInfo(UpdateVersionBean updateVersionBean);
}
