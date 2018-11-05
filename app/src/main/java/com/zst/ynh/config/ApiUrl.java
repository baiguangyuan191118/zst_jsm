package com.zst.ynh.config;

public class ApiUrl {
    private static final String BASE_URL="http://jsm-test.nucdx.com/";
    /**
     * 注册协议
     */
    public static final  String REGISTER_PROTOCOL= BASE_URL+"credit/web/credit-web/safe-login-txt";
    /**
     * 首页的接口
     */
    public static final  String APP_INDEX= BASE_URL+"credit/web/credit-app/app-index";
    /**
     * 发送验证码的接口(分两步，先获取一个random ? 然后通过这个获取code)
     */
    public static final  String GET_RANDOM= BASE_URL+"credit/web/credit-app/random";
    /**
     * 发送验证码的接口(分两步，先获取一个random ? 然后通过这个获取code)
     */
    public static final  String GET_CODE= BASE_URL+"credit/web/credit-user/get-code-login";
    /**
     * 验证码登录
     */
    public static final  String LOGIN_BY_SMS=BASE_URL+"credit/web/credit-user/login-by-sms";
    /**
     * 忘记密码的验证码
     */
    public static final  String FORGET_BY_SMS=BASE_URL+"credit/web/credit-user/reset-pwd-code";
    /**
     * 注册时候获取的邀请码
     */
    public static final  String REGISTER_BY_SMS=BASE_URL+"credit/web/credit-user/reg-get-code";
    /**
     * 密码登录
     */
    public static final  String LOGIN_BY_PWD=BASE_URL+"credit/web/credit-user/login";
    /**
     * 注册
     */
    public static final  String REGISTER=BASE_URL+"credit/web/credit-user/register";
    /**
     * 修改密码
     */
    public static final  String CHANGE_PWD=BASE_URL+"credit/web/credit-user/change-pwd";
    /**
     * 忘记密码
     */
    public static final  String FORGET_PWD=BASE_URL+"credit/web/credit-user/verify-reset-password";
    /**
     * 重置密码
     */
    public static final  String RESET_PWD=BASE_URL+"credit/web/credit-user/reset-password";
    /**
     * 借款确认
     */
    public static final  String LOAN_CONFIRM=BASE_URL+"credit/web/credit-loan/get-cash-period-confirm-loan";
    /**
     * 存管的口子(暂时不用)
     */
    public static final  String DEPOSIT_OPEN=BASE_URL+"credit/web/credit-card/get-deposit-open-info";
    /**
     * 设置交易密码
     */
    public static final  String SET_PAY_PWD=BASE_URL+"credit-user/set-paypassword";
    /**
     * 用户认证向导
     */
    public static final  String CERTIFICATION_GUIDE=BASE_URL+"credit/web/credit-card/guide";
    /**
     * 个人信息
     */
    public static final  String PERSON_INFO=BASE_URL+"credit/web/credit-card/get-person-info";
    /**
     * 上传图片1
     */
    public static final  String FACE_PLUS_IDCARD=BASE_URL+"credit/web/credit-card/face-plus-idcard";
    /**
     * 上传图片2
     */
    public static final  String UPLOAD_IMAGE=BASE_URL+"credit/web/picture/upload-image";


}
