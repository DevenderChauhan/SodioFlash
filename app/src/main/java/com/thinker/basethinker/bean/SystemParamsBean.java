package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farley on 17/5/23.
 * description:系统配置
 */

public class SystemParamsBean extends BaseBean {
    @SerializedName("appDownloadUrl")
    private String appDownloadUrl = null;
    @SerializedName("appName")
    private String appName = null;
    @SerializedName("communicationIp")
    private String communicationIp = null;
    @SerializedName("communicationPort")
    private Integer communicationPort = null;
    @SerializedName("contactMobile")
    private String contactMobile = null;
    @SerializedName("contactUs")
    private List<String> contactUs = new ArrayList();
    @SerializedName("corporateName")
    private String corporateName = null;
    @SerializedName("defaultLanguage")
    private String defaultLanguage = null;
    @SerializedName("isPayTrip")
    private Boolean isPayTrip = null;
    @SerializedName("isSpotParking")
    private Boolean isSpotParking = null;
    @SerializedName("isUserRegister")
    private Boolean isUserRegister = null;
    @SerializedName("isOpenBalance")
    private Boolean isOpenBalance = null;
    @SerializedName("logoImg")
    private String logoImg = null;
    @SerializedName("payWay")
    private String payWay = null;
    @SerializedName("reserveNum")
    private Integer reserveNum = null;
    @SerializedName("reserveTime")
    private Integer reserveTime = null;
    @SerializedName("isAllowShare")
    private Boolean isAllowShare = null;
    @SerializedName("isOpenAd")
    private Boolean isOpenAd = null;
    @SerializedName("cardDesc")
    private String cardDesc = null;//会员卡描述
    @SerializedName("isOpenMemberCard")
    private Boolean isOpenMemberCard = null;//是否开放会员体系
    @SerializedName("isOpenBattery")
    private Boolean isOpenBattery = null;//是否存在蓄电池项目
    @SerializedName("mapSearchScope")
    private Integer mapSearchScope = null;

    public Integer getMapSearchScope() {
        return mapSearchScope;
    }

    public void setMapSearchScope(Integer mapSearchScope) {
        this.mapSearchScope = mapSearchScope;
    }

    public Boolean getOpenBattery() {
        return isOpenBattery;
    }

    public void setOpenBattery(Boolean openBattery) {
        isOpenBattery = openBattery;
    }
    @SerializedName("isNeedAuthen")
    private Boolean isNeedAuthen = null;//是否需要认证 true 需要，false 不需要
    @SerializedName("authenType")
    private String authenType = null;//认证类型：身份证 id_card，学生证 student_card"

    public Boolean getNeedAuthen() {
        return isNeedAuthen;
    }

    public void setNeedAuthen(Boolean needAuthen) {
        isNeedAuthen = needAuthen;
    }

    public String getAuthenType() {
        return authenType;
    }

    public void setAuthenType(String authenType) {
        this.authenType = authenType;
    }

    public String getAppDownloadUrl() {
        return appDownloadUrl;
    }

    public void setAppDownloadUrl(String appDownloadUrl) {
        this.appDownloadUrl = appDownloadUrl;
    }

    public Boolean getOpenBalance() {
        return isOpenBalance;
    }

    public void setOpenBalance(Boolean openBalance) {
        isOpenBalance = openBalance;
    }

    public String getCardDesc() {
        return cardDesc;
    }

    public void setCardDesc(String cardDesc) {
        this.cardDesc = cardDesc;
    }

    public Boolean getOpenMemberCard() {
        return isOpenMemberCard;
    }

    public void setOpenMemberCard(Boolean openMemberCard) {
        isOpenMemberCard = openMemberCard;
    }

    public Boolean getOpenAd() {
        return isOpenAd;
    }

    public void setOpenAd(Boolean openAd) {
        isOpenAd = openAd;
    }

    public Boolean getPayTrip() {
        return isPayTrip;
    }

    public Boolean getAllowShare() {
        return isAllowShare;
    }

    public void setAllowShare(Boolean allowShare) {
        isAllowShare = allowShare;
    }

    public void setPayTrip(Boolean payTrip) {
        isPayTrip = payTrip;
    }

    public Boolean getSpotParking() {
        return isSpotParking;
    }

    public void setSpotParking(Boolean spotParking) {
        isSpotParking = spotParking;
    }

    public Boolean getUserRegister() {
        return isUserRegister;
    }

    public void setUserRegister(Boolean userRegister) {
        isUserRegister = userRegister;
    }

    public Integer getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(Integer reserveTime) {
        this.reserveTime = reserveTime;
    }

    public Integer getReserveNum() {
        return reserveNum;
    }

    public void setReserveNum(Integer reserveNum) {
        this.reserveNum = reserveNum;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCommunicationIp() {
        return communicationIp;
    }

    public void setCommunicationIp(String communicationIp) {
        this.communicationIp = communicationIp;
    }

    public Integer getCommunicationPort() {
        return communicationPort;
    }

    public void setCommunicationPort(Integer communicationPort) {
        this.communicationPort = communicationPort;
    }


    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public List<String> getContactUs() {
        return contactUs;
    }

    public void setContactUs(List<String> contactUs) {
        this.contactUs = contactUs;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
}
