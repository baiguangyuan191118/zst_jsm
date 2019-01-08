package com.zst.ynh.widget.repayment.paystyle;

import com.zst.ynh.config.ApiUrl;
import com.zst.ynh_base.mvp.present.BasePresent;
import com.zst.ynh_base.net.BaseParams;
import com.zst.ynh_base.net.HttpManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class PayStylePresent extends BasePresent<IPayStyleView> {
    public void getPayUrl(final String repaymentId, String payType, String userCouponId,String platformCode) {
        mView.showLoading();
        Map<String, String> map = BaseParams.getBaseParams();
        map.put("repaymentId", repaymentId);
        map.put("payType", payType);
        if(userCouponId!=null){
            map.put("userCouponId", userCouponId);
        }
        map.put("platformCode",platformCode);
        httpManager.executePostString(ApiUrl.GET_PAY_URL, map, new HttpManager.ResponseCallBack<String>() {
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
                mView.hideLoading();
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    JSONObject data=jsonObject.getJSONObject("data");
                    String url=data.getString("url");
                    mView.getPayUrlSuccess(url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
