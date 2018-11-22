package com.zst.ynh.widget.web;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zst.ynh.R;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.utils.StringUtil;
import com.zst.ynh_base.util.Layout;

@Layout(R.layout.activity_empty_layout)
@Route(path=ArouterUtil.SIMPLE_WEB)
public class SimpleWebActivity extends BaseWebActivity {

    @Override
    protected void initViews() {

        if(getIntent()!=null){
            url=getIntent().getStringExtra(BundleKey.URL);
            isSetSession=getIntent().getBooleanExtra(BundleKey.WEB_SET_SESSION,false);
        }

        if (!StringUtil.isBlank(titleStr)) {
            mTitleBar.setTitle(titleStr);
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
    protected void addJavaScriptInterface() {

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }
}
