package com.zst.ynh.config;

public class Constant {
    public static final String GETCODE_KEY = "jsqb#666*5";
    //五部认证是否需要显示下一步
    public static volatile boolean isStep;
    //聚信立的网址
    public static volatile String targetUrl;

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
