package com.thinker.basethinker.bean;


import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

/**
 * Created by farley on 17/5/23.
 * description:充值押金
 */

public class ChergeBean extends BaseBean {
    @SerializedName("currency")
    private String currency = null;//币种
    @SerializedName("deposit")
    private Double deposit = null;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }
}
