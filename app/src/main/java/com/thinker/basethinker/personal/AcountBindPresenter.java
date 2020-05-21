package com.thinker.basethinker.personal;

import android.widget.Toast;

import com.thinker.basethinker.R;
import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thinker on 17/12/11.
 */

public class AcountBindPresenter extends BasePresenter<MvpView> {
    private AcountBindActivity activity;
    MemberController memberController = APIControllerFactory.getMemberApi();
    public AcountBindPresenter(AcountBindActivity activity) {
        this.activity = activity;
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
                }, getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        showErrorNone(bean,activity);
                        activity.hideLoading();
                    }
                })));
    }
    /**
     * 获取个人资料
     */
    public void boundThirdPartyUsingPOST(String openId, String accessToken, String third_party_type) {
        addSubscription(memberController.boundThirdPartyUsingPOST(openId,accessToken,third_party_type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean bean) {
                        activity.hideLoading();
                        if (bean.getError_code() == 0) {
                            getMyData();
                        } else {
                            showErrorNone(bean, activity);
                        }
                    }
                }, getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        activity.hideLoading();
                        showErrorNone(bean,activity);
                    }
                })));
    }
    /**
     * 解绑
     */
    public void unbundledThirdPartyUsingPOST(String type) {
        addSubscription(memberController.unbundledThirdPartyUsingPOST(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean bean) {
                        activity.hideLoading();
                        if (bean.getError_code() == 0) {
                            LogUtils.e("unbind success");
                            Toast.makeText(activity,activity.getString(R.string.bind_un),Toast.LENGTH_SHORT).show();
                            getMyData();
                        } else {
                            showErrorNone(bean, activity);
                        }
                    }
                },  getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        activity.hideLoading();
                        showErrorNone(bean,activity);
                    }
                })));
    }
}
