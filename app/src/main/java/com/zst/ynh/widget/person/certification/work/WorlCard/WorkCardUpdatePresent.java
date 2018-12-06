package com.zst.ynh.widget.person.certification.work.WorlCard;

import com.alibaba.fastjson.JSON;
import com.zst.ynh.bean.PicItemBean;
import com.zst.ynh.bean.WorkInfoBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class WorkCardUpdatePresent extends BasePresent<IWorkCardUpdateView> {
    /**
     * 获取图片列表
     */
    public void getPicList() {
        mView.loadLoading();
        Map<String, String> map = BaseParams.getBaseParams();
        httpManager.executePostJson(ApiUrl.GET_WORK_INFO, map, new HttpManager.ResponseCallBack<PicItemBean>() {

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
