package com.zst.ynh.widget.person.certification.incertification.itemView;

import android.animation.ObjectAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zst.ynh.R;
import com.zst.ynh.bean.InCertificationBean;
import com.zst.ynh.view.AnimCustomerProgressBar;
import com.zst.ynh_base.adapter.recycleview.base.ItemViewDelegate;
import com.zst.ynh_base.adapter.recycleview.base.ViewHolder;

public class HeadViewHolder implements ItemViewDelegate<InCertificationBean.ItemBean.ListBean> {
    private AnimCustomerProgressBar progressBar;
    private TextView mTv_quota_auth_title, mTv_quota_auth_text;
    private LinearLayout rlToAuth;
    private RelativeLayout rlBaseAuthed,  rlAuthFinally;
    /**
     * 认证中动画的状态
     */
    private boolean isAuthRotate;
    //旋转动画
    private ObjectAnimator animatorRotate;
    private ImageView mIv_quota_auth_rotateimg;
    private TextView tv_quota_title1, tv_quota_title;
    private TextView tv_auth_text, tv_auth_title;
    private InCertificationBean inCertificationBean;


    public HeadViewHolder(InCertificationBean inCertificationBean){
        this.inCertificationBean=inCertificationBean;
    }
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_head_recycle_layout;
    }

    @Override
    public boolean isForViewType(InCertificationBean.ItemBean.ListBean item, int position) {
        if (item.type==-1)
            return true;
        else
            return false;
    }

    @Override
    public void convert(ViewHolder holder, InCertificationBean.ItemBean.ListBean listBean, int position) {
        progressBar = holder.getView(R.id.progress);
        rlBaseAuthed = holder.getView(R.id.rl_base_authed);
        rlToAuth = holder.getView(R.id.rl_to_base_auth);
        rlAuthFinally = holder.getView(R.id.rl_base_auth_finally);
        mIv_quota_auth_rotateimg = holder.getView(R.id.iv_quota_auth_rotateimg);
        mTv_quota_auth_title = holder.getView(R.id.tv_quota_auth_title);
        mTv_quota_auth_text =holder.getView(R.id.tv_quota_auth_text);
        tv_quota_title1 = holder.getView(R.id.tv_quota_title1);
        tv_quota_title = holder.getView(R.id.tv_quota_title);
        rlAuthFinally = holder.getView(R.id.rl_base_auth_finally);
        tv_auth_text = holder.getView(R.id.txt_auth_text);
        tv_auth_title = holder.getView(R.id.txt_auth_title);
        initAnimator();
        setHeadData();
    }

    /**
     * 旋转动画的初始化
     */
    private void initAnimator() {
        animatorRotate = ObjectAnimator.ofFloat(mIv_quota_auth_rotateimg, "rotation", 360);
        animatorRotate.setRepeatCount(-1);
        animatorRotate.setDuration(1200);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        animatorRotate.setInterpolator(linearInterpolator);
    }

    /**
     * 设置head数据
     *
     */
    public void setHeadData() {
        if (inCertificationBean.item.header != null) {
            if (!TextUtils.isEmpty(inCertificationBean.item.header.title)) {
                tv_quota_title.setText(inCertificationBean.item.header.title);
                tv_quota_title1.setText(inCertificationBean.item.header.title);
            }
            switch (inCertificationBean.item.header.status) {
                case 1:
                    // 认证已完成，可立即开卡
                    rlToAuth.setVisibility(View.VISIBLE);
                    rlBaseAuthed.setVisibility(View.GONE);
                    rlAuthFinally.setVisibility(View.GONE);
                    progressBar.setProgressInTime(0, inCertificationBean.item.header.cur_progress, 800);
                    isAuthRotate = false;
                    break;
                case 2:
                    // 认证中: 额度计算中，预计需要5-10分钟，请耐心等待
                    rlToAuth.setVisibility(View.GONE);
                    rlBaseAuthed.setVisibility(View.VISIBLE);
                    rlAuthFinally.setVisibility(View.GONE);
                    mTv_quota_auth_title.setText("极速");
                    mTv_quota_auth_text.setText(inCertificationBean.item.header.active_title);
                    if (!isAuthRotate) {
                        mIv_quota_auth_rotateimg.clearAnimation();
                        mIv_quota_auth_rotateimg.setImageResource(R.mipmap.quota_rotate_auth);
                        animatorRotate.start();
                        isAuthRotate = true;
                    }
                    break;
                case 3:
                    //认证完成
                    rlToAuth.setVisibility(View.GONE);
                    rlBaseAuthed.setVisibility(View.GONE);
                    rlAuthFinally.setVisibility(View.VISIBLE);
                    tv_auth_text.setText(inCertificationBean.item.header.data);
                    tv_auth_title.setText(inCertificationBean.item.header.title);
                    rlAuthFinally.setEnabled(false);
                    mIv_quota_auth_rotateimg.clearAnimation();
                    break;
                default:
                    break;

            }
        }
    }
}
