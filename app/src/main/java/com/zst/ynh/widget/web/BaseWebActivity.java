package com.zst.ynh.widget.web;

import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.zst.ynh.BuildConfig;
import com.zst.ynh.R;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.util.VersionUtil;

import butterknife.BindView;

public abstract class BaseWebActivity extends BaseActivity {

    @BindView(R.id.outer_layout)
    FrameLayout outerLayout;
    protected WebView webView;
    protected String titleStr;
    protected int tag;
    protected String url;
    protected String authMethod;
    protected boolean isSetSession;//是否需要设置sessionid 的cookie

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        webView=new WebView(this);
        outerLayout.removeAllViews();
        outerLayout.addView(webView);
        initWebView();
    }

    private void initWebView() {

        if(getIntent()!=null){
            initViews();
            if(isSetSession){
                synchronousWebCookies();
            }
        }

        WebSettings settings = webView.getSettings();
        settings.setTextZoom(100);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDatabaseEnabled(true);
        settings.setUserAgentString(settings.getUserAgentString() + "/" + BuildConfig.USER_AGENT + "/" + VersionUtil.getLocalVersion(this));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
//        webView.addJavascriptInterface(new JavaMethod(), "nativeMethod");
//        if (isInfoDomain(context, url) && !StringUtil.isBlank(authMethod)) {
//            webView.addJavascriptInterface(new AuthMethod(), authMethod);
//        }

        setWebClient();

        addJavaScriptInterface();

        webView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }

            if(isSetSession){
                removeCookie();
            }

            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
        }
    }

    private void synchronousWebCookies() {
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

    private void removeCookie(){
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null);
            cookieManager.flush();
        } else {
            cookieManager.removeSessionCookie();
            CookieSyncManager.getInstance().sync();
        }
    }

    protected abstract void initViews();
    protected abstract void setWebClient();
    protected abstract void addJavaScriptInterface();

}
