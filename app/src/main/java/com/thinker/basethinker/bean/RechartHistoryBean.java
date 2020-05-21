package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import java.util.Date;

/**
 * Created by farley on 17/8/22.
 * description:
 */

public class RechartHistoryBean extends BaseBean {
    @SerializedName("createTime")
    private Date createTime = null;
    @SerializedName("isDeleted")
    private Boolean isDeleted = null;
    @SerializedName("logAmount")
    private Double logAmount = null;//日志操作金额
    @SerializedName("logInfo")
    private String logInfo = null;//日志信息
    @SerializedName("logSourceId")
    private Long logSourceId = null;
    @SerializedName("logType")
    private String logType = null;//操作类型，分为充值、提现、消费、兑换金币、人工操作
    @SerializedName("logUserId")
    private Long logUserId = null;
    @SerializedName("outOrderId")
    private String outOrderId = null;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Double getLogAmount() {
        return logAmount;
    }

    public void setLogAmount(Double logAmount) {
        this.logAmount = logAmount;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    public Long getLogSourceId() {
        return logSourceId;
    }

    public void setLogSourceId(Long logSourceId) {
        this.logSourceId = logSourceId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public Long getLogUserId() {
        return logUserId;
    }

    public void setLogUserId(Long logUserId) {
        this.logUserId = logUserId;
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }
}
