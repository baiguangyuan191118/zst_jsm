package com.zst.ynh.widget.splash;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bqs.risk.df.android.BqsDF;
import com.bqs.risk.df.android.BqsParams;
import com.bqs.risk.df.android.OnBqsDFListener;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.zst.ynh.BuildConfig;
import com.zst.ynh.JsmApplication;
import com.zst.ynh.R;
import com.zst.ynh.base.UMBaseActivity;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.WeakHandler;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.ImageLoaderUtils;
import com.zst.ynh_base.util.Layout;

import java.util.List;

import butterknife.BindView;
import cn.tongdun.android.shell.FMAgent;
import cn.tongdun.android.shell.exception.FMException;
import cn.tongdun.android.shell.inter.FMCallback;

@Layout(R.layout.activity_splash_layout)
public class SplashActivity extends UMBaseActivity implements OnBqsDFListener, SplashView {
    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    private WeakHandler weakHandler;
    private SplashPresent present;
    private String response;
    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        mTitleBar.setVisibility(View.GONE);
        ImageLoaderUtils.loadRes(this, R.mipmap.splash, ivSplash);
        weakHandler = new WeakHandler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {
                    if(SPUtils.getInstance().getBoolean(SPkey.FIRST_IN,true)){
                        ARouter.getInstance().build(ArouterUtil.GUIDE).withString(BundleKey.MAIN_DATA,response).navigation();
                        SPUtils.getInstance().put(BundleKey.MAIN_DATA,response);
                        SplashActivity.this.finish();
                    }else{
                        if (JsmApplication.isActive) {
                            String key = SPUtils.getInstance().getString(SPkey.USER_PHONE);
                            if (!StringUtils.isEmpty(key)) {
                                String pwd = SPUtils.getInstance().getString(key);
                                if (!StringUtils.isEmpty(pwd)) {
                                    ARouter.getInstance().build(ArouterUtil.GESTURE_SET).withInt(BundleKey.GESTURE_MODE, BundleKey.VERIFY_GESTURE).withString(BundleKey.MAIN_DATA,response).navigation();
                                    SplashActivity.this.finish();
                                    return true;
                                }
                            }
                        }


                        ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_DATA,response).navigation();
                        SplashActivity.this.finish();

                    }
                }
                return true;
            }
        });
        present=new SplashPresent();
        present.attach(this);
        present.getTabList();
        requestPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(present!=null){
            present.detach();
        }
    }

    /**
     * 在欢迎页面注册同盾 返回这个字段 传给服务器 供风控使用
     * 同盾环境类型 测试环境(FMAgent.ENV_SANDBOX)/生产环境(FMAgent.ENV_PRODUCTION)
     */
    private void getBlackBox(){
        String environment=BuildConfig.DEBUG ? FMAgent.ENV_SANDBOX:FMAgent.ENV_PRODUCTION;
        try {
            FMAgent.initWithCallback(this, environment, new FMCallback() {
                @Override
                public void onEvent(final String blackbox) {
                    // 注意这里不是主线程 请不要在这个函数里进行ui操作，否则可能会出现崩溃
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SPUtils.getInstance().put(SPkey.TONG_DUN_bLACK_BOX,blackbox);
                        }
                    });
                }
            });
        }catch (FMException e){
            e.printStackTrace();
        }
    }
    private void requestPermission(){
        String[] requestPermissions = BqsDF.getRuntimePermissions(true, true, true);
        if (XXPermissions.isHasPermission(this,requestPermissions)) {
            initBqsDFSDK();
            getBlackBox();
            weakHandler.sendEmptyMessageDelayed(1, 3000);
        } else {
            XXPermissions.with(this)
                    .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    .permission(requestPermissions)
                    .request(new OnPermission() {

                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (isAll) {
                                initBqsDFSDK();
                                getBlackBox();
                            } else {
                                ToastUtils.showShort("为了您能正常使用，请授权");
                            }
                            weakHandler.sendEmptyMessageDelayed(1, 3000);
                        }
                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if (quick) {
                                ToastUtils.showShort("被永久拒绝授权，请手动授予权限");
                                //如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.gotoPermissionSettings(SplashActivity.this);
                            } else {
                                ToastUtils.showShort("获取权限失败");
                            }
                            weakHandler.sendEmptyMessageDelayed(1, 3000);
                        }
                    });
        }
    }
    /**
     * 初始化白骑士
     */
    private void initBqsDFSDK() {
        //1、添加设备信息采集回调
        BqsDF.setOnBqsDFListener(this);

        //2、配置初始化参数
        BqsParams params = new BqsParams();
        params.setPartnerId("shlr");//商户编号
        params.setTestingEnv(BuildConfig.DEBUG ? true : false);//false是生产环境 true是测试环境
        params.setGatherGps(true);//是否采集 GPS 信息
        params.setGatherBaseStation(true);//是否采集基站信息
        params.setGatherSensorInfo(true);//是否采集传感器信息
        params.setGatherInstalledApp(true);//是否采集已安装的 app 名称

        //3、执行初始化
        BqsDF.initialize(this, params);
    }
    @Override
    public void onSuccess(String tokenKey) {
        //回调的tokenkey和通过BqsDF.getTokenKey()拿到的值都是一样的
    }

    @Override
    public void onFailure(String resultCode, String resultDesc) {

    }

    @Override
    public void getTabListSuccess(String response) {
        this.response=response;
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
}
