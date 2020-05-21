package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import java.util.Date;

/**
 * Created by thinker on 17/12/13.
 */

public class IntegralLogBean extends BaseBean {
    @SerializedName("bizSourceId")
    private Long bizSourceId = null;
    @SerializedName("logInfo")
    private String logInfo = null;//积分来源
    @SerializedName("logIntegral")
    private Long logIntegral = null;//积分数
    @SerializedName("logType")
    private String logType = null;
    @SerializedName("createTime")
    private Date createTime = null;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getBizSourceId() {
        return bizSourceId;
    }

    public void setBizSourceId(Long bizSourceId) {
        this.bizSourceId = bizSourceId;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    public Long getLogIntegral() {
        return logIntegral;
    }

    public void setLogIntegral(Long logIntegral) {
        this.logIntegral = logIntegral;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }
}
