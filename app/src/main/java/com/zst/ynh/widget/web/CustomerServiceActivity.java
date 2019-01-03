package com.zst.ynh.widget.web;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zst.ynh.R;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh_base.util.Layout;

/*
咨询客服
* */
@Route(path = ArouterUtil.CUSTOMER_SERVICE)
@Layout(R.layout.activity_empty_layout)
public class CustomerServiceActivity extends BaseWebActivity {

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessage5;
    public static final int FILECHOOSER_RESULTCODE = 5173;
    public static final int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 5174;

    @Override
    protected void initViews() {

        url = getIntent().getStringExtra(BundleKey.URL);
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

    WebViewClient myWebViewClient = new BaseWebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    WebChromeClient myWebChromeClient = new BaseWebChromeClient() {

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            this.openFileChooser(uploadMsg, "*/*");
        }

        // For Android >= 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType) {
            this.openFileChooser(uploadMsg, acceptType, null);
        }

        // For Android >= 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(Intent.createChooser(i, "File Browser"),
                    FILECHOOSER_RESULTCODE);
        }

        // For Lollipop 5.0+ Devices
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView mWebView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         WebChromeClient.FileChooserParams fileChooserParams) {
            if (mUploadMessage5 != null) {
                mUploadMessage5.onReceiveValue(null);
                mUploadMessage5 = null;
            }
            mUploadMessage5 = filePathCallback;
            Intent intent = fileChooserParams.createIntent();
            try {
                startActivityForResult(intent,
                        FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
            } catch (ActivityNotFoundException e) {
                mUploadMessage5 = null;
                return false;
            }
            return true;
        }
    };


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) {
                return;
            }
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null
                    : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessage5) {
                return;
            }
            mUploadMessage5.onReceiveValue(WebChromeClient.FileChooserParams
                    .parseResult(resultCode, data));
            mUploadMessage5 = null;
        }
    }


    @Override
    protected void addJavaScriptInterface() {

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
