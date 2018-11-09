package com.zst.ynh.widget.person.certification.mohe;

import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class MagicBoxPresent extends BasePresent<IMagicBoxView> {
    /**
     * 获取个人认证信息
     */
    public void saveMagicBox(String taskId,String userId) {
        mView.showLoading();
        Map<String,String> map=BaseParams.getBaseParams();
        map.put("taskId",taskId);
        map.put("userId",userId);
        httpManager.executePostString(ApiUrl.SAVE_MOHE, map, new HttpManager.ResponseCallBack<String>() {

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
                mView.skipToMain();
            }
        });
    }
}
