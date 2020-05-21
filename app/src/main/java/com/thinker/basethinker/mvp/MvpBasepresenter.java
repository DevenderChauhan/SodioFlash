package com.thinker.basethinker.mvp;

import java.lang.ref.WeakReference;

/**
 * Created by farley on 2017/2/23.
 * description:mvp的基础管理presenter用于保持一个对MvpView的引用
 */

public class MvpBasepresenter <V extends MvpView> implements Mvppresenter<V> {
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


}
