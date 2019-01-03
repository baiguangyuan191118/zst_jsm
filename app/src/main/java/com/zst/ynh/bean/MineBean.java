package com.zst.ynh.bean;

import android.support.annotation.IntDef;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/*

我的界面

* */
public class MineBean implements Serializable{

    private MineItemBean item;

    public MineItemBean getItem() {
        return item;
    }

    public void setItem(MineItemBean item) {
        this.item = item;
    }

    public static class MineItemBean implements Serializable{
        private String card_info;//卡信息
        private String invite_code;//邀请码
        private CreditInfoBean credit_info;
        private VerifyInfoBean verify_info;
        private String card_url;//卡链接
        private String avatar;
        private String red_pack_total;
        private String speed_coins_url;
        /**
         * 是否跳转认证向导 1：是
         */
        private int target_guide;

        public int getTarget_guide() {
            return target_guide;
        }

        public void setTarget_guide(int target_guide) {
            this.target_guide = target_guide;
        }

        public String getSpeed_coins_url() {
            return speed_coins_url;
        }

        public void setSpeed_coins_url(String speed_coins_url) {
            this.speed_coins_url = speed_coins_url;
        }

        /**
         * share_title : 口袋现金
         * share_body : 口袋现金口袋现金口袋现金口袋现金口袋现金
         * share_logo : http://192.168.39.214/kdkj/credit/web/logo_share.png
         */

        private String share_title;
        private String share_body;
        private String share_logo;
        private String share_url;

        private List<MoreItem> item_list;

        public String getCard_info() {
            return card_info;
        }

        public void setCard_info(String card_info) {
            this.card_info = card_info;
        }

        public String getRed_pack_total() {
            return red_pack_total;
        }

        public void setRed_pack_total(String red_pack_total) {
            this.red_pack_total = red_pack_total;
        }

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public CreditInfoBean getCredit_info() {
            return credit_info;
        }

        public void setCredit_info(CreditInfoBean credit_info) {
            this.credit_info = credit_info;
        }

        public VerifyInfoBean getVerify_info() {
            return verify_info;
        }

        public void setVerify_info(VerifyInfoBean verify_info) {
            this.verify_info = verify_info;
        }

        public String getShare_title() {
            return share_title;
        }

        public void setShare_title(String share_title) {
            this.share_title = share_title;
        }

        public String getShare_body() {
            return share_body;
        }

        public void setShare_body(String share_body) {
            this.share_body = share_body;
        }

        public String getShare_logo() {
            return share_logo;
        }

        public void setShare_logo(String share_logo) {
            this.share_logo = share_logo;
        }

        public String getCard_url() {
            return card_url;
        }

        public void setCard_url(String card_url) {
            this.card_url = card_url;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public List<MoreItem> getItem_list() {
            return item_list;
        }

        public void setItem_list(List<MoreItem> item_list) {
            this.item_list = item_list;
        }
    }

    public static class CreditInfoBean implements Serializable {

        private String card_amount;//总额度
        private double card_apr;//卡的费率
        private String card_color; //卡片颜色
        private int card_late_apr;//逾期费率
        private int card_money_max;//最大可借金额
        private int card_money_min;//最小可借金额
        private String card_no;//卡号
        private int card_locked_amount;//
        private int card_period_max;//最大期限
        private int card_period_min;//最小期限
        private String card_subtitle;//卡的副标题
        private String card_title;//卡标题
        private int card_type;//卡类型
        private int card_unused_amount;//未借金额
        private int card_used_amount;//已借金额

        public String getCard_amount() {
            return card_amount;
        }

        public void setCard_amount(String card_amount) {
            this.card_amount = card_amount;
        }

        public double getCard_apr() {
            return card_apr;
        }

        public void setCard_apr(double card_apr) {
            this.card_apr = card_apr;
        }

        public String getCard_color() {
            return card_color;
        }

        public void setCard_color(String card_color) {
            this.card_color = card_color;
        }

        public int getCard_late_apr() {
            return card_late_apr;
        }

        public void setCard_late_apr(int card_late_apr) {
            this.card_late_apr = card_late_apr;
        }

        public int getCard_money_max() {
            return card_money_max;
        }

        public void setCard_money_max(int card_money_max) {
            this.card_money_max = card_money_max;
        }

        public int getCard_money_min() {
            return card_money_min;
        }

        public void setCard_money_min(int card_money_min) {
            this.card_money_min = card_money_min;
        }

        public String getCard_no() {
            return card_no;
        }

        public void setCard_no(String card_no) {
            this.card_no = card_no;
        }

        public int getCard_locked_amount() {
            return card_locked_amount;
        }

        public void setCard_locked_amount(int card_locked_amount) {
            this.card_locked_amount = card_locked_amount;
        }

        public int getCard_period_max() {
            return card_period_max;
        }

        public void setCard_period_max(int card_period_max) {
            this.card_period_max = card_period_max;
        }

        public int getCard_period_min() {
            return card_period_min;
        }

        public void setCard_period_min(int card_period_min) {
            this.card_period_min = card_period_min;
        }

        public String getCard_subtitle() {
            return card_subtitle;
        }

        public void setCard_subtitle(String card_subtitle) {
            this.card_subtitle = card_subtitle;
        }

        public String getCard_title() {
            return card_title;
        }

        public void setCard_title(String card_title) {
            this.card_title = card_title;
        }

        public int getCard_type() {
            return card_type;
        }

        public void setCard_type(int card_type) {
            this.card_type = card_type;
        }

        public int getCard_unused_amount() {
            return card_unused_amount;
        }

        public void setCard_unused_amount(int card_unused_amount) {
            this.card_unused_amount = card_unused_amount;
        }

        public int getCard_used_amount() {
            return card_used_amount;
        }

        public void setCard_used_amount(int card_used_amount) {
            this.card_used_amount = card_used_amount;
        }
    }

    public static class VerifyInfoBean implements Serializable {
        private int authentication_pass;
        private int authentication_total;
        private int real_bind_bank_card_status;//收款银行卡是否绑定  1已绑定
        private int real_contact_status;
        private int real_jxl_status;
        private int real_more_status;
        private int real_pay_pwd_status;//交易密码状态 1已设置
        private int real_verify_status;//实名认证状态 1已认证
        private int real_work_status;
        private int real_zmxy_status;
        private int verify_loan_pass;

        public int getAuthentication_pass() {
            return authentication_pass;
        }

        public void setAuthentication_pass(int authentication_pass) {
            this.authentication_pass = authentication_pass;
        }

        public int getAuthentication_total() {
            return authentication_total;
        }

        public void setAuthentication_total(int authentication_total) {
            this.authentication_total = authentication_total;
        }

        public int getReal_bind_bank_card_status() {
            return real_bind_bank_card_status;
        }

        public void setReal_bind_bank_card_status(int real_bind_bank_card_status) {
            this.real_bind_bank_card_status = real_bind_bank_card_status;
        }

        public int getReal_contact_status() {
            return real_contact_status;
        }

        public void setReal_contact_status(int real_contact_status) {
            this.real_contact_status = real_contact_status;
        }

        public int getReal_jxl_status() {
            return real_jxl_status;
        }

        public void setReal_jxl_status(int real_jxl_status) {
            this.real_jxl_status = real_jxl_status;
        }

        public int getReal_more_status() {
            return real_more_status;
        }

        public void setReal_more_status(int real_more_status) {
            this.real_more_status = real_more_status;
        }

        public int getReal_pay_pwd_status() {
            return real_pay_pwd_status;
        }

        public void setReal_pay_pwd_status(int real_pay_pwd_status) {
            this.real_pay_pwd_status = real_pay_pwd_status;
        }

        public int getReal_verify_status() {
            return real_verify_status;
        }

        public void setReal_verify_status(int real_verify_status) {
            this.real_verify_status = real_verify_status;
        }

        public int getReal_work_status() {
            return real_work_status;
        }

        public void setReal_work_status(int real_work_status) {
            this.real_work_status = real_work_status;
        }

        public int getReal_zmxy_status() {
            return real_zmxy_status;
        }

        public void setReal_zmxy_status(int real_zmxy_status) {
            this.real_zmxy_status = real_zmxy_status;
        }

        public int getVerify_loan_pass() {
            return verify_loan_pass;
        }

        public void setVerify_loan_pass(int verify_loan_pass) {
            this.verify_loan_pass = verify_loan_pass;
        }
    }

    public static final int LOAN_RECORDS = 1;// 借款记录
    public static final int PERFECT_INFO = 2;// 完善资料
    public static final int BANK_CARD = 3;// 收款银行卡
    public static final int HELP_CENTER = 4;// 帮助中心
    //public static final int MY_DISCOUNT = 5;// 我的优惠
    public static final int MESSAGE_CENTER = 6;// 消息中心
    public static final int MY_INVITATION = 7;// 我的邀请
    public static final int SETTINGS = 8;// 设置
    public static final int LIMIT = 10;// 额度管理
    public static final int REWARDS=11;//奖励金
    public static final int MY_DISCOUNT = 12;// 优惠券

    @IntDef({LOAN_RECORDS, PERFECT_INFO, BANK_CARD, HELP_CENTER, MESSAGE_CENTER, MY_INVITATION, SETTINGS,REWARDS,MY_DISCOUNT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MoreItemType {
    }

    public static class MoreItem implements Serializable {

        /**
         * title : 借款记录
         * subtitle :
         * group : 1
         * tag : 1
         * logo : http://192.168.39.214/kdkj/credit/web/image/tag/loan_record.png
         */

        private String title;
        private String subtitle;
        private int group;
        private int tag;
        private String logo;
        private String url;
        private int res;
        private int is_overdue;

        public int getIs_overdue() {
            return is_overdue;
        }

        public void setIs_overdue(int is_overdue) {
            this.is_overdue = is_overdue;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public int getGroup() {
            return group;
        }

        public void setGroup(int group) {
            this.group = group;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;


        }

        public int getRes() {
            return res;
        }

        public void setRes(int res) {
            this.res = res;
        }
    }
}
