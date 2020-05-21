package com.thinker.basethinker.pay;

import android.app.Activity;
import android.os.Looper;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.thinker.basethinker.pay.alipay.AlipayHelper;
import com.thinker.basethinker.pay.wxpay.WechatHelper;


/**
 * 支付代理类
 *
 * @author BaoHong.Li
 * @version V1.0
 * @date 2015-7-16 上午11:23:48
 * @update (date)
 */
public class PayAgent {

    private static volatile PayAgent instance;

    /***
     * 支付方式
     */
    public enum PayType {
        /**
         * 支付宝
         */
        ALIPAY,
        /**
         * 微信
         */
        WECHATPAY
    }

    private static AlipayHelper mAlipayHelper;
    private static WechatHelper mWechatpayHelper;

    private PayAgent() {

    }

    public static PayAgent getInstance() {

        if (null == instance) {
            synchronized (PayAgent.class) {
                if (null == instance) {
                    instance = new PayAgent();
                }
            }
        }
        return instance;
    }

    public AlipayHelper getAlipayHelper() {
        if (null == mAlipayHelper) {
            mAlipayHelper = new AlipayHelper();
        }
        return mAlipayHelper;
    }

    public WechatHelper getWechatpayHelper() {
        if (null == mWechatpayHelper) {
            mWechatpayHelper = new WechatHelper();
        }
        return mWechatpayHelper;
    }

    public void onAliPay(Activity activity, String payInfo, OnPayListener listener) {
        if (null == payInfo) {
            throw new IllegalArgumentException(" payinfo  is null!");
        }

        if (null == activity) {
            throw new IllegalArgumentException(" Activity  is null!");
        }

        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalArgumentException(Thread.currentThread().getName()
                    + "'. " + "onPay methods must be called on the UI thread. ");
        }

        getAlipayHelper().pay(activity, payInfo, listener);
    }

    public void onWxPay(Activity activity, PayReq req, OnPayListener listener) {

        if (null == req) {
            throw new IllegalArgumentException(" payinfo  is null!");
        }

        if (null == activity) {
            throw new IllegalArgumentException(" Activity  is null!");
        }

        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalArgumentException(Thread.currentThread().getName()
                    + "'. " + "onPay methods must be called on the UI thread. ");
        }
        getWechatpayHelper().pay(req, listener);
    }
}
