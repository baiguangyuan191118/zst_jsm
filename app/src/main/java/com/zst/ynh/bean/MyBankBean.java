package com.zst.ynh.bean;

import java.util.List;

public class MyBankBean {
    public List<Bank> card_list;
    public int can_set_main_card;
    public int is_show_notice;
    public String notice_msg;
    public String tips;

    public static class Bank {

        /**
         * id : 283
         * card_no : **** **** ****8930
         * bank_id : 1
         * bank_name : 工商银行
         * bank_logo : http://local.jsqb.com/credit/web/image/bank/bank_1.png
         * tag : 主卡
         * tag_msg :
         */
        public long id;
        public String card_no;
        public long bank_id;
        public String bank_name;
        public String bank_logo;
        public int tag;
        public String tag_msg;
        public int is_main_card;
    }
}
