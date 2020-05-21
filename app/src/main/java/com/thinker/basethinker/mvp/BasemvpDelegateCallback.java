package com.thinker.basethinker.mvp;

/**
 * Created by farley on 2017/2/23.
 * description:用于对获取MvpView、创建以及获取presenter
 */

public interface BasemvpDelegateCallback <P extends Mvppresenter,V extends MvpView>{
    public  void setPresenter();
    public  P getPresenter();
    public  P createPresenter();
    public V getMvpView();
}
