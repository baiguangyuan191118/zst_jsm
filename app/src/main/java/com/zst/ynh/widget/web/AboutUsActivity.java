package com.zst.ynh.widget.web;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zst.ynh.R;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.utils.StringUtil;
import com.zst.ynh_base.util.Layout;

@Layout(R.layout.activity_empty_layout)
@Route(path=ArouterUtil.ABOUT_US)
public class AboutUsActivity extends BaseWebActivity {


    @Override
    protected void getIntentData() {
        url=getIntent().getStringExtra(BundleKey.URL);
        isSetSession=true;
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
}
