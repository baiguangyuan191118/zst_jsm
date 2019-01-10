package com.zst.ynh.bean;

public class TokenStatusBean {


    /**
     * code : 0
     * message : 获取成功
     * data : {"sync_success":1,"token_overdue":0,"jsl_overdue":0,"zmop_overdue":0}
     */

    public int code;
    public String message;
    public DataBean data;

    public static class DataBean {
        /**
         * sync_success : 1
         * token_overdue : 0
         * jsl_overdue : 0
         * zmop_overdue : 0
         */

        public int sync_success;
        public int token_overdue;
        public int jsl_overdue;
        public int zmop_overdue;

    }
}
