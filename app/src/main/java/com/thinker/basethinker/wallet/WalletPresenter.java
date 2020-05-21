package com.thinker.basethinker.wallet;

import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
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
 * Created by thinker on 17/12/12.
 */

public class WalletPresenter extends BasePresenter<MvpView> {
    private WalletActiity activity;
    MemberController memberController = APIControllerFactory.getMemberApi();
    public WalletPresenter(WalletActiity actiity) {
        this.activity = actiity;
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
                        activity.hideLoading();
                        if (personalBean.getError_code() == 0) {
                            MyUtils.savaPesonData(personalBean);
                            activity.initData(personalBean);
                        } else {
                            showErrorNone(personalBean, activity);
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
