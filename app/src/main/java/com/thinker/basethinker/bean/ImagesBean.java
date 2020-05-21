package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import java.util.Date;

/**
 * Created by farley on 17/8/15.
 * description:
 */

public class ImagesBean extends BaseBean {
    @SerializedName("createTime")
    private Date createTime = null;
    @SerializedName("id")
    private Long id = null;
    @SerializedName("initImg")
    private String initImg = null;
    @SerializedName("isDelete")
    private Boolean isDelete = null;
    @SerializedName("linkUrl")
    private String linkUrl = null;
    @SerializedName("name")
    private String name = null;
    @SerializedName("updateTime")
    private Date updateTime = null;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInitImg() {
        return initImg;
    }

    public void setInitImg(String initImg) {
        this.initImg = initImg;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
