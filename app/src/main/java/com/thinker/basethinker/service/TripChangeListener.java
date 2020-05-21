package com.thinker.basethinker.service;


import com.thinker.basethinker.bean.OrderBean;

/**
 * Created by farley on 17/8/30.
 * description:
 */

public interface TripChangeListener {
    void onChange(OrderBean orderBean);
}
