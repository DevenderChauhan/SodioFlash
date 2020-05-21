package com.thinker.basethinker.wallet;

import android.widget.Toast;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.thinker.basethinker.R;
import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.bean.ChergeBean;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.bean.WalletBean;
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

import static com.thinker.basethinker.app.MyApplication.wxApi;

/**
 * Created by thinker on 17/12/13.
 */

public class DepositPresenter extends BasePresenter<MvpView> {
    private DepositActivity activity;
    MemberController memberController = APIControllerFactory.getMemberApi();
    public DepositPresenter(DepositActivity epdositActivity) {
        super();
        activity = epdositActivity;
    }

    /**
     * 获取我的钱包记录
     * @param time
     */
    public void getMyWallet(final Long time){
        addSubscription(memberController.getMyWallet(time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WalletBean>() {
                    @Override
                    public void call(WalletBean walletBean) {
                        if (walletBean.getError_code() == 0){
                            activity.loadMore(walletBean,time);
                        }else{
                            showErrorNone(walletBean,activity);
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
     * 获取需要交纳的押金金额
     */
    public void getCherge(){
        addSubscription(memberController.getCherge()
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ChergeBean>() {
                    @Override
                    public void call(ChergeBean chergeBean) {
                        if (chergeBean.getError_code() == 0){
                            activity.setCherge(chergeBean);
                        }else{
                            showErrorNone(chergeBean,activity);
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
     * 退押金
     */
    public void refundDeposit(){
        addSubscription(memberController.repounDisposit()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean bean) {
                        if (bean.getError_code() == 0){
                            Toast.makeText(activity,activity.getString(R.string.return_deposit_success),Toast.LENGTH_SHORT).show();
                            getMyData();
                            getMyWallet(null);
                        }else{
                            showErrorNone(bean,activity);
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
     * 获取个人资料
     */
    public void getMyData(){
        addSubscription(memberController.getPersonalData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PersonalBean>() {
                    @Override
                    public void call(PersonalBean personalBean) {
                        if (personalBean.getError_code() == 0){
                            MyUtils.savaPesonData(personalBean);
                            activity.initData(personalBean);
                        }else{
                            showErrorNone(personalBean,activity);
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
     * 充值
     *
     */
    public void recharge(final String payTpye){
        addSubscription(memberController.recharge(payTpye)
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
                                            getMyWallet(null);
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
                                            getMyWallet(null);
                                        }

                                        @Override
                                        public void onPayFail(String code, String msg) {
                                            Toast.makeText(activity,activity.getString(R.string.pay_failed),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else if(MyUtils.PAY_TYPE_STRIPE.contentEquals(payTpye)){
                            if (payDetails != null) {
                                Toast.makeText(activity, activity.getString(R.string.pay_success), Toast.LENGTH_SHORT).show();
                                getMyData();
                                getMyWallet(null);
                            }
                            activity.hideLoading();
                        }
                    }
                }, getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        showErrorNone(bean, activity);
                    }
                })));
    }
}
