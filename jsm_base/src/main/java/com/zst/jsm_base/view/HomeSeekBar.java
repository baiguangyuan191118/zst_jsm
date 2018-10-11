package com.zst.jsm_base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;

/**
 * Created by User on 2016/9/14.
 */
public class HomeSeekBar extends android.support.v7.widget.AppCompatSeekBar {
    private Paint mPaint= new Paint();
    private int mTextSize=SizeUtils.sp2px(14);
    private Rect rect=new Rect();
    private Rect mTextBound=new Rect();

    private String mTitle;


    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public HomeSeekBar(Context context) {
        super(context);
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);
    }
    public HomeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public HomeSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
    private int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
//            /**
//             * descent()是文字的底部y坐标, ascent()是文字顶部y坐标
//             * 所以两者之差即为文字高度
//             */
//            int textHeight = (int) (mReachHeight+10+DEFAULT_TEXT_SIZE*2);
//            /**
//             * 三者之中的最大值即为我们所绘制的view的高度.
//             */
//            result = Math.max(Math.max(mReachHeight, mUNReachHeight), Math.abs(textHeight));
            result = getPaddingTop() + getPaddingBottom() + getHeight() + mTextBound.height();
            /**
             * 如果为wrap_content的话,则result取计算result和size的最小值
             */
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.CYAN);
    }

    private OnSeekBarChangeListener mOnSeekBarChangeListener;

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        mOnSeekBarChangeListener = l;
        super.setOnSeekBarChangeListener(l);
    }
}
