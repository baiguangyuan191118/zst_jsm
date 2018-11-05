package com.zst.ynh;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.UpdateHeaderUtils;
import com.zst.ynh.view.JSMRefreshLayout;
import com.zst.ynh_base.BaseApplication;
import com.zst.ynh_base.net.HttpManager;

public class JsmApplication extends BaseApplication {
    private static JsmApplication instance;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        context = getApplicationContext();
        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this);
        //更新消息头
        UpdateHeaderUtils.updateHeader(SPUtils.getInstance().getString(SPkey.USER_SESSIONID));
        /**
         * 登录态失效的拦截
         */
        HttpManager.setOnGlobalInterceptor(new HttpManager.OnGlobalInterceptor() {
            @Override
            public void onInterceptor() {
                SPUtils.getInstance().put(SPkey.USER_SESSIONID,"");
                ARouter.getInstance().build(ArouterUtil.LOGIN).navigation();
            }
        });

    }
    public static JsmApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new JSMRefreshLayout(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
}
