package com.zst.jsm_base.mvp.view;

public interface IBaseView {
    void showLoading();
    void hideLoading();
    void ToastErrorMessage(String msg);
}
