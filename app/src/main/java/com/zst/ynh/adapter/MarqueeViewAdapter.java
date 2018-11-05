package com.zst.ynh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.stx.xmarqueeview.XMarqueeView;
import com.stx.xmarqueeview.XMarqueeViewAdapter;
import com.zst.ynh.R;

import java.util.List;

public class MarqueeViewAdapter extends XMarqueeViewAdapter<String> {
    private Context mContext;
    public MarqueeViewAdapter(List<String> datas, Context context) {
        super(datas);
        mContext = context;
    }
    @Override
    public View onCreateView(XMarqueeView parent) {
        //跑马灯单个显示条目布局，自定义
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ad_layout, null);
    }

    @Override
    public void onBindView(View parent, View view, final int position) {
        //布局内容填充
        TextView tvOne = view.findViewById(R.id.tv_marquee);
        tvOne.setText(mDatas.get(position));
    }
}
