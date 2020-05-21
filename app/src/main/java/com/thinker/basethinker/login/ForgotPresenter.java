package com.thinker.basethinker.login;

import android.widget.Toast;

import com.thinker.basethinker.R;
import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.PreferencesUtils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thinker on 17/12/8.
 */

public class ForgotPresenter extends BasePresenter<MvpView> {
    private ForgotActivity activity;

    public ForgotPresenter(ForgotActivity activity) {
        this.activity = activity;
    }
    private MemberController memberController = APIControllerFactory.getMemberApiNoToken();
    public void getAuth(String phoneNum){
        addSubscription(memberController.getAuth(phoneNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        if (baseBean.getError_code() == 0){
                            activity.getAuthSuccess();
                            Toast.makeText(activity,activity.getString(R.string.getauthsuccess),Toast.LENGTH_SHORT).show();
                        }else{
                            showErrorNone(baseBean,activity);
                        }
                    }
                },getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        showErrorNone(bean,activity);
                    }
                })));
    }
    public void updatePasswordByMobileUsingPOST(String phone,String countryCode,String verifycode,String password){
        addSubscription(memberController.updatePasswordByMobileUsingPOST(countryCode,phone,verifycode,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        if (baseBean.getError_code() == 0){
                            activity.modifySuccess();

                            Toast.makeText(activity,activity.getString(R.string.modifysuccess),Toast.LENGTH_SHORT).show();
                        }else{
                            showErrorNone(baseBean,activity);
                        }
                        activity.setComplete();
                    }
                },getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        showErrorNone(bean,activity);
                        activity.setComplete();
                    }
                })));
    }
    /**
     * 登录并保存token
     * @param phone
     * @param code
     */
    public void loginService(String phone,String code){
        addSubscription(memberController.doLogin(phone, code).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String token) {
                        JPushInterface.setAlias(activity, token.replace("-",""), new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                LogUtils.e("jpush======"+s);
                            }
                        });
                        MyApplication.setIsLoginStatus(true);
                        PreferencesUtils.putString(activity,"RADISHSAAS_IS_BIND",token);
                        activity.turnToMain();
                    }
                }, getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        showErrorNone(bean,activity);
                    }
                })));
    }
}
