package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import java.util.Date;

/**
 * Created by thinker on 17/12/25.
 */

public class UserCouponBean extends BaseBean{
    @SerializedName("amount")
    private Double amount = null;
    @SerializedName("cityId")
    private String cityId = null;
    @SerializedName("cityName")
    private String cityName = null;
    @SerializedName("expireDate")
    private Date expireDate = null;
    @SerializedName("id")
    private Long id = null;
    @SerializedName("source")
    private String source = null;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
