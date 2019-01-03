package com.zst.ynh.widget.tie;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
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

    WebViewClient myWebViewClient = new BaseWebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            WebView.HitTestResult hitTestResult = view.getHitTestResult();

            if (hitTestResult == null) {
                return false;
            }

            if (hitTestResult.getType() == WebView.HitTestResult.UNKNOWN_TYPE) {
                return false;
            }

            Intent intent=new Intent(TieFragment.this.getActivity(),SimpleWebActivity.class);
            intent.putExtra("url",url);
            startActivity(intent);
            return true;
        }

    };
    WebChromeClient myWebChromeClient = new BaseWebChromeClient() {

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