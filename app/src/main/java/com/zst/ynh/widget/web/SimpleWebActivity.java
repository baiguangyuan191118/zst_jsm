package com.zst.ynh.widget.web;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.zst.ynh.JsmApplication;
import com.zst.ynh.R;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.Constant;
import com.zst.ynh.utils.StringUtil;
import com.zst.ynh_base.util.Layout;

import java.net.URISyntaxException;

@Layout(R.layout.activity_empty_layout)
@Route(path = ArouterUtil.SIMPLE_WEB)
public class SimpleWebActivity extends BaseWebActivity {
    private boolean NOTSKIPMAGICBOX;

    @Override
    protected void initViews() {

        if (getIntent() != null) {
            url = getIntent().getStringExtra(BundleKey.URL);
            isSetSession = getIntent().getBooleanExtra(BundleKey.WEB_SET_SESSION, false);
            NOTSKIPMAGICBOX = getIntent().getBooleanExtra(BundleKey.NOTSKIPMAGICBOX, false);
        }

        if (!StringUtils.isEmpty(titleStr)) {
            mTitleBar.setTitle(titleStr);
        }
        mTitleBar.setLeftImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleWebActivity.this.finish();
            }
        });

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

    WebViewClient myWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //判断是否要重新登录
            if (url.contains(Constant.TAG_JUMP_FROM_H5_LOGIN)) {
                JsmApplication.toLoginFromMain();
                return true;
            }
            //判断是否需要跳转到淘宝认证页面（处理从认证中心过来的页面不需要往下跳转）
            if (url.contains(Constant.TAG_JUMP_FROM_H5_JXL)) {
                if (NOTSKIPMAGICBOX) {
                    finish();
                } else {
                    ARouter.getInstance().build(ArouterUtil.MAGIC_BOX).navigation();
                    finish();
                }
                return true;
            }
            view.loadUrl(url);
            return true;
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (view.canGoBack()) {
                mTitleBar.setLeftImageVisible(View.VISIBLE);
            } else {
                mTitleBar.setLeftImageVisible(View.GONE);
            }
        }
    };

    WebChromeClient myWebChromeClient = new WebChromeClient() {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (StringUtils.isEmpty(titleStr)) {
                titleStr = title;
                mTitleBar.setTitle(title);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                //加载完毕让进度条消失
                progressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
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
            if(getIntent().getBooleanExtra(BundleKey.MAIN_FRESH,false)){//消息界面
                ARouter.getInstance().build(ArouterUtil.MAIN).withBoolean(BundleKey.MAIN_FRESH,true).withString(BundleKey.MAIN_SELECTED,"0").navigation();
            }
            finish();
        }
    }
}
