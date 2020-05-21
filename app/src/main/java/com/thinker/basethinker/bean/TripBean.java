package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vc.thinker.colours.client.model.APIBatteryBO;
import vc.thinker.colours.client.model.APIElectrombileBO;
import vc.thinker.colours.client.model.APIFeedbackBO;
import vc.thinker.colours.client.model.BicycleBO;
import vc.thinker.colours.client.model.TripCyclingPointBO;
import vc.thinker.colours.client.model.UserCouponBO;

/**
 * Created by thinker on 17/12/21.
 */

public class TripBean extends BaseBean {
    @SerializedName("battery")
    private APIBatteryBO battery = null;
    @SerializedName("beginLcationLat")
    private Double beginLcationLat = null;
    @SerializedName("beginLcationLon")
    private Double beginLcationLon = null;
    @SerializedName("beginLocationDetails")
    private String beginLocationDetails = null;
    @SerializedName("beginTime")
    private Date beginTime = null;
    @SerializedName("bicycle")
    private BicycleBO bicycle = null;
    @SerializedName("bicycleId")
    private Long bicycleId = null;
    @SerializedName("calorie")
    private Integer calorie = null;
    @SerializedName("carbon")
    private Integer carbon = null;
    @SerializedName("createTime")
    private Date createTime = null;
    @SerializedName("cyclingPoints")
    private List<TripCyclingPointBO> cyclingPoints = new ArrayList();
    @SerializedName("distance")
    private Integer distance = null;
    @SerializedName("doingFeedbacks")
    private List<APIFeedbackBO> doingFeedbacks = new ArrayList();
    @SerializedName("electrombile")
    private APIElectrombileBO electrombile = null;
    @SerializedName("endLcationLat")
    private Double endLcationLat = null;
    @SerializedName("endLcationLon")
    private Double endLcationLon = null;
    @SerializedName("endLocationDetails")
    private String endLocationDetails = null;
    @SerializedName("finishTime")
    private Date finishTime = null;
    @SerializedName("fitCoupon")
    private UserCouponBO fitCoupon = null;
    @SerializedName("id")
    private Long id = null;
    @SerializedName("inTheParkingLot")
    private Boolean inTheParkingLot = null;
    @SerializedName("isAutoEnd")
    private Boolean isAutoEnd = null;
    @SerializedName("isDeleted")
    private Boolean isDeleted = null;
    @SerializedName("kmMark")
    private String kmMark = null;
    @SerializedName("lastLockLocationTime")
    private Date lastLockLocationTime = null;
    @SerializedName("lastLockTime")
    private Date lastLockTime = null;
    @SerializedName("lastUnlockTime")
    private Date lastUnlockTime = null;
    @SerializedName("lockLocationAutoEndEffectiveTime")
    private Integer lockLocationAutoEndEffectiveTime = null;
    @SerializedName("lockOnoff")
    private Boolean lockOnoff = null;
    @SerializedName("payTime")
    private Date payTime = null;
    @SerializedName("payType")
    private String payType = null;
    @SerializedName("paymentMark")
    private String paymentMark = null;
    @SerializedName("price")
    private Double price = null;
    @SerializedName("rideTime")
    private Integer rideTime = null;
    @SerializedName("status")
    private Integer status = null;
    @SerializedName("stopType")
    private String stopType = null;
    @SerializedName("sysCode")
    private String sysCode = null;
    @SerializedName("tripType")
    private String tripType = null;

}
