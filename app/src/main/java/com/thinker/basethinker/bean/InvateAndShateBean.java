package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

/**
 * Created by farley on 17/5/31.
 * description:
 */

public class InvateAndShateBean extends BaseBean {
    @SerializedName("inviteContent")
    private String inviteContent = null;
    @SerializedName("inviteTitle")
    private String inviteTitle = null;
    @SerializedName("inviteWay")
    private String inviteWay = null;
    @SerializedName("shareContent")
    private String shareContent = null;
    @SerializedName("shareTitle")
    private String shareTitle = null;
    @SerializedName("shareWay")
    private String shareWay = null;
    @SerializedName("isAllowInvite")
    private Boolean isAllowInvite = null;
    @SerializedName("isAllowShare")
    private Boolean isAllowShare = null;

    public Boolean getAllowInvite() {
        return isAllowInvite;
    }

    public void setAllowInvite(Boolean allowInvite) {
        isAllowInvite = allowInvite;
    }

    public Boolean getAllowShare() {
        return isAllowShare;
    }

    public void setAllowShare(Boolean allowShare) {
        isAllowShare = allowShare;
    }

    public String getInviteContent() {
        return inviteContent;
    }

    public void setInviteContent(String inviteContent) {
        this.inviteContent = inviteContent;
    }

    public String getInviteTitle() {
        return inviteTitle;
    }

    public void setInviteTitle(String inviteTitle) {
        this.inviteTitle = inviteTitle;
    }

    public String getInviteWay() {
        return inviteWay;
    }

    public void setInviteWay(String inviteWay) {
        this.inviteWay = inviteWay;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareWay() {
        return shareWay;
    }

    public void setShareWay(String shareWay) {
        this.shareWay = shareWay;
    }
}
