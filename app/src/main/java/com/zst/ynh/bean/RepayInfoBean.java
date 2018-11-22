package com.zst.ynh.bean;

import java.util.List;

public class RepayInfoBean {
    public RepaymentItemBean item;

    public static class RepaymentItemBean{

        public TotalData total_data; //0：表示单期账单

        public List<RepaymentListBean> list;//待还信息列表

        public RepaymentBanner banner;

        public String detail_url;//跳转h5页面url地址(查看详情)

        public String repayment_url;//跳转h5页面url地址(立即还款)

        public String count;//待还比数

        public String pay_title;//支付方式文案

        public List<PayType> pay_type;//还款方式

        public static class TotalData{
            public String order_title; //订单标题
            public String money_amount;//借款金额
            public String total_period; //借款期限
            public String late_money; //还款金额
        }

        public static class RepaymentListBean{
            public int pay_state;
            public String debt;//实际欠款金额
            public String principal;//借款本金
            public String counter_fee;//服务费
            public String receipts;//实际到账金额
            public String coupon_fee;//优惠券
            public String interests;//利息
            public String late_fee;//逾期还款滞纳金
            public String plan_repayment_time;//应还日期
            public String true_total_money;//已还金额
            public String is_overdue;//是否逾期
            public String overdue_day;//逾期天数
            public String text_tip;//文本提示
            public String url;//跳转h5页面url地址(立即还款)
            public String current_period; //当前期数
            public String repay_id; //还款id
            public int status; //状态
            public String status_zh; //待还款（状态）
            public String repay_time; //还款日 02-12
            public String repay_desc; //还款日 2018-02-12 第1/3期（描述）
            public String repay_money; //当期还款金额

        }

        public static class RepaymentBanner{
            public int button; //授信状态 1:授信完成 2:授信中
            public int status; //借款审核状态 1:审核中 2:驳回
            public String can_loan_time; //再次借款时间间隔 0
            public String export_title; //被拒情况下按钮文本
            public String export_url; //被拒情况下按钮链接
            public String message;
        }

        public static class PayType{
            /**
             * type : 1
             * title : 主动还款(银行卡)
             * img_url : http://192.168.39.214/kdkj/credit/web/image/card/union_pay.png
             * link_url : http://api.koudailc.com/page/detail?id=629
             */

            public int type;//类型  1、主动还款(银行卡)  2、到期自动扣款(银行卡)  3、支付宝还款
            public String title;//标题
            public String img_url;//图片url
            public String link_url;//链接
        }

    }

}
