package com.zst.ynh.widget.person.certification.bindbank;


import com.zst.ynh.bean.BankBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IBindBankCardView extends IBaseView {
    void sendSMSSuccess();

    void loadContent();

    void loadLoading();

    void loadError();

    void getBankListData(BankBean response);

    void addBankCardSuccess();
}
