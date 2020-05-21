package com.thinker.basethinker.mvp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


/**
 * Created by farley on 17/3/3.
 * description:
 */

public abstract class MvpFragment<P extends Mvppresenter, V extends MvpView> extends Fragment implements
        ActivityMvpDelegateCallback<P, V>, MvpView {
    private FragmentDelegate fragmentDelegate;
    private P mPresenter;

    public MvpFragment() {
        super();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentDelegate = new FragmentDelefatelmp<>(this);
        fragmentDelegate.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentDelegate.onDestroy();
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


}
