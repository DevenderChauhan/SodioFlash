package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

/**
 * Created by thinker on 18/3/6.
 */

public class BasicCostBean extends BaseBean {
    @SerializedName("cost")
    private Double cost = null;
    @SerializedName("currency")
    private String currency = null;

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
