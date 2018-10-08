package com.zst.jsm.widget.repayment;

import com.zst.jsm.R;
import com.zst.jsm_base.mvp.view.BaseFragment;
import com.zst.jsm_base.util.Layout;

@Layout(R.layout.loan_fragment_layout)
public class RepaymentFragment extends BaseFragment implements IRepaymentView {

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
