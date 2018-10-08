package com.zst.jsm.widget.person;


import com.zst.jsm.R;
import com.zst.jsm_base.mvp.view.BaseFragment;
import com.zst.jsm_base.util.Layout;

@Layout(R.layout.loan_fragment_layout)
public class PersonFragment extends BaseFragment implements IPersonView{

    public static PersonFragment newInstance() {
        PersonFragment fragment = new PersonFragment();
        return fragment;
    }

    @Override
    protected void onRetry() {

    }

    @Override
    protected void initView() {

    }
}
