package com.zst.ynh.widget.person.certification.person;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.LimitPersonInfoBean;
import com.zst.ynh.bean.Province;
import com.zst.ynh.utils.DialogUtil;
import com.zst.ynh.view.BottomDialog;
import com.zst.ynh.view.ChooseCityDialog;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Layout(R.layout.activity_person_certification_layout)
public class PersonInfoCertificationActivity extends BaseActivity implements IPersonInfoCertificationView {
    @BindView(R.id.tv_degree)
    TextView tvDegree;
    @BindView(R.id.tv_marriage)
    TextView tvMarriage;
    @BindView(R.id.tv_home_area)
    TextView tvHomeArea;
    @BindView(R.id.et_home_address)
    EditText etHomeAddress;
    @BindView(R.id.tv_live_time)
    TextView tvLiveTime;
    @BindView(R.id.btn_save)
    Button btnSave;
    private BottomDialog bottomDialog;
    private PersonInfoCertificationPresent personInfoCertificationPresent;
    private LimitPersonInfoBean limitPersonInfoBean;
    private String[] arrayName;
    private List<Province> cityJson = null;
    private ChooseCityDialog chooseCityDialog;

    @Override
    public void savePersonInfoDataSuccess() {
        finish();
    }

    @Override
    public void getPersonInfoData(LimitPersonInfoBean limitPersonInfoBean) {
        if (!TextUtils.isEmpty(limitPersonInfoBean.data.info.address)) {
            tvDegree.setText(limitPersonInfoBean.data.info.degrees);
            tvMarriage.setText(limitPersonInfoBean.data.info.marriage);
            tvHomeArea.setText(limitPersonInfoBean.data.info.address_distinct);
            etHomeAddress.setText(limitPersonInfoBean.data.info.address);
            tvLiveTime.setText(limitPersonInfoBean.data.info.address_distinct);
            this.limitPersonInfoBean = limitPersonInfoBean;
        }
    }

    @Override
    public void loadLoading() {
        loadLoadingView();
    }

    @Override
    public void LoadError() {
        loadErrorView();
    }

    @Override
    public void loadContent() {
        loadContentView();
    }

    @Override
    public void onRetry() {
        personInfoCertificationPresent.getPersonInfoData();
    }

    @Override
    public void initView() {
        mTitleBar.setTitle("个人信息");
        personInfoCertificationPresent = new PersonInfoCertificationPresent();
        personInfoCertificationPresent.attach(this);
        personInfoCertificationPresent.getPersonInfoData();
        bottomDialog = new BottomDialog(this);
        new MyThread(this).start();
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


    @OnClick({R.id.tv_degree, R.id.tv_marriage, R.id.tv_home_area, R.id.tv_live_time, R.id.btn_save})
    public void onViewClicked(View view) {
        btnSave.setEnabled(checkBtnClickEnable());
        switch (view.getId()) {
            case R.id.tv_degree:
                arrayName = new String[limitPersonInfoBean.data.degrees_all.size()];
                for (int i = 0; i < limitPersonInfoBean.data.degrees_all.size(); i++) {
                    arrayName[i] = limitPersonInfoBean.data.degrees_all.get(i).name;
                }
                bottomDialog.setData(arrayName);
                bottomDialog.show();
                break;
            case R.id.tv_marriage:
                arrayName = new String[limitPersonInfoBean.data.marriage_all.size()];
                for (int i = 0; i < limitPersonInfoBean.data.marriage_all.size(); i++) {
                    arrayName[i] = limitPersonInfoBean.data.marriage_all.get(i).name;
                }
                bottomDialog.setData(arrayName);
                bottomDialog.show();
                break;
            case R.id.tv_home_area:
                chooseCityDialog = new ChooseCityDialog(this);
                chooseCityDialog.setTitle("现居城市选择");
                chooseCityDialog.setData(cityJson);
                chooseCityDialog.setCallBack(new ChooseCityDialog.SelectCallBack() {
                    @Override
                    public void select(String province, String city, String area, String details) {
                        tvHomeArea.setText(province + city + area);
                        if (!TextUtils.isEmpty(details)) {
                            etHomeAddress.setText(details);
                        }
                    }
                });
                chooseCityDialog.show();
                break;
            case R.id.tv_live_time:
                arrayName = new String[limitPersonInfoBean.data.live_time_type_all.size()];
                for (int i = 0; i < limitPersonInfoBean.data.live_time_type_all.size(); i++) {
                    arrayName[i] = limitPersonInfoBean.data.live_time_type_all.get(i).name;
                }
                bottomDialog.setData(arrayName);
                bottomDialog.show();
                break;
            case R.id.btn_save:
                personInfoCertificationPresent.savePersonInfoData(etHomeAddress.getText().toString().trim(), tvHomeArea.getText().toString()
                        , tvDegree.getText().toString(), tvLiveTime.getText().toString(), tvMarriage.getText().toString());
                break;
        }
    }

    /**
     * 判断按钮是否可以点击
     *
     * @return
     */
    private boolean checkBtnClickEnable() {
        if (!TextUtils.isEmpty(tvDegree.getText())) {
            return true;
        }
        if (!TextUtils.isEmpty(tvHomeArea.getText())) {
            return true;
        }
        if (!TextUtils.isEmpty(tvLiveTime.getText())) {
            return true;
        }
        if (!TextUtils.isEmpty(tvMarriage.getText())) {
            return true;
        }
        if (!TextUtils.isEmpty(etHomeAddress.getText().toString().trim())) {
            return true;
        }
        return false;
    }

    private static class MyThread extends Thread {
        WeakReference<PersonInfoCertificationActivity> mThreadActivityRef;

        public MyThread(PersonInfoCertificationActivity activity) {
            mThreadActivityRef = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            super.run();
            if (null != mThreadActivityRef && mThreadActivityRef.get() != null) {
                mThreadActivityRef.get().getCity();
            }
        }
    }

    /**
     * 通过本地文件读取省市区
     */
    private void getCity() {
        try {
            InputStream is = getAssets().open("city.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            cityJson = JSON.parseArray(new String(buffer), Province.class);

        } catch (Exception e) {
            ToastUtils.showShort("城市列表获取失败");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        personInfoCertificationPresent.detach();
        DialogUtil.hideDialog(bottomDialog);
        DialogUtil.hideDialog(chooseCityDialog);
    }
}
