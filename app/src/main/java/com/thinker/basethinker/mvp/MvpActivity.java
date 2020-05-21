package com.thinker.basethinker.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.thinker.basethinker.R;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.dialog.StanderdDialog;
import com.thinker.basethinker.eventbus.MainMsgEvent;
import com.thinker.basethinker.eventbus.MessageEvent;
import com.thinker.basethinker.login.LoginActivity;
import com.thinker.basethinker.utils.ErrorDialogHandler;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.PreferencesUtils;
import com.thinker.basethinker.wallet.DepositActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


/**
 * Created by farley on 2017/2/23.
 * description:实现了ActivityMvpDelegateCallback，然后创建一个BasemvpDelegateCallback:
 */

public abstract class MvpActivity<P extends Mvppresenter, V extends MvpView> extends Activity implements
        ActivityMvpDelegateCallback<P, V>, MvpView {
    private ActyvityDelegate mActyvityDelegate;
    private P mPresenter;

    public MvpActivity() {
        super();
    }

    LoadingDialog dialog;//loading
    private ErrorDialogHandler mErrorDialogHandler;//提醒dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActyvityDelegate = new ActivityDelegateImp<P, V>(this);
        mActyvityDelegate.onCreate();
        mErrorDialogHandler = new ErrorDialogHandler(getFragmentManager());
        ControllerActivity.addActivity(this);
    }

    //    public void showError(int code ,String msg){
//        mErrorDialogHandler.showError(code,msg);
//    }
    @Override
    protected void onResume() {
        super.onResume();
        mActyvityDelegate.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActyvityDelegate.onPause();
        EventBus.getDefault().unregister(this);
    }

    //收到监听
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyEvent(MessageEvent event) {
        if (event == null) {
            return;
        }
        if (event.getReceiverBean() == null) {
            return;
        }
        switch (event.getReceiverBean().getMsgType()) {
            case "99999":
                PreferencesUtils.putString(this, "RADISHSAAS_IS_BIND_LOGOUTTYPE", "99999");
                PreferencesUtils.putString(this, "CURRENTORDERCODE", "");
                showLoginError(event.getReceiverBean().getContent());
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mActyvityDelegate.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActyvityDelegate.onDestroy();
        ControllerActivity.removeActivity(this);
    }

    @Override
    public void setPresenter() {

    }

    protected abstract P CreatePresenter();//暴露一个创建的方法用于创建presenter

    @Override
    public P createPresenter()//这个方法由MvpInternalDelegate 调用BasemvpDelegateCallback 来创建presenter

    {
        mPresenter = CreatePresenter();
        return mPresenter;
    }

    @Override
    public P getPresenter() {//
        return mPresenter;
    }

    @Override
    public V getMvpView() {
        return (V) this;
    }

    @Override
    public void showLoading() {
        dialog = new LoadingDialog(MvpActivity.this);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void showLoginError(String result) {
        StanderdDialog dialog = new StanderdDialog(MvpActivity.this, result, getString(R.string.feedback_toast5),
                new StanderdDialog.OnDialogClickListener() {
                    @Override
                    public void doAnyClick() {
                    }

                    @Override
                    public void doMainClick() {
                        JPushInterface.setAlias(MvpActivity.this, "11", new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                LogUtils.e("注销jpush======" + s);
                            }
                        });
                        String token = "";
                        PreferencesUtils.putString(MvpActivity.this, "RADISHSAAS_IS_BIND", token);
                        MyApplication.setIsLoginStatus(true);
                        startActivity(new Intent(MvpActivity.this, LoginActivity.class));
                    }
                });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }
}
