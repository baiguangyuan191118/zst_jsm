package com.zst.ynh.widget.person.certification.contact;

import com.zst.ynh.bean.ContactRelationBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IEmergencyContactView extends IBaseView {
    void saveSuccess();
    void getContactRelation(ContactRelationBean contactRelationBean);
    void showLoadingProgressView();
    void hideLoadingProgressView();
    void showErrorView();
}
