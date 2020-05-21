package com.thinker.basethinker.wallet;

import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MoneyController;
import com.thinker.basethinker.bean.RechartHistoryListBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thinker on 17/12/13.
 */

public class BalancePresenter extends BasePresenter<MvpView> {
    private BalanceListActivity activity;

    public BalancePresenter(BalanceListActivity activity) {
        this.activity = activity;
    }
    MoneyController moneyController = APIControllerFactory.getMoneyController();
    public void getYueList(final Long time){
        addSubscription(
                moneyController.getRechartHistoryList(time)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RechartHistoryListBean>() {
                            @Override
                            public void call(RechartHistoryListBean rechartHistoryListBean) {
                                if (rechartHistoryListBean.getError_code() == 0){
                                    activity.loadMoreAmountDetail(rechartHistoryListBean,time);
                                }else{

                                }
                            }
                        },getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                showErrorNone(bean,activity);
                            }
                        }))
        );
    }
}
