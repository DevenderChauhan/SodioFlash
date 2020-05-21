package com.thinker.basethinker.wallet;

import android.widget.Toast;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.thinker.basethinker.R;
import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MoneyController;
import com.thinker.basethinker.api.controller.StripeController;
import com.thinker.basethinker.bean.PayDetailBean;
import com.thinker.basethinker.bean.RechartTypeListBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;
import com.thinker.basethinker.pay.OnPayListener;
import com.thinker.basethinker.pay.PayAgent;
import com.thinker.basethinker.stripe.StripeUtils;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import vc.thinker.colours.client.model.WeiXinPaymetBO;

import static com.thinker.basethinker.app.MyApplication.wxApi;

/**
 * Created by farley on 17/8/21.
 * description:
 */

public class ToRechartWalletPresenter extends BasePresenter<MvpView> {
    private ToRechartWalletActivity activity;
    private MoneyController moneyController = APIControllerFactory.getMoneyController();

    public ToRechartWalletPresenter(ToRechartWalletActivity activity) {
        this.activity = activity;
    }

    public void getRechartType() {
        addSubscription(
                moneyController.getRechartList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RechartTypeListBean>() {
                            @Override
                            public void call(RechartTypeListBean rechartTypeListBean) {
                                activity.hideLoading();
                                if (rechartTypeListBean.getError_code() == 0) {
                                    activity.initData(rechartTypeListBean);
                                }
                            }
                        }, getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                activity.hideLoading();
                                showErrorNone(bean, activity);
                            }
                        }))

        );
    }

    /**
     * 充值
     */
    public void recharge(Long money, final String payTpye) {

        addSubscription(moneyController.getRechartResult(money, payTpye)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PayDetailBean>() {
                    @Override
                    public void call(PayDetailBean payDetails) {
                        if (MyUtils.PAY_TYPE_ALIPAY.contentEquals(payTpye)) {
                            PayAgent.getInstance().onAliPay(activity, payDetails.getAlipaPpaySignature(),
                                    new OnPayListener() {
                                        @Override
                                        public void onPaySuccess() {
                                            Toast.makeText(activity, activity.getString(R.string.pay_success), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onPayFail(String code, String msg) {
                                            Toast.makeText(activity, activity.getString(R.string.pay_failed), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else if (MyUtils.PAY_TYPE_WXPAY.contentEquals(payTpye)) {

                            if (wxApi == null || !wxApi.isWXAppInstalled()) {
                                Toast.makeText(activity, activity.getString(R.string.no_wechat), Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(activity, activity.getString(R.string.pay_success), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onPayFail(String code, String msg) {
                                            Toast.makeText(activity, activity.getString(R.string.pay_failed), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else if (MyUtils.PAY_TYPE_STRIPE.contentEquals(payTpye)) {
                            if (payDetails != null) {
                                if (payDetails.getError_code() == 0) {
                                    Toast.makeText(activity, activity.getString(R.string.pay_success), Toast.LENGTH_SHORT).show();
                                    activity.hideLoading();
                                    activity.settoRechartTrue();
                                }else{
                                    Toast.makeText(activity, payDetails.getResult() + ".", Toast.LENGTH_SHORT).show();
                                    activity.hideLoading();
                                }
                            }
                        }
                    }
                }, getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        activity.settoRechartTrue();
                        Toast.makeText(activity, bean.getResult() + ".", Toast.LENGTH_SHORT).show();
                        activity.hideLoading();
                    }
                })));
    }

}
