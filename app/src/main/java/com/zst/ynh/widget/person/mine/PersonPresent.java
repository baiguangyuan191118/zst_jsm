package com.zst.ynh.widget.person.mine;

import com.zst.ynh.bean.DepositOpenInfoVBean;
import com.zst.ynh.bean.MineBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

public class PersonPresent extends BasePresent<IPersonView> {

    public void getPersonData(){

        httpManager.executeGet(ApiUrl.PERSON_FRAGMENT, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<MineBean>() {


            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.getPersonDataFailed(code,errorMSG);
            }

            @Override
            public void onSuccess(MineBean response) {
                mView.showPersonData(response);
            }
        });

    }

    public void getDepositeOpenInfo(){
        mView.showProgressLoading();
        httpManager.executeGet(ApiUrl.DEPOSIT_OPEN, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<DepositOpenInfoVBean>() {
            @Override
            public void onCompleted() {
                mView.hideProgressLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(DepositOpenInfoVBean response) {
                mView.getDepositeOpenInfo(response);
            }
        });
    }

}
