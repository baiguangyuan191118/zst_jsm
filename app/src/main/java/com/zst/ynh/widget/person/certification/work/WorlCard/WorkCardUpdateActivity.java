package com.zst.ynh.widget.person.certification.work.WorlCard;

import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.zst.ynh.R;
import com.zst.ynh.adapter.PicListAdapter;
import com.zst.ynh.bean.PicItemBean;
import com.zst.ynh_base.mvp.view.BaseActivity;


public class WorkCardUpdateActivity extends BaseActivity implements IWorkCardUpdateView ,TakePhoto.TakeResultListener, InvokeListener {
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private WorkCardUpdatePresent workCardUpdatePresent;
    private PicListAdapter picListAdapter;
    private PicItemBean.DataBeanX.ItemBean.DataBean dataBean;


    @Override
    public void onRetry() {
        workCardUpdatePresent.getPicList();
    }

    @Override
    public void initView() {
        workCardUpdatePresent=new WorkCardUpdatePresent();
        workCardUpdatePresent.getPicList();
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

    @Override
    protected void onChildCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onChildCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        ToastUtils.showShort(result.getImage().getCompressPath());
    }

    @Override
    public void takeFail(TResult result, String msg) {
        ToastUtils.showShort("选取失败，请重试");
    }

    @Override
    public void takeCancel() {
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }


    @Override
    public void getPicList(PicItemBean picItemBean) {
        if (picListAdapter==null){
            picListAdapter=new PicListAdapter(this, R.layout.item_upload_pic_layout,picItemBean.data.item.data,100);
        }
    }

    @Override
    public void loadContent() {
        loadContentView();
    }

    @Override
    public void loadError() {
        loadErrorView();
    }

    @Override
    public void loadLoading() {
        loadLoadingView();
    }
}
