package com.zst.ynh_base.net;

import android.app.Activity;
import android.content.Context;

import org.json.JSONObject;

import okhttp3.ResponseBody;

/**
 * Created by kevin on 17/1/3.
 */

public class StringCallback extends ApiCallback<ResponseBody> {

    private HttpManager.ResponseCallBack<String> callBack;
    private HttpManager.OnGlobalInterceptor onGlobalInterceptor;
    private Context context;
    private int code=-100;//自己设置一个错误代码 只是区分 没有实质的意义


    public StringCallback(Context context, HttpManager.ResponseCallBack<String> callBack
            , HttpManager.OnGlobalInterceptor onGlobalInterceptor) {
        this.callBack = callBack;
        this.onGlobalInterceptor = onGlobalInterceptor;
        this.context = context;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onSuccess(ResponseBody model) {
        try {

            String str = model.string();
            JSONObject jsonObject = new JSONObject(str);

            //重新登录的拦截
            if (jsonObject.get("code").equals(config.HTTP_ERROR_NEED_LOGIN)){
                if (onGlobalInterceptor != null) {
                    onGlobalInterceptor.onInterceptor();
                    onFinish();
                    if (context != null && context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                    return;
                }
            }
            if (!"0".equals(jsonObject.getString("code"))) {
                this.callBack.onError((int)jsonObject.get("code"),jsonObject.get("message")+"");
                onFinish();
                return;
            }

            callBack.onSuccess(str);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onError(code,e.getMessage());
        }

    }

    @Override
    public void onFailure(String msg) {
        callBack.onError(code,msg);

    }

    @Override
    public void onFinish() {
        callBack.onCompleted();

    }
}
