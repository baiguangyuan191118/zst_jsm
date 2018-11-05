package com.zst.ynh_base.mvp.present;


import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zst.ynh_base.net.HttpManager;

public abstract class BasePresent<T> {
    public T mView;
    protected HttpManager httpManager;

    public void attach(T mView) {
        this.mView = mView;
        this.httpManager = new HttpManager();
        if (mView instanceof AppCompatActivity) {
            this.httpManager.setContext((Context) mView);
        } else if (mView instanceof View) {
            this.httpManager.setContext(((View) mView).getContext());
        } else if (mView instanceof Dialog) {
            this.httpManager.setContext(((Dialog) mView).getContext());
        } else if (mView instanceof Fragment) {
            this.httpManager.setContext(((Fragment) mView).getActivity());
        }
    }

    public void detach() {
        this.httpManager.onUnsubscribe();
        this.mView = null;
    }

    public HttpManager gethttpManager() {
        return this.httpManager;
    }
}
