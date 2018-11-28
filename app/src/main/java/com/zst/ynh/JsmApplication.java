package com.zst.ynh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.UpdateHeaderUtils;
import com.zst.ynh.view.JSMRefreshLayout;
import com.zst.ynh.widget.splash.SplashActivity;
import com.zst.ynh_base.BaseApplication;
import com.zst.ynh_base.net.HttpManager;

import cn.fraudmetrix.octopus.aspirit.main.OctopusManager;

public class JsmApplication extends BaseApplication {
    private static JsmApplication instance;
    private static Context context;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //具体的定位类
    public AMapLocation aMapLocation;
    public static boolean isActive=false;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this);
        //数据魔盒(是为了拿到芝麻信用分)
        OctopusManager.getInstance().init(this, "zx_mohe", "759abb6d367846eb9dbfa90f98e6f714");
        initMap();
        //更新消息头
        UpdateHeaderUtils.updateHeader(SPUtils.getInstance().getString(SPkey.USER_SESSIONID));
        /**
         * 登录态失效的拦截
         */
        HttpManager.setOnGlobalInterceptor(new HttpManager.OnGlobalInterceptor() {
            @Override
            public void onInterceptor() {
                logoutData();
                ARouter.getInstance().build(ArouterUtil.LOGIN).withString(BundleKey.LOGIN_FROM,BundleKey.LOGIN_FROM_MAIN).navigation();
            }
        });

        Utils.init(this);

        AppUtils.registerAppStatusChangedListener(this, new Utils.OnAppStatusChangedListener() {
            @Override
            public void onForeground() {
                LogUtils.d("onForeground");
                isActive=true;
                Activity activity=ActivityUtils.getTopActivity();
                if(!(activity instanceof SplashActivity)){
                    String key = SPUtils.getInstance().getString(SPkey.USER_PHONE);
                    if(!StringUtils.isEmpty(key)){
                        String pwd = SPUtils.getInstance().getString(key);
                        if(!StringUtils.isEmpty(pwd)){
                            ARouter.getInstance().build(ArouterUtil.GESTURE_SET).withInt(BundleKey.GESTURE_MODE,BundleKey.VERIFY_GESTURE).navigation();
                        }
                    }
                }
            }

            @Override
            public void onBackground() {
                LogUtils.d("onBackground");
                isActive=false;
            }
        });

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

    public static JsmApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    private void initMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                    } else {
                        LogUtils.d("ErrCode:" + aMapLocation.getErrorCode() + "errInfo:" + aMapLocation.getErrorInfo());
                    }
                }
            }
        });
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AppUtils.unregisterAppStatusChangedListener(this);
    }

    /**
     * 跳转去登录（点击返回到首页）
     */
    public static void toLoginFromMain() {
        logoutData();
        ARouter.getInstance().build(ArouterUtil.LOGIN).withString(BundleKey.LOGIN_FROM,BundleKey.LOGIN_FROM_MAIN).navigation();
        return;
    }

    public static void logoutData(){
        SPUtils.getInstance().put(SPkey.USER_SESSIONID, "");
        SPUtils.getInstance().put(SPkey.REAL_NAME, "");
        SPUtils.getInstance().put(SPkey.UID, "");
        //手势操作
        String username=SPUtils.getInstance().getString(SPkey.USER_PHONE);
        if(!StringUtils.isEmpty(username)){
            SPUtils.getInstance().put(username,"");
        }
        SPUtils.getInstance().put(SPkey.TIP_SELECTED,false);
        //清楚cookie
        CookieSyncManager.createInstance(context);
        CookieManager cm = CookieManager.getInstance();
        cm.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }
}
