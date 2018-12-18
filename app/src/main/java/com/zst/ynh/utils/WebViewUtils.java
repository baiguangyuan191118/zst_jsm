package com.zst.ynh.utils;

import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.blankj.utilcode.util.SPUtils;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.SPkey;

public class WebViewUtils {
    public static void synchronousWebCookies() {
        String cookies = "SESSIONID=" + SPUtils.getInstance().getString(SPkey.USER_SESSIONID);
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null);
            cookieManager.flush();
        } else {
            cookieManager.removeSessionCookie();
            CookieSyncManager.getInstance().sync();
        }
        //cookieManager.removeAllCookie();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(ApiUrl.BASE_URL, cookies);
    }

    public static void removeCookie(){
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null);
            cookieManager.flush();
        } else {
            cookieManager.removeSessionCookie();
            CookieSyncManager.getInstance().sync();
        }
    }
}
