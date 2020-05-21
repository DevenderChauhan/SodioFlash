package com.thinker.basethinker.scan;

import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.CabinetController;
import com.thinker.basethinker.api.controller.OrderController;
import com.thinker.basethinker.bean.CabinetBean;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thinker on 17/12/22.
 */

public class SelectTypePresenter extends BasePresenter<MvpView> {
    private SelectTypeActivity activity;
    private CabinetController cabinetController = APIControllerFactory.getCabinetController();
    private OrderController orderController = APIControllerFactory.getOrderController();
    public SelectTypePresenter(SelectTypeActivity activity) {
        this.activity = activity;
    }
    public void profileUsingGET(String qrCode){
        addSubscription(
                cabinetController.profileUsingGET(qrCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CabinetBean>() {
                    @Override
                    public void call(CabinetBean cabinetBean) {
                        activity.hideLoading();
                        if (cabinetBean.getError_code() == 0){
                            activity.initData(cabinetBean);
                        }else{
                            showErrorNone(cabinetBean,activity);
                        }
                    }
                },getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        activity.hideLoading();
                        showErrorNone(bean,activity);
                    }
                }))
        );
    }
    public void createOrderUsingPOST(String qrCode,String batteryType){
        addSubscription(
                orderController.createOrderUsingPOST(qrCode,batteryType)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<OrderBean>() {
                            @Override
                            public void call(OrderBean orderBean) {
                                activity.hideLoading();
                                if (orderBean.getError_code() == 0){
                                    activity.borrowData(orderBean);
                                }else if (orderBean.getError_code() == 409){
                                    activity.showErrorNone(orderBean);
                                }else{
                                    showErrorNone(orderBean,activity);
                                }
                            }
                        },getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                activity.hideLoading();
                                showErrorNone(bean,activity);
                            }
                        }))
        );
    }
}
