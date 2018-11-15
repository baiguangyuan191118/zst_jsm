package com.zst.ynh.bean;

import java.util.List;

/*
* 借款记录
* */
public class LoanRecordBean {

    private List<LoanRecordListBean> item;//借款记录集合

    private String link_url;//还款帮助 跳转链接

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public List<LoanRecordListBean> getItem() {
        return item;
    }

    public void setItem(List<LoanRecordListBean> item) {
        this.item = item;
    }

    public static class LoanRecordListBean{
        private String title;//标题
        private String time;//时间
        private String url;//链接
        private String text;//状态
        private String rep_id;
        private boolean is_repay;


        public String getRep_id() {
            return rep_id;
        }

        public void setRep_id(String rep_id) {
            this.rep_id = rep_id;
        }

        public boolean isIs_repay() {
            return is_repay;
        }

        public void setIs_repay(boolean is_repay) {
            this.is_repay = is_repay;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

}
