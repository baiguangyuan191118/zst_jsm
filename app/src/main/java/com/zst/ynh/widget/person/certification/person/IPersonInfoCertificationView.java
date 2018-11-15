package com.zst.ynh.widget.person.certification.person;

import com.zst.ynh.bean.LimitPersonInfoBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IPersonInfoCertificationView extends IBaseView {
    void savePersonInfoDataSuccess();
    void getPersonInfoData(LimitPersonInfoBean limitPersonInfoBean);
    void loadLoading();
    void LoadError();
    void loadContent();
}
