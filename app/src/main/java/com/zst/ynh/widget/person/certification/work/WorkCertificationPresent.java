package com.zst.ynh.widget.person.certification.work;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.zst.ynh.bean.WorkInfoBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.utils.StringUtil;
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
            if(!StringUtils.isEmpty(company_address)){
                map.put("company_address", company_address);
            }
            if(!StringUtils.isEmpty(company_address_distinct)){
                map.put("company_address_distinct", company_address_distinct);
            }
            if(!StringUtils.isEmpty(company_name)){
                map.put("company_name", company_name);
            }
            if(!StringUtils.isEmpty(company_phone)){
                map.put("company_phone", company_phone);
            }
            if(!StringUtils.isEmpty(company_payday)){
                map.put("company_payday", company_payday);
            }
            if(company_period!=0){
                map.put("company_period", company_period+"");
            }
            if(!StringUtils.isEmpty(latitude)){
                map.put("latitude", latitude);
            }
            if(!StringUtils.isEmpty(longitude)){
                map.put("longitude", longitude);
            }
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
        httpManager.executePostJson(ApiUrl.GET_WORK_INFO, map, new HttpManager.ResponseCallBack<WorkInfoBean>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.LoadError();
            }

            @Override
            public void onSuccess(WorkInfoBean response) {
                mView.getWorkInfo(response);
                mView.loadContent();
            }
        });
    }
}
