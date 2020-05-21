package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import java.util.Date;

/**
 * Created by farley on 17/5/23.
 * description:个人信息
 */

public class PersonalBean extends BaseBean {
    @SerializedName("authApplyRemark")
    private String authApplyRemark = null;//审批备注
    @SerializedName("authApplyStatus")
    private Integer authApplyStatus = null;//认证审批状态 1 审批中 2审批成功 3审批失败
    @SerializedName("authStatus")
    private String authStatus = null;//认证状态：0未认证，1认证中，2认证成功，3认证失败
    @SerializedName("authStep")
    private Integer authStep = null;//认证步骤：1.手机认证,2. 押金充值 3.实名认证 4.认证完成
    @SerializedName("birthdate")
    private Date birthdate = null;
    @SerializedName("deposit")
    private Double deposit = null;
    @SerializedName("cabinetAlias")
    private String cabinetAlias = null;
    @SerializedName("email")
    private String email = null;
    @SerializedName("headImgPath")
    private String headImgPath = null;
    @SerializedName("balance")
    private Double balance = null;
    @SerializedName("height")
    private Integer height = null;
    @SerializedName("idCard")
    private String idCard = null;
    @SerializedName("inviteCode")
    private String inviteCode = null;
    @SerializedName("isBindFasebook")
    private Boolean isBindFasebook = null;
    @SerializedName("isBindQQ")
    private Boolean isBindQQ = null;
    @SerializedName("isBindWeixin")
    private Boolean isBindWeixin = null;
    @SerializedName("isBindMobile")
    private Boolean isBindMobile = null;
    @SerializedName("mobile")
    private String mobile = null;
    @SerializedName("name")
    private String name = null;
    @SerializedName("nickname")
    private String nickname = null;
    @SerializedName("remark")
    private String remark = null;
    @SerializedName("rideDistance")
    private Double rideDistance = null;//骑行m
    @SerializedName("rideTime")
    private Long rideTime = null;//时长min
    @SerializedName("sex")
    private Integer sex = null;
    @SerializedName("status")
    private String status = null;
    @SerializedName("weight")
    private Integer weight = null;
    @SerializedName("calorie")
    private Double calorie = null;
    @SerializedName("jobNumber")
    private String jobNumber = null;
    @SerializedName("vipExpiresIn")
    private Date vipExpiresIn = null;
    @SerializedName("isVIP")
    private Boolean isVIP = null;
    @SerializedName("motorPower")
    private Integer motorPower = null;//电机功率
    @SerializedName("vipDxpireDateDesc")
    private String vipDxpireDateDesc = null;//会员到期时间描述
    @SerializedName("studentId")
    private String studentId = null;
    @SerializedName("schoolName")
    private String schoolName = null;
    @SerializedName("integral")
    private Long integral = null;
    @SerializedName("rewardAmount")
    private Long rewardAmount = null;
    @SerializedName("currency")
    private String currency = null;
    @SerializedName("sysCode")
    private String sysCode = null;
    @SerializedName("isPayBasicCost")
    private Boolean isPayBasicCost = null;

    public Boolean getBindMobile() {
        return isBindMobile;
    }

    public void setBindMobile(Boolean bindMobile) {
        isBindMobile = bindMobile;
    }

    public String getCabinetAlias() {
        return cabinetAlias;
    }

    public void setCabinetAlias(String cabinetAlias) {
        this.cabinetAlias = cabinetAlias;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public Boolean getPayBasicCost() {
        return isPayBasicCost;
    }

    public void setPayBasicCost(Boolean payBasicCost) {
        isPayBasicCost = payBasicCost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Long rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getAuthApplyStatus() {
        return authApplyStatus;
    }

    public String getAuthApplyRemark() {
        return authApplyRemark;
    }

    public void setAuthApplyRemark(String authApplyRemark) {
        this.authApplyRemark = authApplyRemark;
    }

    public void setAuthApplyStatus(Integer authApplyStatus) {
        this.authApplyStatus = authApplyStatus;
    }



    public Integer getMotorPower() {
        return motorPower;
    }

    public void setMotorPower(Integer motorPower) {
        this.motorPower = motorPower;
    }

    public String getVipDxpireDateDesc() {
        return vipDxpireDateDesc;
    }

    public void setVipDxpireDateDesc(String vipDxpireDateDesc) {
        this.vipDxpireDateDesc = vipDxpireDateDesc;
    }

    public Boolean getVIP() {
        return isVIP;
    }

    public void setVIP(Boolean VIP) {
        isVIP = VIP;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


    public Date getVipExpiresIn() {
        return vipExpiresIn;
    }

    public void setVipExpiresIn(Date vipExpiresIn) {
        this.vipExpiresIn = vipExpiresIn;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public PersonalBean() {
    }

    public Double getCalorie() {
        return calorie;
    }

    public void setCalorie(Double calorie) {
        this.calorie = calorie;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public Integer getAuthStep() {
        return authStep;
    }

    public void setAuthStep(Integer authStep) {
        this.authStep = authStep;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadImgPath() {
        return headImgPath;
    }

    public void setHeadImgPath(String headImgPath) {
        this.headImgPath = headImgPath;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getRideDistance() {
        return rideDistance;
    }

    public void setRideDistance(Double rideDistance) {
        this.rideDistance = rideDistance;
    }

    public Long getRideTime() {
        return rideTime;
    }

    public void setRideTime(Long rideTime) {
        this.rideTime = rideTime;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getBindFasebook() {
        return isBindFasebook;
    }

    public void setBindFasebook(Boolean bindFasebook) {
        isBindFasebook = bindFasebook;
    }

    public Boolean getBindQQ() {
        return isBindQQ;
    }

    public void setBindQQ(Boolean bindQQ) {
        isBindQQ = bindQQ;
    }

    public Boolean getBindWeixin() {
        return isBindWeixin;
    }

    public void setBindWeixin(Boolean bindWeixin) {
        isBindWeixin = bindWeixin;
    }
}
