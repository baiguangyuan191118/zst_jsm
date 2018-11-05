package com.zst.ynh.event;

public class StringEvent {
    private String message;
    private int value;

    public  StringEvent(String message , int value){
        this.message=message;
        this.value=value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
