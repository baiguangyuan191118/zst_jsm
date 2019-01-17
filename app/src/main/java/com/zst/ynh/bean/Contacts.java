package com.zst.ynh.bean;

public class Contacts {
    private String name;
    private String mobile;
    private int recordId;
    private String uid;
    private int status;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
