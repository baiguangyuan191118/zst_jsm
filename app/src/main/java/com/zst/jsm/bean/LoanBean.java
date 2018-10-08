package com.zst.jsm.bean;

import java.util.List;

public class LoanBean {

    /**
     * item : [{"id":"13","title":"test1","color":"","data":"测试而已","position":"APP_INDEX","skip_code":"101","status":"up","created_at":"2018-09-18 19:30:36","updated_at":"2018-09-18 19:30:36","up_at":"2018-09-18 19:30:36","order":"1","active_url":"","img_url":"https://www.baidu.com/img/baidu_jgylogo3.gif"}]
     * amounts : [100000,110000,120000,130000,140000,150000,160000,170000,180000,190000,200000,210000,220000,230000,240000,250000,260000,270000,280000,290000,300000,310000,320000,330000,340000,350000,360000,370000,380000,390000,400000,410000,420000,430000,440000,450000,460000,470000,480000,490000,500000,510000,520000,530000,540000,550000,560000,570000,580000,590000,600000,610000,620000,630000,640000,650000,660000,670000,680000,690000,700000,710000,720000,730000,740000,750000,760000,770000,780000,790000,800000,810000,820000,830000,840000,850000,860000,870000,880000,890000,900000,910000,920000,930000,940000,950000,960000,970000,980000,990000,1000000]
     * amounts_max : 1000000
     * unused_amount : 0
     * is_contact : 0
     * user_loan_log_list : ["我们的客服不会以任何名义要求您还款至个人账户，请使用官方提供的还款方式进行还款","我们郑重承诺：不向学生提供服务","我们积极拥抱监管，服务已全面升级，欢迎体验","今日待抢额度：25000000元","不向学生提供服务"]
     * amounts_min : 50000
     * title : 分期商城
     * title_v2 : http://jsm-test.nucdx.com/res/RRKJ/images/index/title.png
     * amount_button : 2
     * service_fee : {"interest_rate":0.02,"interest_rate_des":"月利率与投资人的资金成本相关，此处展示仅作为参考"}
     * message : {"message_url":"http://jsm-test.nucdx.com/mobile/web/app-page/msg-center","message_no":1}
     * period_num : [{"pk":14,"pv":"14天"}]
     * is_show_installment : 0
     */

    public int amounts_max;
    public int unused_amount;
    public int is_contact;
    public int amounts_min;
    public String title;
    public String title_v2;
    public String amount_button;
    public ServiceFeeBean service_fee;
    public MessageBean message;
    public int is_show_installment;
    public List<ItemBean> item;
    public List<Integer> amounts;
    public List<String> user_loan_log_list;
    public List<PeriodNumBean> period_num;


    public static class ServiceFeeBean {
        /**
         * interest_rate : 0.02
         * interest_rate_des : 月利率与投资人的资金成本相关，此处展示仅作为参考
         */

        public double interest_rate;
        public String interest_rate_des;


    }

    public static class MessageBean {
        /**
         * message_url : http://jsm-test.nucdx.com/mobile/web/app-page/msg-center
         * message_no : 1
         */

        public String message_url;
        public int message_no;


    }

    public static class ItemBean {
        /**
         * id : 13
         * title : test1
         * color :
         * data : 测试而已
         * position : APP_INDEX
         * skip_code : 101
         * status : up
         * created_at : 2018-09-18 19:30:36
         * updated_at : 2018-09-18 19:30:36
         * up_at : 2018-09-18 19:30:36
         * order : 1
         * active_url :
         * img_url : https://www.baidu.com/img/baidu_jgylogo3.gif
         */

        public String id;
        public String title;
        public String color;
        public String data;
        public String position;
        public String skip_code;
        public String status;
        public String created_at;
        public String updated_at;
        public String up_at;
        public String order;
        public String active_url;
        public String img_url;


    }

    public static class PeriodNumBean {
        /**
         * pk : 14
         * pv : 14天
         */

        public int pk;
        public String pv;


    }
}
