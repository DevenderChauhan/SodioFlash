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
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thinker on 17/12/8.
 */

public class RegistPresenter extends BasePresenter<MvpView> {
    private RegistActivity activity;
    private MemberController memberController = APIControllerFactory.getMemberApiNoToken();
    public RegistPresenter(RegistActivity activity) {
        this.activity = activity;
    }
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
    public void regist(String phone,String countryCode,String verifycode,String password,String syscode){
        addSubscription(memberController.registeredUsingPOST(countryCode,phone,verifycode,password,syscode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        if (baseBean.getError_code() == 0){
                            activity.registSuccess();
                            Toast.makeText(activity,activity.getString(R.string.registsuccess),Toast.LENGTH_SHORT).show();
                        }else{
                            showErrorNone(baseBean,activity);
                        }
                        activity.setRegistcomplete();
                    }
                },getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        activity.setRegistcomplete();
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
