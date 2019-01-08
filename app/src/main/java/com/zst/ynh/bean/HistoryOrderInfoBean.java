package com.zst.ynh.bean;

import java.util.List;

public class HistoryOrderInfoBean {

    public List<OrderItem> item;
    public String link_url;


    /*
    *
    *  {
                "title":"借款 1500.00元",
                "time":"2019-01-04 16:30",
                "url":"http://jsm-wzz.nucdx.com/mobile/web/loan/loan-detail?id=590500",
                "text":"<font color="#B0B0B0" size="3">已还款</font>",
                "rep_id":"85885",
                "is_repay":false,
                "platform":"91来钱快",
                "platform_code":"91",
                "logo":"http://jsm-test.nucdx.com/res/RRKJ/images/repay/weixin@2x.png",
                "status_text":"待还款",
                "repayment_date":"2019-01-10",
                "money":"1500.00",
                "period":"7"
            }
    * */

   public static class OrderItem{
       public String title;
       public String time;
       public String url;
       public String text;
       public String rep_id;
       public boolean is_repay;
       public String platform;
       public String platform_code;
       public String logo;
       public String status_text;
       public String repayment_date;
       public String money;
       public String period;
   }



}
