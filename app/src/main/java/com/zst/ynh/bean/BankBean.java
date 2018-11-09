package com.zst.ynh.bean;

import java.util.List;

public class BankBean {
    public List<BankItem> item;//银行卡列表
    public String tips;//提示
    public static class BankItem{
        public int bank_id;//银行id
        public String bank_name;//银行名称
        public String url;//银行图片URL
        public int card_id;//卡ID
        public int main_card;//卡类型
        public String bank_info;
        public String bank_info_name;
        public String bank_info_num;
        public int is_supprot_withhold;
    }
}
