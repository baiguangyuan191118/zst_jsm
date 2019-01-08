package com.zst.ynh.widget.person.mine;

import com.zst.ynh.bean.DepositOpenInfoVBean;
import com.zst.ynh.bean.MineBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IPersonView extends IBaseView {

    void showPersonData(MineBean mineBean);

    void getPersonDataFailed(int code,String errorMSG);

    void getDepositeOpenInfo(DepositOpenInfoVBean depositOpenInfoVBean);

    void showProgressLoading();

    void hideProgressLoading();

}
