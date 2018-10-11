package com.zst.jsm.widget.web;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zst.jsm.BuildConfig;
import com.zst.jsm.R;
import com.zst.jsm_base.mvp.view.BaseActivity;
import com.zst.jsm_base.util.Layout;
import com.zst.jsm_base.util.VersionUtil;

import butterknife.BindView;

@Route(path = "/web/baseWebActivity")
@Layout(R.layout.activity_empty_layout)
public class BaseWebActivity extends BaseActivity {
    @BindView(R.id.outer_layout)
    FrameLayout outerLayout;
    private WebView webView;
    private String ua;

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
        ua = settings.getUserAgentString();
        settings.setUserAgentString(settings.getUserAgentString() + "/" + BuildConfig.USER_AGENT + "/" + VersionUtil.getLocalVersion(this));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
//        webView.addJavascriptInterface(new JavaMethod(), "nativeMethod");
//        if (isInfoDomain(context, url) && !StringUtil.isBlank(authMethod)) {
//            webView.addJavascriptInterface(new AuthMethod(), authMethod);
//        }
    }

    }
