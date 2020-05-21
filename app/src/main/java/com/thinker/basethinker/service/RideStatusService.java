package com.thinker.basethinker.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.OrderController;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.OnHttpListener;
import com.thinker.basethinker.utils.LogUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thinker on 17/12/25.
 */

public class RideStatusService extends Service {
    private Integer time = 5000;
    private boolean flag = true;
    private MyThread myThread;
    private MyBinder mBinder = new MyBinder();
    private OrderController orderController = APIControllerFactory.getOrderController();
    private TripChangeListener tripChangeListener;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        public RideStatusService getService() {
            return RideStatusService.this;
        }

        public void startCheckStatus() {
            myThread = new MyThread();
            myThread.start();
            flag = true;
        }

        public void stopCheckStatus() {
            LogUtils.e("服务终止");
            flag = false;
        }
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            while (flag) {
                try {
                    //获取进行中的订单
                    orderController.findDoingInfoUsingGET()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<OrderBean>() {
                                @Override
                                public void call(OrderBean orderBean) {
                                    LogUtils.e("================轮询服务================");
                                    tripChangeListener.onChange(orderBean);
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    if (throwable == null){
                                        return;
                                    }
                                    try {
                                        throwable.printStackTrace();
                                        LogUtils.e("================throwable================" + throwable);
                                    }catch (Exception e){
                                        return;
                                    }
                                }
                            });
                    // 每个2秒向服务器发送一次请求
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void setChangeListener(TripChangeListener tripChangeListener){
        this.tripChangeListener = tripChangeListener;
    }
}
