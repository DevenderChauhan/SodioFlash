package com.thinker.basethinker.member.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import java.util.Date;

/**
 * Created by farley on 17/8/18.
 * description:
 */

public class VipPayBean extends BaseBean {
    @SerializedName("amount")
    private Double amount = null;//金额
    @SerializedName("createTime")
    private Date createTime = null;
    @SerializedName("payTime")
    private Date payTime = null;
    @SerializedName("paymentMark")
    private String paymentMark = null;
    @SerializedName("paymentMarkName")
    private String paymentMarkName = null;//支付方式
    @SerializedName("sn")
    private String sn = null;
    @SerializedName("status")
    private Integer status = null;//1:支付中 2:支付成功
    @SerializedName("vipCardName")
    private String vipCardName = null;
    @SerializedName("vipDay")
    private Integer vipDay = null;
    @SerializedName("vipDiscount")
    private Double vipDiscount = null;

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

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPaymentMark() {
        return paymentMark;
    }

    public String getPaymentMarkName() {
        return paymentMarkName;
    }

    public void setPaymentMarkName(String paymentMarkName) {
        this.paymentMarkName = paymentMarkName;
    }

    public void setPaymentMark(String paymentMark) {
        this.paymentMark = paymentMark;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVipCardName() {
        return vipCardName;
    }

    public void setVipCardName(String vipCardName) {
        this.vipCardName = vipCardName;
    }

    public Integer getVipDay() {
        return vipDay;
    }

    public void setVipDay(Integer vipDay) {
        this.vipDay = vipDay;
    }

    public Double getVipDiscount() {
        return vipDiscount;
    }

    public void setVipDiscount(Double vipDiscount) {
        this.vipDiscount = vipDiscount;
    }
}
