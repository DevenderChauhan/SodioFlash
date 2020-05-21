package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import vc.thinker.colours.client.model.APISellerBO;

/**
 * Created by thinker on 17/12/22.
 */

public class CabinetBean extends BaseBean {
    @SerializedName("batteryType2Count")
    private Integer batteryType2Count = null;//二合一数量
    @SerializedName("batteryType3Count")
    private Integer batteryType3Count = null;//type-c 数量
    @SerializedName("batteryType4Count")
    private Integer batteryType4Count = null;//三合一数量
    @SerializedName("cabinetCode")
    private String cabinetCode = null;//充电柜编号
    @SerializedName("chargeRuleDesc")
    private String chargeRuleDesc = null;//收费规则描述
    @SerializedName("existPositionNum")
    private Integer existPositionNum = null;
    @SerializedName("idlePositionNum")
    private Integer idlePositionNum = null;
    @SerializedName("positionTotal")
    private Integer positionTotal = null;
    @SerializedName("seller")
    private SellerBean seller = null;//商户信息
    @SerializedName("sysCode")
    private String sysCode = null;//系统编号
    @SerializedName("typeId")
    private Long typeId = null;

    public Integer getBatteryType2Count() {
        return batteryType2Count;
    }

    public void setBatteryType2Count(Integer batteryType2Count) {
        this.batteryType2Count = batteryType2Count;
    }

    public Integer getBatteryType3Count() {
        return batteryType3Count;
    }

    public void setBatteryType3Count(Integer batteryType3Count) {
        this.batteryType3Count = batteryType3Count;
    }

    public Integer getBatteryType4Count() {
        return batteryType4Count;
    }

    public void setBatteryType4Count(Integer batteryType4Count) {
        this.batteryType4Count = batteryType4Count;
    }

    public String getCabinetCode() {
        return cabinetCode;
    }

    public void setCabinetCode(String cabinetCode) {
        this.cabinetCode = cabinetCode;
    }

    public String getChargeRuleDesc() {
        return chargeRuleDesc;
    }

    public void setChargeRuleDesc(String chargeRuleDesc) {
        this.chargeRuleDesc = chargeRuleDesc;
    }

    public Integer getExistPositionNum() {
        return existPositionNum;
    }

    public void setExistPositionNum(Integer existPositionNum) {
        this.existPositionNum = existPositionNum;
    }

    public Integer getIdlePositionNum() {
        return idlePositionNum;
    }

    public void setIdlePositionNum(Integer idlePositionNum) {
        this.idlePositionNum = idlePositionNum;
    }

    public Integer getPositionTotal() {
        return positionTotal;
    }

    public void setPositionTotal(Integer positionTotal) {
        this.positionTotal = positionTotal;
    }

    public SellerBean getSeller() {
        return seller;
    }

    public void setSeller(SellerBean seller) {
        this.seller = seller;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
