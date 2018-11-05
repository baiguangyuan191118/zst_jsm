package com.zst.ynh.utils;

import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class NoLineClickSpan extends ClickableSpan {
    private onSpanClick onSpanClick;

    public NoLineClickSpan(onSpanClick onSpanClick) {
        this.onSpanClick = onSpanClick;
    }

    @Override
    public void onClick(@NonNull View widget) {
        onSpanClick.onSpanClick();
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        /**set textColor**/
        ds.setColor(ds.linkColor);
        /**Remove the underline**/
        ds.setUnderlineText(false);
    }

    public interface onSpanClick {
        void onSpanClick();
    }
}
