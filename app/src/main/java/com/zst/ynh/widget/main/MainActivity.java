package com.zst.ynh.widget.main;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.zst.ynh.R;
import com.zst.ynh.adapter.ContentPagerAdapter;
import com.zst.ynh.bean.TabListBean;
import com.zst.ynh.bean.UpdateVersionBean;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.ColorUtils;
import com.zst.ynh.utils.DialogUtil;
import com.zst.ynh.utils.StringUtil;
import com.zst.ynh.view.AppUpdateProgressDialog;
import com.zst.ynh.widget.kouzi.KouziFragment;
import com.zst.ynh.widget.loan.Home.LoanFragment;
import com.zst.ynh.widget.person.mine.PersonFragment;
import com.zst.ynh.widget.repayment.repaymentfragment.ListType;
import com.zst.ynh.widget.repayment.repaymentfragment.MultiRepaymentFragment;
import com.zst.ynh.widget.tie.TieFragment;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.uploadimg.ProgressUIListener;
import com.zst.ynh_base.util.ImageLoaderUtils;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.util.UploadImgUtil;
import com.zst.ynh_base.view.BaseDialog;

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

    private TabListBean tabListBean;

    private long exitTime = 0;
    private List<Fragment> tabFragments = new ArrayList<>();
    private String[] titleNames;
    private ContentPagerAdapter contentAdapter;
    private LoanFragment loanFragment;
    private PersonFragment personFragment;
    private MultiRepaymentFragment multiRepaymentFragment;
    private TieFragment tieFragment;
    private KouziFragment kouziFragment;
    private View message;

    private MainPresent mainPresent;


    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {
        LogUtils.d("initView OnCreate");
        loadContentView();
        String tabdata = getIntent().getStringExtra(BundleKey.MAIN_DATA);
        if(!StringUtils.isEmpty(tabdata)){
            tabListBean=new Gson().fromJson(tabdata,TabListBean.class);
        }else{
            String preData=SPUtils.getInstance().getString(BundleKey.MAIN_DATA);
            if(!StringUtils.isEmpty(preData)){
                tabListBean=new Gson().fromJson(preData,TabListBean.class);
            }else{
                initDefaultTab();
            }

        }
        initFragment();
        initTitle();
        initTab();
        addTabListener();
        mainPresent = new MainPresent();
        mainPresent.attach(this);
        getUpdateVersion();
    }


    private void initDefaultTab() {
        int menu_loan_normal = R.mipmap.menu_loan_normal;
        int menu_loan_pressed = R.mipmap.menu_loan_pressed;
        int menu_repay_normal = R.mipmap.menu_repay_normal;
        int menu_repay_pressed = R.mipmap.menu_repay_pressed;
        int menu_mine_normal = R.mipmap.menu_mine_normal;
        int menu_mine_pressed = R.mipmap.menu_mine_pressed;
        String name_color_on= ColorUtils.toHexEncoding(getResources().getColor(R.color.them_color));
        String defaultTabJson = "{\"bottom_nav\":" +
                "[{\"icon\":\"" + menu_loan_normal + "\",\"icon_on\":\"" + menu_loan_pressed + "\",\"name\":\"借款\",\"name_color\":\"#babfc9\",\"name_color_on\":\""+name_color_on+"\",\"type\":0,\"url\":\"loan/index\"}," +
                "{\"icon\":\"" + menu_repay_normal + "\",\"icon_on\":\"" + menu_repay_pressed + "\",\"name\":\"还款\",\"name_color\":\"#babfc9\",\"name_color_on\":\""+name_color_on+"\",\"type\":0,\"url\":\"repayment/index\"}," +
                "{\"icon\":\"" + menu_mine_normal + "\",\"icon_on\":\"" + menu_mine_pressed + "\",\"name\":\"我的\",\"name_color\":\"#babfc9\",\"name_color_on\":\""+name_color_on+"\",\"type\":0,\"url\":\"user/index\"}]," +
                "\"bottom_nav_on\":0}";
        tabListBean = new Gson().fromJson(defaultTabJson, TabListBean.class);
    }

    private void getUpdateVersion() {
        mainPresent.getVersionInfo();
    }

    private void initTitle() {
        mTitleBar.setLeftImageResource(0);
        mTitleBar.setActionTextColor(R.color.theme_color);
        message = LayoutInflater.from(this).inflate(R.layout.view_message, null);
        mTitleBar.addRightLayout(message);
        message.setVisibility(View.GONE);
        loanFragment.setTitle(message);
    }


    private void initFragment() {

        List<TabListBean.BottomNavBean> bottomNavBeans = tabListBean.getBottom_nav();
        titleNames = new String[bottomNavBeans.size()];
        for (int i = 0; i < bottomNavBeans.size(); i++) {
            TabListBean.BottomNavBean bottomNavBean = bottomNavBeans.get(i);
            String url = bottomNavBean.getUrl();
            if (!url.contains("http://")) {
                switch (url) {
                    case BundleKey.MAIN_LOAN:
                        titleNames[i] = getResources().getString(R.string.app_name);
                        loanFragment = LoanFragment.newInstance();
                        loanFragment.setLazyload(true);
                        loanFragment.setTabId(i);
                        tabFragments.add(loanFragment);
                        break;
                    case BundleKey.MAIN_USER:
                        titleNames[i] = bottomNavBean.getName();
                        personFragment = PersonFragment.newInstance();
                        personFragment.setLazyload(true);
                        personFragment.setTabId(i);
                        tabFragments.add(personFragment);
                        break;
                    case BundleKey.MAIN_REPAYMENT:
                        titleNames[i] = bottomNavBean.getName();
                        multiRepaymentFragment =new MultiRepaymentFragment();
                        multiRepaymentFragment.setLazyload(true);
                        multiRepaymentFragment.setYnhType(ListType.YNH_REPAYMENT);
                        multiRepaymentFragment.setOtherType(ListType.OTHER_REPAYMENT);
                        multiRepaymentFragment.setTabId(i);
                        tabFragments.add(multiRepaymentFragment);
                        break;
                }
            } else {
                if (bottomNavBean.getType() == 1) {
                    switch (bottomNavBean.getName()){
                        case "口子":
                            kouziFragment = KouziFragment.newInstance();
                            kouziFragment.setSyncCookie(false);
                            kouziFragment.setLazyload(true);
                            kouziFragment.setUrl(bottomNavBean.getUrl());
                            kouziFragment.setTabId(i);
                            tabFragments.add(kouziFragment);
                            break;
                        case "提额":
                            tieFragment = TieFragment.newInstance();
                            tieFragment.setSyncCookie(false);
                            tieFragment.setLazyload(true);
                            tieFragment.setUrl(bottomNavBean.getUrl());
                            tieFragment.setTabId(i);
                            tabFragments.add(tieFragment);
                            break;
                    }
                }
                titleNames[i] = bottomNavBean.getName();
            }
        }
    }


    private void initTab() {
        ViewCompat.setElevation(tlTab, 10);
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager(), tabFragments, tabListBean.getBottom_nav(), this);
        vpContent.setOffscreenPageLimit(tabFragments.size());
        vpContent.setAdapter(contentAdapter);
        tlTab.setupWithViewPager(vpContent, true);
        for (int i = 0; i < tlTab.getTabCount(); i++) {
            tlTab.getTabAt(i).setCustomView(contentAdapter.getTabView(i));
        }

        int navOn = tabListBean.getBottom_nav_on();
        tlTab.getTabAt(navOn).select();
        if(navOn==0){
            message.setVisibility(View.VISIBLE);
        }
        mTitleBar.setTitle(titleNames[navOn]);
        setTabStyle(navOn,true);
    }

    private void addTabListener() {

        tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabListBean.BottomNavBean bottomNavBean = tabListBean.getBottom_nav().get(tab.getPosition());
                String url = bottomNavBean.getUrl();
                if (!url.contains("http://")) {
                    mTitleBar.setVisibility(View.VISIBLE);
                    mTitleBar.setTitle(titleNames[tab.getPosition()]);
                    switch (url) {
                        case BundleKey.MAIN_LOAN:
                            message.setVisibility(View.VISIBLE);
                            mTitleBar.setTitleColor(Color.BLACK);
                            mTitleBar.setBackgroundColor(Color.WHITE);
                            mTitleBar.setDividerColor(0);
                            break;
                        case BundleKey.MAIN_REPAYMENT:
                            if (TextUtils.isEmpty(SPUtils.getInstance().getString(SPkey.USER_SESSIONID))) {
                                ARouter.getInstance().build(ArouterUtil.LOGIN).withString(BundleKey.LOGIN_FROM, BundleKey.LOGIN_FROM_MAIN).navigation();
                                return;
                            }
                            message.setVisibility(View.GONE);
                            mTitleBar.setTitleColor(Color.BLACK);
                            mTitleBar.setDividerColor(getResources().getColor(R.color.dividing_color));
                            mTitleBar.setBackgroundColor(Color.WHITE);
                            break;
                        case BundleKey.MAIN_USER:
                            if (TextUtils.isEmpty(SPUtils.getInstance().getString(SPkey.USER_SESSIONID))) {
                                ARouter.getInstance().build(ArouterUtil.LOGIN).withString(BundleKey.LOGIN_FROM, BundleKey.LOGIN_FROM_MAIN).navigation();
                                return;
                            }
                            message.setVisibility(View.GONE);
                            mTitleBar.setTitleColor(Color.WHITE);
                            mTitleBar.setBackgroundResource(R.color.them_color);
                            mTitleBar.setDividerColor(0);
                            break;

                    }
                } else {
                    mTitleBar.setVisibility(View.GONE);
                    message.setVisibility(View.GONE);

                }

                setTabStyle(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTabStyle(tab.getPosition(),false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setTabStyle(int tabposition,boolean isOn){
        TabListBean.BottomNavBean bottomNavBean=tabListBean.getBottom_nav().get(tabposition);
        ImageView tabIcon= tlTab.getTabAt(tabposition).getCustomView().findViewById(R.id.iv_tab_icon);
        String icon;
        if(isOn){
            icon=bottomNavBean.getIcon_on();
        }else{
            icon=bottomNavBean.getIcon();
        }
        if(icon.contains("http://")){
            ImageLoaderUtils.loadUrl(MainActivity.this,icon,tabIcon);
        }else{
            int resid=Integer.parseInt(icon);
            ImageLoaderUtils.loadRes(MainActivity.this,resid,tabIcon);
        }
        TextView tabText= tlTab.getTabAt(tabposition).getCustomView().findViewById(R.id.tv_tab_text);
        String color;
        if(isOn){
           color =bottomNavBean.getName_color_on();
        }else{
            color=bottomNavBean.getName_color();
        }
        tabText.setTextColor(Color.parseColor(color));
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String select = intent.getStringExtra(BundleKey.MAIN_SELECTED);
        boolean isFresh = intent.getBooleanExtra(BundleKey.MAIN_FRESH, false);
        if (!TextUtils.isEmpty(select)) {
            switch (select) {
                case BundleKey.MAIN_LOAN:
                    tlTab.getTabAt(loanFragment.getTabId()).select();
                    if (isFresh) {
                        loanFragment.autoFresh();
                    }
                    break;
                case BundleKey.MAIN_REPAYMENT:
                   tlTab.getTabAt(multiRepaymentFragment.getTabId()).select();
                   if(isFresh){
                       multiRepaymentFragment.autoFresh();
                   }
                    break;
                case BundleKey.MAIN_USER:
                    tlTab.getTabAt(personFragment.getTabId()).select();
                    if (isFresh) {
                        personFragment.autoFresh();
                    }
                    break;
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
                filepath = SDCardUtils.getSDCardPathByEnvironment() + "/jsm/jsm.apk";
                file = new File(filepath);
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
            updateProgressDialog.updateProgress((int) (percent * 100));
        }

        @Override
        public void onUIProgressFinish() {
            super.onUIProgressFinish();
            updateProgressDialog.updateProgress(100);
            updateProgressDialog.dismiss();
            try {
                installApk(file);
            } catch (Exception e) {
                dialog = new BaseDialog.Builder(MainActivity.this).setCancelable(false).setTitle("无法自动安装更新包").setContent1("请打开存储设备中jsm/jsm.apk文件安装")
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
            dialog = new BaseDialog.Builder(this).setCancelable(false).setTitle("无法自动安装更新包").setContent1("请打开存储设备中jsm/jsm.apk文件安装")
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
