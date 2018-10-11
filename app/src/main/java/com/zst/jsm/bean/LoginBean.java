package com.zst.jsm.bean;

public class LoginBean {

    /**
     * item : {"uid":411198,"username":"17621228636","realname":"*源鸿","sessionid":"b14ubp1qb1h85gd41q3mh4hgui","special":0,"face":0,"real_contact_status":0,"mall_url":""}
     * is_show_installment : 0
     */
    public ItemBean item;
    public int is_show_installment;

    public static class ItemBean {
        /**
         * uid : 411198
         * username : 17621228636
         * realname : *源鸿
         * sessionid : b14ubp1qb1h85gd41q3mh4hgui
         * special : 0
         * face : 0
         * real_contact_status : 0
         * mall_url : 
         */

        public int uid;
        public String username;
        public String realname;
        public String sessionid;
        public int special;
        public int face;
        public int real_contact_status;
        public String mall_url;

    }
}
