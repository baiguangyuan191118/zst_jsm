package com.zst.ynh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zst.ynh.R;

public class UpLoadProgressBar extends View {
    private Context context;
    private Paint mTextPaint;
    private String textTitle;
    private int mTextSize;
    private int progressFrontColor;//前景色
    private int progressBackColor;//背景色
    private Paint mBackPaint;
    private Paint mFrontPaint;
    private Rect mBound;
    private int startLineY;
    private int maxProgress=100;
    private int progress;
    private Path path;

    public UpLoadProgressBar(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public UpLoadProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UpLoadProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height;

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mTextPaint.setTextSize(mTextSize);
            mTextPaint.getTextBounds(textTitle, 0, textTitle.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }
        setMeasuredDimension(widthSize, height);
    }

    private void init() {
        path=new Path();
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextSize = 16;
        textTitle="0%";
        mBound = new Rect();
        progressBackColor= R.color.progress_back;
        progressFrontColor=R.color.progress_front;
        mBackPaint=new Paint();
        mBackPaint.setColor(progressBackColor);
        mFrontPaint=new Paint();
        mFrontPaint.setColor(progressFrontColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        //画背景
        canvas.drawRect(0,getPaddingTop(),getMeasuredWidth(),getHeight(),mBackPaint);
        //画前景
        canvas.drawRect(0,getPaddingTop(),(100/getWidth()*progress),getHeight(),mFrontPaint);
        if (progress<maxProgress){
            canvas.drawText(maxProgress+"%",getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2,mTextPaint);
        }else if(progress==maxProgress){
            canvas.drawText("已完成",getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2,mTextPaint);
        }else{
            canvas.drawText("上传失败",getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2,mTextPaint);
        }
    }
    public void setPorgress(int progress){
        this.progress=progress;
        invalidate();
    }
}
