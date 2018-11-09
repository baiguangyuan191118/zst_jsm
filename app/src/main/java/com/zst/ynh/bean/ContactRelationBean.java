package com.zst.ynh.bean;

import java.util.List;

public class ContactRelationBean {
    public List<RelationBean> lineal_list;//直系亲属关系
    public List<RelationBean> other_list;//其他联系人关系

    public List<EnterTimeAndSalaryBean> live_time_type_all;//居住时间

    public String address;//现居住详细住址
    public String address_distinct;//居住区域
    public int live_period;//居住时长id  1 半年以内  2半年到一年  3一年以上
    public int lineal_relation;//与用户的关系ID :1、父亲 3、母亲  9、儿子  10、女儿  11、兄弟  12、姐妹  2、配偶
    public String lineal_name;// 直系联系人名称
    public String lineal_mobile;// 直系联系人手机号
    public int other_relation;//与用户的关系ID :6、朋友   5、同学  7、同事 8、亲戚  100、其他
    public String other_name;// 其他联系人姓名
    public String other_mobile;// 其他手机号
    public int special;
    public String url;
    
    public static class RelationBean{
        public int type;//类型
        public String name;//名称
    }
    public static class EnterTimeAndSalaryBean{
        public String name;//显示名称
        public int entry_time_type;//入司时长id
        public int salary_type;//月薪id
        public int degrees;//学历id
        public int live_time_type;//居住时长id
        public int marriage;//婚姻状态id
        public int work_type_id;//工作类型id
        public int work_type;//工作类型
    }
}
