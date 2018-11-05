package com.zst.ynh.widget.person.certification.Identity;

import com.zst.ynh.bean.PersonInfoBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IIdentityCertificationView extends IBaseView {
    void getPersonInfoData(PersonInfoBean personInfoBean);
    void showLoadingProgressView();
    void hideLoadingProgressView();
}
