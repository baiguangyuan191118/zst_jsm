package com.zst.ynh.widget.person.certification.incertification.itemView;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zst.ynh.R;
import com.zst.ynh.bean.InCertificationBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.Constant;
import com.zst.ynh.core.bitmap.ImageLoaderUtils;
import com.zst.ynh_base.adapter.recycleview.base.ItemViewDelegate;
import com.zst.ynh_base.adapter.recycleview.base.ViewHolder;

import java.util.List;

public class itemViewHolder implements ItemViewDelegate<InCertificationBean.ItemBean.ListBean> {
    private TextView tvName, tvFlag;
    private ImageView ivPic;
    private int real_verify_status;
    private RelativeLayout rl_content;
    private Context context;
    private List<InCertificationBean.ItemBean.ListBean> datas;
    /**
     * 身份认证
     */
    private static final int ID_CARD = 1;
    /**
     * 联系方式
     */
    private static final int CONTACT_STYLE = 3;
    /**
     * 银行卡
     */
    private static final int BANK_CARD = 4;
    /**
     * 手机运营商
     */
    private static final int PHONE_CARRIER = 5;
    /**
     * 芝麻授信
     */
    private static final int MAGIC_BOX = 8;
    /**
     * 个人信息
     */
    private static final int PERSON_INFO = 16;
    /**
     * 工作信息
     */
    private static final int WORK_INFO = 2;
    /**
     * 更多认证(凡是不属于上方的 剩余的都是更多认证)
     */
    private static final int MORE_CERTIFICATION = -1;
    public itemViewHolder(Context context,List<InCertificationBean.ItemBean.ListBean> datas){
        this.context=context;
        this.datas=datas;
    }
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_content_recycle_layout;
    }

    @Override
    public boolean isForViewType(InCertificationBean.ItemBean.ListBean item, int position) {
        if (item.type == 1 || item.type == 3)
            return true;
        return false;
    }

    @Override
    public void convert(ViewHolder holder, InCertificationBean.ItemBean.ListBean listBean, final int position) {
        tvName = holder.getView(R.id.tv_name);
        tvFlag = holder.getView(R.id.tvFlag);
        ivPic =  holder.getView(R.id.image);
        rl_content =holder.getView(R.id.rl_content);
        setItemData(listBean);
        rl_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListener(datas.get(position));
            }
        });

    }

    private void setListener(InCertificationBean.ItemBean.ListBean listBean) {
        //表明是从认证中心进来的页面
        Constant.setIsStep(false);
        switch (listBean.tag) {
            case ID_CARD:
                ARouter.getInstance().build(ArouterUtil.IDENTITY_CERTIFICATION).navigation();
                break;
            case CONTACT_STYLE:
                ARouter.getInstance().build(ArouterUtil.EMERGENCY_CONTACT).navigation();
                break;
            case BANK_CARD:
                ARouter.getInstance().build(ArouterUtil.BANK_LIST).navigation();
                break;
            case PHONE_CARRIER:
                ARouter.getInstance().build(ArouterUtil.ABOUT_US).withString(BundleKey.URL,listBean.url).navigation();
                break;
            case MAGIC_BOX:
                ARouter.getInstance().build(ArouterUtil.MAGIC_BOX).navigation();
                break;
            case PERSON_INFO:
                ARouter.getInstance().build(ArouterUtil.PERSON_CERTIFICATION).navigation();
                break;
            case WORK_INFO:
                ARouter.getInstance().build(ArouterUtil.WORK_CERTIFICATION).navigation();
                break;
            default:
                //走更过认证
                ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL,listBean.url).navigation();
                break;
        }
    }
    /**
     * 设置item数据
     * @param listBean
     */
    public void setItemData(InCertificationBean.ItemBean.ListBean listBean){
        if (listBean != null) {
            tvFlag.setBackgroundResource(R.mipmap.bg_tips);
            if (!TextUtils.isEmpty(listBean.title)) {
                tvName.setText(listBean.title);
            }
            if (!TextUtils.isEmpty(listBean.logo)) {
                ImageLoaderUtils.loadUrl(context,listBean.logo,ivPic);
            }
            if (listBean.show_verify_tag == 1 && !TextUtils.isEmpty(listBean.verify_tag_content)) {
                if ("yellow".equals(listBean.logo_color)) {
                    tvFlag.setBackgroundResource(R.mipmap.bg_tips);
                } else if ("red".equals(listBean.logo_color)) {
                    tvFlag.setBackgroundResource(R.mipmap.bg_tips_red);
                }
                tvFlag.setVisibility(View.VISIBLE);
                tvFlag.setText(listBean.verify_tag_content);
            } else {
                tvFlag.setVisibility(View.GONE);
            }
        }
    }
}
