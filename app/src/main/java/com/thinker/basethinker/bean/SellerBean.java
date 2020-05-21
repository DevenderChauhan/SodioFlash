package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinker on 17/12/21.
 * 商户的bean
 */

public class SellerBean extends BaseBean {
    @SerializedName("batteryType2Count")
    private Integer batteryType2Count = null;//二合一数量
    @SerializedName("batteryType3Count")
    private Integer batteryType3Count = null;//type-c 数量
    @SerializedName("batteryType4Count")
    private Integer batteryType4Count = null;//三合一数量
    @SerializedName("contactMobile")
    private String contactMobile = null;
    @SerializedName("contactUserName")
    private String contactUserName = null;
    @SerializedName("country")
    private String country = null;
    @SerializedName("distance")
    private Integer distance = null;//距离
    @SerializedName("existCabinetNum")
    private Integer existCabinetNum = null;//充电柜数量
    @SerializedName("idlePositionNum")
    private Integer idlePositionNum = null;//可还的通道数量
    @SerializedName("locationAddress")
    private String locationAddress = null;//地址
    @SerializedName("locationDesc")
    private String locationDesc = null;//详细地址
    @SerializedName("locationLat")
    private Double locationLat = null;//纬度
    @SerializedName("locationLon")
    private Double locationLon = null;//经度
    @SerializedName("sellerCover")
    private List<String> sellerCover = new ArrayList();//封面
    @SerializedName("sellerLogo")
    private String sellerLogo = null;//商户的头像
    @SerializedName("sellerName")
    private String sellerName = null;//服务商名称
    @SerializedName("serviceTime")
    private String serviceTime = null;//服务时间
    @SerializedName("uid")
    private Long uid = null;//服务商Id

    public Integer getBatteryType2Count() {
        return batteryType2Count;
    }

    public void setBatteryType2Count(Integer batteryType2Count) {
        this.batteryType2Count = batteryType2Count;
    }

    public Integer getBatteryType3Count() {
        return batteryType3Count;
    }

    public void setBatteryType3Count(Integer batteryType3Count) {
        this.batteryType3Count = batteryType3Count;
    }

    public Integer getBatteryType4Count() {
        return batteryType4Count;
    }

    public void setBatteryType4Count(Integer batteryType4Count) {
        this.batteryType4Count = batteryType4Count;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactUserName() {
        return contactUserName;
    }

    public void setContactUserName(String contactUserName) {
        this.contactUserName = contactUserName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getExistCabinetNum() {
        return existCabinetNum;
    }

    public void setExistCabinetNum(Integer existCabinetNum) {
        this.existCabinetNum = existCabinetNum;
    }

    public Integer getIdlePositionNum() {
        return idlePositionNum;
    }

    public void setIdlePositionNum(Integer idlePositionNum) {
        this.idlePositionNum = idlePositionNum;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }

    public Double getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(Double locationLon) {
        this.locationLon = locationLon;
    }

    public List<String> getSellerCover() {
        return sellerCover;
    }

    public void setSellerCover(List<String> sellerCover) {
        this.sellerCover = sellerCover;
    }

    public String getSellerLogo() {
        return sellerLogo;
    }

    public void setSellerLogo(String sellerLogo) {
        this.sellerLogo = sellerLogo;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
