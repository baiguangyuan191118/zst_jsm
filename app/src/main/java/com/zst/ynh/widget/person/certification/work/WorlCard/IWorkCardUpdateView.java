package com.zst.ynh.widget.person.certification.work.WorlCard;

import com.zst.ynh.bean.PicItemBean;
import com.zst.ynh_base.mvp.view.IBaseView;

public interface IWorkCardUpdateView extends IBaseView {
    void getPicList(PicItemBean picItemBean);
    void loadContent();
    void loadError();
    void loadLoading();
}
