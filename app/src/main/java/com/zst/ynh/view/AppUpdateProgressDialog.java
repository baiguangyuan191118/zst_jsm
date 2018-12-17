package com.zst.ynh.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zst.ynh.R;
import com.zst.ynh.megvii.livenesslib.view.AnimProgressBar;

public class AppUpdateProgressDialog extends AlertDialog {


    AnimProgressBar progressView;
    private TextView update;
    private String contents;
    private TextView container;
    private LinearLayout rlFirst, rlSecond;
    private Context context;

    public AppUpdateProgressDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);
        WindowManager.LayoutParams params=new WindowManager.LayoutParams();
        params.gravity=Gravity.CENTER;
        params.width=WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(params);
        this.getWindow().setBackgroundDrawableResource(R.color.transparent);
        initViews();
    }

    private void initViews(){
        progressView = this.findViewById(R.id.progress);
        container = this.findViewById(R.id.tv_container);
        if (!TextUtils.isEmpty(contents)) {
            container.setText(contents);
        }
        update = this.findViewById(R.id.tv_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlSecond.setVisibility(View.VISIBLE);
                rlFirst.setVisibility(View.GONE);
                if (null != updateStartCallBack) {
                    updateStartCallBack.start();
                }
            }
        });
        rlFirst = this.findViewById(R.id.rl_first);
        rlSecond =this.findViewById(R.id.rl_second);
    }

    public void updateProgress(int progress) {
        if (progressView != null) {
            progressView.setProgress(progress);
        }
    }

    public void setTotal(int total) {
        if (progressView != null) {
            progressView.setMax(total);
        }
    }

    public void setContent(String contents) {
        this.contents = contents;
    }

    private UpdateStartCallBack updateStartCallBack;

    public void setUpdateStartCallBack(UpdateStartCallBack updateStartCallBack) {
        this.updateStartCallBack = updateStartCallBack;
    }

    public interface UpdateStartCallBack {
        void start();
    }

}
