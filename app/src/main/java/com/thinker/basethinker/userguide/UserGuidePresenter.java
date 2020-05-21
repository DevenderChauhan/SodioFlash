package com.thinker.basethinker.userguide;


import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.api.controller.UserController;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.OnHttpListener;
import com.thinker.basethinker.set.bean.SetBean;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by farley on 17/5/18.
 * description:
 */

public class UserGuidePresenter extends BasePresenter<IUserGuideView> {
    private UserGuideActivity activity;
    private UserController userController = APIControllerFactory.getUserApi();
    public UserGuidePresenter(UserGuideActivity userGuideActivity) {
        super();
        activity = userGuideActivity;
    }
    /**
     * 获取h5
     * @param type
     */
    public void getGuideList(Integer type){
        addSubscription(userController.getGuideList(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SetBean>() {
                    @Override
                    public void call(SetBean setBean) {
                        if (setBean.getError_code() == 0){
                            activity.loadData(setBean);
                        }else{
                            showErrorNone(setBean,activity);
                        }
                    }
                },getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        showErrorNone(bean,activity);
                    }
                })));
    }
}
