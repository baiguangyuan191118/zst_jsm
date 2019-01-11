package com.zst.ynh.config;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

public class UMClickEvent {
    private volatile static UMClickEvent instance;

    private UMClickEvent() {
    }

    public static UMClickEvent getInstance() {
        if (instance == null) {
            synchronized (UMClickEvent.class) {
                if (instance == null) {
                    instance = new UMClickEvent();
                }
            }
        }
        return instance;
    }
    public static void OnClickEvent(Context context,String eventId, String key, String v){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(key, v);
        MobclickAgent.onEvent(context, eventId, map);
    }
    public void onClick(Context context,String eventId, String v){
        MobclickAgent.onEvent(context, eventId, v);
    }
}
