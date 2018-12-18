package com.zst.ynh.widget.web;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
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

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected void onRetry() {

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
