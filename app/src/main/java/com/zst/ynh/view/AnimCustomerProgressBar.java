package com.zst.ynh.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import com.zst.ynh.R;

/**
 * Created by Kitterick on 2017/9/4.
 */

public class AnimCustomerProgressBar extends View {
    private Paint mPaint;
    private Paint mPaintBg;
    private Paint mPaintDot;
    private int mWidth;
    private int total = 100;
    private int current = 0;
    Rect rect;
    /**
     * 要绘制的bitmap
     */
    private Bitmap mBitmap;


    public AnimCustomerProgressBar(Context context) {
        this(context, null);
        init(context);
    }

    public AnimCustomerProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintDot = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(dip2px(this.getContext(), 5));
        mPaintBg.setStrokeWidth(dip2px(this.getContext(), 5));
        mPaintDot.setStrokeWidth(dip2px(this.getContext(), 10));
        mPaintDot.setColor(Color.parseColor("#ffffff"));
        mPaintDot.setStrokeCap(Paint.Cap.ROUND);
        mPaintDot.setStyle(Paint.Style.FILL);


        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaintBg.setColor(Color.parseColor("#21B0D9"));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintBg.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaintBg.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setTextSize(dip2px(this.getContext(), 10));
        Resources res = getResources();
        mBitmap = BitmapFactory.decodeResource(res, R.mipmap.quota_progress);
        rect = new Rect();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = this.getMeasuredSize(widthMeasureSpec, true);
        int height = this.getMeasuredSize(heightMeasureSpec, false);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float rate = 1.0f * current / total;
        int temp = mWidth - mBitmap.getWidth();
        int h15 = dip2px(this.getContext(), 10);
        mPaint.setStrokeWidth(dip2px(this.getContext(), 0.5f));
        canvas.drawBitmap(mBitmap, rate * temp, 0, mPaint);
        String rank = String.valueOf(current) + "%";
        mPaint.getTextBounds(rank, 0, rank.length(), rect);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getResources().getColor(R.color.theme_color));
        canvas.drawText(rank, mBitmap.getWidth() / 2 + rate * temp - rect.width() / 2, mBitmap.getHeight() / 2, mPaint);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(dip2px(this.getContext(), 5));
        mPaint.setColor(Color.parseColor("#ffffff"));
        canvas.drawLine(mBitmap.getWidth() / 2, mBitmap.getHeight() + h15, mBitmap.getWidth() / 2 + temp, mBitmap.getHeight() + h15, mPaintBg);
        canvas.drawLine(mBitmap.getWidth() / 2, mBitmap.getHeight() + h15, mBitmap.getWidth() / 2 + temp * rate, mBitmap.getHeight() + h15, mPaint);

        canvas.drawPoint(mBitmap.getWidth() / 2 + temp * rate, mBitmap.getHeight() + h15, mPaintDot);
    }

    public int dip2px(Context context, float dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public void setProgressInTime(int startProgress, final int progress, final long duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startProgress, progress);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //获得当前动画的进度值，整型，1-100之间
                int currentValue = (Integer) animator.getAnimatedValue();
                setProgress(currentValue);
            }
        });

        AccelerateInterpolator interpolator = new AccelerateInterpolator();
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public void setProgress(int current) {
        this.current = current;
        postInvalidate();
    }

    private int getMeasuredSize(int length, boolean isWidth) {
        // 模式
        int specMode = MeasureSpec.getMode(length);
        // 尺寸
        int specSize = MeasureSpec.getSize(length);
        // 计算所得的实际尺寸，要被返回
        int retSize = 0;
        // 得到两侧的padding（留边）
        int padding = (isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom());

        // 对不同的指定模式进行判断
        if (specMode == MeasureSpec.EXACTLY) {  // 显式指定大小，如40dp或match_parent
            retSize = specSize;
        } else {                              // 如使用wrap_content
            retSize = (isWidth ? mWidth + padding : (int) (mWidth * 0.4) + padding);
            if (specMode == MeasureSpec.UNSPECIFIED) {
                retSize = Math.min(retSize, specSize);
            }
        }

        return retSize;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        DisplayMetrics displayMetrics = this.getContext().getResources()
                .getDisplayMetrics();
        mWidth = w;
    }
}
