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
 * Created by thinker on 18/3/6.
 */

public class ModifySyscodePresenter extends BasePresenter<MvpView> {
    private ModifySyscodeActivity activity;
    MemberController memberController = APIControllerFactory.getMemberApi();
    public ModifySyscodePresenter(ModifySyscodeActivity fixUserNameActivity) {
        super();
        activity = fixUserNameActivity;
    }
    /**
     * 修改机器码编号
     * @param syscode
     */
    public void modifyCode(String syscode){
        activity.showLoading();
        addSubscription(memberController.updateBindingSysCodeUsingGET(syscode)
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
