package com.zst.ynh.bean;

import java.util.List;

public class LoanBean {


    /**
     * code : 0
     * message : success
     * data : {"item":[{"id":"14","title":"图片1","color":"","data":"","position":"APP_INDEX","skip_code":"101","status":"up","created_at":"2018-10-09 20:15:46","updated_at":"2018-10-09 20:15:46","up_at":"2018-10-09 20:15:46","order":"10","active_url":"","img_url":"http://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/yunying_img/yunying_img_01.jpg"},{"id":"15","title":"图片2","color":"","data":"","position":"APP_INDEX","skip_code":"101","status":"up","created_at":"2018-10-09 20:16:21","updated_at":"2018-12-05 15:55:18","up_at":"2018-10-09 20:16:21","order":"9","active_url":"http://jsm-test.nucdx.com/frontend/web/act/a2018/1204","img_url":"http://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/yunying_img/yunying_img_02.png"},{"id":"17","title":"限时提额","color":"","data":"","position":"APP_INDEX","skip_code":"108","status":"up","created_at":"2018-12-05 15:41:21","updated_at":"2018-12-05 15:57:24","up_at":"2018-12-05 15:41:21","order":"7","active_url":"http://jsm-test.nucdx.com/frontend/web/act/a2018/1204","img_url":"http://img.mp.itc.cn/upload/20161220/316de4085e884aa8ac7dcf2b7b92a58e_th.jpeg"}],"amounts":[120000,130000,140000,150000,160000,170000,180000,190000,200000,210000,220000,230000,240000,250000,260000,270000,280000,290000,300000,310000,320000,330000,340000,350000,360000,370000,380000,390000,400000,410000,420000,430000,440000,450000,460000,470000,480000,490000,500000],"amounts_max":500000,"unused_amount":0,"is_contact":0,"user_loan_log_list":["我们的客服不会以任何名义要求您还款至个人账户，请使用官方提供的还款方式进行还款","我们郑重承诺：不向学生提供服务","我们积极拥抱监管，服务已全面升级，欢迎体验","今日待抢额度：25150000元","不向学生提供服务"],"amounts_min":50000,"title":"分期商城","title_v2":"http://jsm-test.nucdx.com/res/RRKJ/images/index/title.png","amount_button":"0","service_fee":{"interest_rate":0.001,"interest_rate_des":"利率与投资人的资金成本相关日，不包含平台使用费，此处展示仅作为参考"},"risk_status":{"status":0},"export":{"icon":"http://jsm-test.nucdx.com/res/m/a20181204/img/xuanfu.png","url":"http://jsm-test.nucdx.com/frontend/web/act/a2018/1204","width":"200","height":"200"},"message":{"message_url":"http://jsm-test.nucdx.com/mobile/web/app-page/msg-center","message_no":0},"period_num":[{"pk":"7-14","pv":"7-14天"}],"is_show_installment":0}
     */

    public DataBean data;


    public static class DataBean {
        /**
         * item : [{"id":"14","title":"图片1","color":"","data":"","position":"APP_INDEX","skip_code":"101","status":"up","created_at":"2018-10-09 20:15:46","updated_at":"2018-10-09 20:15:46","up_at":"2018-10-09 20:15:46","order":"10","active_url":"","img_url":"http://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/yunying_img/yunying_img_01.jpg"},{"id":"15","title":"图片2","color":"","data":"","position":"APP_INDEX","skip_code":"101","status":"up","created_at":"2018-10-09 20:16:21","updated_at":"2018-12-05 15:55:18","up_at":"2018-10-09 20:16:21","order":"9","active_url":"http://jsm-test.nucdx.com/frontend/web/act/a2018/1204","img_url":"http://zst-test-youmihua.oss-cn-shanghai.aliyuncs.com/yunying_img/yunying_img_02.png"},{"id":"17","title":"限时提额","color":"","data":"","position":"APP_INDEX","skip_code":"108","status":"up","created_at":"2018-12-05 15:41:21","updated_at":"2018-12-05 15:57:24","up_at":"2018-12-05 15:41:21","order":"7","active_url":"http://jsm-test.nucdx.com/frontend/web/act/a2018/1204","img_url":"http://img.mp.itc.cn/upload/20161220/316de4085e884aa8ac7dcf2b7b92a58e_th.jpeg"}]
         * amounts : [120000,130000,140000,150000,160000,170000,180000,190000,200000,210000,220000,230000,240000,250000,260000,270000,280000,290000,300000,310000,320000,330000,340000,350000,360000,370000,380000,390000,400000,410000,420000,430000,440000,450000,460000,470000,480000,490000,500000]
         * amounts_max : 500000
         * unused_amount : 0
         * is_contact : 0
         * user_loan_log_list : ["我们的客服不会以任何名义要求您还款至个人账户，请使用官方提供的还款方式进行还款","我们郑重承诺：不向学生提供服务","我们积极拥抱监管，服务已全面升级，欢迎体验","今日待抢额度：25150000元","不向学生提供服务"]
         * amounts_min : 50000
         * title : 分期商城
         * title_v2 : http://jsm-test.nucdx.com/res/RRKJ/images/index/title.png
         * amount_button : 0
         * service_fee : {"interest_rate":0.001,"interest_rate_des":"利率与投资人的资金成本相关日，不包含平台使用费，此处展示仅作为参考"}
         * risk_status : {"status":0}
         * export : {"icon":"http://jsm-test.nucdx.com/res/m/a20181204/img/xuanfu.png","url":"http://jsm-test.nucdx.com/frontend/web/act/a2018/1204","width":"200","height":"200"}
         * message : {"message_url":"http://jsm-test.nucdx.com/mobile/web/app-page/msg-center","message_no":0}
         * period_num : [{"pk":"7-14","pv":"7-14天"}]
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
        public RiskStatusBean risk_status;
        public ExportBean export;
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

        public static class RiskStatusBean {
            /**
             * status : 0
             */

            public int status;
            public String message;
            public String register_url;

        }

        public static class ExportBean {
            /**
             * icon : http://jsm-test.nucdx.com/res/m/a20181204/img/xuanfu.png
             * url : http://jsm-test.nucdx.com/frontend/web/act/a2018/1204
             * width : 200
             * height : 200
             */

            public String icon;
            public String url;
            public String width;
            public String height;

        }

        public static class MessageBean {
            /**
             * message_url : http://jsm-test.nucdx.com/mobile/web/app-page/msg-center
             * message_no : 0
             */

            public String message_url;
            public int message_no;

        }

        public static class ItemBean {
            /**
             * id : 14
             * title : 图片1
             * color :
             * data :
             * position : APP_INDEX
             * skip_code : 101
             * status : up
             * created_at : 2018-10-09 20:15:46
             * updated_at : 2018-10-09 20:15:46
             * up_at : 2018-10-09 20:15:46
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
             * pk : 7-14
             * pv : 7-14天
             */

            public String pk;
            public String pv;

        }
    }
}
