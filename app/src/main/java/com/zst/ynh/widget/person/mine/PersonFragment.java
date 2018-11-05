package com.zst.ynh.widget.person.mine;


import com.zst.ynh.R;
import com.zst.ynh_base.lazyviewpager.LazyFragmentPagerAdapter;
import com.zst.ynh_base.mvp.view.BaseFragment;
import com.zst.ynh_base.util.Layout;

@Layout(R.layout.loan_fragment_layout)
public class PersonFragment extends BaseFragment implements IPersonView,LazyFragmentPagerAdapter.Laziable {

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
