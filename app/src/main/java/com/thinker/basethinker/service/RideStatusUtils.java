package com.thinker.basethinker.service;

import com.thinker.basethinker.bean.OrderBean;

import java.util.Observable;

/**
 * Created by thinker on 17/12/25.
 */

public class RideStatusUtils extends Observable implements TripChangeListener {
    private OrderBean orderBean;
    public RideStatusUtils(RideStatusService rideStatusService) {
        rideStatusService.setChangeListener(this);
    }
    public RideStatusUtils(){

    }
    public void measurementsChanged(){
        setChanged();
        notifyObservers();
    }
    @Override
    public void onChange(OrderBean orderBean) {
        this.orderBean = orderBean;
        measurementsChanged();
    }

    public OrderBean getOrderBean() {
        return orderBean;
    }
}
