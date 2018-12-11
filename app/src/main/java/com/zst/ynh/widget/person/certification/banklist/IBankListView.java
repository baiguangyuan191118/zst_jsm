package com.zst.ynh.widget.person.certification.banklist;

import com.zst.ynh.bean.MyBankBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IBankListView extends IBaseView {
    void getBankListData(MyBankBean myBankBean);
    void loadContent();
    void loadError();
    void loadLoading();
    void getIsAddCard();
    void unbindCard();
    void setMasterCard();
    void sendSMSSuccess();
}
