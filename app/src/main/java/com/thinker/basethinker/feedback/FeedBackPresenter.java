package com.thinker.basethinker.feedback;

import com.thinker.basethinker.R;
import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.FileController;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.dialog.StanderdDialog;
import com.thinker.basethinker.feedback.bean.FeedBackUpLoadBean;
import com.thinker.basethinker.feedback.bean.FeedbackTypeListBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.OnHttpListener;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by farley on 17/5/22.
 * description:
 */

public class FeedBackPresenter extends BasePresenter<IFeedBackView> {
    private FeedBackActivity activity;
    MemberController memberController = APIControllerFactory.getMemberApi();
    FileController fileController = APIControllerFactory.getClientFileController();
    public FeedBackPresenter(FeedBackActivity feedBackActivity) {
        super();
        activity = feedBackActivity;
    }
    //获取为题反馈列表 反馈类型分类，1 一般问题 2 行程中 3 已完成用户
    public void getFeedBackTypeList(String type){
        addSubscription(memberController.getFeedBackTypeList(type)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<FeedbackTypeListBean>() {
            @Override
            public void call(FeedbackTypeListBean feedbackTypeListBean) {
                if (feedbackTypeListBean.getError_code() == 0){
                    activity.initData(feedbackTypeListBean);
                }else {
                    showErrorNone(feedbackTypeListBean,activity);
                }
            }
        },getErrorAction(new OnHttpListener() {
            @Override
            public void onResult(BaseBean bean) {
                showErrorNone(bean,activity);
            }
        })));
    }

    /**
     * 提交问题反馈
     * @param bean
     */
    public void feedback(final FeedBackUpLoadBean bean){
        activity.showLoading();
        if (activity.getImageList() != null) {
            for (int i = 0; i < activity.getImageList().size(); i++) {
                final int finalI = i;
                addSubscription(fileController.postFile(MyApplication.appContext, activity.getImageList().get(i)).
                        subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                if (finalI == 0) {
                                    bean.setImgUrl1(s);
                                } else if (finalI == 1) {
                                    bean.setImgUrl2(s);
                                } else if (finalI == 2) {
                                    bean.setImgUrl3(s);
                                } else if (finalI == 3) {
                                    bean.setImgUrl4(s);
                                }
                                if (finalI == activity.getImageList().size() - 1) {
                                    upData(bean);
                                }
                            }
                        }, getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                showErrorNone(bean, activity);
                            }
                        })));
            }
        }else{
            upData(bean);
        }

    }
    private void upData(FeedBackUpLoadBean bean){
        addSubscription(memberController.feedback(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean bean) {
                        activity.hideLoading();
                        if (bean.getError_code() == 0){
                            final StanderdDialog dialog = new StanderdDialog(activity, activity.getString(R.string.feedback_tips), activity.getString(R.string.feedback_toast5),
                                    new StanderdDialog.OnDialogClickListener() {
                                        @Override
                                        public void doAnyClick() {
                                        }

                                        @Override
                                        public void doMainClick() {
                                            activity.finish();
                                        }
                                    });
                            dialog.show();
                            dialog.setCanceledOnTouchOutside(false);
                        }else{
                            showErrorNone(bean,activity);
                        }
                    }
                },getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        activity.hideLoading();
                        showErrorNone(bean,activity);
                    }
                })));
    }

}
