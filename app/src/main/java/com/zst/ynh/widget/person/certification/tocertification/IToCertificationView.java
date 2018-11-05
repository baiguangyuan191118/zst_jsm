package com.zst.ynh.widget.person.certification.tocertification;

import com.zst.ynh.bean.CertificationGuideBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IToCertificationView extends IBaseView {
    void getCertificationType(CertificationGuideBean certificationGuideBean);
}
