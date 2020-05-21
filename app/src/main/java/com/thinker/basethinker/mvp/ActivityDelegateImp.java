package com.thinker.basethinker.mvp;

/**
 * Created by farley on 2017/2/23.
 * description:实现了ActyvityDelegate，在生命周期里控制presenter与MvpView：
 */

public class ActivityDelegateImp<P extends Mvppresenter,V extends MvpView> implements ActyvityDelegate {
    private BasemvpDelegateCallback<P,V> basemvpDelegateCallback;
    private MvpInternalDelegate<P,V> mvpInternalDelegate;

    public ActivityDelegateImp(BasemvpDelegateCallback<P,V> basemvpDelegateCallback) {//传入BasemvpDelegateCallback 去控制presenter
        if(basemvpDelegateCallback==null)
            throw new NullPointerException("the basemvpDelegateCallback in  ActivityDelegateImpn is null");
        this.basemvpDelegateCallback=basemvpDelegateCallback;
        mvpInternalDelegate=new MvpInternalDelegate<P,V>(this.basemvpDelegateCallback);

    }

    @Override
    public void onCreate() {
        mvpInternalDelegate.creatPresneter();
        mvpInternalDelegate.attachView();

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        mvpInternalDelegate.detacthView();
    }
}
