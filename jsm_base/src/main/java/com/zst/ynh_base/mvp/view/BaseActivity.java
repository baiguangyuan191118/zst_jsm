package com.zst.ynh_base.mvp.view;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.zst.ynh_base.R;
import com.zst.ynh_base.util.LayoutUtils;
import com.zst.ynh_base.view.LoadingDialog;
import com.zst.ynh_base.view.TitleBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private View loadingView;
    private View contentView;

    private View errorView;
    private LayoutInflater layoutInflater;
    private LoadingDialog loadingDialog;
    private TextView tv_error_msg;
    private Button btn_on_retry;

    private View nodataView;

    protected TitleBar mTitleBar;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(isVerticalScreenLock() ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(LayoutUtils.LayoutInflater(this));
        layoutInflater = LayoutInflater.from(this);
        unbinder = ButterKnife.bind(this);
        if (isUseEventBus()) {
            EventBus.getDefault().register(this);
        }
        initTitleBar();
        initView();
    }


    private void initTitleBar() {
        boolean isImmersive = false;
        if (hasKitKat() && !hasLollipop() && isImmersive()) {
            isImmersive = true;
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
        } else if (hasLollipop() && isImmersive()) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            isImmersive = true;
        }
        mTitleBar = findViewById(R.id.title_bar);
        mTitleBar.setImmersive(isImmersive);
        mTitleBar.setBackgroundColor(Color.WHITE);
        mTitleBar.setLeftImageResource(R.drawable.system_back);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleBar.setTitleColor(Color.BLACK);
        mTitleBar.setSubTitleColor(Color.WHITE);
        mTitleBar.setActionTextColor(Color.WHITE);
        mTitleBar.setTitle(getString(R.string.app_name));

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(getLayoutInflater().inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, null);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(R.layout.activity_base_mall);
        RelativeLayout relativeLayout = findViewById(R.id.activity_base_mall);

        if (params != null) {
            if (params instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.BELOW, R.id.title_bar);
                relativeLayout.addView(view, params);
            } else {
                throw new RuntimeException("the layout params must is relayout params");
            }
        } else {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.title_bar);
            relativeLayout.addView(view, layoutParams);

        }
        errorView = findViewById(R.id.ll_retry);
        contentView = view;
        tv_error_msg = findViewById(R.id.tv_error_msg);
        btn_on_retry = findViewById(R.id.btn_on_retry);
        tv_error_msg.setText(NetworkUtils.isConnected() ? R.string.error_runtime : R.string.error_no_network);
        btn_on_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRetry();
            }
        });

        nodataView=findViewById(R.id.ll_nodata);
    }

    /**
     * 是否是竖屏锁定
     *
     * @return 默认是竖屏锁定，需要横屏的Activity重写此方法
     */
    protected boolean isVerticalScreenLock() {
        return true;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 是否将statusbar的高度计算内
     *
     * @return
     */
    public boolean isImmersive() {
        return false;
    }

    /**
     * 是否用eventbus 默认不适用
     *
     * @return
     */
    protected boolean isUseEventBus() {
        return false;
    }

    /**
     * 加载错误视图
     */
    protected void loadErrorView() {
        hideLoadingView();
        if (contentView != null) {
            contentView.setVisibility(View.GONE);
        }
        if(nodataView!=null){
            nodataView.setVisibility(View.GONE);
        }

        errorView.setVisibility(View.VISIBLE);

    }

    /**
     * 加载loading视图
     */
    protected void loadLoadingView() {
        if (contentView != null) {
            contentView.setVisibility(View.GONE);
        }
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }

        if(nodataView!=null){
            nodataView.setVisibility(View.GONE);
        }

        showLoadingView();
    }

    protected void loadNoDataView(){

        if (contentView != null) {
            contentView.setVisibility(View.GONE);
        }
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }

        if(nodataView!=null){
            nodataView.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 加载真实视图
     */
    protected void loadContentView() {
        hideLoadingView();
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
        if(nodataView!=null){
            nodataView.setVisibility(View.GONE);
        }
        contentView.setVisibility(View.VISIBLE);
    }

    protected void hideLoadingView() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    protected void showLoadingView() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStop() {
        super.onStop();
        hideLoadingView();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        hideLoadingView();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        super.onDestroy();
    }


    public abstract void onRetry();

    public abstract void initView();


}
