package com.zst.ynh.widget.person.certification.tocertification;

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
import com.zst.ynh.core.bitmap.ImageLoaderUtils;
import com.zst.ynh_base.mvp.view.BaseActivity;
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
        this.certificationGuideBean = certificationGuideBean;
    }


    @OnClick(R.id.btn_certification)
    public void onViewClicked() {
        //点击下一步的时候必须先同意gps定位的权限
        if (XXPermissions.isHasPermission(this,new String[]{ Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION})) {
            typeToSkipPage();
        } else {
            XXPermissions.with(this)
                    .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    .permission(new String[]{ Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION})
                    .request(new OnPermission() {

                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (isAll) {
                                typeToSkipPage();
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
            switch (certificationGuideBean.target_tag) {
                case TYPE_FACE_ID:
                    //身份认证
                    ARouter.getInstance().build(ArouterUtil.IDENTITY_CERTIFICATION).withBoolean(BundleKey.IS_FROM_TOCERTIFICATION,true).navigation();
                    break;
                case TYPE_CONTACT:
                    //紧急联系人
                    break;
                case TYPE__BANK:
                    //绑卡

                    break;
                case TYPE__PHONE:
                    //聚信立 调到H5
                    break;
                case TYPE__TB:
                    //跳转魔盒
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toCertificationPresent != null)
            toCertificationPresent.detach();
    }
}
