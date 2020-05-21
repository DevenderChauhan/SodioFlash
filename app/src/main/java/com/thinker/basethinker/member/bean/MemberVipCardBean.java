package com.thinker.basethinker.member.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

/**
 * Created by farley on 17/8/17.
 * description:
 */

public class MemberVipCardBean extends BaseBean {
    @SerializedName("cardAmount")
    private Double cardAmount = null;//"购买金额单位元"
    @SerializedName("cardDesc")
    private String cardDesc = null;//会员卡说明
    @SerializedName("cardEffectiveTime")
    private Integer cardEffectiveTime = null;//会员卡有的期限（单位天")
    @SerializedName("cardName")
    private String cardName = null;
    @SerializedName("id")
    private Long id = null;

    public Double getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(Double cardAmount) {
        this.cardAmount = cardAmount;
    }

    public String getCardDesc() {
        return cardDesc;
    }

    public void setCardDesc(String cardDesc) {
        this.cardDesc = cardDesc;
    }

    public Integer getCardEffectiveTime() {
        return cardEffectiveTime;
    }

    public void setCardEffectiveTime(Integer cardEffectiveTime) {
        this.cardEffectiveTime = cardEffectiveTime;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
