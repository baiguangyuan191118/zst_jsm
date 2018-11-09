package com.zst.ynh.widget.splash;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zst.ynh.R;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.core.bitmap.ImageLoaderUtils;
import com.zst.ynh.utils.WeakHandler;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;

import butterknife.BindView;

@Layout(R.layout.activity_splash_layout)
public class SplashActivity extends BaseActivity {
    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    private WeakHandler weakHandler;
    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        mTitleBar.setVisibility(View.GONE);
        ImageLoaderUtils.loadRes(this,R.mipmap.splash,ivSplash);
        weakHandler=new WeakHandler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what==1){
                    ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED,"0").navigation();
                }
                return true;
            }
        });
        weakHandler.sendEmptyMessageDelayed(1,3000);
    }


}
