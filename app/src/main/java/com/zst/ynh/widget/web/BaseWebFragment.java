package com.zst.ynh.widget.web;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.zst.ynh.BuildConfig;
import com.zst.ynh.R;
import com.zst.ynh.utils.WebViewUtils;
import com.zst.ynh_base.mvp.view.BaseLazyFragment;
import com.zst.ynh_base.util.VersionUtil;

import butterknife.BindView;

public abstract class BaseWebFragment extends BaseLazyFragment {


    @BindView(R.id.webview)
    protected WebView webView;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    protected String titleStr;
    protected String url;
    protected boolean isLoadFailed;

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected void onRetry() {
        webView.reload();
        isLoadFailed=false;
    }

    @Override
    protected void initView() {
        initViews();

        WebViewUtils.synchronousWebCookies();
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
        settings.setUserAgentString(settings.getUserAgentString() + "/" + BuildConfig.USER_AGENT + "/" + VersionUtil.getLocalVersion(this.getActivity()));

        setWebClient();

        addJavaScriptInterface();
    }

   protected class BaseWebViewClient extends WebViewClient{
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return;
            }
            isLoadFailed=true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setProgress(0);
            progressBar.setVisibility(View.VISIBLE);
        }

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (request.isForMainFrame()) { // 或者： if(request.getUrl().toString() .equals(getUrl()))
                // 在这里显示自定义错误页
                isLoadFailed=true;
            }

        }

       @TargetApi(Build.VERSION_CODES.M)
       @Override
       public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
           super.onReceivedHttpError(view, request, errorResponse);
           if(request.isForMainFrame()){
               isLoadFailed=true;
           }
       }

       @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(isLoadFailed){
                loadErrorView();
            }else{
                loadContentView();

            }
        }
    }


    protected class BaseWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                //加载完毕让进度条消失
                progressBar.setVisibility(View.INVISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            WebViewUtils.removeCookie();
            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
        }
    }

    protected abstract void initViews();
    protected abstract void setWebClient();
    protected abstract void addJavaScriptInterface();
}
