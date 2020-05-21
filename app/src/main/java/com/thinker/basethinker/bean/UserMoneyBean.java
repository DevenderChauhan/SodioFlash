package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

/**
 * Created by thinker on 18/1/4.
 */

public class UserMoneyBean extends BaseBean {
    @SerializedName("availableBalance")
    private Double availableBalance = null;
    @SerializedName("currency")
    private String currency = null;

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
