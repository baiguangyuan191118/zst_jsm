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

        public String loanAmount;
        public String lateFee;
        public String amountPayable;
        public List<PaymentMethodBean> paymentMethod;


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
