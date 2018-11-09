package com.zst.ynh.widget.person.certification.banklist;

import com.zst.ynh_base.mvp.view.IBaseView;

public interface IBankListView extends IBaseView {
    void getBankListData();
    void loadContent();
    void loadError();
    void loadLoading();
}
