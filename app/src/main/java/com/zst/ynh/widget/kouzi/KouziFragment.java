package com.zst.ynh.widget.kouzi;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zst.ynh.R;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.widget.web.BaseWebFragment;
import com.zst.ynh.widget.web.SimpleWebActivity;
import com.zst.ynh_base.util.Layout;

import org.json.JSONException;
import org.json.JSONObject;

/*
* 口子
* */
@Layout(R.layout.activity_empty_layout)
public class KouziFragment extends BaseWebFragment {

    public static KouziFragment newInstance() {
        KouziFragment fragment = new KouziFragment();
        return fragment;
    }

    @Override
    public void onLazyLoad() {
        webView.loadUrl(url);
    }

    @Override
    protected void onRetry() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setWebClient() {
        webView.setWebViewClient(myWebViewClient);
        webView.setWebChromeClient(myWebChromeClient);
    }

    @Override
    protected void addJavaScriptInterface() {
        webView.addJavascriptInterface(new JavaMethod(), "nativeMethod");
    }

    WebViewClient myWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            WebView.HitTestResult hitTestResult = view.getHitTestResult();

            if (hitTestResult == null) {
                return false;
            }
            Log.d("kouzi", "type:" + hitTestResult.getType());
            if (hitTestResult.getType() == WebView.HitTestResult.UNKNOWN_TYPE) {
                return false;
            }
            Intent intent = new Intent(KouziFragment.this.getActivity(), SimpleWebActivity.class);
            intent.putExtra(BundleKey.URL, url);
            startActivity(intent);
            return true;
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };
    WebChromeClient myWebChromeClient = new WebChromeClient() {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
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

    public class JavaMethod {

        @JavascriptInterface
        public void returnNativeMethod(String typeStr) {
            Log.d("webview",typeStr);
            try {
                JSONObject object=new JSONObject(typeStr);
                String type=object.getString("type");
                if ("3".equals(type)) {
                    ARouter.getInstance().build(ArouterUtil.CERTIFICATION_CENTER).navigation();
                } else if ("4".equals(type)) {
                    ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED,BundleKey.MAIN_LOAN).navigation();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
