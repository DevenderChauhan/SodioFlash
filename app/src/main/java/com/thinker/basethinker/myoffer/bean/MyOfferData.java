package com.thinker.basethinker.myoffer.bean;


import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by farley on 17/5/22.
 * description:
 */

public class MyOfferData {
    @SerializedName("source")
    private String offerType;//邀请券类型
    @SerializedName("expireDate")
    private Date effectiveDate;//有效期
    @SerializedName("amount")
    private Double money;
    @SerializedName("cityId")
    private String cityId = null;
    @SerializedName("cityName")
    private String cityName = null;
    @SerializedName("id")
    private Long id = null;
    @SerializedName("currency")
    private String currency = null;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
