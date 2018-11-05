package com.zst.ynh.widget.repayment;

import com.zst.ynh.R;
import com.zst.ynh_base.lazyviewpager.LazyFragmentPagerAdapter;
import com.zst.ynh_base.mvp.view.BaseFragment;
import com.zst.ynh_base.util.Layout;

@Layout(R.layout.loan_fragment_layout)
public class RepaymentFragment extends BaseFragment implements IRepaymentView ,LazyFragmentPagerAdapter.Laziable {

    public static RepaymentFragment newInstance() {
        RepaymentFragment fragment = new RepaymentFragment();
        return fragment;
    }

    @Override
    protected void onRetry() {

    }

    @Override
    protected void initView() {

    }
}
