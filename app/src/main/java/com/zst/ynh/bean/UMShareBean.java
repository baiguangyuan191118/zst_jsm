package com.zst.ynh.bean;

/*
* 友盟分享bean
* */
public class UMShareBean {
    private int type;
    private String platform;
    private String extend;
    private String callback;
    private String share_title;
    private String share_body;
    private String share_url;
    private String share_logo;
    private String share_image;
    private int share_data_type;//默认是0 ,图片是1

    public int getShare_data_type() {
        return share_data_type;
    }

    public void setShare_data_type(int share_data_type) {
        this.share_data_type = share_data_type;
    }

    public String getShare_image() {
        return share_image;
    }

    public void setShare_image(String share_image) {
        this.share_image = share_image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getShare_logo() {
        return share_logo;
    }

    public void setShare_logo(String share_logo) {
        this.share_logo = share_logo;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public String getExtend()
    {
        return extend;
    }

    public void setExtend(String extend)
    {
        this.extend = extend;
    }

    public String getCallback()
    {
        return callback;
    }

    public void setCallback(String callback)
    {
        this.callback = callback;
    }
}
