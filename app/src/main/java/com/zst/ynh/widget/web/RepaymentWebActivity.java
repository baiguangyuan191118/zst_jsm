package com.zst.ynh.widget.web;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
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
@Route(path = ArouterUtil.REPAYMENT_WEBVIEW)
public class RepaymentWebActivity extends BaseWebActivity {
    private boolean NOTSKIPMAGICBOX;
    /**
     * 设置Schemes白名单
     */
    private final String[] whiteList = {"taobao://", "alipayqr://", "alipays://", "wechat://", "weixin://", "mqq://", "mqqwpa://", "openApp.jdMobile://"};

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
                finish();
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
            //公信宝白名单处理--start
            for (String s : whiteList) {
                //判断url如果是在Schemes的白名单里，就启动对于的app，如果不是直接加载url
                if (url.startsWith(s)) {
                    try {
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        startActivity(intent);
                        return true;
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
            //公信宝白名单处理--end
            if (parseScheme(url)) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            }
            //支付宝的处理
            try {
                if (url.startsWith("alipays://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
            } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                return true;
            }
            if (url.contains("/recharge/pay?payId=1") ||
                    url.contains("https://mclient.alipay.com/cashier/mobilepay.htm")
                    || url.contains("https://openapi.alipay.com/gateway.do")) {
                return false;
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
            //处理支付中title是网址的bug
            if (title.contains("repayment-by-web") || title.contains("frontend/web")) {
                mTitleBar.setTitle("");
            } else {
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
        webView.addJavascriptInterface(new JavaMethod(), "nativeMethod");
    }


    public class JavaMethod {
        @JavascriptInterface
        public void callToMain() {
            ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED,"0").withBoolean(BundleKey.MAIN_FRESH,true).navigation();
        }
    }


    public boolean parseScheme(String url) {
        if (url.contains("platformapi/startApp")) {

            return true;
        } else {

            return false;
        }
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
