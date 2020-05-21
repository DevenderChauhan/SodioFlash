package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import vc.thinker.colours.client.model.WeiXinPaymetBO;

/**
 * Created by farley on 17/8/21.
 * description:
 */

public class PayDetailBean extends BaseBean {
    @SerializedName("alipaPpaySignature")
    private String alipaPpaySignature = null;
    @SerializedName("sn")
    private String sn = null;
    @SerializedName("weiXinPaymet")
    private WeiXinPaymetBO weiXinPaymet = null;


    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public WeiXinPaymetBO getWeiXinPaymet() {
        return weiXinPaymet;
    }

    public void setWeiXinPaymet(WeiXinPaymetBO weiXinPaymet) {
        this.weiXinPaymet = weiXinPaymet;
    }

    public String getAlipaPpaySignature() {
        return alipaPpaySignature;
    }

    public void setAlipaPpaySignature(String alipaPpaySignature) {
        this.alipaPpaySignature = alipaPpaySignature;
    }
}
