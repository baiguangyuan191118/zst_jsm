package com.zst.ynh.widget.tie;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
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
import com.zst.ynh.widget.web.BaseWebActivity;
import com.zst.ynh.widget.web.BaseWebFragment;
import com.zst.ynh_base.util.Layout;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 提额
 */
@Layout(R.layout.activity_empty_layout)
public class TieFragment extends BaseWebFragment {

    public static TieFragment newInstance() {
        TieFragment fragment = new TieFragment();
        return fragment;
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
            Intent intent=new Intent(TieFragment.this.getActivity(),BaseWebActivity.class);
            intent.putExtra("url",url);
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

    @Override
    public void onLazyLoad() {
        webView.loadUrl(url);
    }

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