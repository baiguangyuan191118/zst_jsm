package com.zst.ynh.megvii.idcardlib.util;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class MyTextView extends AppCompatTextView {
	public MyTextView(Context context) {
		super(context);
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 倾斜度45,上下左右居中
		canvas.rotate(90, getMeasuredWidth() * 10 / 25, getMeasuredHeight() * 10 / 25);
		super.onDraw(canvas);
	}
}
