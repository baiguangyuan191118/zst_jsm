package com.zst.ynh.event;

public class CertificationEvent {
    private int type;
    private String data;

    public CertificationEvent(int type,String data){
        this.type=type;
        this.data=data;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
