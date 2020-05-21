package com.thinker.basethinker.member;

import android.widget.Toast;


import com.alipay.sdk.util.LogUtils;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.thinker.basethinker.R;
import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.member.bean.VipListBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;
import com.thinker.basethinker.pay.OnPayListener;
import com.thinker.basethinker.pay.PayAgent;
import com.thinker.basethinker.pay.bean.PayDetails;
import com.thinker.basethinker.utils.MyUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import vc.thinker.colours.client.model.WeiXinPaymetBO;

import static android.app.Activity.RESULT_OK;
import static com.thinker.basethinker.app.MyApplication.wxApi;

/**
 * Created by farley on 17/8/17.
 * description:
 */

public class RechartMembrePresenter extends BasePresenter<MvpView> {
    private RechartMemberActivity activity;
    private MemberController memberController = APIControllerFactory.getMemberApi();
    public RechartMembrePresenter(RechartMemberActivity activity) {
        this.activity = activity;
    }
    /**
     * 获取个人资料
     */
    public void getMyData() {
        addSubscription(memberController.getPersonalData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PersonalBean>() {
                    @Override
                    public void call(PersonalBean personalBean) {
                        activity.hideLoading();
                        if (personalBean.getError_code() == 0) {
                            MyUtils.savaPesonData(personalBean);
                            activity.setPersonData(personalBean);
                        } else {
                            showErrorNone(personalBean, activity);
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
    public void getMemberData(){
        addSubscription(
                memberController.getVipList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VipListBean>() {
                    @Override
                    public void call(VipListBean vipListBean) {
                        if (vipListBean.getError_code() == 0){
                            activity.getDataListSuccess(vipListBean);
                        }else{
                            activity.getDataListFailed();
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
    /**
     * 充值
     *
     */
    public void recharge(long cardId,final String payTpye){
        addSubscription(memberController.buyVip(cardId,payTpye)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PayDetails>() {
                    @Override
                    public void call(PayDetails payDetails) {
                        if (MyUtils.PAY_TYPE_ALIPAY.contentEquals(payTpye)) {
                            PayAgent.getInstance().onAliPay(activity, payDetails.getAlipaPpaySignature(),
                                    new OnPayListener() {
                                        @Override
                                        public void onPaySuccess() {
                                            Toast.makeText(activity,activity.getString(R.string.pay_success),Toast.LENGTH_SHORT).show();
                                           getMyData();
                                        }

                                        @Override
                                        public void onPayFail(String code, String msg) {
                                            Toast.makeText(activity,activity.getString(R.string.pay_failed),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else if (MyUtils.PAY_TYPE_WXPAY.contentEquals(payTpye)) {

                            if (wxApi == null || !wxApi.isWXAppInstalled()) {
                                Toast.makeText(activity,activity.getString(R.string.no_wechat),Toast.LENGTH_SHORT).show();
                                return;
                            }

                            PayReq req = new PayReq();
                            WeiXinPaymetBO wxPay = payDetails.getWeiXinPaymet();

                            req.appId = wxPay.getAppId();
                            req.partnerId = wxPay.getPartnerId();
                            req.prepayId = wxPay.getPrepayId();
                            req.packageValue = wxPay.getPackage1();
                            req.nonceStr = wxPay.getNonceStr();
                            req.timeStamp = wxPay.getTimeStamp();
                            req.sign = wxPay.getSign();

                            PayAgent.getInstance().onWxPay(activity, req,
                                    new OnPayListener() {
                                        @Override
                                        public void onPaySuccess() {
                                            Toast.makeText(activity,activity.getString(R.string.pay_success),Toast.LENGTH_SHORT).show();
                                            getMyData();
                                        }

                                        @Override
                                        public void onPayFail(String code, String msg) {
                                            Toast.makeText(activity,activity.getString(R.string.pay_failed),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else if(MyUtils.PAY_TYPE_STRIPE.contentEquals(payTpye)){
                            if (payDetails != null) {
                                getMyData();
                                Toast.makeText(activity, activity.getString(R.string.pay_success), Toast.LENGTH_SHORT).show();
                            }
                            activity.hideLoading();
                        }
                    }
                }, getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        showErrorNone(bean, activity);
                        activity.hideLoading();
                    }
                })));
    }
}
