package com.zst.ynh.umeng;

import android.content.Context;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.zst.ynh.BuildConfig;

public class InitUmeng {
    public static void init(Context context){
        UMConfigure.init(context,UMConfigure.DEVICE_TYPE_PHONE,"");
        //微信
        PlatformConfig.setWeixin(BuildConfig.SHARE_WEIXIN_KEY,BuildConfig.SHARE_WEIXIN_SECRET);
        //新浪微博
        //QQ
        PlatformConfig.setQQZone(BuildConfig.SHARE_QQ_ID, BuildConfig.SHARE_QQ_KEY);
    }
}
