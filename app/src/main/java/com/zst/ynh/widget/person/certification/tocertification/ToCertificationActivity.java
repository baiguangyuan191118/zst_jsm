package com.zst.ynh.widget.person.certification.tocertification;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.zst.ynh.R;
import com.zst.ynh.bean.CertificationGuideBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.Constant;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.ImageLoaderUtils;
import com.zst.ynh_base.util.Layout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ArouterUtil.TO_CERTIFICATION)
@Layout(R.layout.activity_to_certification_layout)
public class ToCertificationActivity extends BaseActivity implements IToCertificationView {
    @BindView(R.id.btn_certification)
    Button btnCertification;
    @BindView(R.id.iv_guide_bg)
    ImageView ivGuideBg;
    ToCertificationPresent toCertificationPresent;
    private CertificationGuideBean certificationGuideBean;

    /**
     * 身份证
     */
    private static final int TYPE_FACE_ID = 1;
    /**
     * 紧急联系人
     */
    private static final int TYPE_CONTACT = 3;
    /**
     * 银行卡信息
     */
    private static final int TYPE__BANK = 4;
    /**
     * 手机运营商
     */
    private static final int TYPE__PHONE = 5;
    /**
     * 淘宝认证
     */
    private static final int TYPE__TB = 8;

    @Override
    public void onRetry() {
        toCertificationPresent.getCertificationGuide();
    }

    @Override
    public void initView() {
        toCertificationPresent = new ToCertificationPresent();
        toCertificationPresent.attach(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        toCertificationPresent.getCertificationGuide();
    }

    @Override
    public void showLoading() {
        loadLoadingView();
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void ToastErrorMessage(String msg) {
        loadErrorView();
        ToastUtils.showShort(msg);
    }

    @Override
    public void getCertificationType(CertificationGuideBean certificationGuideBean) {
        loadContentView();
        ImageLoaderUtils.loadUrl(this, certificationGuideBean.background_image, ivGuideBg);
        Constant.setTargetUrl(certificationGuideBean.target_url);
        this.certificationGuideBean = certificationGuideBean;
    }


    @OnClick(R.id.btn_certification)
    public void onViewClicked() {
        //点击下一步的时候必须先同意gps定位的权限
        if (XXPermissions.isHasPermission(this,new String[]{ Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION})) {
            if (!isOPenGps(this)){
                ToastUtils.showShort("请先打开gps定位");
                Intent intent=new Intent();
                intent.setAction(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }else{
                typeToSkipPage();
            }
        } else {
            XXPermissions.with(this)
                    .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    .permission(new String[]{ Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION})
                    .request(new OnPermission() {

                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (isAll) {
                                if (!isOPenGps(ToCertificationActivity.this)){
                                    ToastUtils.showShort("请先打开gps定位");
                                    Intent intent=new Intent();
                                    intent.setAction(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(intent);
                                }else{
                                    typeToSkipPage();
                                }
                            } else {
                                ToastUtils.showShort("为了您能正常使用，请授权");
                            }
                        }
                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if (quick) {
                                ToastUtils.showShort("被永久拒绝授权，请手动授予权限");
                                //如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.gotoPermissionSettings(ToCertificationActivity.this);
                            } else {
                                ToastUtils.showShort("获取权限失败");
                            }
                        }
                    });
        }

    }

    /**
     * 根据type跳转不同的页面
     */
    private void typeToSkipPage() {
        if (null != certificationGuideBean && certificationGuideBean.target_tag > 0) {
            Constant.setIsStep(true);
            switch (certificationGuideBean.target_tag) {
                case TYPE_FACE_ID:
                    //身份认证
                    ARouter.getInstance().build(ArouterUtil.IDENTITY_CERTIFICATION).navigation();
                    break;
                case TYPE_CONTACT:
                    //紧急联系人
                    ARouter.getInstance().build(ArouterUtil.EMERGENCY_CONTACT).navigation();
                    break;
                case TYPE__BANK:
                    //绑卡
                    ARouter.getInstance().build(ArouterUtil.BIND_BANK_CARD).withBoolean(BundleKey.ISCHANGE,false).navigation();
                    break;
                case TYPE__PHONE:
                    //聚信立 调到H5
                    ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL,certificationGuideBean.target_url).withBoolean(BundleKey.WEB_SET_SESSION,true).navigation();
                    break;
                case TYPE__TB:
                    //跳转魔盒
                    ARouter.getInstance().build(ArouterUtil.MAGIC_BOX).navigation();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public  boolean isOPenGps(final Context context) {
        LocationManager locationManager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toCertificationPresent != null)
            toCertificationPresent.detach();
    }
}
