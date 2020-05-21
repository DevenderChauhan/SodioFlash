package com.thinker.basethinker.messagecenter;



import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MessageCenterController;
import com.thinker.basethinker.bean.MessageBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.OnHttpListener;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by farley on 17/7/3.
 * description:
 */

public class MessageCenterPresenter extends BasePresenter<IMessageView> {
    private MessageCenterActivity activity;
    private MessageCenterController messageCenterController = APIControllerFactory.getMessag();
    public MessageCenterPresenter(MessageCenterActivity messageCenterActivity) {
        activity = messageCenterActivity;
    }
    public void getMessage(Long lTime){
        addSubscription(messageCenterController.findMessage(lTime)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<MessageBean>() {
            @Override
            public void call(MessageBean messageBean) {
                if (messageBean.getError_code() == 0 ){
                        activity.loadMore(messageBean);
                }else{
                    showErrorNone(messageBean,activity);
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
