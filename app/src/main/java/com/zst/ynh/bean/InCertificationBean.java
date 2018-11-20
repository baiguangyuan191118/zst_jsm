package com.zst.ynh.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InCertificationBean {

    /**
     * item : {"list":[{"title":"身份认证","title_mark":"<font color=\"#52628D\" size=\"3\">(必填)<\/font>","subtitle":"请确保您的信息真实有效","tag":1,"operator":"已填写","logo":"http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/id_card_logo2.png","status":1,"type":1,"show_verify_tag":0,"logo_color":"red","verify_tag_content":"","first_url":"http://yy.jsm.51zxdai.com/mobile/web/app-page/bank-card-info","url":"http://yy.jsm.51zxdai.com/mobile/web/app-page/bank-card-info","title ":"手机运营商 ","tag ":5,"url ":"http: //yy.jsm.51zxdai.com/credit/web/credit-web/verification-jxl"},{"title":"联系方式","title_mark":"<font color=\"#52628D\" size=\"3\">(必填)<\/font>","subtitle":"特殊情况可帮助我们联系到您","tag":3,"operator":"<font color=\"#52628D\" size=\"3\">未完善<\/font>","logo":"http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/contact_info_logo.png","status":1,"type":1},{"title":"银行卡","title_mark":"<font color=\"#52628D\" size=\"3\">(必填)<\/font>","subtitle":"您的借款将打到这张卡上","tag":4,"operator":"已完善","logo":"http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/bank_card_info_logo.png","first_url":"http://yy.jsm.51zxdai.com/mobile/web/app-page/bank-card-info","status":1,"type":1,"url":"http://yy.jsm.51zxdai.com/mobile/web/app-page/bank-card-info"},{"title ":"手机运营商 ","tag ":5,"url ":"http: //yy.jsm.51zxdai.com/credit/web/credit-web/verification-jxl","status":1,"type":1,"logo":"http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/mobile_info_logo.png"},{"title":"芝麻授信","title_mark":"<font color=\"#52628D\" size=\"3\">(必填)<\/font>","subtitle":"芝麻授权可以帮助您更快借款","tag":8,"operator":"已填写","logo":"http://yy.jsm.51zxdai.com/credit/web/image/tag/zhimashouxin-1.png","first_url":"http://yy.jsm.51zxdai.com/frontend/web/credit-qsh/redirect-url?id=2&time=1541570143&sign=40b4832788b7b174fa278266172ca62b","url":"http://yy.jsm.51zxdai.com/frontend/web/credit-qsh/redirect-url?id=2&time=1541570143&sign=40b4832788b7b174fa278266172ca62b","status":1,"type":1},{"title":"个人信息","tag":16,"status":1,"logo_color":"","show_verify_tag":0,"verify_tag_content":"","type":3,"logo":"http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/personal_icon_completed.png"},{"title":"工作信息","type":3,"status":0,"tag":2,"logo":"http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/v2-work_info_logo_0.png"},{"title":"更多认证","url":"http://yy.jsm.51zxdai.com/mobile/web/app-page/more-user-info","status":1,"logo":"http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/more_info_logo.png","type":3}],"real_verify_status":1,"list_title":"以下项目为选填信息，完善资料有助于提升额度","agreement":{"title":"《信息授权和使用协议》","link":"http://yy.jsm.51zxdai.com/credit/web/credit-web/safe-login-text"},"header":{"status":3,"title":"超过了75%的用户","data":"¥1500.00","active_url":"","active_title":"更新额度","cur_progress":100},"footer":{"title":"查看借款额度","status":1,"card_type":1},"list_name":{"1":{"title":"基础信息 <font color='#adadad' size='2'>及时更新基础信息 有助于提升额度<\/font>"},"3":{"title":"加分信息 <font color='#adadad' size='2'>完善加分信息 赚取更多额度<\/font>"}},"message_box":"恭喜您，您的额度已提升至1500元，保持良好信用还能继续提额哦！","act_info":{"act_logo":"http://yy.jsm.51zxdai.com/credit/web/image/act_rebate.gif","act_url":"http://yy.jsm.51zxdai.com/credit/web/credit-web/rebate?source_tag=yq9&clientType=wap"},"isShowReadContactsTip":true}
     */

    public ItemBean item;


    public static class ItemBean {
        /**
         * list : [{"title":"身份认证","title_mark":"<font color=\"#52628D\" size=\"3\">(必填)<\/font>","subtitle":"请确保您的信息真实有效","tag":1,"operator":"已填写","logo":"http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/id_card_logo2.png","status":1,"type":1,"show_verify_tag":0,"logo_color":"red","verify_tag_content":""},{"title":"联系方式","title_mark":"<font color=\"#52628D\" size=\"3\">(必填)<\/font>","subtitle":"特殊情况可帮助我们联系到您","tag":3,"operator":"<font color=\"#52628D\" size=\"3\">未完善<\/font>","logo":"http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/contact_info_logo.png","status":1,"type":1},{"title":"银行卡","title_mark":"<font color=\"#52628D\" size=\"3\">(必填)<\/font>","subtitle":"您的借款将打到这张卡上","tag":4,"operator":"已完善","logo":"http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/bank_card_info_logo.png","first_url":"http://yy.jsm.51zxdai.com/mobile/web/app-page/bank-card-info","status":1,"type":1,"url":"http://yy.jsm.51zxdai.com/mobile/web/app-page/bank-card-info"},{"title ":"手机运营商 ","tag ":5,"url ":"http: //yy.jsm.51zxdai.com/credit/web/credit-web/verification-jxl","status":1,"type":1,"logo":"http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/mobile_info_logo.png"},{"title":"芝麻授信","title_mark":"<font color=\"#52628D\" size=\"3\">(必填)<\/font>","subtitle":"芝麻授权可以帮助您更快借款","tag":8,"operator":"已填写","logo":"http://yy.jsm.51zxdai.com/credit/web/image/tag/zhimashouxin-1.png","first_url":"http://yy.jsm.51zxdai.com/frontend/web/credit-qsh/redirect-url?id=2&time=1541570143&sign=40b4832788b7b174fa278266172ca62b","url":"http://yy.jsm.51zxdai.com/frontend/web/credit-qsh/redirect-url?id=2&time=1541570143&sign=40b4832788b7b174fa278266172ca62b","status":1,"type":1},{"title":"个人信息","tag":16,"status":1,"logo_color":"","show_verify_tag":0,"verify_tag_content":"","type":3,"logo":"http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/personal_icon_completed.png"},{"title":"工作信息","type":3,"status":0,"tag":2,"logo":"http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/v2-work_info_logo_0.png"},{"title":"更多认证","url":"http://yy.jsm.51zxdai.com/mobile/web/app-page/more-user-info","status":1,"logo":"http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/more_info_logo.png","type":3}]
         * real_verify_status : 1
         * list_title : 以下项目为选填信息，完善资料有助于提升额度
         * agreement : {"title":"《信息授权和使用协议》","link":"http://yy.jsm.51zxdai.com/credit/web/credit-web/safe-login-text"}
         * header : {"status":3,"title":"超过了75%的用户","data":"¥1500.00","active_url":"","active_title":"更新额度","cur_progress":100}
         * footer : {"title":"查看借款额度","status":1,"card_type":1}
         * list_name : {"1":{"title":"基础信息 <font color='#adadad' size='2'>及时更新基础信息 有助于提升额度<\/font>"},"3":{"title":"加分信息 <font color='#adadad' size='2'>完善加分信息 赚取更多额度<\/font>"}}
         * message_box : 恭喜您，您的额度已提升至1500元，保持良好信用还能继续提额哦！
         * act_info : {"act_logo":"http://yy.jsm.51zxdai.com/credit/web/image/act_rebate.gif","act_url":"http://yy.jsm.51zxdai.com/credit/web/credit-web/rebate?source_tag=yq9&clientType=wap"}
         * isShowReadContactsTip : true
         */

        public int real_verify_status;
        public String list_title;
        public AgreementBean agreement;
        public HeaderBean header;
        public FooterBean footer;
        public ListNameBean list_name;
        public String message_box;
        public ActInfoBean act_info;
        public boolean isShowReadContactsTip;
        public List<ListBean> list;


        public static class AgreementBean {
            /**
             * title : 《信息授权和使用协议》
             * link : http://yy.jsm.51zxdai.com/credit/web/credit-web/safe-login-text
             */

            public String title;
            public String link;

        }

        public static class HeaderBean {
            /**
             * status : 3
             * title : 超过了75%的用户
             * data : ¥1500.00
             * active_url :
             * active_title : 更新额度
             * cur_progress : 100
             */

            public int status;
            public String title;
            public String data;
            public String active_url;
            public String active_title;
            public int cur_progress;

        }

        public static class FooterBean {
            /**
             * title : 查看借款额度
             * status : 1
             * card_type : 1
             */

            public String title;
            public int status;
            public int card_type;

        }

        public static class ListNameBean {
            /**
             * 1 : {"title":"基础信息 <font color='#adadad' size='2'>及时更新基础信息 有助于提升额度<\/font>"}
             * 3 : {"title":"加分信息 <font color='#adadad' size='2'>完善加分信息 赚取更多额度<\/font>"}
             */

            public TitleBean _1;
            public TitleBean _3;


            public static class TitleBean {
                /**
                 * title : 基础信息 <font color='#adadad' size='2'>及时更新基础信息 有助于提升额度</font>
                 */

                public String title;

            }

//            public static class TitleBean {
//                /**
//                 * title : 加分信息 <font color='#adadad' size='2'>完善加分信息 赚取更多额度</font>
//                 */
//
//                public String title;
//
//            }
        }

        public static class ActInfoBean {
            /**
             * act_logo : http://yy.jsm.51zxdai.com/credit/web/image/act_rebate.gif
             * act_url : http://yy.jsm.51zxdai.com/credit/web/credit-web/rebate?source_tag=yq9&clientType=wap
             */

            public String act_logo;
            public String act_url;

        }

        public static class ListBean {
            /**
             * title : 身份认证
             * title_mark : <font color="#52628D" size="3">(必填)</font>
             * subtitle : 请确保您的信息真实有效
             * tag : 1
             * operator : 已填写
             * logo : http://yy.jsm.51zxdai.com/res/RRKJ//images/credit/personal_center/id_card_logo2.png
             * status : 1
             * type : 1
             * show_verify_tag : 0
             * logo_color : red
             * verify_tag_content :
             * first_url : http://yy.jsm.51zxdai.com/mobile/web/app-page/bank-card-info
             * url : http://yy.jsm.51zxdai.com/mobile/web/app-page/bank-card-info
             * title  : 手机运营商
             * tag  : 5
             * url  : http: //yy.jsm.51zxdai.com/credit/web/credit-web/verification-jxl
             */

            public boolean isTitleItem=false;
            public String title;
            public String title_mark;
            public String subtitle;
            public int tag;
            public String operator;
            public String logo;
            public int status;
            public int type;
            public int show_verify_tag;
            public String logo_color;
            public String verify_tag_content;
            public String first_url;
            public String url;
        }
    }
}
