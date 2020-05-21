package com.thinker.basethinker.mvp;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * Created by farley on 2017/2/23.
 * description:实现了ActivityMvpDelegateCallback，然后创建一个BasemvpDelegateCallback:
 */

public abstract class MvpFragmentActivity<P extends Mvppresenter, V extends MvpView> extends FragmentActivity implements
        ActivityMvpDelegateCallback<P, V>, MvpView {
    private ActyvityDelegate mActyvityDelegate;
    private P mPresenter;
    public MvpFragmentActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActyvityDelegate = new ActivityDelegateImp<P, V>(this);
        mActyvityDelegate.onCreate();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mActyvityDelegate.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActyvityDelegate.onPause();
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
    }

    @Override
    public void setPresenter() {

    }

    protected abstract P CreatePresenter();//暴露一个创建的方法用于创建presenter

    @Override
    public P createPresenter()//这个方法由MvpInternalDelegate 调用BasemvpDelegateCallback 来创建presenter

    {
        mPresenter=CreatePresenter();
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


}
