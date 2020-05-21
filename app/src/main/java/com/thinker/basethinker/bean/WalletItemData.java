package com.thinker.basethinker.bean;


import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by farley on 17/5/18.
 * description:
 */

public class WalletItemData {
    @SerializedName("currency")
    private String currency = null;
    @SerializedName("amount")
    private Double amount = null;
    @SerializedName("createTime")
    private Date createTime = null;
    @SerializedName("type")
    private String type = null;//1充值2退款
    @SerializedName("uid")
    private Long uid = null;
    @SerializedName("updateTime")
    private Date updateTime = null;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
