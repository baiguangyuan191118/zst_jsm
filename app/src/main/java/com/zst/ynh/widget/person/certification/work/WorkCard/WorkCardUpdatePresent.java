package com.zst.ynh.widget.person.certification.work.WorkCard;

import android.util.Log;

import com.zst.ynh.bean.PicItemBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class WorkCardUpdatePresent extends BasePresent<IWorkCardUpdateView> {
    /**
     * 获取图片列表
     */
    public void getPicList(String type) {
        mView.loadLoading();
        Map<String, String> map = BaseParams.getBaseParams();
        map.put("type",type);
        httpManager.executePostJson(ApiUrl.GET_WORK_PIC_LIST, map, new HttpManager.ResponseCallBack<PicItemBean>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.ToastErrorMessage(errorMSG);
                mView.loadError();
            }

            @Override
            public void onSuccess(PicItemBean response) {
                mView.getPicList(response);
                mView.loadContent();
            }
        });
    }
}
