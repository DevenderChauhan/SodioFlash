package com.thinker.basethinker.mvp;

/**
 * Created by farley on 2017/2/23.
 * description:mvp的基础presenter
 */

public interface  Mvppresenter<V extends MvpView> {
    //添加view
    public void attachView(V view);
    //为防止内存泄露，要及时取消关联
    public void detachView();
}
