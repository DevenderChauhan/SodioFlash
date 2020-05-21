package com.thinker.basethinker.pay.wxpay;


import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.thinker.basethinker.pay.OnPayListener;

import static com.thinker.basethinker.app.MyApplication.wxApi;

public class WechatHelper {

	private static final String TAG = WechatHelper.class.getName();
	
	private static OnPayListener mListener;

	public void pay(PayReq req, OnPayListener listener) {
		mListener = listener;
		wxApi.sendReq(req);
	}

	/**
	 * 接收 支付回调
	 * 
	 * @param resp
	 * @return void
	 * @autour BaoHong.Li
	 * @date 2015-7-16 下午4:02:28
	 * @update (date)
	 */
	public static void handleOnResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX && resp.errCode ==0) {
			if (null !=mListener) {
				mListener.onPaySuccess();
			}
		//支付失败
		}
		else {
			if (null !=mListener) {
				mListener.onPayFail(String.valueOf(resp.errCode), resp.errStr);
			}
		}

	}
}
