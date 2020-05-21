package com.thinker.basethinker.eventbus;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by thinker on 17/12/19.
 */

public class MainMsgEvent {
    /* 0 ：地图的中间位置
    *  1 : 用户选择商户后
    *  2 ：最近一次定位后 获取周边的所有商户
    *  3 ：地图点击事件*/

    private int code = -1;
    private Marker mMarker;
    private int position = 0;
    private LatLng latLng;

    public int getPosition() {
        return position;
    }

    public MainMsgEvent(int code, Marker mMarker, int position) {
        this.code = code;
        this.mMarker = mMarker;
        this.position = position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public MainMsgEvent(int i, LatLng latLng) {
        this.code = i;
        this.latLng = latLng;
    }
    public MainMsgEvent(int i, Marker mMarker) {
        this.code = i;
        this.mMarker = mMarker;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Marker getmMarker() {
        return mMarker;
    }

    public void setmMarker(Marker mMarker) {
        this.mMarker = mMarker;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
