package com.zst.ynh.bean;

import java.io.Serializable;
import java.util.List;

public class PaymentStyleBean implements Serializable {
    /**
     * code : 0
     * message : 成功
     * data : {"loanAmount":1000,"lateFee":0,"amountPayable":500,"paymentMethod":[{"type":1,"name":"支付宝支付","successUrl":"http://...","callbackUrl":"http://...","iconUrl":"http://..."}]}
     */


    /**
     * loanAmount : 1000
     * lateFee : 0
     * amountPayable : 500
     * paymentMethod : [{"type":1,"name":"支付宝支付","successUrl":"http://...","callbackUrl":"http://...","iconUrl":"http://..."}]
     */
    public DataBean data;


    public static class DataBean implements Serializable{
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
}
