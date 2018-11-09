package com.zst.ynh.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.zst.ynh.R;
import com.zst.ynh.adapter.BankListAdapter;
import com.zst.ynh.bean.BankBean;
import com.zst.ynh.utils.NoDoubleClickListener;

import java.util.List;


public class BankListDialog extends Dialog {

    public BankListDialog(Context context) {
        super(context);
    }

    public BankListDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        BankListDialog dialog;
        private Context context;
        private List<BankBean.BankItem> list;
        private ListView banklist;
        private String tips;

        public Builder(Context context, List<BankBean.BankItem> list, String tips) {
            super();
            this.context = context;
            this.list = list;
            this.tips = tips;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public List<BankBean.BankItem> getList() {
            return list;
        }

        public void setList(List<BankBean.BankItem> list) {
            this.list = list;
        }

        public ListView getBanklist() {
            return banklist;
        }

        public BankListDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            dialog = new BankListDialog(context,
                    R.style.AlertDialogStyle);
            dialog.setCanceledOnTouchOutside(false);
            View layout = inflater.inflate(R.layout.dialog_bank_list_layout, null);
            View iv_back = layout.findViewById(R.id.iv_back);
            TextView tv_tip = layout.findViewById(R.id.tv_tip);
            if (TextUtils.isEmpty(tips)) {
                tv_tip.setVisibility(View.GONE);
            } else {
                tv_tip.setText(tips);
            }
            dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, (int) (ScreenUtils.getScreenHeight() * 0.75)));
            banklist = layout.findViewById(R.id.lv_bank_list);
            banklist.setAdapter(new BankListAdapter(context, R.layout.item_bank_list_layout, list));
            iv_back.setOnClickListener(new NoDoubleClickListener() {

                @Override
                public void onNoDoubleClick(View view) {
                    dialog.dismiss();
                }
            });
            setListViewHeightBasedOnChildren(banklist);
            return dialog;
        }

        private void setListViewHeightBasedOnChildren(ListView listView) {
            // 获取ListView对应的Adapter
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
                View listItem = listAdapter.getView(i, null, listView);
                listItem.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                listItem.measure(0, 0); // 计算子项View 的宽高
                totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
            }
            LayoutParams params = listView.getLayoutParams();
            int height = totalHeight
                    + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            // listView.getDividerHeight()获取子项间分隔符占用的高度
            if (height > ScreenUtils.getScreenHeight() * 0.6) {
                params.height = (int) (ScreenUtils.getScreenHeight() * 0.6);
            } else {
                params.height = height;
            }
            // params.height最后得到整个ListView完整显示需要的高度
            listView.setLayoutParams(params);
        }

    }
}
