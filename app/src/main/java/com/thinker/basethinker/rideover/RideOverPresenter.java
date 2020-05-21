package com.thinker.basethinker.rideover;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.thinker.basethinker.R;
import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.api.controller.OrderController;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.bean.PayDetailBean;
import com.thinker.basethinker.bean.UserMoneyBean;
import com.thinker.basethinker.dialog.StanderdDialog;
import com.thinker.basethinker.login.LoginActivity;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.BasePresenter;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.mvp.OnHttpListener;
import com.thinker.basethinker.pay.OnPayListener;
import com.thinker.basethinker.pay.PayAgent;
import com.thinker.basethinker.pay.bean.PayDetails;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.PreferencesUtils;
import com.thinker.basethinker.wallet.DepositActivity;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import vc.thinker.colours.client.model.WeiXinPaymetBO;

import static com.thinker.basethinker.app.MyApplication.wxApi;

/**
 * Created by thinker on 17/12/25.
 */

public class RideOverPresenter extends BasePresenter<MvpView> {
    private RideOverActivity activity;
    private OrderController orderController = APIControllerFactory.getOrderController();
    private MemberController memberController = APIControllerFactory.getMemberApi();
    public RideOverPresenter(RideOverActivity activity) {
        this.activity = activity;
    }
    //获取所有商户
    public void findNotPayUsingGET(){
        activity.showLoading();
        addSubscription(
                orderController.findNotPayUsingGET()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<OrderBean>() {
                            @Override
                            public void call(OrderBean orderBean) {
                                activity.hideLoading();
                                if (orderBean.getError_code() == 0){
                                    activity.initData(orderBean);
                                }else{
                                    showErrorNone(orderBean,activity);
                                }
                            }
                        },getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                activity.hideLoading();
                                showErrorNone(bean, activity);
                            }
                        }))
        );
    }
    //获取订单详情
    public void profileUsingGET(String ordercode){
        activity.showLoading();
        addSubscription(
                orderController.profileUsingGET(ordercode)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<OrderBean>() {
                            @Override
                            public void call(OrderBean orderBean) {
                                activity.hideLoading();
                                if (orderBean.getError_code() == 0){
                                    activity.initData(orderBean);
                                }else{
                                    showErrorNone(orderBean,activity);
                                }
                            }
                        },getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                activity.hideLoading();
                                showErrorNone(bean, activity);
                            }
                        }))
        );
    }
    //获取余额
    public void getMyBalanceUsingGET(){
        addSubscription(
                memberController.getMyBalanceUsingGET()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<UserMoneyBean>() {
                            @Override
                            public void call(UserMoneyBean belance) {
                                activity.setBalence(belance);
                            }
                        },getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                showErrorNone(bean, activity);
                            }
                        }))
        );
    }
    /**
     * 支付
     *
     */
    public void paymetUsingPOST(String orderCode, Long cid, final String payTpye){
        activity.showLoading();
        addSubscription(orderController.paymetUsingPOST(orderCode,cid,payTpye)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PayDetailBean>() {
                    @Override
                    public void call(PayDetailBean payDetails) {
                        activity.hideLoading();
                        if (payDetails != null &&  payDetails.getError_code() != 0){
                            showErrorNone(payDetails,activity);
                            return;
                        }
                        if (MyUtils.PAY_TYPE_ALIPAY.contentEquals(payTpye)) {
                            PayAgent.getInstance().onAliPay(activity, payDetails.getAlipaPpaySignature(),
                                    new OnPayListener() {
                                        @Override
                                        public void onPaySuccess() {
                                            Toast.makeText(activity,activity.getString(R.string.pay_success),Toast.LENGTH_SHORT).show();
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
                                        }

                                        @Override
                                        public void onPayFail(String code, String msg) {
                                            Toast.makeText(activity,activity.getString(R.string.pay_failed),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else if(MyUtils.PAY_TYPE_STRIPE.contentEquals(payTpye)){
                            Toast.makeText(activity, activity.getString(R.string.pay_success), Toast.LENGTH_SHORT).show();
                            activity.finish();
                        }else{
                            Toast.makeText(activity, activity.getString(R.string.pay_success), Toast.LENGTH_SHORT).show();
                            activity.finish();
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
    /**
     * 什么都不需要做的
     * @param bean
     * @param activity
     */
    public void showErrorNone(final BaseBean bean, final Activity activity){
        final StanderdDialog dialog = new StanderdDialog(activity, bean.getResult(), activity.getString(R.string.feedback_toast5),
                new StanderdDialog.OnDialogClickListener() {
                    @Override
                    public void doAnyClick() {
                    }

                    @Override
                    public void doMainClick() {
                        if (bean.getError_code() == -10){
                            JPushInterface.setAlias(activity, "11", new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {
                                    LogUtils.e("注销jpush======" + s);
                                }
                            });
                            String token = "";
                            PreferencesUtils.putString(activity,"RADISHSAAS_IS_BIND",token);
                            MyApplication.setIsLoginStatus(true);
                            activity.startActivity(new Intent(activity, LoginActivity.class));
//                            ControllerActivity.finishAll();
//                            activity.finish();
                        }
                        if (bean.getError_code() == 409){
//                            activity.startActivity(new Intent(activity, DepositActivity.class));
                        }
                    }
                });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }
}
