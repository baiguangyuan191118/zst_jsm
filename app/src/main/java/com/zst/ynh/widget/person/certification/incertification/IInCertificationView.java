package com.zst.ynh.widget.person.certification.incertification;

import com.zst.ynh.bean.InCertificationBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IInCertificationView extends IBaseView {
    void getCertificationData(InCertificationBean inCertificationBean);
    void updateLimitSuccess();
    void showContentView();
    void showErrorView();
    void showLoadView();
}
