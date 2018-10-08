package com.zst.jsm_base.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * Created by User on 2016/9/14.
 */
public class HomeSeekBar extends android.support.v7.widget.AppCompatSeekBar
{
    Drawable mThumb;

    public HomeSeekBar(Context context) {
        super(context);
    }
    public HomeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public HomeSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
        mThumb = thumb;
    }
    public Drawable getSeekBarThumb() {
        return mThumb;
    }

    @Override
    public synchronized void setProgress(int progress)
    {
        super.setProgress(progress);
        if(progress == getProgress()){
            if (mOnSeekBarChangeListener != null) {
                mOnSeekBarChangeListener.onProgressChanged(this, getProgress(), false);
            }
        }
    }

    private OnSeekBarChangeListener mOnSeekBarChangeListener;

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        mOnSeekBarChangeListener = l;
        super.setOnSeekBarChangeListener(l);
    }
}
