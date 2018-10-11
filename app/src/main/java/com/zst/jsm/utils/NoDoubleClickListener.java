package com.zst.jsm.utils;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.Calendar;

/**
 * 防抖点击  需要这样处理的使用这个
 */
public abstract class NoDoubleClickListener implements OnClickListener {

	public static final int MIN_CLICK_DELAY_TIME = 600;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        } 
    }
    
    public abstract void onNoDoubleClick(View view);

}
