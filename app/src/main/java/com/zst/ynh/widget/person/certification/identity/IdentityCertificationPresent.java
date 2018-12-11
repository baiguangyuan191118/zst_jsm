package com.zst.ynh.widget.person.certification.identity;

import android.net.Uri;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.zst.ynh.bean.IdCardInfoBean;
import com.zst.ynh.bean.PersonInfoBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.SPkey;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;
import com.zst.ynh_base.util.ProgressListener;
import com.zst.ynh_base.util.UploadImgUtil;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class IdentityCertificationPresent extends BasePresent<IIdentityCertificationView> {
    /**
     * 获取个人认证信息
     */
    public void getPersonInfo() {
        mView.loadLoading();
        httpManager.executeGet(ApiUrl.PERSON_INFO, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<PersonInfoBean>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.loadError();
            }

            @Override
            public void onSuccess(PersonInfoBean response) {
                mView.getPersonInfoData(response);
                mView.loadContent();
            }
        });
    }

    /**
     * 根据身份证图片获取信息
     */
    public void getIdInfoFromFace() {
        mView.showLoading();
        httpManager.executePostString(ApiUrl.FACE_PLUS_IDCARD, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.onFailMessage(errorMSG);
            }

            @Override
            public void onSuccess(String response) {
                mView.getIdCardInfo(JSON.parseObject(response,IdCardInfoBean.class));
            }
        });
    }

    /**
     * 上传图片
     */
    public void uploadPicture(String imgUrl, final int requestType) {
        mView.showLoading();
        UploadImgUtil.FileBean bean = new UploadImgUtil.FileBean();
        bean.addExtraParms("type", requestType + "");
        Uri uri = Uri.parse(imgUrl);
        bean.setFileSrc(uri.getPath());
        try {
            String sessionid = SPUtils.getInstance().getString(SPkey.USER_SESSIONID);
            UploadImgUtil.upLoadImg(ApiUrl.UPLOAD_IMAGE, sessionid, bean, new ProgressListener() {
                @Override
                public void onProgress(long currentBytes, long contentLength, boolean done) {

                }

                @Override
                public void onSuccess(Call call, Response response) {
                    mView.hideLoading();
                    mView.updatePicSuccess(requestType);
                }

                @Override
                public void onFailed(Call call, Exception exception) {
                    mView.ToastErrorMessage(exception.getMessage());
                    mView.hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存信息
     *
     * @param latitude
     * @param longitude
     * @param name
     * @param idNumber
     */
    public void savePersonData(String latitude, String longitude, String name, String idNumber) {
        mView.showLoading();
        Map<String, String> map = BaseParams.getBaseParams();
        map.put("name", name);
        map.put("id_number", idNumber);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        httpManager.executePostString(ApiUrl.SAVE_ID_CARD_INFO2, BaseParams.getBaseParams(), new HttpManager.ResponseCallBack<String>() {

            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.savePersonFail(code, errorMSG);
            }

            @Override
            public void onSuccess(String response) {
                mView.saveIdCardDataSuccess();
            }
        });
    }

}
