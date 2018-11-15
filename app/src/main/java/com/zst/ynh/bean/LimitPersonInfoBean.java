package com.zst.ynh.bean;

import java.util.List;

public class LimitPersonInfoBean {

    /**
     * code : 0
     * message : 成功获取个人信息
     * data : {"info":{"degrees":"0","marriage":"0","address":"","address_period":"0","address_distinct":"","longitude":"","latitude":""},"degrees_all":[{"degrees":4,"name":"大专"},{"degrees":5,"name":"中专"},{"degrees":3,"name":"本科"},{"degrees":2,"name":"硕士及以上"},{"degrees":6,"name":"高中及以下"}],"marriage_all":[{"marriage":1,"name":"未婚"},{"marriage":2,"name":"已婚未育"},{"marriage":3,"name":"已婚已育"},{"marriage":4,"name":"离异"},{"marriage":100,"name":"其他"}],"live_time_type_all":[{"live_time_type":1,"name":"半年以内"},{"live_time_type":2,"name":"半年到一年"},{"live_time_type":3,"name":"一年以上"}]}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * info : {"degrees":"0","marriage":"0","address":"","address_period":"0","address_distinct":"","longitude":"","latitude":""}
         * degrees_all : [{"degrees":4,"name":"大专"},{"degrees":5,"name":"中专"},{"degrees":3,"name":"本科"},{"degrees":2,"name":"硕士及以上"},{"degrees":6,"name":"高中及以下"}]
         * marriage_all : [{"marriage":1,"name":"未婚"},{"marriage":2,"name":"已婚未育"},{"marriage":3,"name":"已婚已育"},{"marriage":4,"name":"离异"},{"marriage":100,"name":"其他"}]
         * live_time_type_all : [{"live_time_type":1,"name":"半年以内"},{"live_time_type":2,"name":"半年到一年"},{"live_time_type":3,"name":"一年以上"}]
         */

        public InfoBean info;
        public List<DegreesAllBean> degrees_all;
        public List<MarriageAllBean> marriage_all;
        public List<LiveTimeTypeAllBean> live_time_type_all;


        public static class InfoBean {
            /**
             * degrees : 0
             * marriage : 0
             * address :
             * address_period : 0
             * address_distinct :
             * longitude :
             * latitude :
             */

            public String degrees;
            public String marriage;
            public String address;
            public String address_period;
            public String address_distinct;
            public String longitude;
            public String latitude;

        }

        public static class DegreesAllBean {
            /**
             * degrees : 4
             * name : 大专
             */

            public int degrees;
            public String name;

        }

        public static class MarriageAllBean {
            /**
             * marriage : 1
             * name : 未婚
             */

            public int marriage;
            public String name;

        }

        public static class LiveTimeTypeAllBean {
            /**
             * live_time_type : 1
             * name : 半年以内
             */

            public int live_time_type;
            public String name;

        }
    }
}
