package com.zst.ynh.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.zst.ynh.R;
import com.zst.ynh.bean.LoanConfirmBean;

public class ServiceChatgeDialog  extends Dialog {

    private Context context;
    private RelativeLayout layout_interest, layout_platform_service_fees;
    private TextView interest, platform_service_fees, desc;
    private LinearLayout ll_dialog;
    private ImageView close;
    private LoanConfirmBean.ItemBean.FeesBean fee;

    public ServiceChatgeDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    public ServiceChatgeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
    }

    public void setContent(LoanConfirmBean.ItemBean.FeesBean fee) {
        this.fee = fee;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.dialog_service_charge, null);
        setContentView(view);
        initViews(view);
        setCancelable(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        ll_dialog.setLayoutParams(new FrameLayout.LayoutParams((int) (ScreenUtils.getScreenWidth() * 0.70), LinearLayout.LayoutParams.WRAP_CONTENT));

    }

    private void updateUI() {
        //利息
        if (TextUtils.isEmpty(fee.interest)) {
            layout_interest.setVisibility(View.GONE);
        } else {
            interest.setText(fee.interest + "元");
        }

        if (TextUtils.isEmpty(fee.other_fee)) {
            layout_platform_service_fees.setVisibility(View.GONE);
        } else {
            platform_service_fees.setText(fee.other_fee + "元");
        }
        desc.setText(fee.desc);
    }

    private void initViews(View contentView) {
        ll_dialog = contentView.findViewById(R.id.ll_dialog);
        layout_interest =contentView.findViewById(R.id.layout_interest);
        layout_platform_service_fees = contentView.findViewById(R.id.layout_platform_service_fees);

        interest = contentView.findViewById(R.id.interest);
        platform_service_fees =  contentView.findViewById(R.id.platform_service_fees);
        desc =  contentView.findViewById(R.id.desc);
        close = contentView.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceChatgeDialog.this.dismiss();
            }
        });
        if (fee!=null) {
            updateUI();
        }
    }
}
