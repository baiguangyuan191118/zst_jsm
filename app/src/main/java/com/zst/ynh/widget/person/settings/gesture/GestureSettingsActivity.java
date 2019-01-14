package com.zst.ynh.widget.person.settings.gesture;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zst.gesturelock.BirthdayGestureLockView;
import com.zst.gesturelock.ColorSetting;
import com.zst.gesturelock.GestureLockLayout;
import com.zst.gesturelock.JDLockView;
import com.zst.ynh.JsmApplication;
import com.zst.ynh.R;
import com.zst.ynh.base.UMBaseActivity;
import com.zst.ynh.bean.GestureLockInfo;
import com.zst.ynh.config.ArouterUtil;
import com.zst.ynh.config.BundleKey;
import com.zst.ynh.config.SPkey;
import com.zst.ynh.utils.DateUtil;
import com.zst.ynh.utils.NoDoubleClickListener;
import com.zst.ynh.utils.StringUtil;
import com.zst.ynh.utils.WeakHandler;
import com.zst.ynh.widget.person.settings.ISettingsView;
import com.zst.ynh.widget.person.settings.SettingsActivity;
import com.zst.ynh.widget.person.settings.SettingsPresent;
import com.zst.ynh_base.mvp.view.BaseActivity;
import com.zst.ynh_base.util.Layout;
import com.zst.ynh_base.view.AlertDialog;

import java.util.List;

import butterknife.BindView;

@Layout(R.layout.activity_gesture_settings)
@Route(path = ArouterUtil.GESTURE_SET)
public class GestureSettingsActivity extends UMBaseActivity implements IGestureSettingsView,ISettingsView{

    @BindView(R.id.txt_date_day)
    TextView day;
    @BindView(R.id.txt_date_month)
    TextView month;
    @BindView(R.id.txt_saying)
    TextView saying;
    @BindView(R.id.img_lock)
    ImageView icon;
    @BindView(R.id.txt_reminder)
    TextView reminder;
    @BindView(R.id.gesture_lock_layout)
    GestureLockLayout gestureLockLayout;
    @BindView(R.id.btn_cancel)
    TextView cancel;
    @BindView(R.id.verrify_bottom_layout)
    LinearLayout verifyBottomLayout;
    @BindView(R.id.btn_forget_gesture)
    TextView forgetGesture;
    @BindView(R.id.btn_login_by_other)
    TextView loginByOther;

    private int getsureMode;
    private int gestureLayoutMode;
    private ObjectAnimator objectAnimator;
    private WeakHandler handler=new WeakHandler();
    private GestureSettingPresent gestureSettingPresent;
    private SettingsPresent settingsPresent;
    private boolean clearUserName;//是否清空密码

    @Override
    public void onRetry() {

    }

    @Override
    public void initView() {

        getsureMode=getIntent().getIntExtra(BundleKey.GESTURE_MODE,0);
        mTitleBar.setVisibility(View.GONE);
        if(getsureMode==BundleKey.RESET_GESTURE){
            gestureLayoutMode=GestureLockLayout.RESET_MODE;
        }else{
            gestureLayoutMode=GestureLockLayout.VERIFY_MODE;
        }
        if(gestureLayoutMode==GestureLockLayout.RESET_MODE){
            icon.setImageResource(R.mipmap.lock_setting);
            verifyBottomLayout.setVisibility(View.GONE);
            cancel.setOnClickListener(listener);
        }else{
            icon.setImageResource(R.mipmap.user_default);
            String uName =SPUtils.getInstance().getString(SPkey.USER_PHONE);
            if (!StringUtils.isEmpty(uName)) {
                reminder.setText("您好 " + StringUtil.changeMobile(uName));
            }
            cancel.setVisibility(View.GONE);
            forgetGesture.setOnClickListener(listener);
            loginByOther.setOnClickListener(listener);
        }

        setGetsture();

        settingsPresent=new SettingsPresent();
        settingsPresent.attach(this);
        gestureSettingPresent=new GestureSettingPresent();
        gestureSettingPresent.attach(this);
        gestureSettingPresent.getGestureSettingInfo();

    }

    NoDoubleClickListener listener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            switch (view.getId()) {
                case R.id.btn_forget_gesture:
                    showForgetGestureDialog();
                    break;

                case R.id.btn_login_by_other:
                    clearUserName=false;
                    logout();
                    break;
                case  R.id.btn_cancel:
                    finish();
                    break;

            }
        }
    };

    private void logout() {
        settingsPresent.logout();
    }

    /**
     * 忘记手势密码Dialog
     */
    private void showForgetGestureDialog() {
        new AlertDialog(this)
                .builder()
                .setTitle("手势密码已关闭，请重新登录")
                .setMsg("在个人中心-设置中，可重新开启手势密码")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearUserName=true;
                        logout();
                    }
                }).show();
    }

    public void setGetsture(){
        ColorSetting.outerMatchedColor= getResources().getColor(R.color.gesture_outer_match);
        ColorSetting.innerMatchedColor= getResources().getColor(R.color.theme_color);
        ColorSetting.innerUnMatchedColor= getResources().getColor(R.color.gesture_inner_un_match);
        ColorSetting.outerUnMatchedColor= getResources().getColor(R.color.gesture_outer_un_match);
        //设置手势解锁view 每行每列点的个数
        gestureLockLayout.setDotCount(3);
        //设置手势解锁view 最少连接数
        gestureLockLayout.setMinCount(4);
        //默认解锁样式为JD手势解锁样式
        gestureLockLayout.setLockView(new JDLockView(this));
        //设置手势解锁view 模式为重置密码模式
        gestureLockLayout.setMode(gestureLayoutMode);

        if(gestureLayoutMode==GestureLockLayout.VERIFY_MODE){
            String key = SPUtils.getInstance().getString(SPkey.USER_PHONE);
            gestureLockLayout.setAnswer(SPUtils.getInstance().getString(key));
        }

        gestureLockLayout.setOnLockVerifyListener(onLockVerifyListener);

        if(gestureLayoutMode==GestureLockLayout.RESET_MODE){
            gestureLockLayout.setOnLockResetListener(onLockResetListener);
        }

    }

    GestureLockLayout.OnLockVerifyListener onLockVerifyListener=new GestureLockLayout.OnLockVerifyListener(){
        @Override
        public void onGestureSelected(int i) {
            if(gestureLayoutMode==GestureLockLayout.RESET_MODE){
                if (TextUtils.equals("两次密码不一致，请重新绘制", reminder.getText().toString().trim())) {
                    reminder.setTextColor(ContextCompat.getColor(GestureSettingsActivity.this, R.color.theme_color));
                    reminder.setText("请再次绘制手势密码");
                } else {
                    reminder.setText("请绘制手势图案，至少连接4个点");
                }
            }

        }

        @Override
        public void onGestureFinished(boolean isMatched) {
            if(gestureLayoutMode==GestureLockLayout.VERIFY_MODE){
                if (isMatched) {
                    //密码匹配
                    if(getsureMode==BundleKey.CLOSE_GESTURE){
                        setResult(SettingsActivity.TAG_RESULT_CODE_SUCCESS);
                    }else if(getsureMode==BundleKey.VERIFY_GESTURE){
                        ARouter.getInstance().build(ArouterUtil.MAIN).withString(BundleKey.MAIN_SELECTED,BundleKey.MAIN_LOAN).withSerializable(BundleKey.MAIN_DATA,getIntent().getSerializableExtra(BundleKey.MAIN_DATA)).navigation();
                    }
                    finish();
                } else {
                    //不匹配
                    reminder.setTextColor(ContextCompat.getColor(GestureSettingsActivity.this, R.color.red));
                    reminder.setText("手势密码错误，还可以输入" + gestureLockLayout.getTryTimes() + "次");
                    resetGesture();
                }
            }

        }

        @Override
        public void onGestureTryTimesBoundary() {//超过次数
            if(gestureLayoutMode==GestureLockLayout.VERIFY_MODE){
                clearUserName=true;
                logout();
            }

        }
    };

    GestureLockLayout.OnLockResetListener onLockResetListener=new GestureLockLayout.OnLockResetListener(){
        @Override
        public void onConnectCountUnmatched(int connectCount, int minCount) {
            //连接数小于最小连接数时调用
            reminder.setText("请至少连接" + minCount + "个点");
            reminder.setTextColor(ContextCompat.getColor(GestureSettingsActivity.this, R.color.red));
            if (objectAnimator == null) {
                objectAnimator = ObjectAnimator.ofFloat(reminder, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0);
            }
            objectAnimator.start();

            resetGesture();
        }

        @Override
        public void onFirstPasswordFinished(List<Integer> answerList) {
            //第一次绘制手势成功时调用
            reminder.setTextColor(ContextCompat.getColor(GestureSettingsActivity.this, R.color.theme_color));
            reminder.setText("请再次绘制手势密码");
            resetGesture();
        }

        @Override
        public void onSetPasswordFinished(boolean isMatched, List<Integer> answerList) {
            //第二次密码绘制成功时调用
            if (isMatched) {
                //两次答案一致，保存
                String key = SPUtils.getInstance().getString(SPkey.USER_PHONE);
                SPUtils.getInstance().put(key, answerList.toString());
                ToastUtils.showShort("手势密码设置成功，2s后跳转前一页");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            } else {
                reminder.setTextColor(ContextCompat.getColor(GestureSettingsActivity.this, R.color.red));
                reminder.setText("两次密码不一致，请重新绘制");
                if (objectAnimator == null) {
                    objectAnimator = ObjectAnimator.ofFloat(reminder, "translationX", 0, 25, -25, 25, -25, 15, -15, 6, -6, 0);
                }
                objectAnimator.start();
                resetGesture();
            }
        }
    };


    private void resetGesture() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gestureLockLayout.resetGesture();
            }
        }, 200);
    }

    @Override
    public void onBackPressed() {
        if(getsureMode!=BundleKey.VERIFY_GESTURE){
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }

        if(settingsPresent!=null){
            settingsPresent.detach();
        }

        if(gestureSettingPresent!=null){
            gestureSettingPresent.detach();
        }

        if (objectAnimator != null) {
            objectAnimator.cancel();
            objectAnimator = null;
        }
    }

    @Override
    public void LogoutSuccess(String response) {

        if (clearUserName) {
            String key = SPUtils.getInstance().getString(SPkey.USER_PHONE);
            if (!StringUtils.isEmpty(key)) {
                SPUtils.getInstance().put(key, "");
            }
        }
        JsmApplication.logoutData();
        if(getsureMode==BundleKey.VERIFY_GESTURE){
            ARouter.getInstance().build(ArouterUtil.LOGIN).withString(BundleKey.LOGIN_FROM,BundleKey.LOGIN_FROM_MAIN).navigation();
        }else if(getsureMode==BundleKey.CLOSE_GESTURE){
            setResult(SettingsActivity.TAG_RESULT_CODE_LOGOUT);
        }
        finish();
      //  ARouter.getInstance().build(ArouterUtil.LOGIN).navigation();
      //  finish();
    }

    @Override
    public void getGestureLockInfoSuccess(GestureLockInfo response) {
        if(response!=null){
            if (!StringUtils.isEmpty(response.getDay())) {
                day.setText(response.getDay());
            }

            if (!StringUtils.isEmpty(response.getMonth())) {
                month.setText(response.getMonth());
            }

            if (!StringUtils.isEmpty(response.getMsg())) {
                saying.setText(response.getMsg());
            }

            //判断当前是否为用户生日
            boolean isUserBirthday = response.getIs_birthday() == 1;
            if (isUserBirthday) {
                saying.setTextColor(Color.RED);
                gestureLockLayout.setLockView(new BirthdayGestureLockView(GestureSettingsActivity.this));
                gestureLockLayout.setBirthdayColor("#e87a90");
            }
        }else{
            setDefaultDate();
        }

    }

    private void setDefaultDate() {
        String currentDay = DateUtil.getCurrentDayFormat();
        String currentMonth = DateUtil.getCurrentMonthFormat();
        if (!TextUtils.isEmpty(currentDay)) {
            day.setText(currentDay);
        }

        if (!TextUtils.isEmpty(currentMonth)) {
            month.setText(currentMonth);
        }

    }

    @Override
    public void getGestureLockInfoError(int code, String errorMSG) {
        setDefaultDate();
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
    }
}
