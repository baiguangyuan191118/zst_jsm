package com.zst.ynh.bean;

import java.io.Serializable;
import java.util.List;

public class PaymentStyleBean implements Serializable {
   /*
   *   "platformCode":"91",
        "repaymentId":"85888",
        "loanAmount":1500,
        "lateFee":0,
        "amountPayable":1500,
        "couponCount":1,
        "couponList":[
            {
                "user_coupon_id":"257",
                "user_id":"419734",
                "coupon_id":"3",
                "coupon_amount":"30.00",
                "status":"1",
                "is_deduction":"1",
                "expire_day":"15",
                "channel":"1",
                "is_locked":"0",
                "is_effect":"0",
                "expire_at":"1547913599",
                "limit_text":"无限制",
                "expire_start":"2019-01-05",
                "expire_end":"2019-01-19"
            }
        ],
        "paymentMethod":[
            {
                "type":2,
                "isRecommend":true,
                "name":"快捷支付",
                "url":"http://jsm-test.nucdx.com/credit/web/credit-loan/repayment-by-web?repaymentId=85888&platform_code=91",
                "iconUrl":"http://jsm-test.nucdx.com/res/RRKJ/images/repay/youlian-kuaijiezhifu@2x.png"
            },
            {
                "type":3,
                "isRecommend":false,
                "name":"微信支付",
                "url":"http://jsm-test.nucdx.com/credit/web/credit-loan/repayment-by-wechat?repaymentId=85888&platform_code=91",
                "iconUrl":"http://jsm-test.nucdx.com/res/RRKJ/images/repay/weixin@2x.png"
            },
            {
                "type":1,
                "isRecommend":false,
                "name":"支付宝支付",
                "url":"http://jsm-test.nucdx.com/credit/web/credit-loan/repayment-by-alipay?repaymentId=85888&platform_code=91",
                "iconUrl":"http://jsm-test.nucdx.com/res/RRKJ/images/repay/zhifubao@2x.png"
            },
            {
                "type":4,
                "isRecommend":false,
                "name":"绑卡支付",
                "url":"http://jsm-test.nucdx.com/credit/web/credit-loan/repayment-by-realname?repaymentId=85888&platform_code=91",
                "iconUrl":"http://jsm-test.nucdx.com/res/RRKJ/images/repay/zhifu@2x.png"
            }
        ]
    }
   *
   * */

    public String platformCode;
    public String repaymentId;
    public float loanAmount;
    public float lateFee;
    public float amountPayable;
    public int couponCount;
    public List<Coupon> couponList;
    public List<PaymentMethodBean> paymentMethod;

    public static class Coupon implements Serializable {
        public String user_coupon_id;
        public String user_id;
        public String counpon_id;
        public String coupon_amount;
        public String status;
        public String is_deduction;
        public String channel;
        public String is_locked;
        public String is_effect;
        public String expire_start;
        public String expire_end;
        public String limit_text;
    }

    public static class PaymentMethodBean implements Serializable{
        /**
         * type : 1
         * name : 支付宝支付
         * successUrl : http://...
         * callbackUrl : http://...
         * iconUrl : http://...
         */

        public int type;
        public String name;
        public String url;
        public boolean isRecommend;
        public String iconUrl;

    }
}
