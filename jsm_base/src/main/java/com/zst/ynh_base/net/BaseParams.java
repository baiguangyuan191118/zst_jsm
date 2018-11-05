package com.zst.ynh_base.net;

import android.util.ArrayMap;

import com.blankj.utilcode.util.DeviceUtils;
import com.zst.ynh_base.BaseApplication;
import com.zst.ynh_base.util.VersionUtil;

import java.util.Map;

public class BaseParams {
    private static Map<String,String> map;
    public static Map getBaseParams(){
        if (map==null){
            map=new ArrayMap<>();
        }
        map.put("clientType","android");
        map.put("appVersion",VersionUtil.getLocalVersion(BaseApplication.getContext())+"");
        map.put("deviceId",DeviceUtils.getAndroidID());
        map.put("deviceName",DeviceUtils.getModel());
        map.put("osVersion",DeviceUtils.getSDKVersionName());
        map.put("appMarket","");//渠道名
        return map;
    }
}
