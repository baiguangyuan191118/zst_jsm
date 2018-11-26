package com.zst.ynh.config;

public class Constant {
    public static final String GETCODE_KEY = "jsqb#666*5";
    //数据魔盒code
    public static final String PARTNER_CODE = "zx_mohe  ";
    //数据魔盒key
    public static final String PARTNER_KEY = "759abb6d367846eb9dbfa90f98e6f714";
    //五部认证是否需要显示下一步
    public static volatile boolean isStep;
    //聚信立的网址
    public static volatile String targetUrl;
    //去登陆
    public static final String TAG_JUMP_FROM_H5_LOGIN = "app.launch/login/applogin";
    //判断是否是从聚信立验证过去的，是否要跳转到下一个淘宝认证的页面
    public static final String TAG_JUMP_FROM_H5_JXL = "/frontend/web/credit-qsh/redirect-url";

    public static String getTargetUrl() {
        return targetUrl;
    }

    public static void setTargetUrl(String targetUrl) {
        Constant.targetUrl = targetUrl;
    }

    public static boolean isIsStep() {
        return isStep;
    }

    public static void setIsStep(boolean isStep) {
        Constant.isStep = isStep;
    }
}
