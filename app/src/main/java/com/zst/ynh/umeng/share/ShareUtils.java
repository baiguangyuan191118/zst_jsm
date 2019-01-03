package com.zst.ynh.umeng.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zst.ynh.R;

import java.io.File;

/*
* 分享Utils
* */
public class ShareUtils {
    private Activity context;

    public ShareUtils(Activity context) {
        this.context = context;
    }

    public void doShare(ShareBean shareBean, UMShareListener umShareListener,boolean isPane,SHARE_MEDIA... platforms){
        ShareAction shareAction = new ShareAction(context);
        if(isPane){
            shareAction.setDisplayList(platforms);
        }else{
            shareAction.setPlatform(platforms[0]);
        }

        shareAction.setCallback(umShareListener);

        if(shareBean instanceof ShareImageBean){
            UMWeb web = new UMWeb(shareBean.getTargetUrl());

            if(shareBean.getTitle() != null){
                web.setTitle(shareBean.getTitle());
            }
            if(shareBean.getText() != null){
                web.setDescription(shareBean.getText());
            }
            web.setThumb(getUMImage(context,((ShareImageBean) shareBean).getImage()));  //缩略图

            for(SHARE_MEDIA platform:platforms){
                if(SHARE_MEDIA.SMS == platform){
                    shareAction.withText(shareBean.getTitle()+shareBean.getTargetUrl());
                }else{
                    shareAction.withMedia(web);
                }
            }

        }

        if(shareBean instanceof ShareOnlyImageBean){
//			UMImage image = new UMImage(context,(String)((ShareOlnlyImageBean) shareBean).getImage());
//			image.setThumb(getUMImage(context,((ShareOlnlyImageBean) shareBean).getImage()));
//
            shareAction.withText(shareBean.getTitle()).withMedia(getUMImage(context,((ShareOnlyImageBean) shareBean).getImage()));
        }

        if(shareBean instanceof ShareMusicBean){
            shareAction.withMedia(((ShareMusicBean) shareBean).getMusic());
        }
        if(shareBean instanceof ShareVideoBean){
            shareAction.withMedia(((ShareVideoBean) shareBean).getVideo());
        }
        if(isPane){
            shareAction.open();
        }else{
            shareAction.share();
        }

    }

    private UMImage getUMImage(Context context, Object image) {
        UMImage umImage = null;
        if (image != null && !"".equals(image)) {
            if (image instanceof Bitmap) {
                umImage = new UMImage(context, (Bitmap) image);
            } else if (image instanceof String) {
                umImage = new UMImage(context, (String) image);
            } else if (image instanceof File) {
                umImage = new UMImage(context, (File) image);
            } else if (image instanceof Integer) {
                umImage = new UMImage(context, (Integer) image);
            }
        } else {
            umImage = new UMImage(context, R.mipmap.app_logo);
        }
        return umImage;
    }
}
