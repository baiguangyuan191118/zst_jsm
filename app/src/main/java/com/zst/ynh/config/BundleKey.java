package com.zst.ynh.config;

public class BundleKey {
    public static final String URL="web_url";
    public static final String WEB_TITLE="web_title";
    public static final String WEB_TAG="web_tag";
    public static final String PHONE="phone";
    public static final String CODE="code";
    public static final String LOAN_CONFIRM="loanconfirm";
    public static final String IS_FROM_TOCERTIFICATION="isFromToCertification";
    public static final String ISVERTICAL="isvertical";
    public static final String SIDE="side";
    public static final String TYPE="type";
    public static final String ISCHANGE="ischange";
    public static final String ISREAL="isReal";
    public static final String MAIN_SELECTED="main_selected";//首页页面的选择 0：首页 1：还款 2：我的
    public static final String LOGIN_FROM="login_from";//从哪里跳转到首页 进行返回逻辑
    /**
     * 从哪里跳转到首页的
     */
    //这块是赋的value
    public static final String LOGIN_FROM_MAIN="login_from_main";
    //Ending

    public static final String LOAN_DETAIL="loan_detail";

    //跳转到设置界面
    public static final String SETTING="settings";
    //跳转到手势密码的类型  0：模式选择，重置密码，设置密码模式 1： 验证密码模式
    public static final String GESTURE_MODE="gesture_mode";

    /*-------------------支付密码设置---------------------*/
    //设置支付密码 是设置界面还是修改界面
    public static final String IS_SET_PAY_PWD="is_set_pay_pwd";

    public static final String PAY_PWD_TYPE="pay_type";
    public static final String PAY_PWD_PHONE="pay_phone";
    public static final String PAY_PWD_VERIFYCODE="pay_verifycode";

}