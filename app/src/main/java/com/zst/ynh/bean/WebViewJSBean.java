package com.zst.ynh.bean;
/**
 * Created by 34472 on 2016/9/29.
 */
public class WebViewJSBean {

    public String type;
    public String object_id;
    public String url;
    public String is_help;
    public String order_id;
    public String skip_code;
    public Data data;

    public static class Data{

        public String period;
        public String money;
        public String platform_code;
    }
    
}
