package com.thinker.basethinker.member;


import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.member.bean.VipListPayBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by farley on 17/8/18.
 * description:
 */

public class MemberListPresenter extends BasePresenter<MvpView> {
    private MemberListActivity activity;
    private MemberController memberController = APIControllerFactory.getMemberApi();
    public MemberListPresenter(MemberListActivity activity) {
        this.activity = activity;
    }
    public void getVipList(Long lgtime){
        addSubscription(memberController.getVipListM(lgtime)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<VipListPayBean>() {
            @Override
            public void call(VipListPayBean vipListBean) {
                if (vipListBean.getError_code() == 0){
                    activity.setListData(vipListBean);
                }
            }
        },getErrorAction(new OnHttpListener() {
            @Override
            public void onResult(BaseBean bean) {
                showErrorNone(bean,activity);
            }
        })));
    }
}
