package com.zst.ynh.base;

import com.umeng.analytics.MobclickAgent;
import com.zst.ynh_base.mvp.view.BaseActivity;

public abstract class UMBaseActivity extends BaseActivity {
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MobclickAgent.onPause(this);
    }
}
