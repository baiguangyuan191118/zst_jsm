package com.zst.ynh.bean;


import java.io.Serializable;
import java.util.List;

public class DepositOpenInfoVBean {
    public int is_deposit_open_account;
    public int have_card;
    public String about_deposit;
    public String deposit_tips;
    public String bank_name;
    public String bank_num;
    public String phone_num;
    public String card_remark;
    public String card_no;
    public int bank_id;
    public Alert alert;
    public List<Protocol> protocols;
    public static class Alert{
        String alert_title;
        String alert_desc;
        String alert_confirm;
        /**
         * 关于存管 标题
         */
        String about_title;
        /**
         * 关于存管的跳转链接
         */
        String about_deposit;
    }
    public static class Protocol implements Serializable {
        String protocol_name;
        String protocol_url;

    }
}
