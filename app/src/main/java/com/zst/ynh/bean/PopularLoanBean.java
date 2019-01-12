package com.zst.ynh.bean;

import java.util.List;

public class PopularLoanBean {


    /**
     * code : 0
     * message : success
     * data : [{"id":"1","app_name":"由你花","app_url":"http://jsm-test.nucdx.com","tag":"ynh","company":"由你花有限公司","logo":"http://jsm-test.nucdx.com/res/RRKJ/images/super_loan/ynh.png","quato":"1000-6000","introduction":"非常好的平台","is_recommend":"1","is_online":"1","interest_rate":"1","start_term":"1","end_term":"1","created_at":"0","updated_at":"1546858777","application_conditions":"1、123\r\n2、456\r\n3、567","is_online_updated_at":"1546852108","count":null},{"id":"2","app_name":"91来钱快","app_url":"http://fhh-test-app.51zxdai.com","tag":"91lqk","company":"91来钱快有限公司","logo":"http://jsm-test.nucdx.com/res/RRKJ/images/super_loan/91lqk.png","quato":"0","introduction":"s","is_recommend":"1","is_online":"1","interest_rate":"1","start_term":"1","end_term":"1","created_at":"0","updated_at":"1546846865","application_conditions":"","is_online_updated_at":null,"count":null}]
     */

    public int code;
    public String message;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 1
         * app_name : 由你花
         * app_url : http://jsm-test.nucdx.com
         * tag : ynh
         * company : 由你花有限公司
         * logo : http://jsm-test.nucdx.com/res/RRKJ/images/super_loan/ynh.png
         * quato : 1000-6000
         * introduction : 非常好的平台
         * is_recommend : 1
         * is_online : 1
         * interest_rate : 1
         * start_term : 1
         * end_term : 1
         * created_at : 0
         * updated_at : 1546858777
         * application_conditions : 1、123
         2、456
         3、567
         * is_online_updated_at : 1546852108
         * count : null
         * page_url:
         */

        public String id;
        public String app_name;
        public String app_url;
        public String tag;
        public String company;
        public String start_quato;
        public String end_quato;
        public String logo;
        public String quato;
        public String introduction;
        public String is_recommend;
        public String is_online;
        public String interest_rate;
        public String start_term;
        public String end_term;
        public String created_at;
        public String updated_at;
        public String application_conditions;
        public String is_online_updated_at;
        public String count;
        public String page_url;


    }
}
