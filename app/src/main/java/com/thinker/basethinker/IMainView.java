package com.thinker.basethinker;

import com.thinker.basethinker.mvp.MvpView;

/**
 * Created by thinker on 17/12/18.
 */

public interface IMainView extends MvpView {
    void setMyLocation();
    void setRefresh();
    void scanLock();
    void getNearSeller();
    void feedback();
    void sellerDetail(Long uid);
    void buyBoxFun();
}
