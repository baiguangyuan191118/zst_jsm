package com.zst.ynh.bean;

public class UpdateVersionBean {

    /**
     * item : {"id":101,"type":1,"has_upgrade":0,"is_force_upgrade":0,"new_ios_version":"1.0.1","new_version":"1.0.1","new_features":"222","ard_url":"http://101.132.76.15:17788/pack/jsm.apk","ard_size":"10000","update_time":1542875545,"operator_name":"admin","is_vest":"1","new_name":null,"test":"1","ios_url":"https://yy-jsm.51zxdai.com/res/RRKJ/html/down.html","ios_url111":"0"}
     */

    private ItemBean item;

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public static class ItemBean {
        /**
         * id : 101
         * type : 1
         * has_upgrade : 0
         * is_force_upgrade : 0
         * new_ios_version : 1.0.1
         * new_version : 1.0.1
         * new_features : 222
         * ard_url : http://101.132.76.15:17788/pack/jsm.apk
         * ard_size : 10000
         * update_time : 1542875545
         * operator_name : admin
         * is_vest : 1
         * new_name : null
         * test : 1
         * ios_url : https://yy-jsm.51zxdai.com/res/RRKJ/html/down.html
         * ios_url111 : 0
         */

        private int id;
        private int type;
        private int has_upgrade;
        private int is_force_upgrade;
        private String new_ios_version;
        private String new_version;
        private String new_features;
        private String ard_url;
        private String ard_size;
        private int update_time;
        private String operator_name;
        private String is_vest;
        private Object new_name;
        private String test;
        private String ios_url;
        private String ios_url111;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getHas_upgrade() {
            return has_upgrade;
        }

        public void setHas_upgrade(int has_upgrade) {
            this.has_upgrade = has_upgrade;
        }

        public int getIs_force_upgrade() {
            return is_force_upgrade;
        }

        public void setIs_force_upgrade(int is_force_upgrade) {
            this.is_force_upgrade = is_force_upgrade;
        }

        public String getNew_ios_version() {
            return new_ios_version;
        }

        public void setNew_ios_version(String new_ios_version) {
            this.new_ios_version = new_ios_version;
        }

        public String getNew_version() {
            return new_version;
        }

        public void setNew_version(String new_version) {
            this.new_version = new_version;
        }

        public String getNew_features() {
            return new_features;
        }

        public void setNew_features(String new_features) {
            this.new_features = new_features;
        }

        public String getArd_url() {
            return ard_url;
        }

        public void setArd_url(String ard_url) {
            this.ard_url = ard_url;
        }

        public String getArd_size() {
            return ard_size;
        }

        public void setArd_size(String ard_size) {
            this.ard_size = ard_size;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public String getOperator_name() {
            return operator_name;
        }

        public void setOperator_name(String operator_name) {
            this.operator_name = operator_name;
        }

        public String getIs_vest() {
            return is_vest;
        }

        public void setIs_vest(String is_vest) {
            this.is_vest = is_vest;
        }

        public Object getNew_name() {
            return new_name;
        }

        public void setNew_name(Object new_name) {
            this.new_name = new_name;
        }

        public String getTest() {
            return test;
        }

        public void setTest(String test) {
            this.test = test;
        }

        public String getIos_url() {
            return ios_url;
        }

        public void setIos_url(String ios_url) {
            this.ios_url = ios_url;
        }

        public String getIos_url111() {
            return ios_url111;
        }

        public void setIos_url111(String ios_url111) {
            this.ios_url111 = ios_url111;
        }
    }
}
