package com.zst.ynh.widget.loan.applysuccess;

import com.blankj.utilcode.util.StringUtils;
import com.zst.ynh.bean.ApplySuccessBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import java.util.Map;

public class ApplySuccessPresent extends BasePresent<IApplySuccessView> {
    public void getLoanSuccessInfo(int order, String platfrom) {
        mView.showLoading();
        Map<String, String> map = BaseParams.getBaseParams();
        map.put("order_id", order + "");
        if (!StringUtils.isEmpty(platfrom)) {
            map.put("platform_code", platfrom);
        }
        httpManager.executePostJson(ApiUrl.APPLY_LOAN_SUCCESS_INFO, map, new HttpManager.ResponseCallBack<ApplySuccessBean>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onSuccess(ApplySuccessBean response) {
                mView.getApplySuccess(response);
            }

            @Override
            public void onError(int code, String errorMSG) {
                mView.getApplyFailed(code, errorMSG);
            }
        });
    }
}
