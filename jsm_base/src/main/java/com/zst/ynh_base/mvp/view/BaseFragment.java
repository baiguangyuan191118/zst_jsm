package com.zst.ynh_base.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.zst.ynh_base.R;
import com.zst.ynh_base.util.LayoutUtils;
import com.zst.ynh_base.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    protected View contentView;
    private View errorView;
    private LayoutInflater layoutInflater;
    private LoadingDialog loadingDialog;
    private TextView tv_error_msg;
    private Button btn_on_retry;
    private Unbinder unbinder;
    private BaseActivity mActivity;
    protected RelativeLayout relativeLayout;
    private View nodataView;
    private TextView tvNodata;
    private ImageView ivNoData;
    protected boolean isLazyload;
    protected boolean isInit = false;
    protected boolean isLoad = false;
    protected int tabId;
    private boolean isVisibleToUser; // 是否对用户可见
    private boolean isHidden = true; // 记录当前fragment的是否隐藏



    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    public int getTabId() {
        return tabId;
    }

    public boolean isLazyload() {
        return isLazyload;
    }

    public void setLazyload(boolean lazyload) {
        isLazyload = lazyload;
    }


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
        nodataView = relativeLayout.findViewById(R.id.ll_nodata);
        tvNodata = nodataView.findViewById(R.id.tv_nodata);
        ivNoData = nodataView.findViewById(R.id.iv_nodata);
        btn_on_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRetry();
            }
        });
        onViewInflateOver();
        initView();
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();
        return relativeLayout;
    }

    /**
     * 使用show()、hide()控制fragment显示、隐藏时回调该方法
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHidden = hidden;
        if (!hidden) {
          loadChildData();
        }
    }

    /*
    * 加载子fragment的数据
    * */
    private void loadChildData() {
        if (!isParentHidden()) {
            onLazyLoad();
            isLoad = true;
            dispatchParentHiddenState();
        }
    }

    /**
     * show()、hide()场景下，当前fragment没隐藏，如果其子fragment也没隐藏，则尝试让子fragment请求数据
     * 多层嵌套
     */
    private void dispatchParentHiddenState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof BaseFragment && !((BaseFragment) child).isHidden) {
                ((BaseFragment) child).loadChildData();
            }
        }
    }
    /**
     * show()、hide()场景下，父fragment是否隐藏
     * 子fragment
     * @return
     */
    private boolean isParentHidden() {
        Fragment fragment = getParentFragment();
        if (fragment == null) {
            return false;
        } else if (fragment instanceof BaseFragment && !((BaseFragment) fragment).isHidden) {
            return false;
        }
        return true;
    }


    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        isCanLoadData();
    }

    //View初始化结束后后调用此方法
    protected void onViewInflateOver() {
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {

        if (!isLazyload) {
            return;
        }

        if(!isInit){
            return;
        }

        if (getUserVisibleHint() && isParentVisible()) {
            onLazyLoad();
            isLoad = true;
            dispatchParentVisibleState();
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }
/*
* ViewPager场景下，当前fragment可见，如果其子fragment也可见，则尝试让子fragment加载请求
* */
    private void dispatchParentVisibleState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof BaseFragment && ((BaseFragment) child).isVisibleToUser) {
                ((BaseFragment) child).onLazyLoad();
            }
        }
    }

    /**
     * ViewPager场景下，判断父fragment是否可见
     *
     * @return
     */
    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        return fragment == null || (fragment instanceof BaseFragment && ((BaseFragment) fragment).getUserVisibleHint());
    }

    //数据加载接口，留给子类实现
    protected void onLazyLoad() {
    }

    ;

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isUseEventBus()) {
            EventBus.getDefault().register(this);
        }

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
        if (nodataView != null) {
            nodataView.setVisibility(View.GONE);
        }
        errorView.setVisibility(View.VISIBLE);
        tv_error_msg.setText(NetworkUtils.isConnected() ? R.string.error_runtime : R.string.error_no_network);
    }

    protected void loadNoDataView(int resId, String data) {

        if (contentView != null) {
            contentView.setVisibility(View.GONE);
        }
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }

        if (nodataView != null) {
            nodataView.setVisibility(View.VISIBLE);
            tvNodata.setText(TextUtils.isEmpty(data) ? "暂无数据" : data);
            ivNoData.setImageResource(resId == 0 ? R.drawable.no_record : resId);
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
        if (nodataView != null) {
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
            loadingDialog = new LoadingDialog(mActivity,contentView.getMeasuredHeight());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
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
