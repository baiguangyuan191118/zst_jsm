package com.zst.ynh.widget.person.certification.person;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.LimitPersonInfoBean;
import com.zst.ynh.bean.Province;
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

@Route(path = ArouterUtil.PERSON_CERTIFICATION)
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
    /**
     * 学历
     */
    private final int DEGREE_EVENT=1;
    /**
     * 婚姻
     */
    private final int MARRIAGE_EVENT=2;
    /**
     * 居住时间
     */
    private final int TIME_EVENT=3;

    //选择的学历类型
    private int degree_type;
    //选择的婚姻状况类型
    private int marriage_type;
    //选择的居住时间类型
    private int live_time_type;

    @Override
    public void savePersonInfoDataSuccess() {
        ToastUtils.showShort("保存成功");
        finish();
    }

    @Override
    public void getPersonInfoData(LimitPersonInfoBean limitPersonInfoBean) {
        this.limitPersonInfoBean = limitPersonInfoBean;
        if (!TextUtils.isEmpty(limitPersonInfoBean.data.info.address)) {
            for (int i=0;i<limitPersonInfoBean.data.degrees_all.size();i++){
                if (limitPersonInfoBean.data.degrees_all.get(i).degrees==Integer.valueOf(limitPersonInfoBean.data.info.degrees)){
                    tvDegree.setText(limitPersonInfoBean.data.degrees_all.get(i).name);
                    degree_type=limitPersonInfoBean.data.degrees_all.get(i).degrees;
                }
            }
            for (int i=0;i<limitPersonInfoBean.data.marriage_all.size();i++){
                if (limitPersonInfoBean.data.marriage_all.get(i).marriage==Integer.valueOf(limitPersonInfoBean.data.info.marriage)){
                    tvMarriage.setText(limitPersonInfoBean.data.marriage_all.get(i).name);
                    marriage_type=limitPersonInfoBean.data.marriage_all.get(i).marriage;
                }
            }
            for (int i=0;i<limitPersonInfoBean.data.live_time_type_all.size();i++){
                if (limitPersonInfoBean.data.live_time_type_all.get(i).live_time_type==Integer.valueOf(limitPersonInfoBean.data.info.address_period)){
                    tvLiveTime.setText(limitPersonInfoBean.data.live_time_type_all.get(i).name);
                    live_time_type=limitPersonInfoBean.data.live_time_type_all.get(i).live_time_type;
                }
            }
            tvHomeArea.setText(limitPersonInfoBean.data.info.address_distinct);
            etHomeAddress.setText(limitPersonInfoBean.data.info.address);
        }
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
    /**
     * 选择的赋值
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StringEvent messageEvent) {
        switch (bottomDialog.getType()){
            case DEGREE_EVENT:
                tvDegree.setText(messageEvent.getMessage());
                degree_type=limitPersonInfoBean.data.degrees_all.get(messageEvent.getValue()).degrees;
                break;
            case MARRIAGE_EVENT:
                tvMarriage.setText(messageEvent.getMessage());
                marriage_type=limitPersonInfoBean.data.marriage_all.get(messageEvent.getValue()).marriage;
                break;
            case TIME_EVENT:
                tvLiveTime.setText(messageEvent.getMessage());
                live_time_type=limitPersonInfoBean.data.live_time_type_all.get(messageEvent.getValue()).live_time_type;
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


    @OnClick({R.id.tv_degree, R.id.tv_marriage, R.id.tv_home_area, R.id.tv_live_time, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_degree:
                if (limitPersonInfoBean!=null){
                    arrayName = new String[limitPersonInfoBean.data.degrees_all.size()];
                    for (int i = 0; i < limitPersonInfoBean.data.degrees_all.size(); i++) {
                        arrayName[i] = limitPersonInfoBean.data.degrees_all.get(i).name;
                    }
                    bottomDialog.setData(arrayName);
                    bottomDialog.setType(DEGREE_EVENT);
                    bottomDialog.notifyDataChanged();
                    bottomDialog.show();
                }
                break;
            case R.id.tv_marriage:
                if (limitPersonInfoBean!=null) {
                    arrayName = new String[limitPersonInfoBean.data.marriage_all.size()];
                    for (int i = 0; i < limitPersonInfoBean.data.marriage_all.size(); i++) {
                        arrayName[i] = limitPersonInfoBean.data.marriage_all.get(i).name;
                    }
                    bottomDialog.setData(arrayName);
                    bottomDialog.setType(MARRIAGE_EVENT);
                    bottomDialog.notifyDataChanged();
                    bottomDialog.show();
                }
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
                if (limitPersonInfoBean!=null) {
                    arrayName = new String[limitPersonInfoBean.data.live_time_type_all.size()];
                    for (int i = 0; i < limitPersonInfoBean.data.live_time_type_all.size(); i++) {
                        arrayName[i] = limitPersonInfoBean.data.live_time_type_all.get(i).name;
                    }
                    bottomDialog.setData(arrayName);
                    bottomDialog.setType(TIME_EVENT);
                    bottomDialog.notifyDataChanged();
                    bottomDialog.show();
                }
                break;
            case R.id.btn_save:
                if (TextUtils.isEmpty(tvDegree.getText())) {
                    ToastUtils.showShort("学历不能为空");
                    return ;
                }
                if (TextUtils.isEmpty(tvHomeArea.getText())) {
                    ToastUtils.showShort("现居城市不能为空");
                    return ;
                }
                if (TextUtils.isEmpty(tvLiveTime.getText())) {
                    ToastUtils.showShort("居住时间不能为空");
                    return ;
                }
                if (TextUtils.isEmpty(tvMarriage.getText())) {
                    ToastUtils.showShort("婚姻状况不能为空");
                    return ;
                }
                if (TextUtils.isEmpty(etHomeAddress.getText().toString().trim())) {
                    ToastUtils.showShort("居住地址不能为空");
                    return ;
                }
                personInfoCertificationPresent.savePersonInfoData(etHomeAddress.getText().toString().trim(), tvHomeArea.getText().toString()
                        , degree_type+"", live_time_type+"", marriage_type+"");
                break;
        }
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
