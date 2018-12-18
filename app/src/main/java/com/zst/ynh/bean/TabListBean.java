package com.zst.ynh.bean;

import java.io.Serializable;
import java.util.List;

public class TabListBean implements Serializable {
    /**
     * bottom_nav : [{"icon":"http://jsm-test.nucdx.com/res/RRKJ/images/index/tab_loan.png","icon_on":"http://jsm-test.nucdx.com/res/RRKJ/images/index/tab_loan_1.png","name":"借款","name_color":"#babfc9","name_color_on":"#52628D","type":0,"url":"index"},{"icon":"http://jsm-test.nucdx.com/res/m/img/icon/kouzi.png","icon_on":"http://jsm-test.nucdx.com/res/m/img/icon/kouzi_on.png","name":"口子","name_color":"#babfc9","name_color_on":"#52628D","type":1,"url":"https://daichao.tianshengcm.com/index/Index/generalize.html?code=younihua"},{"icon":"http://jsm-test.nucdx.com/res/RRKJ/images/index/tab_repayment.png","icon_on":"http://jsm-test.nucdx.com/res/RRKJ/images/index/tab_repayment_1.png","name":"还款","name_color":"#babfc9","name_color_on":"#52628D","type":0,"url":"repayment/index"},{"icon":"http://jsm-test.nucdx.com/res/RRKJ/images/index/tab_mine.png","icon_on":"http://jsm-test.nucdx.com/res/RRKJ/images/index/tab_mine_1.png","name":"我的","name_color":"#babfc9","name_color_on":"#52628D","type":0,"url":"user/index"}]
     * bottom_nav_on : 1
     */

    private int bottom_nav_on;
    private List<BottomNavBean> bottom_nav;

    public int getBottom_nav_on() {
        return bottom_nav_on;
    }

    public void setBottom_nav_on(int bottom_nav_on) {
        this.bottom_nav_on = bottom_nav_on;
    }

    public List<BottomNavBean> getBottom_nav() {
        return bottom_nav;
    }

    public void setBottom_nav(List<BottomNavBean> bottom_nav) {
        this.bottom_nav = bottom_nav;
    }

    public static class BottomNavBean implements Serializable{
        /**
         * icon : http://jsm-test.nucdx.com/res/RRKJ/images/index/tab_loan.png
         * icon_on : http://jsm-test.nucdx.com/res/RRKJ/images/index/tab_loan_1.png
         * name : 借款
         * name_color : #babfc9
         * name_color_on : #52628D
         * type : 0
         * url : index
         */

        private String icon;
        private String icon_on;
        private String name;
        private String name_color;
        private String name_color_on;
        private int type;
        private String url;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIcon_on() {
            return icon_on;
        }

        public void setIcon_on(String icon_on) {
            this.icon_on = icon_on;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_color() {
            return name_color;
        }

        public void setName_color(String name_color) {
            this.name_color = name_color;
        }

        public String getName_color_on() {
            return name_color_on;
        }

        public void setName_color_on(String name_color_on) {
            this.name_color_on = name_color_on;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
