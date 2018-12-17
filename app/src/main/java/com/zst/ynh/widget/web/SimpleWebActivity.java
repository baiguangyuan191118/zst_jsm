package com.zst.ynh.widget.web;

import android.net.http.SslError;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.zst.ynh.JsmApplication;
import com.zst.ynh.R;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.Constant;
import com.zst.ynh.config.SPkey;
import com.zst.ynh_base.util.Layout;


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
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                //加载完毕让进度条消失
                progressBar.setVisibility(View.INVISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }
    };

    @Override
    protected void addJavaScriptInterface() {
        webView.addJavascriptInterface(new JavaMethod(), "nativeMethod");
    }


    public class JavaMethod {
        @JavascriptInterface
        //支付成功后跳转到首页
        public void callToMain() {
            ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED,BundleKey.MAIN_LOAN).withBoolean(BundleKey.MAIN_FRESH,true).navigation();
        }
        @JavascriptInterface
        public void returnNativeMethod(String typeStr) {
            //此方法用于导流的处理
            if ("3".equals(typeStr)) {
                //认证中心
                if (TextUtils.isEmpty(SPUtils.getInstance().getString(SPkey.USER_SESSIONID))) {
                    ARouter.getInstance().build(ArouterUtil.LOGIN).navigation();
                    return;
                }
                ARouter.getInstance().build(ArouterUtil.CERTIFICATION_CENTER).navigation();
            } else if ("4".equals(typeStr)) {
                //首页
                ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED,BundleKey.MAIN_LOAN).withBoolean(BundleKey.MAIN_FRESH,true).navigation();
            }
        }
    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if(getIntent().getBooleanExtra(BundleKey.MAIN_FRESH,false)){//消息界面
                ARouter.getInstance().build(ArouterUtil.MAIN).withBoolean(BundleKey.MAIN_FRESH,true).withString(BundleKey.MAIN_SELECTED,BundleKey.MAIN_LOAN).navigation();
            }
            finish();
        }
    }
}
