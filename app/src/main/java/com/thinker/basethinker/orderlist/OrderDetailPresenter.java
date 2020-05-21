package com.thinker.basethinker.orderlist;

import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.OrderController;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thinker on 17/12/26.
 */

public class OrderDetailPresenter extends BasePresenter<MvpView> {
    private OrderDetailActivity activity;
    private OrderController orderController = APIControllerFactory.getOrderController();
    public OrderDetailPresenter(OrderDetailActivity activity) {
        this.activity = activity;
    }
    //获取订单详情
    public void profileUsingGET(String ordercode){
        activity.showLoading();
        addSubscription(
                orderController.profileUsingGET(ordercode)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<OrderBean>() {
                            @Override
                            public void call(OrderBean orderBean) {
                                activity.hideLoading();
                                if (orderBean.getError_code() == 0){
                                    activity.initData(orderBean);
                                }else{
                                    showErrorNone(orderBean,activity);
                                }
                            }
                        },getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                activity.hideLoading();
                                showErrorNone(bean, activity);
                            }
                        }))
        );
    }
}
