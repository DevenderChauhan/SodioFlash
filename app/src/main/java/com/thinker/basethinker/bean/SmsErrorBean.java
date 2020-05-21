package com.thinker.basethinker.bean;

import com.google.gson.annotations.Expose;

/**
 * Created by thinker on 18/1/26.
 */

public class SmsErrorBean  {
    @Expose
    private String status;
    @Expose
    private String detail;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
