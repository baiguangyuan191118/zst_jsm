package com.zst.ynh.widget.person.certification.work;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.JsmApplication;
import com.zst.ynh.R;
import com.zst.ynh.bean.Province;
import com.zst.ynh.bean.WorkInfoBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.event.StringEvent;
import com.zst.ynh.utils.DialogUtil;
import com.zst.ynh.view.BottomDialog;
import com.zst.ynh.view.ChooseCityDialog;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ArouterUtil.WORK_CERTIFICATION)
@Layout(R.layout.activity_work_certification_layout)
public class WorkCertificationActivity extends BaseActivity implements IWorkCertificationView {
    @BindView(R.id.tv_work_type)
    TextView tvWorkType;
    @BindView(R.id.et_company_name)
    EditText etCompanyName;
    @BindView(R.id.tv_company_address)
    TextView tvCompanyAddress;
    @BindView(R.id.et_company_address)
    EditText etCompanyAddress;
    @BindView(R.id.et_company_phone)
    EditText etCompanyPhone;
    @BindView(R.id.layout_choose_work_pic)
    LinearLayout layoutChooseWorkPic;
    @BindView(R.id.layout_business)
    LinearLayout layoutBuiness;
    @BindView(R.id.tv_work_time)
    TextView tvWorkTime;
    @BindView(R.id.tv_money_time)
    TextView tvMoneyTime;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.tv_tag)
    TextView tvTag;
    private String[] arrayName;
    private BottomDialog bottomDialog;
    private WorkCertificationPresent workCertificationPresent;
    private WorkInfoBean workInfoBean;
    private List<Province> cityJson = null;
    private ChooseCityDialog chooseCityDialog;
    private String latitude = "";
    private String longitude = "";

    /**
     * 工作类型
     */
    private final int WORK_TYPE = 1;
    /**
     * 工作时长
     */
    private final int WORK_TIME = 2;
    /**
     * 发薪日期
     */
    private final int PAY_DAY = 3;
    //工作类型
    private int work_type;
    //工作时间
    private int work_time;

    @Override
    public void getWorkInfo(WorkInfoBean workInfoBean) {
        this.workInfoBean = workInfoBean;
        if (workInfoBean != null && !TextUtils.isEmpty(workInfoBean.data.item.company_worktype)) {
            if (workInfoBean.data.item.company_worktype.equals("1")) {
                layoutBuiness.setVisibility(View.VISIBLE);
                for (int i = 0; i < workInfoBean.data.item.company_worktype_list.size(); i++) {
                    if (workInfoBean.data.item.company_worktype_list.get(i).work_type == Integer.valueOf(workInfoBean.data.item.company_worktype))
                        tvWorkType.setText(workInfoBean.data.item.company_worktype_list.get(i).name);
                }
                for (int i = 0; i < workInfoBean.data.item.company_period_list.size(); i++) {
                    if (workInfoBean.data.item.company_period_list.get(i).entry_time_type == Integer.valueOf(workInfoBean.data.item.company_period))
                        tvWorkTime.setText(workInfoBean.data.item.company_period_list.get(i).name);
                }
                tvMoneyTime.setText(workInfoBean.data.item.company_payday);
                tvCompanyAddress.setText(workInfoBean.data.item.company_address_distinct);
                etCompanyAddress.setText(workInfoBean.data.item.company_address);
                etCompanyName.setText(workInfoBean.data.item.company_name);
                etCompanyPhone.setText(workInfoBean.data.item.company_phone);
            } else {
                layoutBuiness.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void saveWorkInfo() {
        ToastUtils.showShort("保存成功");
        finish();
    }

    @Override
    protected boolean isUseEventBus() {
        return true;
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
        workCertificationPresent.getWorkInfoData();
    }

    @Override
    public void initView() {
        mTitleBar.setTitle("工作信息");
        workCertificationPresent = new WorkCertificationPresent();
        workCertificationPresent.attach(this);
        workCertificationPresent.getWorkInfoData();
        bottomDialog = new BottomDialog(this);
        new MyThread(this).start();
    }

    /**
     * 选择的赋值
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StringEvent messageEvent) {
        switch (bottomDialog.getType()) {
            case WORK_TYPE:
                tvWorkType.setText(messageEvent.getMessage());
                work_type = workInfoBean.data.item.company_worktype_list.get(messageEvent.getValue()).work_type;
                if (work_type != 1) {
                    layoutBuiness.setVisibility(View.GONE);
                } else {
                    layoutBuiness.setVisibility(View.VISIBLE);
                }
                break;
            case WORK_TIME:
                tvWorkTime.setText(messageEvent.getMessage());
                work_time = workInfoBean.data.item.company_period_list.get(messageEvent.getValue()).entry_time_type;
                break;
            case PAY_DAY:
                tvMoneyTime.setText(messageEvent.getMessage());
                break;
        }

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


    @OnClick({R.id.tv_work_type, R.id.tv_company_address, R.id.layout_choose_work_pic, R.id.tv_work_time, R.id.tv_money_time, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_work_type:
                if (workInfoBean != null) {
                    arrayName = new String[workInfoBean.data.item.company_worktype_list.size()];
                    for (int i = 0; i < workInfoBean.data.item.company_worktype_list.size(); i++) {
                        arrayName[i] = workInfoBean.data.item.company_worktype_list.get(i).name;
                    }
                    bottomDialog.setData(arrayName);
                    bottomDialog.setType(WORK_TYPE);
                    bottomDialog.notifyDataChanged();
                    bottomDialog.show();
                }
                break;
            case R.id.tv_company_address:
                chooseCityDialog = new ChooseCityDialog(this);
                chooseCityDialog.setTitle("单位地址选择");
                chooseCityDialog.setData(cityJson);
                chooseCityDialog.setCallBack(new ChooseCityDialog.SelectCallBack() {
                    @Override
                    public void select(String province, String city, String area, String details) {
                        tvCompanyAddress.setText(province + city + area);
                        if (!TextUtils.isEmpty(details)) {
                            etCompanyAddress.setText(details);
                        }
                    }
                });
                chooseCityDialog.show();
                break;
            case R.id.layout_choose_work_pic:

                break;
            case R.id.tv_work_time:
                if (workInfoBean != null) {
                    arrayName = new String[workInfoBean.data.item.company_period_list.size()];
                    for (int i = 0; i < workInfoBean.data.item.company_period_list.size(); i++) {
                        arrayName[i] = workInfoBean.data.item.company_period_list.get(i).name;
                    }
                    bottomDialog.setData(arrayName);
                    bottomDialog.setType(WORK_TIME);
                    bottomDialog.notifyDataChanged();
                    bottomDialog.show();
                }
                break;
            case R.id.tv_money_time:
                if (workInfoBean != null) {
                    arrayName = new String[workInfoBean.data.item.company_payday_list.size()];
                    for (int i = 0; i < workInfoBean.data.item.company_payday_list.size(); i++) {
                        arrayName[i] = workInfoBean.data.item.company_payday_list.get(i);
                    }
                    bottomDialog.setData(arrayName);
                    bottomDialog.setType(PAY_DAY);
                    bottomDialog.notifyDataChanged();
                    bottomDialog.show();
                }
                break;
            case R.id.btn_save:
                if (null != JsmApplication.getInstance().aMapLocation) {
                    latitude = JsmApplication.getInstance().aMapLocation.getLatitude() + "";
                    longitude = JsmApplication.getInstance().aMapLocation.getLongitude() + "";
                }
                workCertificationPresent.saveWorkInfoData(etCompanyAddress.getText().toString().trim(), tvCompanyAddress.getText().toString(),
                        etCompanyName.getText().toString().trim(), etCompanyPhone.getText().toString().trim(), tvMoneyTime.getText().toString().trim(),
                        work_time, latitude, longitude, work_type);
                break;
        }
    }

    private static class MyThread extends Thread {
        WeakReference<WorkCertificationActivity> mThreadActivityRef;

        public MyThread(WorkCertificationActivity activity) {
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
        workCertificationPresent.detach();
        DialogUtil.hideDialog(bottomDialog);
        DialogUtil.hideDialog(chooseCityDialog);
    }
}
