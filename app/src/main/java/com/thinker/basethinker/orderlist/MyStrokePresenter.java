package com.thinker.basethinker.orderlist;

import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.OrderController;
import com.thinker.basethinker.bean.OrderListBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by farley on 17/5/17.
 * description:
 */

public class MyStrokePresenter extends BasePresenter<MvpView> {
    private MyStrokeActivity activity;
    OrderController orderController = APIControllerFactory.getOrderController();
    public MyStrokePresenter(MyStrokeActivity myStrokeActivity) {
        super();
        activity = myStrokeActivity;
    }
    //获取我所有的行程
    public void getMyAllStroke(Long time){
        activity.showLoading();
        addSubscription(orderController.findOrderListUsingPOST(time)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<OrderListBean>() {
            @Override
            public void call(OrderListBean orderListBean) {
                activity.hideLoading();
                if (orderListBean.getError_code() == 0){
                    activity.loadMore(orderListBean);
                }else{
                    showErrorLogin(orderListBean,activity);
                }
            }
        },getErrorAction(new OnHttpListener() {
            @Override
            public void onResult(BaseBean bean) {
                activity.hideLoading();
                showErrorNone(bean,activity);
            }
        })));
    }
}
