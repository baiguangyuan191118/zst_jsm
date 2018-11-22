package com.zst.ynh.widget.person.certification.work;

import com.alibaba.fastjson.JSON;
import com.zst.ynh.bean.WorkInfoBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class WorkCertificationPresent extends BasePresent<IWorkCertificationView> {
    /**
     * 保存个人信息
     */
    public void saveWorkInfoData(String company_address, String company_address_distinct, String company_name, String company_phone
            , String company_payday, int company_period, String latitude, String longitude,int company_worktype) {
        mView.showLoading();
        Map<String, String> map = BaseParams.getBaseParams();
        if (company_worktype==1){
            map.put("company_address", company_address);
            map.put("company_address_distinct", company_address_distinct);
            map.put("company_name", company_name);
            map.put("company_phone", company_phone);
            map.put("company_payday", company_payday);
            map.put("company_period", company_period+"");
            map.put("latitude", latitude);
            map.put("longitude", longitude);
        }
        map.put("company_worktype", company_worktype+"");
        httpManager.executePostString(ApiUrl.SAVE_WORK_INFO, map, new HttpManager.ResponseCallBack<String>() {

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
                mView.saveWorkInfo();
            }
        });
    }

    /**
     * 获取人信息
     */
    public void getWorkInfoData() {
        mView.loadLoading();
        Map<String, String> map = BaseParams.getBaseParams();
        httpManager.executePostString(ApiUrl.GET_WORK_INFO, map, new HttpManager.ResponseCallBack<String>() {

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
                mView.getWorkInfo(JSON.parseObject(response,WorkInfoBean.class));
                mView.loadContent();
            }
        });
    }
}
