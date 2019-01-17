package com.zst.ynh.widget.person.mine;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zst.ynh.R;
import com.zst.ynh.adapter.PersonFragmentItemAdapter;
import com.zst.ynh.bean.DepositOpenInfoVBean;
import com.zst.ynh.bean.MineBean;
import com.zst.ynh.config.ApiUrl;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.config.UMClicEventID;
import com.zst.ynh.config.UMClickEvent;
import com.zst.ynh.utils.DialogUtil;
import com.zst.ynh.utils.StringUtil;
import com.zst.ynh_base.lazyviewpager.LazyFragmentPagerAdapter;
import com.zst.ynh_base.mvp.view.BaseFragment;
import com.zst.ynh_base.util.ImageLoaderUtils;
import com.zst.ynh_base.util.Layout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.zst.ynh.bean.MineBean.BANK_CARD;
import static com.zst.ynh.bean.MineBean.HELP_CENTER;
import static com.zst.ynh.bean.MineBean.LIMIT;
import static com.zst.ynh.bean.MineBean.LOAN_RECORDS;
import static com.zst.ynh.bean.MineBean.MESSAGE_CENTER;
import static com.zst.ynh.bean.MineBean.MY_DISCOUNT;
import static com.zst.ynh.bean.MineBean.MY_INVITATION;
import static com.zst.ynh.bean.MineBean.PERFECT_INFO;
import static com.zst.ynh.bean.MineBean.REWARDS;
import static com.zst.ynh.bean.MineBean.SETTINGS;

@Layout(R.layout.person_fragment_layout)
public class PersonFragment extends BaseFragment implements IPersonView, LazyFragmentPagerAdapter.Laziable {

    private static final String TAG = PersonPresent.class.getSimpleName();

    public static PersonFragment newInstance() {
        PersonFragment fragment = new PersonFragment();
        return fragment;
    }

    @BindView(R.id.user_photo)
    ImageView userPhoto;

    @BindView(R.id.tv_user_name)
    TextView userName;

    @BindView(R.id.person_freshlayout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.person_item_layout)
    LinearLayout personItemLayout;

    private PersonPresent personPresent;
    private LayoutInflater inflater;

    private MineBean.MineItemBean mineItemBean;
    //判断是去认证中心 还是去认证
    private int TARGET_GUIDE = -1;

    public void autoFresh() {
        if(smartRefreshLayout!=null){
            onLazyLoad();
        }
    }

    @Override
    protected void onRetry() {
        showLoadingView();
        if (personPresent != null) {
            personPresent.getPersonData();
        }
    }

    @Override
    protected void initView() {
        Log.d(TAG, "initView");
        personPresent = new PersonPresent();
        personPresent.attach(this);
        inflater = LayoutInflater.from(this.getActivity());
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadContentView();
                if (personPresent != null) {
                    personPresent.getPersonData();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        autoFresh();
    }

    @Override
    public void onLazyLoad() {
        Log.d(TAG, "onLazyLoad");
        if (!TextUtils.isEmpty(SPUtils.getInstance().getString(SPkey.USER_SESSIONID))) {
            if (smartRefreshLayout.getState()!=RefreshState.None) {
                smartRefreshLayout.finishRefresh();
            }
            smartRefreshLayout.autoRefresh();
        }
    }


    @Override
    public void showProgressLoading() {
        showLoadingView();
    }

    @Override
    public void hideProgressLoading() {
        hideLoadingView();
    }

    @Override
    public void showLoading() {
        showLoadingView();
    }

    @Override
    public void hideLoading() {
        hideLoadingView();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void ToastErrorMessage(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void showPersonData(MineBean mineBean) {
        Log.i(TAG, mineBean.toString());
        loadContentView();
        if (mineBean == null) {
            showData(getDefaults());
        } else {
            mineItemBean = mineBean.getItem();
            TARGET_GUIDE = mineItemBean.getTarget_guide();
            ImageLoaderUtils.loadUrl(this.getContext(), mineItemBean.getAvatar(), userPhoto);
            userName.setText(StringUtil.changeMobile(SPUtils.getInstance().getString(SPkey.USER_PHONE)));
            List<MineBean.MoreItem> moreItemList = mineItemBean.getItem_list();
            personItemLayout.removeAllViews();

            LinkedHashMap<Integer, List<MineBean.MoreItem>> map = new LinkedHashMap<>();
            for (MineBean.MoreItem moreItem : moreItemList) {
                int groupId = moreItem.getGroup();
                if (map.containsKey(groupId)) {
                    map.get(groupId).add(moreItem);
                } else {
                    List<MineBean.MoreItem> list = new ArrayList<MineBean.MoreItem>();
                    list.add(moreItem);
                    map.put(groupId, list);
                }
            }
            showData(map);
        }


    }

    @Override
    public void getPersonDataFailed(int code, String errorMSG) {
        loadErrorView();
    }

    @Override
    public void getDepositeOpenInfo(DepositOpenInfoVBean depositOpenInfoVBean) {

        if (1 == depositOpenInfoVBean.have_card || 2 == depositOpenInfoVBean.is_deposit_open_account) {//已绑定银行卡
            ARouter.getInstance().build(ArouterUtil.BANK_LIST).navigation();
            return;
        }
        if (0 == depositOpenInfoVBean.have_card) {//没有绑定银行卡
            ARouter.getInstance().build(ArouterUtil.BIND_BANK_CARD).navigation();
        }

    }


    private void showData(Map<Integer, List<MineBean.MoreItem>> map) {

        for (Integer i : map.keySet()) {
            View view = inflater.inflate(R.layout.person_fragment_group, null, false);
            RecyclerView recyclerView = view.findViewById(R.id.person_group_recyclerview);
            List<MineBean.MoreItem> items = map.get(i);
            PersonFragmentItemAdapter adapter = new PersonFragmentItemAdapter(items);
            adapter.setOnItemClickListener(myOnItemClick);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
            personItemLayout.addView(view);
        }
    }

    PersonFragmentItemAdapter.OnItemClickListener myOnItemClick = new PersonFragmentItemAdapter.OnItemClickListener() {
        @Override
        public void OnItemClick(MineBean.MoreItem moreItem) {
            switch (moreItem.getTag()) {
                case LOAN_RECORDS://借款记录

                    ARouter.getInstance().build(ArouterUtil.LOAN_RECORD).navigation();

                    break;
                case PERFECT_INFO://认证中心

                    if (TARGET_GUIDE == 1) {//认证向导
                        ARouter.getInstance().build(ArouterUtil.TO_CERTIFICATION).navigation();
                    } else {//认证中心
                        ARouter.getInstance().build(ArouterUtil.CERTIFICATION_CENTER).navigation();
                    }

                    break;

                case BANK_CARD://收款银行卡

                    if (mineItemBean.getVerify_info().getReal_verify_status() == 1) {//已认证
                        if (personPresent != null) {
                            personPresent.getDepositeOpenInfo();
                        }
                    } else {//未认证
                        DialogUtil.showDialogToCertitication(PersonFragment.this.getContext());
                    }

                    break;

                case HELP_CENTER://帮助中心
                    UMClickEvent.getInstance().onClick(getActivity(),UMClicEventID.UM_EVENT_HELP_CENTER,"帮助中心");
                    ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL, ApiUrl.HELP_CENTER).navigation();

                    break;

                case MESSAGE_CENTER://

                    break;

                case MY_INVITATION://咨询客服

                    ARouter.getInstance().build(ArouterUtil.CUSTOMER_SERVICE).withString(BundleKey.URL, moreItem.getUrl()).navigation();

                    break;
                case SETTINGS://设置

                    if (mineItemBean == null) {
                        ToastUtils.showShort("数据异常，请下拉刷新");
                        return;
                    }
                    ARouter.getInstance().build(ArouterUtil.SETTINGS).withSerializable(BundleKey.SETTING, mineItemBean).navigation();

                    break;

                case REWARDS://奖励金
                    UMClickEvent.getInstance().onClick(getActivity(),UMClicEventID.UM_EVENT_JLJ,"奖励金");
                    ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL,moreItem.getUrl()).withBoolean(BundleKey.WEB_SET_SESSION,true).navigation();
                    break;
                case MY_DISCOUNT://优惠券
                    UMClickEvent.getInstance().onClick(getActivity(),UMClicEventID.UM_EVENT_COUPON,"优惠券");
                    ARouter.getInstance().build(ArouterUtil.SIMPLE_WEB).withString(BundleKey.URL,moreItem.getUrl()).withBoolean(BundleKey.WEB_SET_SESSION,true).navigation();
                    break;

                case LIMIT:
                    break;

            }
        }
    };


    public static Map<Integer, List<MineBean.MoreItem>> getDefaults() {
        Map<Integer, List<MineBean.MoreItem>> map = new ArrayMap<>();
        List<MineBean.MoreItem> moreItems = new ArrayList<>(8);
        moreItems.add(getDefault(LOAN_RECORDS, 1, "借款记录", R.mipmap.ucenter_lend));
        moreItems.add(getDefault(PERFECT_INFO, 1, "完善资料", R.mipmap.permessage));
        moreItems.add(getDefault(BANK_CARD, 1, "收款银行卡", R.mipmap.my_bank));
        map.put(1, moreItems);
        List<MineBean.MoreItem> moreItems2 = new ArrayList<>();
        moreItems2.add(getDefault(HELP_CENTER, 2, "帮助中心", R.mipmap.help_center));
        moreItems2.add(getDefault(SETTINGS, 2, "设置", R.mipmap.settings));
        map.put(2, moreItems2);
        return map;
    }

    private static MineBean.MoreItem getDefault(@MineBean.MoreItemType int moreItemIndex, int group, String title, @DrawableRes int logoId) {
        MineBean.MoreItem item = new MineBean.MoreItem();
        item.setTag(moreItemIndex);
        item.setGroup(group);
        item.setTitle(title);
        item.setRes(logoId);
        return item;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
        if (personPresent != null) {
            personPresent.detach();
        }
    }
}
