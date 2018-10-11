package com.zst.jsm.event;

public class StringEvent {
    private String message;
    public  StringEvent(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
