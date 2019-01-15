package com.zst.ynh.widget.web;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zst.ynh.JsmApplication;
import com.zst.ynh.R;
import com.zst.ynh.bean.LoanConfirmBean;
import com.zst.ynh.bean.UMShareBean;
import com.zst.ynh.bean.WebViewJSBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.Constant;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.umeng.share.ShareBean;
import com.zst.ynh.umeng.share.ShareImageBean;
import com.zst.ynh.umeng.share.ShareOnlyImageBean;
import com.zst.ynh.umeng.share.ShareUtils;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.TitleBar;


@Layout(R.layout.activity_empty_layout)
@Route(path = ArouterUtil.SIMPLE_WEB)
public class SimpleWebActivity extends BaseWebActivity {
    private boolean NOTSKIPMAGICBOX;


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
                SimpleWebActivity.this.finish();
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

    WebViewClient myWebViewClient = new BaseWebViewClient() {

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
            view.loadUrl(url);
            return true;
        }

    };

    WebChromeClient myWebChromeClient = new BaseWebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (title.contains("/credit/")) {
                mTitleBar.setTitle("还款中");
            }
        }
    };

    @Override
    protected void addJavaScriptInterface() {
        webView.addJavascriptInterface(new JavaMethod(), "nativeMethod");
    }


    private static final String CERTIFICATION_CENTER_TYPE = "3";//认证中心
    private static final String MAIN_TYPE = "4";//首页
    private static final String PAY_DIALOG_TYPE = "13";//支付界面


    public class JavaMethod {
        @JavascriptInterface
        //支付成功后跳转到首页
        public void callToMain() {
            ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED, BundleKey.MAIN_LOAN).withBoolean(BundleKey.MAIN_FRESH, true).navigation();
        }

        @JavascriptInterface
        public void returnNativeMethod(String typeStr) {
            WebViewJSBean bean = JSON.parseObject(typeStr, WebViewJSBean.class);
            String type = bean.type;
            //此方法用于导流的处理
            if (CERTIFICATION_CENTER_TYPE.equals(type)) {
                //认证中心
                if (TextUtils.isEmpty(SPUtils.getInstance().getString(SPkey.USER_SESSIONID))) {
                    ARouter.getInstance().build(ArouterUtil.LOGIN).navigation();
                    return;
                }
                ARouter.getInstance().build(ArouterUtil.CERTIFICATION_CENTER).navigation();
            } else if (MAIN_TYPE.equals(type)) {
                //首页
                ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED, BundleKey.MAIN_LOAN).withBoolean(BundleKey.MAIN_FRESH, true).navigation();
            } else if (PAY_DIALOG_TYPE.equals(type)) {//支付dialog
                if (bean.data != null) {
                    LoanConfirmBean.ItemBean itemBean = new LoanConfirmBean.ItemBean();
                    itemBean.money = bean.data.money;
                    itemBean.period = Integer.parseInt(bean.data.period);
                    ARouter.getInstance().build(ArouterUtil.PAY_PWD_INPUT).withSerializable(BundleKey.PAY_PWD_INPUT_DATA, itemBean).withString(BundleKey.PLATFORM, bean.data.platform_code).navigation();
                }

            }

        }

        //友盟分享
        @JavascriptInterface
        public void shareMethod(String shareBean) {
            Log.d("shareMethod", "===" + shareBean);
            final UMShareBean bean = new Gson().fromJson(shareBean, UMShareBean.class);
            //  QQ、QZONE、WEIXIN、WEIXIN_CIRCLE
            if (bean != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int type = bean.getType();
                        switch (type) {
                            case 0:
                                webShare(true, bean);
                                break;
                            case 1:
                                TitleBar.TextAction rightText = new TitleBar.TextAction("分享") {
                                    @Override
                                    public void performAction(View view) {
                                        webShare(true, bean);
                                    }
                                };
                                mTitleBar.addAction(rightText);
                                break;
                            case 2:
                                webShare(false, bean);
                                break;
                        }
                    }
                });
            }
        }

        //安装apk
        @JavascriptInterface
        public void checkAppIsInstalled(String url) {

            final Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            if (intent.resolveActivity(getPackageManager()) != null) {
                final ComponentName componentName = intent.resolveActivity(getPackageManager()); // 打印Log   ComponentName到底是什么 L.d("componentName = " + componentName.getClassName());
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
            } else {
                Toast.makeText(getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void webShare(boolean isShowPane, final UMShareBean bean) {
        SHARE_MEDIA[] platform;
        if (bean.getPlatform() != null && !bean.getPlatform().isEmpty()) {
            String[] strs = bean.getPlatform().split(",");
            platform = new SHARE_MEDIA[strs.length];
            for (int i = 0; i < strs.length; i++) {
                if ("QQ".equals(strs[i])) {
                    platform[i] = SHARE_MEDIA.QQ;
                } else if ("QZONE".equals(strs[i])) {
                    platform[i] = SHARE_MEDIA.QZONE;
                } else if ("WEIXIN".equals(strs[i])) {
                    platform[i] = SHARE_MEDIA.WEIXIN;
                } else if ("WEIXIN_CIRCLE".equals(strs[i])) {
                    platform[i] = SHARE_MEDIA.WEIXIN_CIRCLE;
                }
            }
        } else {
            platform = new SHARE_MEDIA[4];
            platform[0] = SHARE_MEDIA.QQ;
            platform[1] = SHARE_MEDIA.QZONE;
            platform[2] = SHARE_MEDIA.WEIXIN;
            platform[3] = SHARE_MEDIA.WEIXIN_CIRCLE;
        }
        final ShareBean shareBean;
        switch (bean.getShare_data_type()) {
            case 1:
                shareBean = new ShareOnlyImageBean();
                shareBean.setTitle(bean.getShare_title());
                ((ShareOnlyImageBean) shareBean).setImage(bean.getShare_image());
                break;
            default:
                shareBean = new ShareImageBean();
                shareBean.setTitle(bean.getShare_title());
                shareBean.setText(bean.getShare_body());
                ((ShareImageBean) shareBean).setImage(bean.getShare_logo());
                shareBean.setTargetUrl(bean.getShare_url());
                break;
        }

        new ShareUtils(this).doShare(shareBean, new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA platform) {
                if (bean.getCallback() != null && !bean.getCallback().isEmpty()) {
                    webView.loadUrl("javascript:" + bean.getCallback() + "(" + platform.toString() + "," + "Android" + ")");
                }

                if (!(SHARE_MEDIA.SMS == platform)) {
                    ToastUtils.showShort("分享成功");
                }

            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                if (t.getMessage().contains("没有安装应用")) {
                    if (SHARE_MEDIA.QQ == platform) {
                        ToastUtils.showShort("没有安装qq客户端");
                    }
                    if (SHARE_MEDIA.WEIXIN == platform) {
                        ToastUtils.showShort("没有安装微信客户端");
                    }
                } else {
                    ToastUtils.showShort("分享失败");
                }

            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
            }
        }, isShowPane, platform);

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if (getIntent().getBooleanExtra(BundleKey.MAIN_FRESH, false)) {//消息界面 热门贷款刷新
                ARouter.getInstance().build(ArouterUtil.MAIN).withBoolean(BundleKey.MAIN_FRESH, true).withString(BundleKey.MAIN_SELECTED, BundleKey.MAIN_LOAN).navigation();
            }
            if (getIntent().getBooleanExtra(BundleKey.CLICK_FROM_SPLASH, false)) {//从splash点击进入广告页
                if (SPUtils.getInstance().getBoolean(SPkey.FIRST_IN, true)) {
                    ARouter.getInstance().build(ArouterUtil.GUIDE).navigation();
                } else {
                    if (JsmApplication.isActive) {
                        String key = SPUtils.getInstance().getString(SPkey.USER_PHONE);
                        if (!StringUtils.isEmpty(key)) {
                            String pwd = SPUtils.getInstance().getString(key);
                            if (!StringUtils.isEmpty(pwd)) {
                                ARouter.getInstance().build(ArouterUtil.GESTURE_SET).withInt(BundleKey.GESTURE_MODE, BundleKey.VERIFY_GESTURE).navigation();
                            }else{
                                ARouter.getInstance().build(ArouterUtil.MAIN).navigation();
                            }
                        }
                    } else {
                        ARouter.getInstance().build(ArouterUtil.MAIN).navigation();
                    }
                }
            }
            finish();
        }
    }
}
