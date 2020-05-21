package com.thinker.basethinker.mvp;

/**
 * Created by farley on 2017/2/23.
 * description:用于控制presenter与MvpView
 */

public class MvpInternalDelegate<P extends Mvppresenter, V extends MvpView> {
    BasemvpDelegateCallback<P, V> callback;

    public MvpInternalDelegate(BasemvpDelegateCallback<P, V> callback)
    {
        this.callback = callback;
    }


    public P creatPresneter() {
        P p=callback.getPresenter();
        if (p== null)
            p=callback.createPresenter();
        if(p==null)
            throw new NullPointerException("callback.createPresenter() is null in MvpInternalDelegate");
        return p;
    }

    public void attachView() {
        callback.getPresenter().attachView(callback.getMvpView());
    }

    public void detacthView() {
        callback.getPresenter().detachView();
    }
}
