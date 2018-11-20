package com.zst.gesturelock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by gaochujia on 03/04/2018.
 *
 * 手势密码生日解锁
 */

public class BirthdayGestureLockView extends JDLockView {

    private Bitmap mBirthdayBitmap;
    private Rect mSrcRect, mDisRect;

    public BirthdayGestureLockView(Context context) {
        super(context);
        mBirthdayBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lock_birthday);
        mSrcRect = new Rect();
        mDisRect = new Rect();
    }


    /**
     * 画手指触摸状态
     *
     * @param canvas
     */
    @Override
    protected void drawFingerTouch(Canvas canvas) {
        if (mBirthdayBitmap != null) {
            mPaint.setStyle(Paint.Style.FILL);

            mPaint.setColor(Color.parseColor("#6ecae4"));
            canvas.drawCircle(0, 0, mInnerRadius, mPaint);

            int width = (int) (mOuterRadius * 2);
            mDisRect.set(-width / 2, -width / 2, width / 2, width / 2);
            mSrcRect.set(0, 0, width, width);
            canvas.drawBitmap(mBirthdayBitmap, mSrcRect, mDisRect, mPaint);

        } else {
            super.drawFingerTouch(canvas);
        }

    }


    @Override
    public View newInstance(Context context) {
        return new BirthdayGestureLockView(context);
    }
}
