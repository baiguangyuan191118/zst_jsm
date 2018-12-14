package com.zst.ynh_base.net.download;

import android.content.Context;

import rx.Subscriber;


public abstract class BaseSubscriber<T> extends Subscriber<T> {

    protected Context context;

    public BaseSubscriber(Context context) {
        this.context = context;
    }



    public BaseSubscriber() {
    }

    @Override
     public void onError(Throwable e) {
        if (e instanceof Throwable) {
            onError((Throwable) e);
        } else {
            onError(new Throwable(e));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {
    }

}
