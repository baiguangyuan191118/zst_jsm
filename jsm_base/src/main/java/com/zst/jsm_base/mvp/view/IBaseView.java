package com.zst.jsm_base.mvp.view;

public interface IBaseView {
    void showLoadingView();
    void hideLoadingView();
    void ToastErrorMessage(String msg);
}
