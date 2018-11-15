package com.zst.ynh.bean;

import java.io.Serializable;
import java.util.List;

public class LoanDetailBean implements Serializable {
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

    private String loanAmount;
    private String lateFee;
    private String amountPayable;
    private List<PaymentMethodBean> paymentMethod;

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLateFee() {
        return lateFee;
    }

    public void setLateFee(String lateFee) {
        this.lateFee = lateFee;
    }

    public String getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(String amountPayable) {
        this.amountPayable = amountPayable;
    }

    public List<PaymentMethodBean> getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(List<PaymentMethodBean> paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public static class PaymentMethodBean implements Serializable{
        /**
         * type : 1
         * name : 支付宝支付
         * successUrl : http://...
         * callbackUrl : http://...
         * iconUrl : http://...
         */

        private int type;
        private String name;
        private String url;
        private boolean isRecommend;
        private String iconUrl;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isRecommend() {
            return isRecommend;
        }

        public void setRecommend(boolean recommend) {
            isRecommend = recommend;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }
    }
}
