package com.zst.ynh.bean;

import java.util.List;

public class WorkInfoBean {

    /**
     * code : 0
     * message : 成功获取工作信息
     * data : {"item":{"company_name":"","company_post":"","company_address":"","company_longitude":"","company_latitude":"","company_address_distinct":"","company_phone":"","company_period":"","company_picture":0,"company_period_list":[{"entry_time_type":1,"name":"一年以内"},{"entry_time_type":2,"name":"一到三年"},{"entry_time_type":3,"name":"三到五年"},{"entry_time_type":4,"name":"五年以上"},{"entry_time_type":5,"name":"未知"}],"company_worktype_id":"1","company_worktype":"1","company_worktype_list":[{"work_type_id":1,"work_type":1,"name":"上班族"},{"work_type_id":2,"work_type":2,"name":"自由职业"}],"company_payday":"","company_payday_list":["1号","2号","3号","4号","5号","6号","7号","8号","9号","10号","11号","12号","13号","14号","15号","16号","17号","18号","19号","20号","21号","22号","23号","24号","25号","26号","27号","28号","29号","30号","31号"]}}
     */

    public DataBean data;


    public static class DataBean {
        /**
         * item : {"company_name":"","company_post":"","company_address":"","company_longitude":"","company_latitude":"","company_address_distinct":"","company_phone":"","company_period":"","company_picture":0,"company_period_list":[{"entry_time_type":1,"name":"一年以内"},{"entry_time_type":2,"name":"一到三年"},{"entry_time_type":3,"name":"三到五年"},{"entry_time_type":4,"name":"五年以上"},{"entry_time_type":5,"name":"未知"}],"company_worktype_id":"1","company_worktype":"1","company_worktype_list":[{"work_type_id":1,"work_type":1,"name":"上班族"},{"work_type_id":2,"work_type":2,"name":"自由职业"}],"company_payday":"","company_payday_list":["1号","2号","3号","4号","5号","6号","7号","8号","9号","10号","11号","12号","13号","14号","15号","16号","17号","18号","19号","20号","21号","22号","23号","24号","25号","26号","27号","28号","29号","30号","31号"]}
         */

        public ItemBean item;


        public static class ItemBean {
            /**
             * company_name :
             * company_post :
             * company_address :
             * company_longitude :
             * company_latitude :
             * company_address_distinct :
             * company_phone :
             * company_period :
             * company_picture : 0
             * company_period_list : [{"entry_time_type":1,"name":"一年以内"},{"entry_time_type":2,"name":"一到三年"},{"entry_time_type":3,"name":"三到五年"},{"entry_time_type":4,"name":"五年以上"},{"entry_time_type":5,"name":"未知"}]
             * company_worktype_id : 1
             * company_worktype : 1
             * company_worktype_list : [{"work_type_id":1,"work_type":1,"name":"上班族"},{"work_type_id":2,"work_type":2,"name":"自由职业"}]
             * company_payday :
             * company_payday_list : ["1号","2号","3号","4号","5号","6号","7号","8号","9号","10号","11号","12号","13号","14号","15号","16号","17号","18号","19号","20号","21号","22号","23号","24号","25号","26号","27号","28号","29号","30号","31号"]
             */

            public String company_name;
            public String company_post;
            public String company_address;
            public String company_longitude;
            public String company_latitude;
            public String company_address_distinct;
            public String company_phone;
            public String company_period;
            public int company_picture;
            public String company_worktype_id;
            public String company_worktype;
            public String company_payday;
            public List<CompanyPeriodListBean> company_period_list;
            public List<CompanyWorktypeListBean> company_worktype_list;
            public List<String> company_payday_list;


            public static class CompanyPeriodListBean {
                /**
                 * entry_time_type : 1
                 * name : 一年以内
                 */

                public int entry_time_type;
                public String name;

            }

            public static class CompanyWorktypeListBean {
                /**
                 * work_type_id : 1
                 * work_type : 1
                 * name : 上班族
                 */

                public int work_type_id;
                public int work_type;
                public String name;

            }
        }
    }
}
