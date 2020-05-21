package com.thinker.basethinker.mvp;

/**
 * Created by farley on 2017/2/23.
 * description:为了面向接口编程，解耦性好：
 */

public interface ActivityMvpDelegateCallback<P extends Mvppresenter,V extends MvpView>
        extends BasemvpDelegateCallback<P,V> {
}
