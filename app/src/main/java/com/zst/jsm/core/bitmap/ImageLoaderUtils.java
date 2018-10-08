package com.zst.jsm.core.bitmap;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zst.jsm.R;
import com.zst.jsm_base.view.BannerLayout;


/**
 * ImageLoaderUtils
 *
 * @author ZhongDaFeng
 */
public class ImageLoaderUtils implements BannerLayout.ImageLoader{

    public static void load(Context context, String url, ImageView imageView) {
        if (context != null) {
            Glide.with(context).load(url).into(imageView);
        }
    }

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
