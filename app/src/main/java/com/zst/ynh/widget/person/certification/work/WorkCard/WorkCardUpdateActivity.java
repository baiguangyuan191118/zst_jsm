package com.zst.ynh.widget.person.certification.work.WorkCard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.zst.ynh.R;
import com.zst.ynh.adapter.PicListAdapter;
import com.zst.ynh.base.UMBaseActivity;
import com.zst.ynh.bean.PicItemBean;
import com.zst.ynh.bean.UploadPicBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.DialogUtil;
import com.zst.ynh.view.ShowImageDialog;
import com.zst.ynh.view.SpaceItemDecoration;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.uploadimg.ProgressUIListener;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.util.UploadImgUtil;
import com.zst.ynh_base.view.BaseDialog;
import com.zst.ynh_base.view.BottomMenuDialog;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;


@Route(path = ArouterUtil.WORK_UPLOAD_PIC)
@Layout(R.layout.activity_work_upload_pic)
public class WorkCardUpdateActivity extends UMBaseActivity implements IWorkCardUpdateView, TakePhoto.TakeResultListener, InvokeListener {

    private static final String tag = WorkCardUpdateActivity.class.getSimpleName();

    @BindView(R.id.pic_recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_upload)
    Button btnUpload;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private WorkCardUpdatePresent workCardUpdatePresent;
    private PicListAdapter picListAdapter;
    private String type;//上传的类型
    private ArrayList<UploadPicBean> uploadPicBeans = new ArrayList<>();
    private int max_upload_pic;//最大上传输

    public final static int Type_None = 0;// 显示图片
    public final static int Type_Add = 1;// 显示添加
    public final static int Type_TakePhoto = 2;// 显示拍照
    public final static int Type_Uploaded = 3;//上传完成
    public final static int Type_Uploading = 4;//上传中
    public final static int Type_UploadFailed = 5;//上传失败

    @Override
    public void onRetry() {
        workCardUpdatePresent.getPicList(type);
    }

    @Override
    public void initView() {
        mTitleBar.setTitle("工作证照");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration(ConvertUtils.dp2px(5)));
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();

            }
        });

        type = getIntent().getStringExtra(BundleKey.WORK_PIC_TYPE);
        workCardUpdatePresent = new WorkCardUpdatePresent();
        workCardUpdatePresent.attach(this);
        workCardUpdatePresent.getPicList(type);

    }

    private void uploadImage() {
        boolean flag = false;
        for (int i = 0; i < uploadPicBeans.size(); i++) {
            UploadPicBean selectPicBean = uploadPicBeans.get(i);
            if (selectPicBean == null || selectPicBean.url == null) {
                continue;
            }
            if (selectPicBean.type == Type_None || selectPicBean.type == Type_UploadFailed) {
                flag = true;
                UploadImgUtil.FileBean bean = new UploadImgUtil.FileBean();
                bean.addExtraParms("type", type);
                Uri uri = Uri.parse(selectPicBean.url);
                bean.setFileSrc(uri.getPath());
                try {
                    String sessionid = SPUtils.getInstance().getString(SPkey.USER_SESSIONID);
                    MyProgressUIListener myProgressUIListener = new MyProgressUIListener();
                    myProgressUIListener.setPosition(i);
                    UploadImgUtil.upLoadImg(ApiUrl.UPLOAD_IMAGE, sessionid, bean, myProgressUIListener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        if (!flag) {
            ToastUtils.showShort("无新增图片");
            return;
        }
    }

    private class MyProgressUIListener extends ProgressUIListener {

        @Override
        public void onSuccess(Call call, Response response) {
            Log.d(tag, "onSuccess");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    uploadPicBeans.get(MyProgressUIListener.this.getPosition()).type = Type_Uploaded;
                    picListAdapter.notifyDataSetChanged();
                }
            });

        }

        @Override
        public void onFailed(Call call, final Exception exception) {
            Log.d(tag, "onFailed");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showShort(exception.getMessage());
                    uploadPicBeans.get(MyProgressUIListener.this.getPosition()).type = Type_UploadFailed;
                    picListAdapter.notifyDataSetChanged();
                }
            });


        }

        @Override
        public void onUIProgressStart(long totalBytes) {
            super.onUIProgressStart(totalBytes);
            Log.d(tag, "onUIProgressStart");
            uploadPicBeans.get(this.getPosition()).type = Type_Uploading;
            uploadPicBeans.get(this.getPosition()).progress = 0;
            picListAdapter.notifyDataSetChanged();

        }

        @Override
        public void onUIProgressFinish() {
            super.onUIProgressFinish();
            Log.d(tag, "onUIProgressFinish");
            uploadPicBeans.get(this.getPosition()).type = Type_Uploading;
            uploadPicBeans.get(this.getPosition()).progress = 100;
            picListAdapter.notifyDataSetChanged();
        }

        @Override
        public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
            Log.d(tag, "numBytes:" + numBytes + ",totalBytes:" + totalBytes + ",percent:" + percent + ",speed:" + speed);
            uploadPicBeans.get(this.getPosition()).type = Type_Uploading;
            uploadPicBeans.get(this.getPosition()).progress = (int) (percent * 100);
            picListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (workCardUpdatePresent != null) {
            workCardUpdatePresent.detach();
        }
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        if (deleteDialog != null) {
            deleteDialog.dismiss();
            deleteDialog = null;
        }
        if (choosePicDialog != null) {
            choosePicDialog.dismiss();
            choosePicDialog = null;
        }

        if (showImageDialog != null) {
            showImageDialog.dismiss();
            showImageDialog = null;
        }

        if(exitDialog!=null){
            exitDialog.dismiss();
            exitDialog=null;
        }

    }

    private BaseDialog exitDialog;
    @Override
    public void onBackPressed() {

        boolean isUnupload=false;
        for(UploadPicBean uploadPicBean:uploadPicBeans){
            if(uploadPicBean.type==Type_None || uploadPicBean.type==Type_UploadFailed || uploadPicBean.type==Type_Uploading){
             isUnupload=true;
            }
        }
        if(isUnupload){
            if(exitDialog==null){
                exitDialog=new BaseDialog.Builder(this).setCancelable(false).setContent1("有图片未上传,是否继续退出?")
                        .setBtnRightText("退出").setRightOnClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exitDialog.dismiss();
                                finish();
                            }
                        }).setBtnLeftText("取消").setLeftOnClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exitDialog.dismiss();
                            }
                        }).create();
            }

            exitDialog.show();
        }
        else{
            super.onBackPressed();
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
        if (result == null) {
            ToastUtils.showShort("图片保存失败");
        } else {
            String path = result.getImage().getCompressPath();
            if (selectPicBean != null) {
                selectPicBean.type = Type_Add;
            }
            UploadPicBean uploadPicBean = new UploadPicBean();
            uploadPicBean.type = Type_None;
            uploadPicBean.url = "file://" + path;
            uploadPicBean.pic_name = path.substring(path.lastIndexOf("/") + 1);
            uploadPicBeans.add(uploadPicBean);
            Log.d("updatePic", "add:" + uploadPicBean.url);
            picListAdapter.notifyDataSetChanged();

        }

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

        uploadPicBeans.clear();
        UploadPicBean dataBean = new UploadPicBean();
        if (picItemBean.item.data.size() == 0) {
            dataBean.type = Type_TakePhoto;
        } else {
            dataBean.type = Type_Add;
        }
        uploadPicBeans.add(dataBean);
        for (PicItemBean.ItemBean.DataBean d : picItemBean.item.data) {
            UploadPicBean uploadPicBean = new UploadPicBean();
            uploadPicBean.id = d.id;
            uploadPicBean.pic_name = d.pic_name;
            uploadPicBean.type = Type_Uploaded;
            uploadPicBean.url = d.url;
            uploadPicBean.progress = 100;
            uploadPicBeans.add(uploadPicBean);
        }

        max_upload_pic = picItemBean.item.max_pictures;

        if (picListAdapter == null) {
            picListAdapter = new PicListAdapter(this, R.layout.item_upload_pic_layout, uploadPicBeans, 100);
            picListAdapter.setPicClickListener(itemClickListener);
            recyclerView.setAdapter(picListAdapter);
        }
    }

    private BaseDialog dialog;
    private UploadPicBean selectPicBean;
    private BottomMenuDialog choosePicDialog;
    private ShowImageDialog showImageDialog;
    private PicListAdapter.PicClickListener itemClickListener = new PicListAdapter.PicClickListener() {
        @Override
        public void onItemClick(UploadPicBean dataBean) {

            selectPicBean = dataBean;
            switch (selectPicBean.type) {
                case Type_TakePhoto:
                case Type_Add:

                    if (picListAdapter.getItemCount() - 1 == max_upload_pic) {

                        dialog = new BaseDialog.Builder(WorkCardUpdateActivity.this).setCancelable(false)
                                .setContent1("已超过图片上传个数")
                                .setBtnRightText("我知道了")
                                .setRightOnClick(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DialogUtil.hideDialog(dialog);
                                    }
                                }).create();
                        dialog.show();
                        return;
                    }

                    if (choosePicDialog == null) {
                        choosePicDialog = new BottomMenuDialog.Builder(WorkCardUpdateActivity.this)
                                .setButtonNumber(1)
                                .setBtnTitle(new String[]{"拍照"})
                                .setCancelListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        choosePicDialog.dismiss();
                                    }
                                })
                                .setOnClickWithPosition(new BottomMenuDialog.IonItemClickListener() {
                                    @Override
                                    public void onItemClickListener(int position) {
                                        takePicFromCapture();
                                        choosePicDialog.dismiss();
                                    }
                                }).create();
                        choosePicDialog.show();
                    } else {
                        choosePicDialog.show();
                    }

                    break;

                default:
                    if (!StringUtils.isEmpty(selectPicBean.url)) {
                        showImageDialog = new ShowImageDialog(WorkCardUpdateActivity.this, selectPicBean.url);
                        showImageDialog.show();
                    }

                    break;
            }
        }

        @Override
        public void deleteItemListener(UploadPicBean dataBean) {
            selectPicBean = dataBean;
            showDeleteDialog();
        }


    };
    private BaseDialog deleteDialog;

    private void showDeleteDialog() {
        if (deleteDialog == null) {
            deleteDialog = new BaseDialog.Builder(this)
                    .setContent1("是否要删除？")
                    .setBtnRightText("删除").setBtnRightColor(this.getResources().getColor(R.color.red)).setRightOnClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            uploadPicBeans.remove(selectPicBean);
                            picListAdapter.notifyDataSetChanged();
                            deleteDialog.dismiss();
                        }
                    }).setBtnLeftText("取消").setLeftOnClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteDialog.dismiss();
                        }
                    }).create();
            deleteDialog.show();
        } else {
            deleteDialog.show();
        }
    }

    public void takePicFromCapture() {
        File file = new File(Environment.getExternalStorageDirectory(), "/" + getApplicationInfo().packageName + "/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        CompressConfig config = new CompressConfig.Builder().setMaxSize(480 * 853)
                .setMaxSize(102400)
                .create();
        takePhoto.onEnableCompress(config, false);
        takePhoto.onPickFromCapture(imageUri);
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
