package com.thinker.basethinker.rideover;

import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.api.controller.OrderController;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;
import com.thinker.basethinker.myoffer.bean.MyOfferBean;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thinker on 18/1/2.
 */

public class RideOfferPrsenter extends BasePresenter<MvpView> {
    private RideOfferActivity activity;
    private OrderController orderController = APIControllerFactory.getOrderController();
    MemberController memberController = APIControllerFactory.getMemberApi();
    public RideOfferPrsenter(RideOfferActivity myOfferActivity) {
        super();
        activity = myOfferActivity;
    }

    public void findCouponListUsingPOST(){
        activity.showLoading();
        addSubscription(
                orderController.findCouponListUsingPOST()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<MyOfferBean>() {
                            @Override
                            public void call(MyOfferBean orderBean) {
                                activity.hideLoading();
                                if (orderBean.getError_code() == 0){
                                    activity.setData(orderBean);
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
