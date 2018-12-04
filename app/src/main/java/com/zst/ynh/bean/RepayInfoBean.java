package com.zst.ynh.bean;


import java.util.List;

public class RepayInfoBean {


    /**
     * code : 0
     * message : 成功获取
     * data : {"item":{"list":[{"current_period":"1","repay_id":"85681","status":"1","status_zh":"<font size='4' color='#9B56D5'>待还款<\/font>","repay_desc":"还款日2018-11-29","repay_time":"2018-11-29","repay_money":"1500.00"}],"total_data":{"money_amount":"1500.00","total_period":"7天","late_money":"1500.00","order_title":"借款金额","order_id":"590023"},"banner":{"button":1,"status":0,"can_loan_time":-2},"repayment_url":"http://jsm-test.nucdx.com/mobile/web/loan/loan-repayment-type?id=590023","detail_url":"http://jsm-test.nucdx.com/mobile/web/loan/loan-detail?id=590023"}}
     */

    public DataBean data;


    public static class DataBean {
        /**
         * item : {"list":[{"current_period":"1","repay_id":"85681","status":"1","status_zh":"<font size='4' color='#9B56D5'>待还款<\/font>","repay_desc":"还款日2018-11-29","repay_time":"2018-11-29","repay_money":"1500.00"}],"total_data":{"money_amount":"1500.00","total_period":"7天","late_money":"1500.00","order_title":"借款金额","order_id":"590023"},"banner":{"button":1,"status":0,"can_loan_time":-2},"repayment_url":"http://jsm-test.nucdx.com/mobile/web/loan/loan-repayment-type?id=590023","detail_url":"http://jsm-test.nucdx.com/mobile/web/loan/loan-detail?id=590023"}
         */

        public ItemBean item;
        public RiskStatus risk_status;


        public static class RiskStatus {
            public String register_url;
            public int status;
            public String message;

        }
        public static class ItemBean {
            /**
             * list : [{"current_period":"1","repay_id":"85681","status":"1","status_zh":"<font size='4' color='#9B56D5'>待还款<\/font>","repay_desc":"还款日2018-11-29","repay_time":"2018-11-29","repay_money":"1500.00"}]
             * total_data : {"money_amount":"1500.00","total_period":"7天","late_money":"1500.00","order_title":"借款金额","order_id":"590023"}
             * banner : {"button":1,"status":0,"can_loan_time":-2}
             * repayment_url : http://jsm-test.nucdx.com/mobile/web/loan/loan-repayment-type?id=590023
             * detail_url : http://jsm-test.nucdx.com/mobile/web/loan/loan-detail?id=590023
             */

            public TotalDataBean total_data;
            public BannerBean banner;
            public String repayment_url;
            public String detail_url;
            public List<ListBean> list;


            public static class TotalDataBean {
                /**
                 * money_amount : 1500.00
                 * total_period : 7天
                 * late_money : 1500.00
                 * order_title : 借款金额
                 * order_id : 590023
                 */

                public String money_amount;
                public String total_period;
                public String late_money;
                public String order_title;
                public String order_id;

            }

            public static class BannerBean {
                /**
                 * button : 1
                 * status : 0
                 * can_loan_time : -2
                 */
                public String message;
                public int button;
                public int status;
                public String can_loan_time;

            }

            public static class ListBean {
                /**
                 * current_period : 1
                 * repay_id : 85681
                 * status : 1
                 * status_zh : <font size='4' color='#9B56D5'>待还款</font>
                 * repay_desc : 还款日2018-11-29
                 * repay_time : 2018-11-29
                 * repay_money : 1500.00
                 */

                public String current_period;
                public String repay_id;
                public int status;
                public String status_zh;
                public String repay_desc;
                public String repay_time;
                public String repay_money;

            }
        }
    }
}
