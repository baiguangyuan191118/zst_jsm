package com.zst.jsm_base.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.zst.jsm_base.R;
import com.zst.jsm_base.mvp.present.BasePresent;
import com.zst.jsm_base.util.LayoutUtils;
import com.zst.jsm_base.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<V, T extends BasePresent<V>> extends Fragment {
    protected View contentView;
    private View errorView;
    private LayoutInflater layoutInflater;
    private LoadingDialog loadingDialog;
    private TextView tv_error_msg;
    private Button btn_on_retry;
    private Unbinder unbinder;
    private BaseActivity mActivity;
    protected RelativeLayout relativeLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof BaseActivity) {
            mActivity = (BaseActivity) getActivity();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        relativeLayout = (RelativeLayout) inflater.inflate(R.layout.activity_base_mall, container, false);
        View view = inflater.inflate(LayoutUtils.LayoutInflater(this), relativeLayout, false);

        unbinder = ButterKnife.bind(this, view);
        relativeLayout.addView(view);
        errorView = relativeLayout.findViewById(R.id.ll_retry);
        contentView = view;
        tv_error_msg = errorView.findViewById(R.id.tv_error_msg);
        btn_on_retry = errorView.findViewById(R.id.btn_on_retry);
        tv_error_msg.setText(NetworkUtils.isConnected() ? R.string.error_runtime : R.string.error_no_network);
        btn_on_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRetry();
            }
        });
        onViewInflateOver();
        return relativeLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
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
        showLoadingView();
    }

    /**
     * 加载真实视图
     */
    protected void loadContentView() {
        hideLoadingView();
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
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
            loadingDialog = new LoadingDialog(mActivity);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideLoadingView();
    }

    //View初始化结束后后调用此方法
    protected void onViewInflateOver() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        hideLoadingView();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }



    protected abstract void onRetry();

    protected abstract void initView();

}
