package com.zst.ynh.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.zst.ynh.R;
import com.zst.ynh.adapter.ContentPagerAdapter;
import com.zst.ynh.bean.UpdateVersionBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.DialogUtil;
import com.zst.ynh.utils.StringUtil;
import com.zst.ynh.view.AppUpdateProgressDialog;
import com.zst.ynh.widget.loan.Home.LoanFragment;
import com.zst.ynh.widget.person.mine.PersonFragment;
import com.zst.ynh.widget.repayment.repaymentfragment.RepaymentFragment;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.uploadimg.ProgressUIListener;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.util.UploadImgUtil;
import com.zst.ynh_base.view.BaseDialog;
import com.zst.ynh_base.view.TitleBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

@Route(path = ArouterUtil.MAIN)
@Layout(R.layout.activity_main)
public class MainActivity extends BaseActivity implements MainView {
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;

    private long exitTime = 0;
    private int[] titleName = {R.string.app_name, R.string.title_repay, R.string.menu_mine};
    private int[] tabTitle = {R.string.menu_loan, R.string.menu_repay, R.string.menu_mine};
    private int[] tabIcon = {R.drawable.selector_menu_loan, R.drawable.selector_menu_repay, R.drawable.selector_menu_mine};
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    private LoanFragment loanFragment;
    private PersonFragment personFragment;
    private RepaymentFragment repaymentFragment;
    private View history;
    private View message;

    private MainPresent mainPresent;


    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        LogUtils.d("initView OnCreate");
        loadContentView();
        initFragment();
        initTab();
        initTitle();
        addTabListener();
        mainPresent = new MainPresent();
        mainPresent.attach(this);
        getUpdateVersion();
    }

    private void getUpdateVersion() {
        mainPresent.getVersionInfo();
    }

    private void initTitle() {
        mTitleBar.setLeftImageResource(0);
        mTitleBar.setActionTextColor(R.color.theme_color);
        TitleBar.TextAction action = new TitleBar.TextAction("历史") {
            @Override
            public void performAction(View view) {
                ARouter.getInstance().build(ArouterUtil.LOAN_RECORD).navigation();
            }
        };
        history = mTitleBar.addAction(action);
        message = LayoutInflater.from(this).inflate(R.layout.view_message, null);
        mTitleBar.addRightLayout(message);
        message.setVisibility(View.VISIBLE);
        history.setVisibility(View.GONE);
        loanFragment.setTitle(message);
    }

    private void initFragment() {
        tabFragments = new ArrayList<>();
        loanFragment = LoanFragment.newInstance();
        personFragment = PersonFragment.newInstance();
        repaymentFragment = RepaymentFragment.newInstance();
        tabFragments.add(loanFragment);
        tabFragments.add(repaymentFragment);
        tabFragments.add(personFragment);
    }

    private void initTab() {
        tlTab.setTabMode(TabLayout.MODE_FIXED);
        tlTab.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(tlTab, 10);
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager(), tabFragments, tabTitle, tabIcon, this);
        vpContent.setAdapter(contentAdapter);
        tlTab.setupWithViewPager(vpContent, true);
        for (int i = 0; i < tlTab.getTabCount(); i++) {
            tlTab.getTabAt(i).setCustomView(contentAdapter.getTabView(i));
        }
        // tlTab.getTabAt(0).select();
        mTitleBar.setTitle(titleName[0]);
    }

    private void addTabListener() {

        tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTitleBar.setTitle(titleName[tab.getPosition()]);
                switch (tab.getPosition()) {
                    case 0:
                        message.setVisibility(View.VISIBLE);
                        history.setVisibility(View.GONE);
                        mTitleBar.setTitleColor(Color.BLACK);
                        mTitleBar.setBackgroundColor(Color.WHITE);
                        break;
                    case 1:
                        if (TextUtils.isEmpty(SPUtils.getInstance().getString(SPkey.USER_SESSIONID))) {
                            ARouter.getInstance().build(ArouterUtil.LOGIN).withString(BundleKey.LOGIN_FROM, BundleKey.LOGIN_FROM_MAIN).navigation();
                            return;
                        }
                        history.setVisibility(View.VISIBLE);
                        message.setVisibility(View.GONE);
                        mTitleBar.setTitleColor(Color.BLACK);
                        mTitleBar.setBackgroundColor(Color.WHITE);
                        break;
                    case 2:
                        if (TextUtils.isEmpty(SPUtils.getInstance().getString(SPkey.USER_SESSIONID))) {
                            ARouter.getInstance().build(ArouterUtil.LOGIN).withString(BundleKey.LOGIN_FROM, BundleKey.LOGIN_FROM_MAIN).navigation();
                            return;
                        }
                        message.setVisibility(View.GONE);
                        history.setVisibility(View.GONE);
                        mTitleBar.setTitleColor(Color.WHITE);
                        mTitleBar.setBackgroundResource(R.color.them_color);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        boolean isFresh = intent.getBooleanExtra(BundleKey.MAIN_FRESH, false);
        loanFragment.setFresh(isFresh);
        repaymentFragment.setFresh(isFresh);
        personFragment.setFresh(isFresh);

        if (!TextUtils.isEmpty(intent.getStringExtra(BundleKey.MAIN_SELECTED))) {
            if ("0".equals(intent.getStringExtra(BundleKey.MAIN_SELECTED))) {
                tlTab.getTabAt(0).select();
                loanFragment.autoFresh();
            } else if ("1".equals(intent.getStringExtra(BundleKey.MAIN_SELECTED))) {
                tlTab.getTabAt(1).select();
                repaymentFragment.autoFresh();
            } else if ("2".equals(intent.getStringExtra(BundleKey.MAIN_SELECTED))) {
                tlTab.getTabAt(2).select();
                personFragment.autoFresh();
            }

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mainPresent != null) {
            mainPresent.detach();
        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showShort("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void ToastErrorMessage(String msg) {
        ToastUtils.showShort(msg);
    }

    private UpdateVersionBean updateVersionBean;
    private AppUpdateProgressDialog updateProgressDialog;
    private String filepath;
    private File file;
    @Override
    public void getVersionInfo(final UpdateVersionBean updateVersionBean) {
        if (!StringUtil.compareAppVersion(AppUtils.getAppVersionName(), updateVersionBean.getItem().getNew_version())) {
            return;
        }

        if (updateVersionBean.getItem().getHas_upgrade() == 0) {
            return;
        }

        if (!SDCardUtils.isSDCardEnableByEnvironment()) {
            ToastUtils.showShort("未安装sd卡");
            return;
        }

        this.updateVersionBean = updateVersionBean;
        updateProgressDialog = new AppUpdateProgressDialog(this);
        if (updateVersionBean.getItem().getIs_force_upgrade() == 1) {
            updateProgressDialog.setCancelable(false);//强制更新弹窗不消失
        }

        updateProgressDialog.setContent("" + Html.fromHtml(updateVersionBean.getItem().getNew_features()));
        updateProgressDialog.setUpdateStartCallBack(new AppUpdateProgressDialog.UpdateStartCallBack() {
            @Override
            public void start() {
                filepath=SDCardUtils.getSDCardPathByEnvironment() + "/jsm/jsm.apk";
                file=new File(filepath);
                FileUtils.createFileByDeleteOldFile(file);
                UploadImgUtil.download(updateVersionBean.getItem().getArd_url(), progressUIListener, file);
            }
        });

        updateProgressDialog.show();

    }

    private ProgressUIListener progressUIListener = new ProgressUIListener() {

        @Override
        public void onUIProgressStart(long totalBytes) {
            super.onUIProgressStart(totalBytes);
            updateProgressDialog.setTotal(100);
        }

        @Override
        public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
            updateProgressDialog.updateProgress((int) (percent*100));
        }

        @Override
        public void onUIProgressFinish() {
            super.onUIProgressFinish();
            updateProgressDialog.updateProgress(100);
            updateProgressDialog.dismiss();
            try {
                installApk(file);
            }catch (Exception e){
                dialog= new BaseDialog.Builder(MainActivity.this).setCancelable(false).setTitle("无法自动安装更新包").setContent1("请打开存储设备中jsm/jsm.apk文件安装")
                        .setBtnLeftText("我知道了")
                        .setLeftOnClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DialogUtil.hideDialog(dialog);
                            }
                        }).create();
                dialog.show();
            }

        }

        @Override
        public void onSuccess(Call call, Response response) {

        }

        @Override
        public void onFailed(Call call, Exception exception) {
            if (updateVersionBean.getItem().getIs_force_upgrade() == 1) {
                ToastUtils.showShort("更新失败，请重新尝试");
                finish();
            } else {
                ToastUtils.showShort("更新失败");
            }
        }
    };

    private BaseDialog dialog;
    private void installApk(File file) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data;
            String type = "application/vnd.android.package-archive";
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                data = Uri.fromFile(file);
            } else {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                String authority = Utils.getApp().getPackageName() + ".provider";
                data = FileProvider.getUriForFile(Utils.getApp(), authority, file);
            }
            intent.setDataAndType(data, type);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            updateProgressDialog.dismiss();
            dialog= new BaseDialog.Builder(this).setCancelable(false).setTitle("无法自动安装更新包").setContent1("请打开存储设备中jsm/jsm.apk文件安装")
                    .setBtnLeftText("我知道了")
                    .setLeftOnClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtil.hideDialog(dialog);
                        }
                    }).create();
            dialog.show();
        }
    }
}
