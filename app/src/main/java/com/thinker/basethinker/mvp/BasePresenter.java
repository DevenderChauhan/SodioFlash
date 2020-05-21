package com.thinker.basethinker.mvp;

import android.app.Activity;
import android.content.Intent;


import com.thinker.basethinker.R;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.dialog.StanderdDialog;
import com.thinker.basethinker.login.AddInfoActivity;
import com.thinker.basethinker.login.LoginActivity;
import com.thinker.basethinker.set.SetActivity;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.PreferencesUtils;
import com.thinker.basethinker.wallet.DepositActivity;

import java.lang.ref.WeakReference;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by farley on 17/5/22.
 * description:
 */

public class  BasePresenter<V extends MvpView> implements Mvppresenter<V> {

    private WeakReference<V> reference;
    @Override
    public void attachView(V view) {
        if(view==null)
            throw new NullPointerException("view can not be null when in attachview() in MvpBasepresenter");
        else
        {
            if(reference==null)
                reference=new WeakReference<V>(view);

        }

    }

    @Override
    public void detachView() {

        if(reference!=null)
            reference.clear();
    }

    public V getMvpView()
    {
        if(reference!=null&&reference.get()!=null)
            return reference.get();
        else throw new NullPointerException("have you ever called attachview() in MvpBasepresenter");
    }

    public Boolean isAttach()
    {
        return reference!=null&&reference.get()!=null;
    }


    private CompositeSubscription mCompositeSubscription;

    public Action1<Throwable> getErrorAction(final OnHttpListener onHttpListener) {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
                BaseBean bean = new BaseBean("系统错误", -1);
                if (throwable != null && throwable.getMessage() != null){
                    if (throwable.getMessage().contains("UnknownHostException")){
                        bean = new BaseBean(MyApplication.getIntance().getString(R.string.error_str2), -11);
                    }else if (throwable.getMessage().contains("OAuthProblemException")){
                        bean = new BaseBean(MyApplication.getIntance().getString(R.string.error_login), -10);//登录失效
                    }else if(throwable.getMessage().contains("isConnected failed")){
                        bean = new BaseBean(MyApplication.getIntance().getString(R.string.error_str3), -20);//服务器链接失败
                    }else if(throwable.getMessage().contains("failed to connect to")){
                        bean = new BaseBean(MyApplication.getIntance().getString(R.string.error_str4), -30);//服务器链接失败
                    }else if(throwable.getMessage().contains("thinker.vc")){
                        bean = new BaseBean(MyApplication.getIntance().getString(R.string.error_str4), -30);//服务器链接失败
                    }else if(throwable.getMessage().contains("Bad credentials")){
                        bean = new BaseBean(MyApplication.getIntance().getString(R.string.error_str5), -35);//验证码错误
                    }else if(throwable.getMessage().contains("HTTP 500 Server Error")){
                        bean = new BaseBean(MyApplication.getIntance().getString(R.string.error_str6), -36);
                    }else if(throwable.getMessage().contains("HTTP 502 Bad Gateway")){
                        bean = new BaseBean(MyApplication.getIntance().getString(R.string.error_str7), -37);
                    }else if(throwable.getMessage().contains("OAuthSystemException")){
                        bean = new BaseBean(MyApplication.getIntance().getString(R.string.error_str8), -38);
                    }else if(throwable.getMessage().contains("timeout")){
                        bean = new BaseBean(MyApplication.getIntance().getString(R.string.error_str8), -39);
                    }else{
                        bean = new BaseBean(throwable.getMessage(), -1);
                    }
                    onHttpListener.onResult(bean);
                }
            }
        };
    }

    protected void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(subscription);
    }

    /**
     * 什么都不需要做的
     * @param bean
     * @param activity
     */
    public void showErrorNone(final BaseBean bean, final Activity activity){
       final StanderdDialog dialog = new StanderdDialog(activity, bean.getResult(), activity.getString(R.string.feedback_toast5),
                new StanderdDialog.OnDialogClickListener() {
                    @Override
                    public void doAnyClick() {
                    }

                    @Override
                    public void doMainClick() {
                        if (bean.getError_code() == -10){
                            JPushInterface.setAlias(activity, "11", new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {
                                    LogUtils.e("注销jpush======" + s);
                                }
                            });
                            String token = "";
                            PreferencesUtils.putString(activity,"RADISHSAAS_IS_BIND",token);
                            MyApplication.setIsLoginStatus(true);
                            PreferencesUtils.putString(activity,"RADISHSAAS_IS_BIND_LOGOUTTYPE","-10");
                            activity.startActivity(new Intent(activity, LoginActivity.class));
//                            ControllerActivity.finishAll();
//                            activity.finish();
                        }
                        if (bean.getError_code() == 409){
                            activity.startActivity(new Intent(activity, DepositActivity.class));
                        }
                        if (bean.getError_code() == 408){
                            activity.startActivity(new Intent(activity, AddInfoActivity.class));
                        }
                    }
                });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }
    /**
     * finish掉当前界面的
     * @param bean
     * @param activity
     */
    public void showErrorFinish(final BaseBean bean, final Activity activity){
//        StanderdDialog.getInstatnce(activity, bean.getResult(), "知道了", new StanderdDialog.OnDialogClickListener() {
//            @Override
//            public void doAnyClick() {
//
//            }
//
//            @Override
//            public void doMainClick() {
//                activity.finish();
//            }
//        }).show();
        /*StanderdDialog dialog = new StanderdDialog(activity, bean.getResult(), "知道了",
                new StanderdDialog.OnDialogClickListener() {
                    @Override
                    public void doAnyClick() {
                    }

                    @Override
                    public void doMainClick() {
                        activity.finish();
                    }
                });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);*/
    }
    /**
     * 退出到登录界面的
     * @param bean
     * @param activity
     */
    public void showErrorLogin(final BaseBean bean, final Activity activity){
//        StanderdDialog.getInstatnce(activity, bean.getResult(), "知道了", new StanderdDialog.OnDialogClickListener() {
//            @Override
//            public void doAnyClick() {
//
//            }
//
//            @Override
//            public void doMainClick() {
//                PreferencesUtils.putString(MyApplication.appContext,"RADISHSAAS_IS_BIND","");//退出登录要清空token 不能传null
//                PreferencesUtils.putString(MyApplication.appContext,"RADISHSAAS_PERSONAL_DATA","");//退出登录要清空个人资料不能传null
//                ActivityController.startBindPhone(activity);
//                JPushInterface.setAlias(activity, "", new TagAliasCallback() {
//                    @Override
//                    public void gotResult(int i, String s, Set<String> set) {
//                        LogUtils.d("注销jpush======"+s);
//                    }
//                });
//                MyApplication.setIsIdenty(0);
//                MyApplication.toPayStroke = true;
//                ControllerActivity.finishAll();
//                activity.finish();
//            }
//        }).show();
       /* StanderdDialog dialog = new StanderdDialog(activity, bean.getResult(), "知道了",
                new StanderdDialog.OnDialogClickListener() {
                    @Override
                    public void doAnyClick() {
                    }

                    @Override
                    public void doMainClick() {
                        PreferencesUtils.putString(MyApplication.appContext,"RADISHSAAS_IS_BIND","");//退出登录要清空token 不能传null
                        PreferencesUtils.putString(MyApplication.appContext,"RADISHSAAS_PERSONAL_DATA","");//退出登录要清空个人资料不能传null
                        ActivityController.startBindPhone(activity);
                        JPushInterface.setAlias(activity, "", new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                LogUtils.d("注销jpush======"+s);
                            }
                        });
                        MyApplication.setIsIdenty(0);
                        MyApplication.toPayStroke = true;
                        ControllerActivity.finishAll();
                        activity.finish();
                    }
                });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);*/
    }
}
