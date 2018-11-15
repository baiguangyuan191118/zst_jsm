package com.zst.ynh.widget.web;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.SPUtils;
import com.zst.ynh.R;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.StringUtil;
import com.zst.ynh_base.util.Layout;

/*
* 帮助中心
* */
@Route(path=ArouterUtil.HELPER_CENTER)
@Layout(R.layout.activity_empty_layout)
public class HelperCenterActivity extends BaseWebActivity {

    private String titleStr;

    @Override
    protected void getIntentData() {
        if (getIntent() != null) {
            titleStr=getIntent().getStringExtra(BundleKey.WEB_TITLE);
            if (!StringUtil.isBlank(titleStr)) {
                mTitleBar.setTitle(titleStr);
            }
            url = getIntent().getStringExtra(BundleKey.URL);

        }

        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void setWebClient() {
        webView.setWebViewClient(myWebViewClient);
        webView.setWebChromeClient(myWebChromeClient);
    }

    @Override
    protected void addJavaScriptInterface() {

    }


    WebViewClient myWebViewClient=new WebViewClient(){

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };

    WebChromeClient myWebChromeClient=new WebChromeClient(){

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if(StringUtil.isBlank(titleStr)){
                titleStr=title;
                mTitleBar.setTitle(title);
            }

        }
    };


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }
}
