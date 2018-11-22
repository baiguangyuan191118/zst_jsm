package com.zst.ynh.widget.person.certification.person;

import com.alibaba.fastjson.JSON;
import com.zst.ynh.bean.LimitPersonInfoBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class PersonInfoCertificationPresent extends BasePresent<IPersonInfoCertificationView> {
    /**
     * 保存个人信息
     */
    public void savePersonInfoData(String address,String address_distinct,String degrees,String live_time_type,String marriage) {
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("address",address);
        map.put("address_distinct",address_distinct);
        map.put("degrees",degrees);
        map.put("live_time_type",live_time_type);
        map.put("marriage",marriage);
        httpManager.executePostString(ApiUrl.SAVE_PERSON_ADDITON_INFO, map, new HttpManager.ResponseCallBack<String>() {

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
                mView.savePersonInfoDataSuccess();
            }
        });
    }
    /**
     * 获取人信息
     */
    public void getPersonInfoData() {
        mView.loadLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        httpManager.executePostString(ApiUrl.GET_PERSON_ADDITON_INFO, map, new HttpManager.ResponseCallBack<String>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.LoadError();
            }

            @Override
            public void onSuccess(String response) {
                mView.getPersonInfoData(JSON.parseObject(response,LimitPersonInfoBean.class));
                mView.loadContent();
            }
        });
    }
}
