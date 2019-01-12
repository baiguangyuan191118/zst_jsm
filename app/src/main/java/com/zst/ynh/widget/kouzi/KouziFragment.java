package com.zst.ynh.widget.kouzi;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
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
public class KouziFragment extends BaseWebFragment implements IKouziView {

    private static final String tag=KouziFragment.class.getSimpleName();
    public static KouziFragment newInstance() {
        KouziFragment fragment = new KouziFragment();
        return fragment;
    }

    private boolean isFisrtLoad=true;
    private boolean isFromLazyLoad=false;
    private KouziPresent kouziPresent;

    @Override
    public void onLazyLoad() {
        showLoadingView();
        isFromLazyLoad=true;
        if(isFisrtLoad){
            Log.d(tag,"onLazyLoad:"+url);
            webView.loadUrl(url);
            isFisrtLoad=false;
        }else{
            kouziPresent.isSuperLoan();
        }

    }

    @Override
    protected void initViews() {

        kouziPresent=new KouziPresent();
        kouziPresent.attach(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(kouziPresent!=null){
            kouziPresent.detach();
        }
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
            Log.d(tag, "shouldOverrideUrlLoading:type:" + hitTestResult.getType()+";url:"+url);
            if (hitTestResult.getType() == WebView.HitTestResult.UNKNOWN_TYPE) {
                return false;
            }
            if(isFromLazyLoad){
                isFromLazyLoad=false;
                return false;
            }
            Intent intent = new Intent(KouziFragment.this.getActivity(), SimpleWebActivity.class);
            intent.putExtra(BundleKey.URL, url);
            startActivity(intent);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            isFromLazyLoad=false;
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            isFromLazyLoad=false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            isFromLazyLoad=false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            isFromLazyLoad=false;
        }
    };
    WebChromeClient myWebChromeClient = new BaseWebChromeClient() {

    };

    @Override
    public void isSuperLoan(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONObject data=jsonObject.getJSONObject("data");
            String issuperloan=data.getString("is_super_loan");
            if(!StringUtils.isEmpty(issuperloan) && issuperloan.equals("1")){
               String kzurl=data.getString("kzurl");
               if(!StringUtils.isEmpty(kzurl)){
                   url=kzurl;
                   webView.loadUrl(kzurl);
               }else{
                   webView.loadUrl(url);
               }
            }else{
                webView.loadUrl(url);
            }
            Log.d(tag,"isSuperLoan:"+url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void isSuperLoanFailed(int code, String errorMSG) {
        webView.loadUrl(url);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void ToastErrorMessage(String msg) {

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
