package com.zst.ynh.bean;

public class IdCardInfoBean {

    /**
     * code : 0
     * data : {"birthday":{"year":"1992","day":"10","month":"1"},"name":"朱泽斌","race":"汉","address":"福建省宁德市蕉城区霍童镇坑头村1号","time_used":364,"gender":"男","head_rect":{"rt":{"y":0.18402778,"x":0.8524416},"lt":{"y":0.19444445,"x":0.596603},"lb":{"y":0.6736111,"x":0.61889595},"rb":{"y":0.6597222,"x":0.8970276}},"request_id":"1542877663,a15cc0f0-d884-4623-a024-945a68495f57","id_card_number":"352201199201103613","side":"front"}
     * message : null
     */

    public DataBean data;


    public static class DataBean {
        /**
         * birthday : {"year":"1992","day":"10","month":"1"}
         * name : 朱泽斌
         * race : 汉
         * address : 福建省宁德市蕉城区霍童镇坑头村1号
         * time_used : 364
         * gender : 男
         * head_rect : {"rt":{"y":0.18402778,"x":0.8524416},"lt":{"y":0.19444445,"x":0.596603},"lb":{"y":0.6736111,"x":0.61889595},"rb":{"y":0.6597222,"x":0.8970276}}
         * request_id : 1542877663,a15cc0f0-d884-4623-a024-945a68495f57
         * id_card_number : 352201199201103613
         * side : front
         */

        public BirthdayBean birthday;
        public String name;
        public String race;
        public String address;
        public int time_used;
        public String gender;
        public HeadRectBean head_rect;
        public String request_id;
        public String id_card_number;
        public String side;


        public static class BirthdayBean {
            /**
             * year : 1992
             * day : 10
             * month : 1
             */

            public String year;
            public String day;
            public String month;

        }

        public static class HeadRectBean {
            /**
             * rt : {"y":0.18402778,"x":0.8524416}
             * lt : {"y":0.19444445,"x":0.596603}
             * lb : {"y":0.6736111,"x":0.61889595}
             * rb : {"y":0.6597222,"x":0.8970276}
             */

            public RtBean rt;
            public LtBean lt;
            public LbBean lb;
            public RbBean rb;


            public static class RtBean {
                /**
                 * y : 0.18402778
                 * x : 0.8524416
                 */

                public double y;
                public double x;

            }

            public static class LtBean {
                /**
                 * y : 0.19444445
                 * x : 0.596603
                 */

                public double y;
                public double x;

            }

            public static class LbBean {
                /**
                 * y : 0.6736111
                 * x : 0.61889595
                 */

                public double y;
                public double x;

            }

            public static class RbBean {
                /**
                 * y : 0.6597222
                 * x : 0.8970276
                 */

                public double y;
                public double x;

            }
        }
    }
}
