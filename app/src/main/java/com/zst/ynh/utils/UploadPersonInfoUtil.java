package com.zst.ynh.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.zst.ynh.config.SPkey;
import com.zst.ynh_base.BaseApplication;
import com.zst.ynh_base.net.BaseResponseData;
import com.zst.ynh_base.util.VersionUtil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadPersonInfoUtil {
    private static final OkHttpClient client = new OkHttpClient.Builder()
            //设置超时，不设置可能会报异常
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build();

    public static void uploadPersonInfo(String url, List<?> data, int type, final IUploadCallBack iUploadCallBack){
        RequestBody formBody = new FormBody.Builder()
                .add("clientType", "android").
                        add("data", JSON.toJSONString(data)).
                        add("type", "" + type).
                        add("appVersion", VersionUtil.getLocalVersion(BaseApplication.getContext()) + "").
                        add("deviceId", DeviceUtils.getAndroidID()).
                        add("deviceName", DeviceUtils.getModel()).
                        add("osVersion", DeviceUtils.getSDKVersionName()).
                        add("appMarket", "").build();//渠道名
        Request request = new Request.Builder()
                .addHeader("Accept-Encoding", "gzip")
                .addHeader("Cookie", "SESSIONID=" + SPUtils.getInstance().getString(SPkey.USER_SESSIONID))
                .url(url)
                .post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iUploadCallBack.onFail(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                BaseResponseData baseResponseData = JSON.parseObject(result, BaseResponseData.class);
                if (baseResponseData != null && "0".equals(baseResponseData.getCode())){
                    iUploadCallBack.onSuccess();
                }else {
                    iUploadCallBack.onFail(baseResponseData.getMessage());
                }
                iUploadCallBack.onComplete();
            }
        });
    }
    public interface IUploadCallBack{
        void onSuccess();
        void onFail(String message);
        void onComplete();
    }
}
