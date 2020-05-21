package com.thinker.basethinker.login;

import android.content.Intent;
import android.widget.Toast;

import com.thinker.basethinker.MainActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.ControllerActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Farley on 2018/4/20.
 */

public class AddInfoPresenter extends BasePresenter<MvpView> {
    private AddInfoActivity activity;

    public AddInfoPresenter(AddInfoActivity activity) {
        this.activity = activity;
    }
    private MemberController memberController = APIControllerFactory.getMemberApi();
    public void thirdPartyPerfectInfoUsingGET(String contry,String alisa){
        addSubscription(memberController.thirdPartyPerfectInfoUsingGET(contry,alisa)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        if (baseBean.getError_code() == 0){
                            activity. startActivity(new Intent(activity, MainActivity.class));
                            ControllerActivity.finishAll();
                        }else{
                            showErrorNone(baseBean,activity);
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
