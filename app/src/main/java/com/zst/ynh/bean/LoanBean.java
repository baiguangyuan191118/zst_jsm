package com.zst.ynh.bean;

import java.util.List;

public class LoanBean {


    /**
     * item : [{"id":"2","title":"首页banner1","color":"","data":"","position":"APP_INDEX","skip_code":"101","status":"up","created_at":"2018-10-12 09:40:54","updated_at":"2018-10-12 09:40:54","up_at":"2018-10-12 09:40:54","order":"10","active_url":"","img_url":"http://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/yunying_img/yunying_img_01.jpg"},{"id":"3","title":"图片2","color":"","data":"","position":"APP_INDEX","skip_code":"101","status":"up","created_at":"2018-10-12 09:41:23","updated_at":"2018-10-12 10:07:27","up_at":"2018-10-12 09:41:23","order":"9","active_url":"","img_url":"http://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/yunying_img/yunying_img_02.png"},{"id":"4","title":"图片3","color":"","data":"","position":"APP_INDEX","skip_code":"101","status":"up","created_at":"2018-10-12 09:41:48","updated_at":"2018-10-12 09:41:48","up_at":"2018-10-12 09:41:48","order":"7","active_url":"","img_url":"http://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/yunying_img/yunying_img_03.png"}]
     * amounts : [100000,110000,120000,130000,140000,150000]
     * amounts_max : 150000
     * unused_amount : 500000
     * is_contact : 0
     * user_loan_log_list : ["我们的客服不会以任何名义要求您还款至个人账户，请使用官方提供的还款方式进行还款","我们郑重承诺：不向学生提供服务","我们积极拥抱监管，服务已全面升级，欢迎体验","今日待抢额度：25100000元","不向学生提供服务"]
     * amounts_min : 50000
     * title : 分期商城
     * title_v2 : http://yy.jsm.51zxdai.com/res/RRKJ/images/index/title.png
     * amount_button : 1
     * service_fee : {"interest_rate":0.001,"interest_rate_des":"利率与投资人的资金成本相关日，不包含平台使用费，此处展示仅作为参考"}
     * message : {"message_url":"http://yy.jsm.51zxdai.com/mobile/web/app-page/msg-center","message_no":0}
     * period_num : [{"pk":"7","pv":"7天"}]
     * is_show_installment : 0
     */

    public int amounts_max;
    public int unused_amount;
    public int is_contact;
    public int amounts_min;
    public String title;
    public String title_v2;
    public int amount_button;
    public ServiceFeeBean service_fee;
    public MessageBean message;
    public int is_show_installment;
    public List<ItemBean> item;
    public List<Integer> amounts;
    public List<String> user_loan_log_list;
    public List<PeriodNumBean> period_num;


    public static class ServiceFeeBean {
        /**
         * interest_rate : 0.001
         * interest_rate_des : 利率与投资人的资金成本相关日，不包含平台使用费，此处展示仅作为参考
         */

        public double interest_rate;
        public String interest_rate_des;


    }

    public static class MessageBean {
        /**
         * message_url : http://yy.jsm.51zxdai.com/mobile/web/app-page/msg-center
         * message_no : 0
         */

        public String message_url;
        public int message_no;


    }

    public static class ItemBean {
        /**
         * id : 2
         * title : 首页banner1
         * color :
         * data :
         * position : APP_INDEX
         * skip_code : 101
         * status : up
         * created_at : 2018-10-12 09:40:54
         * updated_at : 2018-10-12 09:40:54
         * up_at : 2018-10-12 09:40:54
         * order : 10
         * active_url :
         * img_url : http://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/yunying_img/yunying_img_01.jpg
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
         * pk : 7
         * pv : 7天
         */

        public String pk;
        public String pv;


    }
}
