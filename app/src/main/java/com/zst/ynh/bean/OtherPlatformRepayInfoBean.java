package com.zst.ynh.bean;

import java.util.List;

public class OtherPlatformRepayInfoBean {

    /**
     * item : {"list":[{"current_period":"1","repay_id":"85888","status":"1","status_zh":"<font size='4' color='#9B56D5'>待还款<\/font>","repay_desc":"还款日2019-01-11","repay_time":"2019-01-11","repay_money":"1500.00","logo":"http://jsm-test.nucdx.com/res/RRKJ/images/repay/weixin@2x.png","platform":"91来钱快","platform_code":"91","status_text":"待还款","repayment_date":"2019-01-11","period":"7"},{"current_period":"1","repay_id":"85888","status":"1","status_zh":"<font size='4' color='#9B56D5'>待还款<\/font>","repay_desc":"还款日2019-01-11","repay_time":"2019-01-11","repay_money":"1500.00","logo":"http://jsm-test.nucdx.com/res/RRKJ/images/repay/weixin@2x.png","platform":"91来钱快","platform_code":"91","status_text":"待还款","repayment_date":"2019-01-11","period":"7"}]}
     */

    public ItemBean item;

    public static class ItemBean {
        public List<RepayItemBean> list;
    }
}
