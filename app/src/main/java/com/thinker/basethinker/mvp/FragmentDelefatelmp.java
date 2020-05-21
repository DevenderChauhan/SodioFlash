package com.thinker.basethinker.mvp;

/**
 * Created by farley on 17/3/3.
 * description:
 */

public class FragmentDelefatelmp <P extends Mvppresenter,V extends MvpView> implements FragmentDelegate {
    private BasemvpDelegateCallback<P,V> basemvpDelegateCallback;
    private MvpInternalDelegate<P,V> mvpInternalDelegate;
    public FragmentDelefatelmp(BasemvpDelegateCallback<P,V> basemvpDelegateCallback) {
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
    public void onDestroy() {
        mvpInternalDelegate.detacthView();
    }
}
