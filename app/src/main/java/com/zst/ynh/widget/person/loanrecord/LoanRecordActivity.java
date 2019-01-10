package com.zst.ynh.widget.person.loanrecord;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zst.ynh.R;
import com.zst.ynh.adapter.LoanRecordPagerAdapter;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.widget.repayment.repaymentfragment.ListType;
import com.zst.ynh.widget.repayment.repaymentfragment.MultiRepaymentFragment;
import com.zst.ynh.widget.repayment.repaymentfragment.RepaymentListFragment;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*
* 借款记录
* */

@Route(path = ArouterUtil.LOAN_RECORD)
@Layout(R.layout.activity_loan_record)
public class LoanRecordActivity extends BaseActivity  {


    @BindView(R.id.content)
    LinearLayout content;
    private MultiRepaymentFragment multiRepaymentFragment;

    @Override
    public void onRetry() {
    }

    @Override
    public void initView() {
        initTitlebar();
        multiRepaymentFragment=new MultiRepaymentFragment();
        multiRepaymentFragment.setYnhType(ListType.YNH_ORDERS);
        multiRepaymentFragment.setOtherType(ListType.OTHER_ORDERS);
        FragmentManager fragmentManager= this.getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content,multiRepaymentFragment).commit();

    }

    private void initTitlebar() {
        mTitleBar.setTitle("借款记录");
        mTitleBar.setActionTextColor(R.color.theme_color);
        mTitleBar.setDividerColor(Color.GRAY);
        mTitleBar.addAction(new TitleBar.TextAction("还款帮助") {
            @Override
            public void performAction(View view) {
                ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL,ApiUrl.HELP_CENTER).navigation();
            }
        });
    }

}
