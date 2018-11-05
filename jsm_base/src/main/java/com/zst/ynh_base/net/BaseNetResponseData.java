package com.zst.ynh_base.net;

import java.io.Serializable;

/**
 * .net网络请求返回实体
 */
public class BaseNetResponseData implements Serializable {

    public String date;
    public String errorcode;
    public String errormsg;
    public String methodname;
    public boolean result;
    public String suberrorcode;
    public String suberrormsg;
    public boolean isinglobalmaintenance;

    public boolean isIsinglobalmaintenance() {
        return isinglobalmaintenance;
    }

    public void setIsinglobalmaintenance(boolean isinglobalmaintenance) {
        this.isinglobalmaintenance = isinglobalmaintenance;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
