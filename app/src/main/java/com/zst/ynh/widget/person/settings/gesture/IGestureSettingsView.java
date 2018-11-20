package com.zst.ynh.widget.person.settings.gesture;

import com.zst.ynh.bean.GestureLockInfo;
import com.zst.ynh.widget.person.settings.ISettingsView;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IGestureSettingsView extends ISettingsView {

    void getGestureLockInfoSuccess(GestureLockInfo response);

    void getGestureLockInfoError(int code, String errorMSG);

}
