package com.zst.ynh.config;

import com.zst.ynh.BuildConfig;

public class ApiUrl {
    public static final String BASE_URL = BuildConfig.DEBUG?"http://jsm-test.nucdx.com/":"http://yy.jsm.51zxdai.com/";
    /**
     * 注册协议
     */
    public static final String REGISTER_PROTOCOL = BASE_URL + "credit/web/credit-web/safe-login-txt";
    /**
     * 首页的接口
     */
    public static final String APP_INDEX = BASE_URL + "credit/web/credit-app/app-index";

    /*
    * 首页的热门贷款
    * */

    public static final String APP_POPULAR_LOAN=BASE_URL+"credit/web/credit-app-info/native-get-app-info";

    /**
     * 发送验证码的接口(分两步，先获取一个random ? 然后通过这个获取code)
     */
    public static final String GET_RANDOM = BASE_URL + "credit/web/credit-app/random";
    /**
     * 发送验证码的接口(分两步，先获取一个random ? 然后通过这个获取code)
     */
    public static final String GET_CODE = BASE_URL + "credit/web/credit-user/get-code-login";
    /**
     * 验证码登录
     */
    public static final String LOGIN_BY_SMS = BASE_URL + "credit/web/credit-user/login-by-sms";
    /**
     * 忘记密码的验证码
     */
    public static final String FORGET_BY_SMS = BASE_URL + "credit/web/credit-user/reset-pwd-code";
    /**
     * 注册时候获取的邀请码
     */
    public static final String REGISTER_BY_SMS = BASE_URL + "credit/web/credit-user/reg-get-code";
    /**
     * 密码登录
     */
    public static final String LOGIN_BY_PWD = BASE_URL + "credit/web/credit-user/login";
    /**
     * 注册
     */
    public static final String REGISTER = BASE_URL + "credit/web/credit-user/register";
    /**
     * 修改密码
     */
    public static final String CHANGE_PWD = BASE_URL + "credit/web/credit-user/change-pwd";
    /**
     * 忘记密码
     */
    public static final String FORGET_PWD = BASE_URL + "credit/web/credit-user/verify-reset-password";
    /**
     * 重置密码
     */
    public static final String RESET_PWD = BASE_URL + "credit/web/credit-user/reset-password";
    /**
     * 借款确认
     */
    public static final String LOAN_CONFIRM = BASE_URL + "credit/web/credit-loan/get-cash-period-confirm-loan";
    /**
     * 存管的口子(暂时不用)
     */
    public static final String DEPOSIT_OPEN = BASE_URL + "credit/web/credit-card/get-deposit-open-info";
    /**
     * 设置交易密码
     */
    public static final String SET_PAY_PWD = BASE_URL + "credit-user/set-paypassword";
    /**
     * 用户认证向导
     */
    public static final String CERTIFICATION_GUIDE = BASE_URL + "credit/web/credit-card/guide";
    /**
     * 个人信息
     */
    public static final String PERSON_INFO = BASE_URL + "credit/web/credit-card/get-person-info";
    /**
     * 上传图片1
     */
    public static final String FACE_PLUS_IDCARD = BASE_URL + "credit/web/credit-card/new-face-plus-idcard";
    /**
     * 上传图片2
     */
    public static final String UPLOAD_IMAGE = BASE_URL + "credit/web/picture/upload-image";
    /**
     * 保存联系人信息
     */
    public static final String SAVE_CONTACTS = BASE_URL + "credit/web/credit-card/save-contacts";
    /**
     * 获取联系人之间的关系
     */
    public static final String GET_CONTACTS = BASE_URL + "credit/web/credit-card/get-contacts";
    /**
     * 判断用户是否绑卡(跟存管有关)
     */
    public static final String IS_OPEN_INFO = BASE_URL + "credit/web/credit-card/get-deposit-open-info";
    /**
     * 获取绑定银行卡的验证码
     */
    public static final String GET_BANK_CODE = BASE_URL + "credit/web/credit-card/get-code";
    /**
     * 获取支持的银行列表
     */
    public static final String GET_BANK_LIST = BASE_URL + "credit/web/credit-card/bank-card-info";
    /**
     * 添加银行卡
     */
    public static final String ADD_BANK_CARD = BASE_URL + "credit/web/credit-card/add-bank-card";
    /**
     * 数据魔盒
     */
    public static final String SAVE_MOHE = BASE_URL + "credit/web/credit-card/save-mohe";
    /**
     * 认证中心状态
     */
    public static final String VERIFICATION_INFO = BASE_URL + "credit/web/credit-card/get-verification-info";
    /**
     * 更新额度
     */
    public static final String UPDATE_LIMIT = BASE_URL + "credit/web/credit-info/user-credit-top";
    /**
     * 我的银行卡列表
     */
    public static final String CARD_LIST = BASE_URL + "credit/web/credit-card/card-list";
    /**
     * 判断是否可以重新添加银行卡（可以的话 添加也是这个接口）
     */
    public static final String CHANGE_CARD_CHECK = BASE_URL + "credit/web/credit-card/change-card-check";


    public static final String CHANGE_CARD=BASE_URL+"credit/web/credit-card/change-card";

    /**
     * 获取增加额度添加的详细个人信息
     */
    public static final String GET_PERSON_ADDITON_INFO = BASE_URL + "credit/web/credit-card/get-person-addition-info";
    /**
     * 保存增加额度添加的详细个人信息
     */
    public static final String SAVE_PERSON_ADDITON_INFO = BASE_URL + "credit/web/credit-card/save-person-addition-info";

    /**
     * 我的
     */
    public static final String PERSON_FRAGMENT = BASE_URL + "credit/web/credit-user/get-info";



    //获取还款的url
    public static final String GET_PAY_URL=BASE_URL+"credit/web/credit-loan/get-pay-url";

    /**
     * 帮助中心
     */
    public static final String HELP_CENTER = BASE_URL + "mobile/web/app-page/help-center";

    /**
     * 关于我们
     */

    public static final String ABOUT_US = BASE_URL + "mobile/web/app-page/about-company";

    /**
     * 退出登录
     */
    public static final String LOGOUT = BASE_URL + "credit/web/credit-user/logout";
    /**
     * 手势解锁设置界面信息
     */
    public static final String GESTURE_LOCK_INFO=BASE_URL+"credit/web/credit-user/hand-password-get-user-info";
    /**
     * 修改登录密码
     */
    public static final String UPDATE_LOGIN_PWD=BASE_URL+"credit/web/credit-user/change-pwd";
    /**
     * 修改交易密码
     */
    public static final String UPDATE_TRADE_PWD=BASE_URL+"credit/web/credit-user/change-paypassword";
    /**
     * 重置交易密码
     */
    public static final String RESET_PAY_PASSWORD=BASE_URL+"credit/web/credit-user/reset-pay-password";
    /**
     * 设置交易秘密
     */
    public static final String SET_PAY_PASSWORD=BASE_URL+"credit/web/credit-user/set-paypassword";
    /**
     * 由你花还款信息
     */
    public static final String YNH_REPAYMENT_INFO=BASE_URL+"credit/web/credit-loan/get-pay-order";

    /*
    * 其他平台待还款的信息列表
    * */
    public static final String OTHER_REPAYMENT_INFO=BASE_URL+"credit/web/super-loan/pay-loan-list";

    /*
    *由你花历史订单
    * */

    public static final String YNH_HISTORY_ORDERS=BASE_URL+"credit/web/credit-loan/get-my-orders";

    /*
    * 其他历史订单
    * */

    public static final String OTHER_HISTORY_ORDERS=BASE_URL+"credit/web/super-loan/history-loan-list";

    /**
     * 获取还款方式和金额
     */
    public static final String GET_MY_ZST_LOAN = BASE_URL + "credit/web/credit-loan/get-my-zst-loan";

    /**
     * 申请成功页面数据
     */
    public static final String APPLY_LOAN_SUCCESS_INFO=BASE_URL+"credit/web/credit-loan/achieve-loan";
    /**
     * 输入密码确认借款页面
     */
    public static final String APPLY_LOAN=BASE_URL+"credit/web/credit-loan/cash-period-apply-loan";
    /**
     * 人脸认证（从认证中心过来的）
     */
    public static final String SAVE_ID_CARD_INFO1=BASE_URL+"credit/web/credit-info/save-person-info";
    /**
     * 人脸认证（从去认证过来的）
     */
    public static final String SAVE_ID_CARD_INFO2=BASE_URL+"credit/web/credit-card/save-person-info";
    /**
     * 获取工作信息
     */
    public static final String GET_WORK_INFO = BASE_URL + "credit/web/credit-card/get-work-info";

    /*
    获取工作照片列表
     */
    public static final String GET_WORK_PIC_LIST=BASE_URL+"credit/web/picture/get-pic-list";

    /**
     * 保存工作信息
     */
    public static final String SAVE_WORK_INFO = BASE_URL + "credit/web/credit-card/save-work-info";
    /**
     * 解绑银行卡
     */
    public static final String UNBIND_CARD = BASE_URL + "credit/web/credit-card/unbind-card";
    /**
     * 设置银行卡为主卡
     */
    public static final String BIND_MASTER_CARD = BASE_URL + "credit/web/credit-card/set-main-card";
    /**
     * 上传信息包含（联系人、短信、app列表）
     */
    public static final String UPLOAD_PERSON_INFO = BASE_URL + "credit/web/credit-info/up-load-contents";

    /*
    * 更新版本
    * */
    public static final String UPDATE_VERSION=BASE_URL+"credit/web/credit-app/get-verision";

    /*
    * 获取所有tabs
    * */
    public static final String GET_ALL_TABS=BASE_URL+"credit/web/api/system/setting";

    /*
    * 优惠券使用规则
    * */

    public static final String COUPON_RULE=BASE_URL+"res/voucheractivity/index.html#/use-instructions";


}
