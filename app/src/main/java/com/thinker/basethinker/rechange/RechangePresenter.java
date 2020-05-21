package com.thinker.basethinker.rechange;

import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.bean.IntegralListBean;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;
import com.thinker.basethinker.utils.MyUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thinker on 17/12/26.
 */

public class RechangePresenter extends BasePresenter<MvpView> {
    private RechangeActivity activity;
    MemberController memberController = APIControllerFactory.getMemberApi();
    public RechangePresenter(RechangeActivity activity) {
        this.activity = activity;
    }
    public void getScoreList(final Long ltTime ,String type){
        addSubscription(
                memberController.findIntegralListUsingGET(ltTime,type)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<IntegralListBean>() {
                            @Override
                            public void call(IntegralListBean integralListBean) {
                                if (integralListBean.getError_code() == 0){
                                    activity.getScareData(integralListBean,ltTime);
                                }else{
                                    showErrorNone(integralListBean,activity);
                                }
                            }
                        },getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                showErrorNone(bean,activity);
                            }
                        }))
        );
    }
    public void exchangeCodeUsingPOST(String code){
        addSubscription(
                memberController.exchangeCodeUsingPOST(code)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<BaseBean>() {
                            @Override
                            public void call(BaseBean bean) {
                                if (bean.getError_code() == 0){
                                    activity.exchange(bean);
                                }else{
                                    showErrorNone(bean,activity);
                                }
                            }
                        },getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                showErrorNone(bean,activity);
                            }
                        }))
        );
    }
    /**
     * 获取个人资料
     */
    public void getMyData() {
        addSubscription(memberController.getPersonalData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PersonalBean>() {
                    @Override
                    public void call(PersonalBean personalBean) {
                        if (personalBean.getError_code() == 0) {
                            MyUtils.savaPesonData(personalBean);
                            activity.setPersonData(personalBean);
                        } else {
                            showErrorNone(personalBean, activity);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        if (throwable != null) {
                        }
                    }
                }));
    }
}
