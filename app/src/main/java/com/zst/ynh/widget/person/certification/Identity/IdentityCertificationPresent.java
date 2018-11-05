package com.zst.ynh.widget.person.certification.Identity;

import android.net.Uri;

import com.zst.ynh.bean.PersonInfoBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class IdentityCertificationPresent extends BasePresent<IIdentityCertificationView> {
    /**
     * 获取个人认证信息
     */
    public void getPersonInfo() {
        mView.showLoading();
        httpManager.executeGet(ApiUrl.PERSON_INFO, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<PersonInfoBean>() {

            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
            }

            @Override
            public void onSuccess(PersonInfoBean response) {
                mView.getPersonInfoData(response);
            }
        });
    }

    //上传图片1
    public void uploadPictureFace(Uri uri) {
        mView.showLoading();
        httpManager.upload(ApiUrl.FACE_PLUS_IDCARD, uri, new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {

            }

            @Override
            public void onSuccess(String response) {

            }
        });
    }

    public void uploadPicture(Uri uri) {
        mView.showLoadingProgressView();
        httpManager.upload(ApiUrl.UPLOAD_IMAGE, uri, new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {
                mView.hideLoadingProgressView();
            }

            @Override
            public void onError(int code, String errorMSG) {

            }

            @Override
            public void onSuccess(String response) {

            }
        });
    }

    /**
     * 保存信息 从认证中心的过来的就不要传名字 身份证号了
     *
     * @param status
     * @param latitude
     * @param longitude
     * @param name
     * @param idNumber
     */
    public void saveMessage(int status, String latitude, String longitude, String name, String idNumber) {
        mView.showLoading();
        Map<String, String> map = BaseParams.getBaseParams();
        if (status != 1) {
            map.put("name", name);
            map.put("id_number", idNumber);
        }
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        httpManager.executePostString(ApiUrl.PERSON_INFO, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {

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

            }
        });
    }
}
