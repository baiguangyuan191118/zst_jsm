package com.zst.jsm.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.zst.jsm.R;


public class StatementDialog {
	private Context context;
	private Activity activity;
	private Dialog dialog;
	private LinearLayout lLayout_bg;
	private TextView txt_title;
	private TextView txt_msg;
	private Display display;
	private ImageView close;

	public StatementDialog(Context context) {
		this.context = context;
		this.activity = null;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public StatementDialog(Activity activity) {
		this.activity = activity;
		this.context = activity;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public Activity getActivity(){
		return activity;
	}

	public StatementDialog builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.view_statement_dialog, null);

		// 获取自定义Dialog布局中的控件
		lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_msg = (TextView) view.findViewById(R.id.txt_msg);
		close = (ImageView) view.findViewById(R.id.close);
		txt_title.setVisibility(View.GONE);
		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.progress_dialog);
		dialog.setContentView(view);

		// 调整dialog背景大小
		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
				.getWidth() * 0.70), LayoutParams.WRAP_CONTENT));

		return this;
	}

	public StatementDialog setTitle(String title) {
		if ("".equals(title)) {
			txt_title.setText("");
			txt_title.setVisibility(View.GONE);
		} else {
			txt_title.setVisibility(View.VISIBLE);
			txt_title.setText(title);
		}
		return this;
	}

	public StatementDialog setMsg(String msg) {
		if ("".equals(msg)) {
			txt_msg.setText("");
		} else {
			txt_msg.setText(msg);
		}
		return this;
	}

	public StatementDialog setGravity(int gravity) {
		txt_msg.setGravity(gravity);
		return this;
	}

	public StatementDialog setMsg(String msg, boolean isLeft) {
		if ("".equals(msg)) {
			txt_msg.setText("");
		} else {
			txt_msg.setText(msg);
		}
		if(isLeft){txt_msg.setGravity(Gravity.LEFT);
		}

		return this;
	}

	public StatementDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	private void setLayout() {
		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	public void show() {
		setLayout();
		dialog.show();
	}

	public boolean isShowing(){
		if(dialog != null && dialog.isShowing()){
			return true;
		}else{
			return false;
		}
	}
	public void dissMiss(){
		dialog.dismiss();
	}
}
