package com.thinker.basethinker.login;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.PreferencesUtils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thinker on 17/12/7.
 */

public class LoginPresenter extends BasePresenter<MvpView> {
    private LoginActivity activity;
    private MemberController memberController  = APIControllerFactory.getMemberApiNoToken();
    private MemberController memberControllerVor  ;
    public LoginPresenter(LoginActivity activity) {
        this.activity = activity;
    }
    /**
     * 获取验证码
     * @param phoneNum
     */
    public void getAuth(String phoneNum){
        addSubscription(memberController.getAuth(phoneNum).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean bean) {
                        if (bean.getError_code() != 0){
                            showErrorNone(bean,activity);
                        }else{
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
     * 登录并保存token
     * @param phone
     * @param code
     */
    public void loginService(String phone,String code){
        LogUtils.e("去登录");
        addSubscription(memberController.doLogin(phone, code).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String token) {
                        LogUtils.e("登录成功token="+token);
                        MyApplication.setIsLoginStatus(true);
                        PreferencesUtils.putString(activity,"RADISHSAAS_IS_BIND",token);
                        activity.loginSuccess();
                        JPushInterface.setAlias(activity, token.replace("-",""), new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                LogUtils.e("jpush======"+s);
                            }
                        });
                        getMyData();
                    }
                }, getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        showErrorNone(bean,activity);
                        activity.loginFailed();
                    }
                })));
    }
    public void thirdPartyLoginUsingPOST(final String openId, String accessToken, String queryType){
        addSubscription(memberController.thirdPartyLoginUsingPOST(openId, accessToken,queryType).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean bean) {
                        LogUtils.e("第三方登录成功");
                        if (bean.getError_code() != 0){
                            showErrorNone(bean,activity);
                        }else{
                            loginService(openId,bean.getResult());
                        }
                        activity.hideLoading();
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
     * 获取个人资料
     */
    public void getMyData() {
        LogUtils.e("获取个人");
        memberControllerVor  = APIControllerFactory.getMemberApi();
        addSubscription(memberControllerVor.getPersonalData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PersonalBean>() {
                    @Override
                    public void call(PersonalBean personalBean) {
                        LogUtils.e("获取成功");
                        if (personalBean.getError_code() == 0) {
                            MyUtils.savaPesonData(personalBean);
                            LogUtils.e("personalBean.getMobile()="+personalBean.getMobile());
                            if (TextUtils.isEmpty(personalBean.getCurrency())){
                                activity.turnBind();
                            }else {
                                activity.turnToMain();
                            }
                        } else {
                            showErrorNone(personalBean, activity);
                        }
                    }
                }, getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        showErrorNone(bean,activity);
                    }
                })));
    }
}
