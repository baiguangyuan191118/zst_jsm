package com.zst.ynh.widget.person.certification.contact;

import com.zst.ynh.bean.ContactRelationBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class EmergencyContactPresent extends BasePresent<IEmergencyContactView> {
    /**
     *  保存联系人信息
     */
    public void saveContactData(String mobile,String name,String type,String relation_spare,String mobile_spare,String name_spare) {
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("mobile",mobile);
        map.put("name",name);
        map.put("type",type);
        map.put("relation_spare",relation_spare);
        map.put("mobile_spare",mobile_spare);
        map.put("name_spare",name_spare);
        httpManager.executePostString(ApiUrl.SAVE_CONTACTS, map, new HttpManager.ResponseCallBack<String>() {

            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(String response) {
                mView.saveSuccess();
            }
        });
    }
    /**
     *  获取联系人的关系
     */
    public void getContacts() {
        mView.showLoadingProgressView();
        final Map<String,String> map=BaseParams.getBaseParams();
        httpManager.executeGet(ApiUrl.GET_CONTACTS, map, new HttpManager.ResponseCallBack<ContactRelationBean>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.showErrorView();
            }
            @Override
            public void onSuccess(ContactRelationBean response) {
                mView.getContactRelation(response);
                mView.hideLoadingProgressView();
            }
        });
    }
    /**
     *  判断用户是否绑过卡(好像跟存管有关)
     */
    public void isBindBankCard() {
        mView.showLoadingProgressView();
        final Map<String,String> map=BaseParams.getBaseParams();
        httpManager.executeGet(ApiUrl.IS_OPEN_INFO, map, new HttpManager.ResponseCallBack<ContactRelationBean>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.showErrorView();
            }
            @Override
            public void onSuccess(ContactRelationBean response) {
                mView.getContactRelation(response);
                mView.hideLoadingProgressView();
            }
        });
    }
}
