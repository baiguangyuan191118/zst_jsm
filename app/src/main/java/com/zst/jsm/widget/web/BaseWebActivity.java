package com.zst.jsm.widget.web;

import android.webkit.WebView;
import android.widget.FrameLayout;

import com.zst.jsm.R;
import com.zst.jsm_base.mvp.view.BaseActivity;
import com.zst.jsm_base.util.Layout;

import butterknife.BindView;

@Layout(R.layout.activity_empty_layout)
public class BaseWebActivity extends BaseActivity {
    @BindView(R.id.outer_layout)
    FrameLayout outerLayout;
    private WebView webView;

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        webView=new WebView(this);
        outerLayout.removeAllViews();
        outerLayout.addView(webView);
        initWebView();
    }
    private void initWebView() {

    }
}
