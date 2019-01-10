package com.zst.ynh.bean;


import java.io.Serializable;
import java.util.List;

public class LoanConfirmBean implements Serializable {

    /**
     * item : {"dialog_credit_expired":null,"money":"1500.00","fees":{"interest":"10.50","other_fee":"288.75","desc":"如您未及时还款，会产生逾期滞纳金，用于弥补账户管理费用，计算标准为2.5元/天/100元"},"period":7,"url_one_text":"《借款协议》","url_two_text":"《平台服务协议》","url_three_text":"《授权扣款委托书》","url_one":"http://yy.jsm.51zxdai.com/credit/web/credit-web/platform-service-v400?day=7&money=1500&type=2&clientType=wap","url_two":"http://yy.jsm.51zxdai.com/credit/web/credit-web/loan-agreement-v400?day=7&money=1500&type=2&clientType=wap","url_three":"http://yy.jsm.51zxdai.com/credit/web/credit-web/license-agreement-v400?day=7&money=1500&type=2&clientType=wap","agreement":[{"title":"《借款协议》","url":"http://yy.jsm.51zxdai.com/credit/web/credit-web/platform-service-v400?day=7&money=1500&type=2&clientType=wap"}],"repayment_way":{"name":"分期还款","value":2},"repayment_way_dialog":"分期还款时，按照制定的还款日期还款，每期本息相同；一次性还本付息为到期后一次还清本金和利息","loan_use":[{"name":"教育","value":1},{"name":"装修","value":2},{"name":"旅游","value":3},{"name":"婚庆","value":4},{"name":"手机数码","value":5},{"name":"家用电器","value":6},{"name":"租房","value":7},{"name":"家具家居","value":8},{"name":"健康医疗","value":9},{"name":"其他","value":10},{"name":"购物消费","value":11}],"bank_name":"建设银行","card_no":"6217001210079328058","verify_loan_pass":1,"real_pay_pwd_status":1,"protocol_url":"http://yy.jsm.51zxdai.com/frontend/web/index.php?r=page/detail?id=627","protocol_msg":"","extra_tips":"","order_params":{"list1":[{"name":"借款金额","value":"1500.00元"},{"name":"借款期限","value":"7天"},{"name":"总利息","value":"299.25元"}],"list2":[{"name":"到账金额","value":"1200.75元"},{"name":"到账银行","value":"建设银行(尾号8058)"}],"list3":[{"name":"还款方式","value":"一次性还本付息"},{"name":"还款日","value":"2018-10-26"},{"name":"总计应还","value":"1500.00元"},{"name":"到期应还","value":"1500.00元"}]},"deposit":{"status":0,"bankcard":"6217001210079328058","phone_num":"18538568525","protocol_url1":"","protocol_url2":"","about_deposit":""}}
     */

    public ItemBean item;


    public static class ItemBean implements Serializable{
        /**
         * dialog_credit_expired : null
         * money : 1500.00
         * fees : {"interest":"10.50","other_fee":"288.75","desc":"如您未及时还款，会产生逾期滞纳金，用于弥补账户管理费用，计算标准为2.5元/天/100元"}
         * period : 7
         * url_one_text : 《借款协议》
         * url_two_text : 《平台服务协议》
         * url_three_text : 《授权扣款委托书》
         * url_one : http://yy.jsm.51zxdai.com/credit/web/credit-web/platform-service-v400?day=7&money=1500&type=2&clientType=wap
         * url_two : http://yy.jsm.51zxdai.com/credit/web/credit-web/loan-agreement-v400?day=7&money=1500&type=2&clientType=wap
         * url_three : http://yy.jsm.51zxdai.com/credit/web/credit-web/license-agreement-v400?day=7&money=1500&type=2&clientType=wap
         * agreement : [{"title":"《借款协议》","url":"http://yy.jsm.51zxdai.com/credit/web/credit-web/platform-service-v400?day=7&money=1500&type=2&clientType=wap"}]
         * repayment_way : {"name":"分期还款","value":2}
         * repayment_way_dialog : 分期还款时，按照制定的还款日期还款，每期本息相同；一次性还本付息为到期后一次还清本金和利息
         * loan_use : [{"name":"教育","value":1},{"name":"装修","value":2},{"name":"旅游","value":3},{"name":"婚庆","value":4},{"name":"手机数码","value":5},{"name":"家用电器","value":6},{"name":"租房","value":7},{"name":"家具家居","value":8},{"name":"健康医疗","value":9},{"name":"其他","value":10},{"name":"购物消费","value":11}]
         * bank_name : 建设银行
         * card_no : 6217001210079328058
         * verify_loan_pass : 1
         * real_pay_pwd_status : 1
         * protocol_url : http://yy.jsm.51zxdai.com/frontend/web/index.php?r=page/detail?id=627
         * protocol_msg :
         * extra_tips :
         * order_params : {"list1":[{"name":"借款金额","value":"1500.00元"},{"name":"借款期限","value":"7天"},{"name":"总利息","value":"299.25元"}],"list2":[{"name":"到账金额","value":"1200.75元"},{"name":"到账银行","value":"建设银行(尾号8058)"}],"list3":[{"name":"还款方式","value":"一次性还本付息"},{"name":"还款日","value":"2018-10-26"},{"name":"总计应还","value":"1500.00元"},{"name":"到期应还","value":"1500.00元"}]}
         * deposit : {"status":0,"bankcard":"6217001210079328058","phone_num":"18538568525","protocol_url1":"","protocol_url2":"","about_deposit":""}
         */

        public DialogCreditExpiredBean dialog_credit_expired;
        public String money;
        public FeesBean fees;
        public int period;
        public String url_one_text;
        public String url_two_text;
        public String url_three_text;
        public String url_one;
        public String url_two;
        public String url_three;
        public RepaymentWayBean repayment_way;
        public String repayment_way_dialog;
        public String bank_name;
        public String card_no;
        public int verify_loan_pass;
        public int real_pay_pwd_status;
        public String protocol_url;
        public String protocol_msg;
        public String extra_tips;
        public OrderParamsBean order_params;
        public DepositBean deposit;
        public List<AgreementBean> agreement;
        public List<LoanUseBean> loan_use;
        public int select_loanuse;

        public static class DialogCreditExpiredBean implements Serializable{
            public int code;
            public String message;
            public String title;
        }

        public static class FeesBean implements Serializable{
            /**
             * interest : 10.50
             * other_fee : 288.75
             * desc : 如您未及时还款，会产生逾期滞纳金，用于弥补账户管理费用，计算标准为2.5元/天/100元
             */

            public String interest;
            public String other_fee;
            public String desc;

        }

        public static class RepaymentWayBean implements Serializable{
            /**
             * name : 分期还款
             * value : 2
             */

            public String name;
            public int value;

        }

        public static class OrderParamsBean implements Serializable{
            public List<List1Bean> list1;
            public List<List2Bean> list2;
            public List<List3Bean> list3;


            public static class List1Bean implements Serializable{
                /**
                 * name : 借款金额
                 * value : 1500.00元
                 */

                public String name;
                public String value;

            }

            public static class List2Bean implements Serializable{
                /**
                 * name : 到账金额
                 * value : 1200.75元
                 */

                public String name;
                public String value;

            }

            public static class List3Bean implements Serializable{
                /**
                 * name : 还款方式
                 * value : 一次性还本付息
                 */

                public String name;
                public String value;

            }
        }

        public static class DepositBean implements Serializable{
            /**
             * status : 0
             * bankcard : 6217001210079328058
             * phone_num : 18538568525
             * protocol_url1 :
             * protocol_url2 :
             * about_deposit :
             */

            public int status;
            public String bankcard;
            public String phone_num;
            public String protocol_url1;
            public String protocol_url2;
            public String about_deposit;

        }

        public static class AgreementBean implements Serializable{
            /**
             * title : 《借款协议》
             * url : http://yy.jsm.51zxdai.com/credit/web/credit-web/platform-service-v400?day=7&money=1500&type=2&clientType=wap
             */

            public String title;
            public String url;

        }

        public static class LoanUseBean implements Serializable{
            /**
             * name : 教育
             * value : 1
             */

            public String name;
            public int value;

        }
    }
}
