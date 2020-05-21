package com.thinker.basethinker.wallet;

import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.bean.IntegralListBean;
import com.thinker.basethinker.bean.RechartTypeListBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thinker on 17/12/13.
 */

public class ScorePresenter extends BasePresenter<MvpView> {
    private ScoreActivity activity;
    MemberController memberController = APIControllerFactory.getMemberApi();
    public ScorePresenter(ScoreActivity activity) {
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
                            activity.initData(integralListBean,ltTime);
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
}
