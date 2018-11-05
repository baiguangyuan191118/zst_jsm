package com.zst.ynh.utils;

/**
 * 跳转控制类
 * <p>
 * -一切push跳转
 * -短信拉起跳转
 * -hybrid交互跳转
 * 非线程安全
 */

import android.net.Uri;


public class SchemeTool {
//    public static String MyScheme = BuildConfig.SELF_SCHEME;
    public static final String TAG_JUMP_LEND_HOME = "101";//借款首页
    public static final String TAG_JUMP_LEND_REPAY = "102";//还款列表
    public static final String TAG_JUMP_LEND_ACCOUNT = "103";//我的
    public static final String TAG_JUMP_LEND_COUPON = "104";//优惠券
    public static final String TAG_JUMP_LEND_REDPACK = "105";//现金红包
    public static final String TAG_JUMP_LEND_LOGIN = "106";//登录
    public static final String TAG_JUMP_LEND_CERTIFICATION_CENTER = "107";//认证中心
    public static final String TAG_JUMP_H5 = "108";//H5
    public static final String TAG_JUMP_ADD_BANK = "109";//绑卡
    public static final String TAG_JUMP_FEEDBACK = "110";//意见反馈
    public static final String TAG_JUMP_REPORT = "111";//催收投诉
    public static final String TAG_JUMP_CUSTOMER_SERVICE = "112";//智能客服
    public static final String TAG_JUMP_GUIDE_VERIFY = "113";//认证向导页面
    public static String url = "";//跳转的URL

    public static String skip_code = "";
    public static boolean isJump = false;

    /**************
     * 短信拉起的 有数据上报
     *
     * @param uri
     */

    private static Uri skipUri;

//    public static void scheme(Uri uri) {
//        if (uri == null || uri.getScheme() == null || !MyScheme.equals(uri.getScheme())) {
//            return;
//        }
//        skipUri = uri;
//        SchemeTool.skip_code = uri.getQueryParameter("skip_code");
//        String url = uri.getQueryParameter("url");
//        SchemeTool.url = url;
//        isJump = true;
//    }

    /*************
     * 非短信拉起 无上报
     *
     * @param uri
     */
//    public static void schemeWithNoReport(Uri uri) {
//        if (uri == null || uri.getScheme() == null) {
//            return;
//        }
//        skipUri = uri;
//
//        if ("http".equals(uri.getScheme()) || "https".equals(uri.getScheme())) {
//            SchemeTool.skip_code = "108";
//            SchemeTool.url = uri.toString();
//        } else if (MyScheme.equals(uri.getScheme())) {
//            SchemeTool.skip_code = uri.getQueryParameter("skip_code");
//            String url = uri.getQueryParameter("url");
//            SchemeTool.url = url;
//        }
//        isJump = true;
//    }

    /*********
     * 跳转web
     *
     * @param context
     */
//    private static boolean jumpWebView(Context context) {
//        if (!TAG_JUMP_H5.equals(skip_code)) {
//            return false;
//        }
//        if (url == null || url.trim().length() == 0)
//            return false;
//        Intent intent = new Intent(context, LoanWebViewActivity.class);
//        intent.putExtra("url", url);
//        context.startActivity(intent);
//        url = null;
//        return true;
//    }

    /*************
     * 跳转到主页后处理
     */
//    public static void jumpToMain(Uri uri, Context context) {
//        if (StringUtil.isBlank(skip_code))
//            schemeWithNoReport(uri);
//
//        isJump = true;
//        Intent intent = new Intent(context, MainActivity.class);
//        context.startActivity(intent);
//        if (!(context instanceof MainActivity)) {
//            ((Activity) context).finish();
//        }
//    }

//    /**********
//     * 页面简单跳转
//     *
//     * @param uri
//     */
//    public static boolean jumpOnly(Uri uri, final Context context) {
//        boolean flag = false;
//        if (StringUtil.isBlank(skip_code))
//            schemeWithNoReport(uri);
//        if (jumpWebView(context)) {
//            flag = true;
//        } else if (TAG_JUMP_LEND_COUPON.equals(skip_code)) {
//            flag = true;
//            if (!(context instanceof CouponListActivity)) {
//                UMCountUtil.instance().onClick(UMCountConfig.EVENT_Coupons_Pageview, "优惠券");
//                Intent intent = new Intent(context, CouponListActivity.class);
//                SchemeTool.startActivtyNeedLogin(context, intent);
//            }
//        } else if (TAG_JUMP_LEND_REDPACK.equals(skip_code)) {
//            flag = true;
//            if (!(context instanceof RedPackageActivity)) {
//                UMCountUtil.instance().onClick(UMCountConfig.EVENT_Red_Packet_Pageview, "现金红包");
//                Intent intent = new Intent(context, RedPackageActivity.class);
//                SchemeTool.startActivtyNeedLogin(context, intent);
//            }
//        } else if (TAG_JUMP_LEND_CERTIFICATION_CENTER.equals(skip_code)) {
//            flag = true;
//            if (!(context instanceof CertificationCenterActivityV2)) {
//                UMCountUtil.instance().onClick(UMCountConfig.EVENT_Certification_Pageview, "认证中心");
//                Intent intent = new Intent(context, CertificationCenterActivityV2.class);
//                SchemeTool.startActivtyNeedLogin(context, intent);
//            }
//        } else if (TAG_JUMP_LEND_LOGIN.equals(skip_code)) {
//            flag = true;
//            if (!(context instanceof LoginActivity) || !(context instanceof RegisterPhoneActivity) || !(context instanceof RegisterPasswordActivity)) {
//                goLogin(context);
//            }
//        } else if (TAG_JUMP_ADD_BANK.equals(skip_code)) {
//            flag = true;
//            Intent intent = new Intent(context, AddBankCardActivity.class);
//            SchemeTool.startActivtyNeedLogin(context, intent);
//        } else if (TAG_JUMP_FEEDBACK.equals(skip_code)) {
//            flag = true;
//            Intent intent = new Intent(context, FeedbackActivity.class);
//            SchemeTool.startActivtyNeedLogin(context, intent);
//        } else if (TAG_JUMP_REPORT.equals(skip_code)) {
//            flag = true;
//            Intent intent = new Intent(context, CollectionReportActivity.class);
//            SchemeTool.startActivtyNeedLogin(context, intent);
//        } else if (TAG_JUMP_CUSTOMER_SERVICE.equals(skip_code)) {
//            flag = true;
//            toCustomerService(context, 0L);
//        } else if (TAG_JUMP_GUIDE_VERIFY.equals(skip_code)) {
//            flag = true;
//            if (!(context instanceof VerifyGuideActivity)) {
//                Intent intent = new Intent(context, VerifyGuideActivity.class);
//                SchemeTool.startActivtyNeedLogin(context, intent);
//            }
//        }
//        LogUtil.Log("jumpOnly", "skipUri" + skipUri.toString());
//        if (flag == true) {
//            skip_code = "";
//            url = "";
//            skipUri = null;
//            isJump = false;
//        }
//
//        return flag;
//    }

//    /***************
//     * 跳转
//     *
//     * @param uri
//     */
//    public static void jump(Uri uri, Context context) {
//        if (uri == null || StringUtil.isBlank(uri.getScheme())) {
//            return;
//        }
//
//        schemeWithNoReport(uri);
//
//        if (TextUtils.isEmpty(skip_code)) {
//            LogUtil.Log("", "skip_code is empty");
//            isJump = false;
//            return;
//        }
//        if (!jumpOnly(uri, context))
//            jumpToMain(uri, context);
//    }
//
//    /************
//     * 判断跳转的逻辑
//     *
//     * @param context
//     */
//    public static void isNeedJump(Context context) {
//        if (isJump) {
//            schemeWithNoReport(skipUri);
//
//            if (TextUtils.isEmpty(skip_code)) {
//
//                LogUtil.Log("", "skip_code is empty");
//                isJump = false;
//                return;
//            }
//
//            if (context instanceof MainActivity) {
//                if (!SchemeTool.jumpOnly(skipUri, context)) {
//
//                    LogUtil.Log("skip_code", "skip_code" + skip_code);
//                    if (TAG_JUMP_LEND_HOME.equals(skip_code)) {
//                        EventBus.getDefault().post(new ChangeTabMainEvent(FragmentFactory.FragmentStatus.Lend));
//
//                    } else if (TAG_JUMP_LEND_ACCOUNT.equals(skip_code)) {
//                        EventBus.getDefault().post(new ChangeTabMainEvent(FragmentFactory.FragmentStatus.Account));
//
//                    } else if (TAG_JUMP_LEND_REPAY.equals(skip_code)) {
//                        EventBus.getDefault().post(new ChangeTabMainEvent(FragmentFactory.FragmentStatus.RentLend));
//                    }
//
//                    skip_code = "";
//                    url = "";
//                    skipUri = null;
//                    isJump = false;
//                }
//            } else {
//                SchemeTool.jump(skipUri, context);
//            }
//        }
//    }
//
//    public static void goLogin(Context context) {
//        String rName = SharePreferenceUtil.getInstance(context).getData(Constant.SHARE_TAG_LOGIN_REALNAME);
//        String uName = SharePreferenceUtil.getInstance(context).getData(Constant.SHARE_TAG_LOGIN_USERNAME);
//        if (!StringUtil.isBlank(uName) && StringUtil.isMobileNO(uName)) {
//            if (!StringUtil.isBlank(rName)) {
//                Intent intent = LoginActivity.makeIntent(context, uName);
//                context.startActivity(intent);
//            } else {
//                Intent intent = new Intent(context, LoginActivity.class);
//                context.startActivity(intent);
//            }
//        } else {
//            Intent intent = new Intent(context, LoginActivity.class);
//            context.startActivity(intent);
//        }
//    }
//
//    public static void startActivtyNeedLogin(Context context, Intent intent) {
//        if (!MyApplication.getConfig().getLoginStatus()) {
//            String rName = SharePreferenceUtil.getInstance(context).getData(Constant.SHARE_TAG_LOGIN_REALNAME);
//            String uName = SharePreferenceUtil.getInstance(context).getData(Constant.SHARE_TAG_LOGIN_USERNAME);
//            if (!StringUtil.isBlank(uName) && StringUtil.isMobileNO(uName)) {
//                if (!StringUtil.isBlank(rName)) {
//                    intent = LoginActivity.makeIntent(context, uName,0);
//                    context.startActivity(intent);
//                } else {
//                    intent = new Intent(context, LoginActivity.class);
//                    context.startActivity(intent);
//                }
//            } else {
//                intent = new Intent(context, LoginActivity.class);
//                context.startActivity(intent);
//            }
//        } else {
//            context.startActivity(intent);
//        }
//    }
//
//    public static void toCustomerService(Context context, long msgId) {
//        if (MyApplication.getConfig().getLoginStatus()) {
//            final String title = "急速米在线客服";
//            /**
//             * 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入。
//             * 三个参数分别为：来源页面的url，来源页面标题，来源页面额外信息（可自由定义）。
//             * 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
//             */
//            final ConsultSource source = new ConsultSource("", "急速米", "custom information string");
//            if (msgId > 0L) {
//                source.faqGroupId = msgId;
//            }
//            /**
//             * 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable()，
//             * 如果返回为false，该接口不会有任何动作
//             *
//             * @param context 上下文
//             * @param title   聊天窗口的标题
//             * @param source  咨询的发起来源，包括发起咨询的url，title，描述信息等
//             */
//            Unicorn.openServiceActivity(context, title, source);
//        } else {
//            goLogin(context);
//            return;
//        }
//    }

}
