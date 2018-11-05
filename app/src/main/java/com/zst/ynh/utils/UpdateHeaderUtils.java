package com.zst.ynh.utils;

import com.zst.ynh_base.net.HttpManager;

import java.util.HashMap;
import java.util.Map;

public class UpdateHeaderUtils {
    public static void updateHeader(String sessionId){
        Map<String,String> headMap=new HashMap<>();
        headMap.put("Cookie", "SESSIONID=" + sessionId);
        HttpManager.setHeader(headMap);
    }
}
