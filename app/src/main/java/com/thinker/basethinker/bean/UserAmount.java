package com.thinker.basethinker.bean;


import com.thinker.basethinker.mvp.BaseBean;

/**
 * Created by farley on 17/8/22.
 * description:
 */

public class UserAmount extends BaseBean {
    private Double amount;

    public UserAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
