package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import java.util.Date;

/**
 * Created by farley on 17/7/10.
 * description:
 */

public class MessageData extends BaseBean {
    @SerializedName("bizId")
    private String bizId = null;
    @SerializedName("bizType")
    private Integer bizType = null;
    @SerializedName("content")
    private String content = null;
    @SerializedName("cover")
    private String cover = null;
    @SerializedName("id")
    private Long id = null;
    @SerializedName("imageTextId")
    private Long imageTextId = null;
    @SerializedName("isImageText")
    private Boolean isImageText = null;
    @SerializedName("remark")
    private String remark = null;
    @SerializedName("sendTime")
    private Date sendTime = null;
    @SerializedName("title")
    private String title = null;
    @SerializedName("toUserType")
    private String toUserType = null;

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImageTextId() {
        return imageTextId;
    }

    public void setImageTextId(Long imageTextId) {
        this.imageTextId = imageTextId;
    }

    public Boolean getImageText() {
        return isImageText;
    }

    public void setImageText(Boolean imageText) {
        isImageText = imageText;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToUserType() {
        return toUserType;
    }

    public void setToUserType(String toUserType) {
        this.toUserType = toUserType;
    }
}
