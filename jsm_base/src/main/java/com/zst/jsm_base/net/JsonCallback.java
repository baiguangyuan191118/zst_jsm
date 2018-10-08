package com.zst.jsm_base.net;

import android.app.Activity;
import android.content.Context;

import com.zst.jsm_base.util.Utils;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;


/**
 * Created by kevin on 16/12/30.
 */

public class JsonCallback<T> extends ApiCallback<ResponseBody> {


    private Type type;
    private HttpManager.ResponseCallBack<T> callBack;
    private Context context;
    private HttpManager.OnGlobalInterceptor onGlobalInterceptor;


    public JsonCallback(HttpManager.ResponseCallBack<T> callBack, HttpManager.OnGlobalInterceptor onGlobalInterceptor, Context context) {

        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (Utils.MethodHandler(types) == null || Utils.MethodHandler(types).size() == 0) {
            onFailure("获取实体类型失败");
            return;
        }
        type = Utils.MethodHandler(types).get(0);
        this.context = context;
        this.callBack = callBack;
        this.onGlobalInterceptor = onGlobalInterceptor;

    }

    @Override
    public void onSuccess(ResponseBody model) {
        try {
            if (model == null) {
                onError(new RuntimeException("服务器返回的数据格式异常"));
                onFinish();
                return;
            } else {
                String str = model.string();
                BaseResponseData result = Utils.parseObject(str, BaseResponseData.class);
                if (result != null) {

                    //重新登录的拦截
                    if (result.getCode().equals(config.HTTP_ERROR_NEED_LOGIN)){
                        if (onGlobalInterceptor != null) {
                            onGlobalInterceptor.onInterceptor();
                            onFinish();
                            if (context != null && context instanceof Activity) {
                                ((Activity) context).finish();
                            }
                            return;
                        }
                    }

                    if (!"0".equals(result.getCode())) {
                        this.callBack.onError(result.getMessage());
                        onFinish();
                        return;
                    }
                    String data = result.getData();
                    if (type == Utils.MethodHandler(new String().getClass().getGenericInterfaces()).get(0)) {
                        this.callBack.onSuccess((T) data);
                        onFinish();
                        return;
                    }

                    this.callBack.onSuccess(
                            (T) Utils.parseObject(result.getData(), type));
                    onFinish();
                } else {
                    onError(new RuntimeException("服务器返回的数据格式异常"));
                    onFinish();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
            onError(e);
            onFinish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        callBack.onError(e.getMessage());
    }

    @Override
    public void onFailure(String msg) {
        callBack.onError(msg);
    }

    @Override
    public void onFinish() {
        callBack.onCompleted();
    }
}
