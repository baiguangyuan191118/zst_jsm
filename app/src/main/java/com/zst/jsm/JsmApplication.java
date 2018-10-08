package com.zst.jsm;

import android.content.Context;

import com.zst.jsm_base.BaseApplication;

public class JsmApplication extends BaseApplication {
    private static JsmApplication instance;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static JsmApplication getinstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }
}
