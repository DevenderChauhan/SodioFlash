package com.thinker.basethinker.personal;


import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by farley on 17/6/2.
 * description:
 */

public class FixUserNamePresenter extends BasePresenter<MvpView> {
    private FixUserNameActivity activity;
    MemberController memberController = APIControllerFactory.getMemberApi();
    public FixUserNamePresenter(FixUserNameActivity fixUserNameActivity) {
        super();
        activity = fixUserNameActivity;
    }
    /**
     * 上传昵称
     * @param name
     */
    public void upNicName(String name){
        activity.showLoading();
        addSubscription(memberController.saveUserNickname(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean bean) {
                        activity.hideLoading();
                        if (bean.getError_code() == 0){
                            activity.setSuccessDo();
                        }else{
                            showErrorNone(bean,activity);
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
