package com.zst.ynh.bean;

public class GestureLockInfo {
    private static final long serialVersionUID = 7247714666080613254L;

    private String month;
    private String day;
    /**
     * 是否为用户生日【0：不是生日；1：是生日】
     */
    private int is_birthday;
    private String msg;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getIs_birthday() {
        return is_birthday;
    }

    public void setIs_birthday(int is_birthday) {
        this.is_birthday = is_birthday;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
