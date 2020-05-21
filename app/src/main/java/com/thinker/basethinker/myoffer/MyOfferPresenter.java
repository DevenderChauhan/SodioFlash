package com.thinker.basethinker.myoffer;


import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;
import com.thinker.basethinker.myoffer.bean.MyOfferBean;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by farley on 17/5/22.
 * description:
 */

public class MyOfferPresenter extends BasePresenter<MvpView> {
    private MyOfferActivity activity;
    MemberController memberController = APIControllerFactory.getMemberApi();
    public MyOfferPresenter(MyOfferActivity myOfferActivity) {
        super();
        activity = myOfferActivity;
    }

    /**
     * 获取我的优惠券
     */
    public void getMyOffer(){
        activity.showLoading();
        addSubscription(memberController.getMyOffer()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<MyOfferBean>() {
            @Override
            public void call(MyOfferBean myOfferBean) {
                activity.hideLoading();
                if (myOfferBean.getError_code() == 0){
                    activity.setData(myOfferBean);
                }else{
                    showErrorNone(myOfferBean,activity);
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
