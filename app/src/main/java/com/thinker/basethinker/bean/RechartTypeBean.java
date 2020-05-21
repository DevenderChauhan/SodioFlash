package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

/**
 * Created by farley on 17/8/22.
 * description:
 */

public class RechartTypeBean extends BaseBean {
    @SerializedName("id")
    private Long id = null;
    @SerializedName("payAmount")
    private Double payAmount = null;//用户实际支付金额（单位元）
    @SerializedName("remark")
    private String remark = null;//优惠说明
    @SerializedName("sendAmount")
    private Double sendAmount = null;//赠送金额（单位元
    @SerializedName("currency")
    private String currency = null;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getSendAmount() {
        return sendAmount;
    }

    public void setSendAmount(Double sendAmount) {
        this.sendAmount = sendAmount;
    }
}
