package com.zst.gesturelock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zyyoona7 on 2017/7/7.
 */

public class JDLockView extends View implements ILockView {

    protected Paint mPaint;
    protected int mCurrentState = NO_FINGER;
    protected float mOuterRadius;
    protected float mInnerRadius;

    protected int innerMatchedColor = ColorSetting.innerMatchedColor;

    protected int innerUnMatchedColor = ColorSetting.innerUnMatchedColor;

    protected int outerMatchedColor = ColorSetting.outerMatchedColor;

    protected int outerUnMatchedColor = ColorSetting.outerUnMatchedColor;


    public JDLockView(Context context) {
        this(context, null);
    }

    public JDLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        width = width > height ? height : width;
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Log.d("Yxjie", "innerMatchedColor==android.R.color.black:" + (innerMatchedColor == android.R.color.black));

        super.onDraw(canvas);
        float space = 10;
        float x = getWidth() / 2;
        float y = getHeight() / 2;
        canvas.translate(x, y);
        mOuterRadius = x - space;
        mInnerRadius = (x - space) / 3;
        switch (mCurrentState) {
            case NO_FINGER:
                drawNoFinger(canvas);
                break;
            case FINGER_TOUCH:
                drawFingerTouch(canvas);
                break;
            case FINGER_UP_MATCHED:
                drawFingerUpMatched(canvas);
                break;
            case FINGER_UP_UN_MATCHED:
                drawFingerUpUnmatched(canvas);
                break;
        }
    }

    /**
     * 画无手指触摸状态
     *
     * @param canvas
     */
    protected void drawNoFinger(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(innerMatchedColor);
        canvas.drawCircle(0, 0, mInnerRadius, mPaint);
    }

    /**
     * 画手指触摸状态
     *
     * @param canvas
     */
    protected void drawFingerTouch(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(outerMatchedColor);
        canvas.drawCircle(0, 0, mOuterRadius, mPaint);//先画外层园 再画内侧圆
        mPaint.setColor(innerMatchedColor);
        canvas.drawCircle(0, 0, mInnerRadius, mPaint);
    }

    /**
     * 画手指抬起，匹配状态
     *
     * @param canvas
     */
    private void drawFingerUpMatched(Canvas canvas) {
        drawFingerTouch(canvas);
    }

    /**
     * 画手指抬起，不匹配状态
     *
     * @param canvas
     */
    protected void drawFingerUpUnmatched(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(outerUnMatchedColor);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(ConvertUtils.dp2px(getContext(),1));
        canvas.drawCircle(0, 0, mOuterRadius, mPaint);
        mPaint.setColor(innerUnMatchedColor);
        canvas.drawCircle(0, 0, mInnerRadius, mPaint);
    }


    @Override
    public View getView() {
        return this;
    }

    @Override
    public View newInstance(Context context) {
        return new JDLockView(context);
    }

    @Override
    public void onNoFinger() {
        mCurrentState = NO_FINGER;
        postInvalidate();
    }

    @Override
    public void onFingerTouch() {
        mCurrentState = FINGER_TOUCH;
        postInvalidate();
    }

    @Override
    public void onFingerUpMatched() {
        mCurrentState = FINGER_UP_MATCHED;
        postInvalidate();
    }

    @Override
    public void onFingerUpUnmatched() {
        mCurrentState = FINGER_UP_UN_MATCHED;
        postInvalidate();
    }
}
