package com.zst.ynh.widget.person.certification.work;

import com.zst.ynh.bean.WorkInfoBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IWorkCertificationView extends IBaseView {
    void getWorkInfo(WorkInfoBean workInfoBean);
    void saveWorkInfo();
    void loadLoading();
    void LoadError();
    void loadContent();
}
