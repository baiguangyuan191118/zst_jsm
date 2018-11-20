package com.zst.ynh_base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;


public class BaseApplication extends MultiDexApplication {
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
