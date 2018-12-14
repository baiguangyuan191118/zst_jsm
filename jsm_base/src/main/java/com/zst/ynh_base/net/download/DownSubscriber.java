package com.zst.ynh_base.net.download;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.zst.ynh_base.util.Utils;


/**
 * Created by kevin on 17/10/16.
 */

public class DownSubscriber<ResponseBody extends okhttp3.ResponseBody> extends BaseSubscriber<ResponseBody> {
    private DownLoadCallBack callBack;
    private Context context;
    private String path;
    private String name;
    private String key;

    public DownSubscriber(String key, String path, String name, DownLoadCallBack callBack, Context context) {
        super(context);
        this.key = key;
        this.path = path;
        this.name = name;
        this.callBack = callBack;
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (callBack != null) {
           /* if (TextUtils.isEmpty(key)) {
                key = FileUtil.generateFileKey(path, name);
            }*/
            callBack.onStart(key);
        }
    }

    @Override
    public void onCompleted() {
        if (callBack != null) {
            callBack.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (callBack != null) {
            final Throwable throwable = new Throwable(e);
            if (Utils.checkMain()) {
                callBack.onError(throwable);
            } else {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(throwable);
                    }
                });
            }
        }
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        new NovateDownLoadManager(callBack).writeResponseBodyToDisk(key, path, name, context, responseBody);

    }
}

