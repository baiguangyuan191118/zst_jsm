package com.zst.ynh_base;

import android.app.Application;
import android.content.Context;


public class BaseApplication extends Application {
    private static BaseApplication instance;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        context = getApplicationContext();
    }

    public static BaseApplication getinstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }

}
