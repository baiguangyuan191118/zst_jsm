package com.zst.ynh.widget.person.certification.banklist;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zst.ynh.R;
import com.zst.ynh.adapter.MyBankListAdapter;
import com.zst.ynh.bean.MyBankBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.DialogUtil;
import com.zst.ynh.view.BottomDialog;
import com.zst.ynh.view.InputSMSCardDialog;
import com.zst.ynh_base.adapter.recycleview.HeaderAndFooterWrapper;
import com.zst.ynh_base.adapter.recycleview.MultiItemTypeAdapter;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.BaseDialog;
import com.zst.ynh_base.view.BottomMenuDialog;

import butterknife.BindView;

@Route(path = ArouterUtil.BANK_LIST)
@Layout(R.layout.activity_my_bank_list_layout)
public class BankListActivity extends BaseActivity implements IBankListView, OnRefreshListener {
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_view)
    SmartRefreshLayout refreshView;
    private BankListPresent bankListPresent;
    private MyBankListAdapter myBankListAdapter;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private BottomMenuDialog bottomMenuDialog;
    private View footerView;
    private TextView tvTips;
    private Button btnAdd;
    private String[] title;
    private int[] titleColor;
    //是否可以设置主卡
    private boolean isCanSetMasterCard;
    private InputSMSCardDialog inputSMSCardDialog;

    @Override
    public void getBankListData(final MyBankBean myBankBean) {
        isCanSetMasterCard = myBankBean.can_set_main_card == 0 ? false : true;
        //设置adapter数据
        rv.setLayoutManager(new LinearLayoutManager(this));
            myBankListAdapter = new MyBankListAdapter(this, R.layout.item_my_bank_list_item, myBankBean.card_list);
            headerAndFooterWrapper = new HeaderAndFooterWrapper(myBankListAdapter);
            headerAndFooterWrapper.addFootView(footerView);
            rv.setAdapter(headerAndFooterWrapper);

        //设置上方的消息提醒
        if (myBankBean.is_show_notice == 1 && !TextUtils.isEmpty(myBankBean.notice_msg)) {
            tvNotice.setVisibility(View.VISIBLE);
            tvNotice.setText(myBankBean.notice_msg);
        } else {
            tvNotice.setVisibility(View.GONE);
        }
        //设置下方的footer数据
        if (!TextUtils.isEmpty(myBankBean.tips)) {
            tvTips.setText(myBankBean.tips);
        }
        myBankListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder,final int position) {
                if (myBankBean.card_list.get(position).is_main_card != 1) {
                    bottomMenuDialog = new BottomMenuDialog.Builder(BankListActivity.this).setBtnTitle(title).setBtnTitleColor(titleColor).setButtonNumber(2)
                            .setCancelListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bottomMenuDialog.dismiss();
                                }
                            })
                            .setOnClickWithPosition(new BottomMenuDialog.IonItemClickListener() {
                                @Override
                                public void onItemClickListener(final int index) {
                                    switch (index) {
                                        case 0:
                                            if (isCanSetMasterCard){
                                                inputSMSCardDialog=new InputSMSCardDialog(BankListActivity.this,R.style.statement_dialog);
                                                bankListPresent.sendSMS(SPUtils.getInstance().getString(SPkey.USER_PHONE),myBankBean.card_list.get(position).id+"");
                                                inputSMSCardDialog.setSendSMSCodeVerifyCallBack(new InputSMSCardDialog.SendSMSCodeVerifyCallBack() {
                                                    @Override
                                                    public void sendCode() {
                                                        bankListPresent.sendSMS(SPUtils.getInstance().getString(SPkey.USER_PHONE),myBankBean.card_list.get(position).id+"");
                                                    }
                                                });
                                                inputSMSCardDialog.setVerifyCallBack(new InputSMSCardDialog.VerifyCallBack() {
                                                    @Override
                                                    public void verify(String code) {
                                                        bankListPresent.setMasterCard(myBankBean.card_list.get(position).id+"",code);
                                                    }
                                                });
                                                inputSMSCardDialog.show();
                                            }else{
                                                cantChangeCardDailog();
                                              //  ToastUtils.showShort("当前有进行中的借款，无法变更主卡");
                                            }
                                            break;
                                        case 1:
                                            bankListPresent.unbindCard(myBankBean.card_list.get(position).id+"");
                                            break;
                                    }
                                }
                            }).create();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }

        });


    }
    private  BaseDialog baseDialog;
    private void cantChangeCardDailog(){
       baseDialog =new BaseDialog.Builder(this).setContent1("您当前有未完结的订单")
                .setContent2("暂不支持切换卡")
                .setBtnLeftBackgroundColor(getResources().getColor(R.color.them_color))
                .setBtnLeftText("知道了")
                .setLeftOnClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.hideDialog(baseDialog);
                    }
                }).create();
       baseDialog.show();
    }

    @Override
    public void loadContent() {
        if (refreshView.getState() == RefreshState.Refreshing) {
            refreshView.finishRefresh();
        } else {
            loadContentView();
        }
    }

    @Override
    public void loadError() {
        loadErrorView();
    }

    @Override
    public void loadLoading() {
        if (refreshView.getState() == RefreshState.Refreshing) {

        } else {
            loadLoadingView();
        }

    }

    @Override
    public void getIsAddCard() {
        ARouter.getInstance().build(ArouterUtil.BIND_BANK_CARD).withBoolean(BundleKey.ISCHANGE, true).navigation();
    }

    @Override
    public void unbindCard() {
        DialogUtil.hideDialog(bottomMenuDialog);
        ToastUtils.showShort("解绑成功");
        refreshView.autoRefresh();
    }

    @Override
    public void setMasterCard() {
        DialogUtil.hideDialog(inputSMSCardDialog);
        DialogUtil.hideDialog(bottomMenuDialog);
        ToastUtils.showShort("设置主卡成功");
        refreshView.autoRefresh();
    }

    @Override
    public void sendSMSSuccess() {
        inputSMSCardDialog.start();
    }

    @Override
    public void onRetry() {
        bankListPresent.getBankList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bankListPresent = new BankListPresent();
        bankListPresent.attach(this);
        bankListPresent.getBankList();
    }


    @Override
    public void initView() {
        mTitleBar.setTitle("我的银行卡");
        refreshView.setEnableLoadMore(false);
        refreshView.setOnRefreshListener(this);
        footerView = LayoutInflater.from(this).inflate(R.layout.activity_my_bank_list_footer_layout, null);
        tvTips = footerView.findViewById(R.id.tv_tips);
        btnAdd = footerView.findViewById(R.id.btn_add);
        title = new String[]{"设为主卡", "解除绑定"};
        titleColor = new int[]{R.color.color_f0170236, R.color.red};
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankListPresent.isAddCard();

            }
        });

    }

    @Override
    public void showLoading() {
        showLoadingView();
    }

    @Override
    public void hideLoading() {
        hideLoadingView();
    }

    @Override
    public void ToastErrorMessage(String msg) {
        ToastUtils.showShort(msg);
        if (inputSMSCardDialog!=null&&inputSMSCardDialog.isShowing() )
            inputSMSCardDialog.reset();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bankListPresent.detach();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        bankListPresent.getBankList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
   //     ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED,BundleKey.MAIN_USER).withBoolean(BundleKey.MAIN_FRESH,true).navigation();
    }
}
