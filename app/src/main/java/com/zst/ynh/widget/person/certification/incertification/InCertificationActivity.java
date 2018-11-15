package com.zst.ynh.widget.person.certification.incertification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zst.ynh.R;
import com.zst.ynh.adapter.InCertificationAdapter;
import com.zst.ynh.bean.InCertificationBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ArouterUtil.CERTIFICATION_CENTER)
@Layout(R.layout.activity_in_certification_layout)
public class InCertificationActivity extends BaseActivity implements IInCertificationView {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.btn_update_limit)
    Button btnUpdateLimit;
    private InCertificationPresent inCertificationPresent;
    private List<InCertificationBean.ItemBean.ListBean> list;
    private InCertificationAdapter inCertificationAdapter;

    @Override
    public void getCertificationData(InCertificationBean inCertificationBean) {
        sortData(inCertificationBean);
        if (inCertificationAdapter == null)
            inCertificationAdapter = new InCertificationAdapter(this, list, inCertificationBean);
        else
            inCertificationAdapter.notifyDataSetChanged();
        recycleView.setAdapter(inCertificationAdapter);
        setButtonStyle(inCertificationBean);
    }

    /**
     * 设置页面底部的按钮
     */
    private void setButtonStyle(InCertificationBean inCertificationBean) {
        if (inCertificationBean.item.footer.card_type == 1) {
            btnUpdateLimit.setText(inCertificationBean.item.footer.title);
            switch (inCertificationBean.item.footer.status) {
                case 0:
                    btnUpdateLimit.setVisibility(View.GONE);
                    break;
                case 1:
                    btnUpdateLimit.setVisibility(View.VISIBLE);
                    btnUpdateLimit.setEnabled(false);
                    break;
                case 2:
                case 4:
                    btnUpdateLimit.setVisibility(View.VISIBLE);
                    btnUpdateLimit.setEnabled(true);
                    break;
            }
        }
    }

    private void sortData(InCertificationBean inCertificationBean) {
        list.addAll(inCertificationBean.item.list);
        //添加title进入列表中
        InCertificationBean.ItemBean.ListBean listBean1 = new InCertificationBean.ItemBean.ListBean();
        String title1 = inCertificationBean.item.list_name._$1.title;
        listBean1.type = 0;
        listBean1.title = title1;
        listBean1.isTitleItem = true;
        list.add(listBean1);
        InCertificationBean.ItemBean.ListBean listBean2 = new InCertificationBean.ItemBean.ListBean();
        String title2 = inCertificationBean.item.list_name._$3.title;
        listBean2.type = 2;
        listBean2.title = title2;
        listBean2.isTitleItem = true;
        list.add(listBean2);
        //将所有数据根据type进行排序
        Collections.sort(list, new Comparator<InCertificationBean.ItemBean.ListBean>() {
            /*
             * int compare(Student o1, Student o2) 返回一个基本类型的整型，
             * 返回负数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。
             */
            @Override
            public int compare(InCertificationBean.ItemBean.ListBean o1, InCertificationBean.ItemBean.ListBean o2) {
                //按照下发的type进行升序排列
                if (o1.type > o2.type) {
                    return 1;
                }
                if (o1.type == o2.type) {
                    return 0;
                }
                return -1;
            }
        });
    }

    @Override
    public void updateLimitSuccess() {

    }

    @Override
    public void showContentView() {
        loadContentView();
    }

    @Override
    public void showErrorView() {
        loadErrorView();
    }

    @Override
    public void showLoadView() {
        loadLoadingView();
    }

    @Override
    public void onRetry() {
        inCertificationPresent.getVerificationInfo();
    }

    @Override
    public void initView() {
        mTitleBar.setTitle("认证中心");
        inCertificationPresent = new InCertificationPresent();
        inCertificationPresent.attach(this);
        inCertificationPresent.getVerificationInfo();
        if (list == null)
            list = new ArrayList<>();
        else
            list.clear();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                inCertificationPresent.getVerificationInfo();
            }
        });
    }

    @Override
    public void showLoading() {
        showLoadingView();
    }

    @Override
    public void hideLoading() {
        hideLoadingView();
    }

    @Override
    public void ToastErrorMessage(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        inCertificationPresent.detach();
    }

    @OnClick(R.id.btn_update_limit)
    public void onViewClicked() {
        inCertificationPresent.updateLimit();
    }
}
