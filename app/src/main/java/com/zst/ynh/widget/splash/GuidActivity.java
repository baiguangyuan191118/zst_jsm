package com.zst.ynh.widget.splash;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.zst.ynh.R;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;

import butterknife.BindView;

@Layout(R.layout.activity_guide)
@Route(path=ArouterUtil.GUIDE)
public class GuidActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private GuidePagerAdapter pagerAdapter;
    int[] images = new int[]{R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3};
    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        mTitleBar.setVisibility(View.GONE);
        pagerAdapter = new GuidePagerAdapter();
        viewPager.setAdapter(pagerAdapter);
    }

    class GuidePagerAdapter extends PagerAdapter {
        private ImageView[] views=new ImageView[images.length];
        public GuidePagerAdapter() {
        }

        @NonNull
        private ImageView getImageView(int i) {
            ImageView view = new ImageView(GuidActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setImageResource(images[i]);
            if (i == images.length - 1) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SPUtils.getInstance().put(SPkey.FIRST_IN,false);
                        ARouter.getInstance().build(ArouterUtil.MAIN).withSerializable(BundleKey.MAIN_DATA, getIntent().getSerializableExtra(BundleKey.MAIN_DATA)).navigation();
                        GuidActivity.this.finish();
                    }
                });
            }
            return view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = getImageView(position);
            container.addView(view);
            views[position] = view;
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views[position]);
            views[position] = null;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }
    }

}
