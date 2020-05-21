package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vc.thinker.colours.client.model.APIFeedbackBO;
import vc.thinker.colours.client.model.APIUserCouponBO;

/**
 * Created by thinker on 17/12/22.
 */

public class OrderBean extends BaseBean {
    @SerializedName("chargeRuleDesc")
    private String chargeRuleDesc = null;//支付规则
    @SerializedName("clientSource")
    private String clientSource = null;
    @SerializedName("doingFeedbacks")
    private List<APIFeedbackBO> doingFeedbacks = new ArrayList();
    @SerializedName("beginLocationDetails")
    private String beginLocationDetails = null;//开始的详细地址
    @SerializedName("beginLocationLat")
    private Double beginLocationLat = null;
    @SerializedName("beginLocationLon")
    private Double beginLocationLon = null;
    @SerializedName("beginTime")
    private Date beginTime = null;//开始时间
    @SerializedName("borrowCabinetId")
    private Long borrowCabinetId = null;
    @SerializedName("borrowCabinetStatusCode")
    private String borrowCabinetStatusCode = null;//机柜借的状态码
    @SerializedName("borrowChannel")
    private Integer borrowChannel = null;
    @SerializedName("borrowSellerId")
    private Long borrowSellerId = null;//借的商户
    @SerializedName("borrowSysCode")
    private String borrowSysCode = null;//借的充电柜编码
    @SerializedName("checkDesc")
    private String checkDesc = null;
    @SerializedName("checkName")
    private String checkName = null;
    @SerializedName("checkTime")
    private Date checkTime = null;
    @SerializedName("cityId")
    private String cityId = null;
    @SerializedName("country")
    private String country = null;//国家
    @SerializedName("createTime")
    private Date createTime = null;
    @SerializedName("currency")
    private String currency = null;//币种
    @SerializedName("endLocaitonDetails")
    private String endLocaitonDetails = null;//结束的详细地址
    @SerializedName("endLocationLat")
    private Double endLocationLat = null;
    @SerializedName("endLocationLon")
    private Double endLocationLon = null;
    @SerializedName("finishTime")
    private Date finishTime = null;//结束时间
    @SerializedName("fitCoupon")
    private UserCouponBean fitCoupon = null;//适合该订单的优惠券
    @SerializedName("id")
    private Long id = null;
    @SerializedName("isDeleted")
    private Boolean isDeleted = null;
    @SerializedName("officeId")
    private Long officeId = null;
    @SerializedName("orderCode")
    private String orderCode = null;
    @SerializedName("outOrderId")
    private String outOrderId = null;
    @SerializedName("payOrderCode")
    private String payOrderCode = null;
    @SerializedName("payPrice")
    private Double payPrice = null;//支付金额
    @SerializedName("payTime")
    private Date payTime = null;//支付时间
    @SerializedName("payType")
    private String payType = null;//支付类型：免费：free  现金： cash  会员卡：vip  余额：balance
    @SerializedName("paymentMark")
    private String paymentMark = null;//支付方式
    @SerializedName("pbCode")
    private String pbCode = null;//充电宝 code
    @SerializedName("pbId")
    private Long pbId = null;
    @SerializedName("price")
    private Double price = null;//价格
    @SerializedName("returnCabinetId")
    private Long returnCabinetId = null;
    @SerializedName("returnChannel")
    private Integer returnChannel = null;
    @SerializedName("returnSellerId")
    private Long returnSellerId = null;//还的商户
    @SerializedName("returnSysCode")
    private String returnSysCode = null;//还的机柜sysCode
    @SerializedName("returnType")
    private String returnType = null;
    @SerializedName("rideTime")
    private Integer rideTime = null;//订单时长，分钟"
    @SerializedName("sellerName")
    private String sellerName = null;//服务商的名称
    @SerializedName("status")
    private Integer status = null;//状态  10:创建中 20: 创建失败 30:进行中 40:未支付 50:已支付
    @SerializedName("uid")
    private Long uid = null;
    @SerializedName("userCouponId")
    private Long userCouponId = null;//用户优惠券
    @SerializedName("exchangeRateDesc")
    private String exchangeRateDesc = null;//汇率描述
    @SerializedName("ratePrice")
    private Double ratePrice = null;

    public Double getRatePrice() {
        return ratePrice;
    }

    public void setRatePrice(Double ratePrice) {
        this.ratePrice = ratePrice;
    }

    public String getExchangeRateDesc() {
        return exchangeRateDesc;
    }

    public void setExchangeRateDesc(String exchangeRateDesc) {
        this.exchangeRateDesc = exchangeRateDesc;
    }

    public String getBeginLocationDetails() {
        return beginLocationDetails;
    }

    public void setBeginLocationDetails(String beginLocationDetails) {
        this.beginLocationDetails = beginLocationDetails;
    }

    public Double getBeginLocationLat() {
        return beginLocationLat;
    }

    public void setBeginLocationLat(Double beginLocationLat) {
        this.beginLocationLat = beginLocationLat;
    }

    public Double getBeginLocationLon() {
        return beginLocationLon;
    }

    public void setBeginLocationLon(Double beginLocationLon) {
        this.beginLocationLon = beginLocationLon;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Long getBorrowCabinetId() {
        return borrowCabinetId;
    }

    public void setBorrowCabinetId(Long borrowCabinetId) {
        this.borrowCabinetId = borrowCabinetId;
    }

    public String getBorrowCabinetStatusCode() {
        return borrowCabinetStatusCode;
    }

    public void setBorrowCabinetStatusCode(String borrowCabinetStatusCode) {
        this.borrowCabinetStatusCode = borrowCabinetStatusCode;
    }

    public Integer getBorrowChannel() {
        return borrowChannel;
    }

    public void setBorrowChannel(Integer borrowChannel) {
        this.borrowChannel = borrowChannel;
    }

    public Long getBorrowSellerId() {
        return borrowSellerId;
    }

    public void setBorrowSellerId(Long borrowSellerId) {
        this.borrowSellerId = borrowSellerId;
    }

    public String getBorrowSysCode() {
        return borrowSysCode;
    }

    public void setBorrowSysCode(String borrowSysCode) {
        this.borrowSysCode = borrowSysCode;
    }

    public String getCheckDesc() {
        return checkDesc;
    }

    public void setCheckDesc(String checkDesc) {
        this.checkDesc = checkDesc;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEndLocaitonDetails() {
        return endLocaitonDetails;
    }

    public void setEndLocaitonDetails(String endLocaitonDetails) {
        this.endLocaitonDetails = endLocaitonDetails;
    }

    public Double getEndLocationLat() {
        return endLocationLat;
    }

    public void setEndLocationLat(Double endLocationLat) {
        this.endLocationLat = endLocationLat;
    }

    public Double getEndLocationLon() {
        return endLocationLon;
    }

    public void setEndLocationLon(Double endLocationLon) {
        this.endLocationLon = endLocationLon;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public UserCouponBean getFitCoupon() {
        return fitCoupon;
    }

    public void setFitCoupon(UserCouponBean fitCoupon) {
        this.fitCoupon = fitCoupon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }

    public String getPayOrderCode() {
        return payOrderCode;
    }

    public void setPayOrderCode(String payOrderCode) {
        this.payOrderCode = payOrderCode;
    }

    public Double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPaymentMark() {
        return paymentMark;
    }

    public void setPaymentMark(String paymentMark) {
        this.paymentMark = paymentMark;
    }

    public String getPbCode() {
        return pbCode;
    }

    public void setPbCode(String pbCode) {
        this.pbCode = pbCode;
    }

    public Long getPbId() {
        return pbId;
    }

    public void setPbId(Long pbId) {
        this.pbId = pbId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getReturnCabinetId() {
        return returnCabinetId;
    }

    public void setReturnCabinetId(Long returnCabinetId) {
        this.returnCabinetId = returnCabinetId;
    }

    public Integer getReturnChannel() {
        return returnChannel;
    }

    public void setReturnChannel(Integer returnChannel) {
        this.returnChannel = returnChannel;
    }

    public Long getReturnSellerId() {
        return returnSellerId;
    }

    public void setReturnSellerId(Long returnSellerId) {
        this.returnSellerId = returnSellerId;
    }

    public String getReturnSysCode() {
        return returnSysCode;
    }

    public void setReturnSysCode(String returnSysCode) {
        this.returnSysCode = returnSysCode;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Integer getRideTime() {
        return rideTime;
    }

    public void setRideTime(Integer rideTime) {
        this.rideTime = rideTime;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(Long userCouponId) {
        this.userCouponId = userCouponId;
    }

    public String getChargeRuleDesc() {
        return chargeRuleDesc;
    }

    public void setChargeRuleDesc(String chargeRuleDesc) {
        this.chargeRuleDesc = chargeRuleDesc;
    }

    public String getClientSource() {
        return clientSource;
    }

    public void setClientSource(String clientSource) {
        this.clientSource = clientSource;
    }

    public List<APIFeedbackBO> getDoingFeedbacks() {
        return doingFeedbacks;
    }

    public void setDoingFeedbacks(List<APIFeedbackBO> doingFeedbacks) {
        this.doingFeedbacks = doingFeedbacks;
    }
}
