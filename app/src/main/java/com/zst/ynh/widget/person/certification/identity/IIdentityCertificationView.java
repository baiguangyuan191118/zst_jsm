package com.zst.ynh.widget.person.certification.identity;

import com.zst.ynh.bean.IdCardInfoBean;
import com.zst.ynh.bean.PersonInfoBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IIdentityCertificationView extends IBaseView {
    void getPersonInfoData(PersonInfoBean personInfoBean);
    void loadLoading();
    void loadError();
    void loadContent();
    void onFailMessage(String message);
    void updatePicSuccess(int type);
    void getIdCardInfo(IdCardInfoBean idCardInfoBean);
    void saveIdCardDataSuccess();
    void savePersonFail(int code,String message);
}
