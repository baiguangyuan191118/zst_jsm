package com.zst.ynh_base.net;

import android.util.ArrayMap;
import android.util.Log;

import com.blankj.utilcode.util.DeviceUtils;
import com.zst.ynh_base.BaseApplication;
import com.zst.ynh_base.util.VersionUtil;

import java.util.Map;

public class BaseParams {

    public static Map getBaseParams() {
        Map<String, String> map = new ArrayMap<>();
        map.clear();
        map.put("clientType", "android");
        map.put("appVersion", VersionUtil.getLocalVersion(BaseApplication.getContext()) + "");
        map.put("deviceId", DeviceUtils.getAndroidID());
        map.put("deviceName", DeviceUtils.getModel());
        map.put("osVersion", DeviceUtils.getSDKVersionName());
        map.put("appMarket", "jsm");//渠道名
        return map;
    }
}
