package com.thinker.basethinker.eventbus;

import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.jpush.ReceiverBean;

/**
 * Created by thinker on 17/12/7.
 */

public class MessageEvent {
    private int code = -1;
    private String result;
    private OrderBean orderBean;
    private ReceiverBean receiverBean;
    public MessageEvent(int code, String result) {
        this.code = code;
        this.result = result;
    }
    public MessageEvent(int code, OrderBean orderBean) {
        this.code = code;
        this.orderBean = orderBean;
    }

    public MessageEvent(ReceiverBean bean) {
        receiverBean = bean;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public OrderBean getOrderBean() {
        return orderBean;
    }

    public void setOrderBean(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    public ReceiverBean getReceiverBean() {
        return receiverBean;
    }

    public void setReceiverBean(ReceiverBean receiverBean) {
        this.receiverBean = receiverBean;
    }
}
