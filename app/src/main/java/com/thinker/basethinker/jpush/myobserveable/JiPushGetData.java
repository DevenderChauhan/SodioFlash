package com.thinker.basethinker.jpush.myobserveable;


import java.util.Observable;

import com.thinker.basethinker.jpush.ReceiverBean;

/**
 * Created by farley on 17/8/20.
 * description:关于极光会发送三种消息 ， 一种是自定义消息 一种是通知  一种是点击感喟 在这里只封装自定义消息的接收
 */

public class JiPushGetData extends Observable {
    private ReceiverBean receiverBean;//消息

    public void dataHaveChanged(){
        setChanged();
        notifyObservers();
    }
    public void setDataChange(ReceiverBean receiverBean){
        this.receiverBean = receiverBean;
        dataHaveChanged();
    }

    public ReceiverBean getReceiverBean() {
        return receiverBean;
    }

}
