package com.zst.ynh.widget.web;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.SPUtils;
import com.zst.ynh.BuildConfig;
import com.zst.ynh.R;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.SPkey;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.VersionUtil;

import butterknife.BindView;

public abstract class BaseWebActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    protected String titleStr;
    protected String url;
    protected boolean isSetSession;//是否需要设置sessionid 的cookie

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        initWebView();
    }

    private void initWebView() {

        if(getIntent()!=null){
            initViews();
            if(isSetSession){
                synchronousWebCookies();
            }
        }
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        WebSettings settings = webView.getSettings();
        settings.setTextZoom(100);
        settings.setAllowFileAccess(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDatabaseEnabled(true);
        settings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setUserAgentString(settings.getUserAgentString() + "/" + BuildConfig.USER_AGENT + "/" + VersionUtil.getLocalVersion(this));

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
