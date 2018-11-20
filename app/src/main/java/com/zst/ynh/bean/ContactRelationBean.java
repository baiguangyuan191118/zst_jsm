package com.zst.ynh.bean;

import java.util.List;

public class ContactRelationBean {

    /**
     * code : 0
     * message : 成功获取紧急联系人
     * data : {"item":{"lineal_relation":1,"lineal_name":"你好","lineal_mobile":"17766536852","other_relation":6,"other_name":"哦哦","other_mobile":"16588569853","lineal_list":[{"type":1,"name":"父亲"},{"type":3,"name":"母亲"},{"type":11,"name":"兄弟"},{"type":12,"name":"姐妹"},{"type":2,"name":"配偶"}],"other_list":[{"type":6,"name":"朋友"},{"type":7,"name":"同事"},{"type":8,"name":"亲戚"},{"type":100,"name":"其他"}],"special":0,"url":""}}
     */


    /**
     * item : {"lineal_relation":1,"lineal_name":"你好","lineal_mobile":"17766536852","other_relation":6,"other_name":"哦哦","other_mobile":"16588569853","lineal_list":[{"type":1,"name":"父亲"},{"type":3,"name":"母亲"},{"type":11,"name":"兄弟"},{"type":12,"name":"姐妹"},{"type":2,"name":"配偶"}],"other_list":[{"type":6,"name":"朋友"},{"type":7,"name":"同事"},{"type":8,"name":"亲戚"},{"type":100,"name":"其他"}],"special":0,"url":""}
     */

    public ItemBean item;


    public static class ItemBean {
        /**
         * lineal_relation : 1
         * lineal_name : 你好
         * lineal_mobile : 17766536852
         * other_relation : 6
         * other_name : 哦哦
         * other_mobile : 16588569853
         * lineal_list : [{"type":1,"name":"父亲"},{"type":3,"name":"母亲"},{"type":11,"name":"兄弟"},{"type":12,"name":"姐妹"},{"type":2,"name":"配偶"}]
         * other_list : [{"type":6,"name":"朋友"},{"type":7,"name":"同事"},{"type":8,"name":"亲戚"},{"type":100,"name":"其他"}]
         * special : 0
         * url :
         */

        public int lineal_relation;
        public String lineal_name;
        public String lineal_mobile;
        public int other_relation;
        public String other_name;
        public String other_mobile;
        public int special;
        public String url;
        public List<LinealListBean> lineal_list;
        public List<OtherListBean> other_list;


        public static class LinealListBean {
            /**
             * type : 1
             * name : 父亲
             */

            public int type;
            public String name;

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
        }

        public static class OtherListBean {
            /**
             * type : 6
             * name : 朋友
             */

            public int type;
            public String name;

        }
    }
}
