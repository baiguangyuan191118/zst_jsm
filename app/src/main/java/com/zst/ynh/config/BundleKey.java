package com.zst.ynh.config;

public class BundleKey {
    /* --------------web-------------------------*/
    public static final String URL="web_url";
    public static final String WEB_AUTH="auth_method";
    public static final String WEB_SET_SESSION="set_session";//是否设置session

    public static final String PHONE="phone";
    public static final String CODE="code";
    public static final String LOAN_CONFIRM="loanconfirm";
    public static final String IS_FROM_INCERTIFICATION="isFromInCertification";
    public static final String ISVERTICAL="isvertical";
    public static final String SIDE="side";
    public static final String TYPE="type";
    public static final String ISCHANGE="ischange";
    public static final String ISREAL="isReal";
    public static final String PAYMENTSTYLEBEAN="paymentStyleBean";

    public static final String MAIN_DATA="main_data";//splash 获取的tablist数据

    public static final String MAIN_SELECTED="main_selected";//首页页面的选择 MAIN_LOAN：首页 MAIN_REPAYMENT：还款 MAIN_USER：我的
    public static final String MAIN_LOAN="loan/index";
    public static final String MAIN_REPAYMENT="repayment/index";
    public static final String MAIN_USER="user/index";

    public static final String MAIN_FRESH="main_fresh";//登出后，首页是否需要刷新
    public static final String UPLOAD_TYPE="upload_type";//上传的哪种数据
    public static final String LOGIN_FROM="login_from";//从哪里跳转到首页 进行返回逻辑
    public static final String NOTSKIPMAGICBOX="not_skip_magic_box";//不要自动跳转到魔盒页面
    /**
     * 从哪里跳转到首页的
     */
    //这块是赋的value
    public static final String LOGIN_FROM_MAIN="login_from_main";
    //Ending

    //跳转到设置界面
    public static final String SETTING="settings";
    //跳转到手势密码的类型  0：模式选择，重置密码，设置密码模式 1： 关闭手势密码 2.验证密码
    public static final int RESET_GESTURE=0;
    public static final int CLOSE_GESTURE=1;
    public static final int VERIFY_GESTURE=2;
    public static final String GESTURE_MODE="gesture_mode";

    /*-------------------支付密码设置---------------------*/
    //设置支付密码 是设置界面还是修改界面
    public static final String IS_SET_PAY_PWD="is_set_pay_pwd";

    public static final String PAY_PWD_INPUT_DATA="pay_pwd_input_data";//支付密码输入钱数
    public static final String PLATFORM="platform";//支付密码输入钱数

    public static final String PAY_PWD_TYPE="pay_type";
    public static final String PAY_PWD_PHONE="pay_phone";
    public static final String PAY_PWD_VERIFYCODE="pay_verifycode";

    /*-----------------借款---------------------------*/

    public static final String ORDER_ID="order_id";

    /*----------------工作信息------------------*/
    public static final String WORK_PIC_TYPE="work_pic_type";
    public static final String KEY_UPLOAD_IDCARD = "1";//上传身份证
    public static final String KEY_UPLOAD_DEGREE = "2";//学历证明
    public static final String KEY_UPLOAD_WORK = "3";//工作证明
    public static final String KEY_UPLOAD_SALARY = "4";//薪资
    public static final String KEY_UPLOAD_ASSET = "5";//资产
    public static final String KEY_UPLOAD_BADGE = "6";//工牌
    public static final String KEY_UPLOAD_NAMECARD = "7";//名片
    public static final String KEY_UPLOAD_BANKCARD = "8";//银行卡
    public static final String KEY_UPLOAD_FACE = "10";//人脸识别
    public static final String KEY_UPLOAD_IDCRAD_FAONT = "11";//身份证正面
    public static final String KEY_UPLOAD_IDCRAD_BACK = "12";//身份证反面
    public static final String KEY_UPLOAD_OTHER = "100";//其他

    /*-------------------还款----------------------------*/

    public static final String COUPON_LIST="couponlist";

    public static final String SELECT_COUPON="selectcoupon";
}
