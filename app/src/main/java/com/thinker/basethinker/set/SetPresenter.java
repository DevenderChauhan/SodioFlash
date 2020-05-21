package com.thinker.basethinker.set;


import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.api.controller.UserController;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.OnHttpListener;
import com.thinker.basethinker.personal.ModifyPawActivity;
import com.thinker.basethinker.set.bean.SetBean;
import com.thinker.basethinker.utils.PreferencesUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by farley on 17/5/24.
 * description:
 */

public class SetPresenter extends BasePresenter<ISetView> {
    private SetActivity activity;
    private MemberController memberController = APIControllerFactory.getMemberApi();
    private UserController userController = APIControllerFactory.getUserApi();
    public SetPresenter(SetActivity setActivity) {
        super();
        activity = setActivity;
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
                    activity.setData(setBean);
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

    /**
     * 退出登录
     */
    public void logout(){
        addSubscription(userController.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean bean) {
                        if (bean.getError_code() == 0){
                            PreferencesUtils.putString(activity,"RADISHSAAS_IS_BIND_LOGOUTTYPE","logout");
                            PreferencesUtils.putString(activity,"CURRENTORDERCODE","");
                            activity.logoutSuccess();
                        }else{
                            showErrorNone(bean,activity);
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
