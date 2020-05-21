package com.thinker.basethinker.api.controller;

import com.thinker.basethinker.api.CommonController;
import com.thinker.basethinker.mvp.BaseBean;

import rx.Observable;
import rx.functions.Func1;
import vc.thinker.colours.client.api.StripecontrollerApi;
import vc.thinker.colours.client.model.SimpleResponse;
import vc.thinker.colours.client.model.SingleResponseOfstring;

/**
 * Created by thinker on 17/12/14.
 */

public class StripeController extends CommonController {
    private StripecontrollerApi stripecontrollerApi;

    public StripeController(StripecontrollerApi stripecontrollerApi) {
        this.stripecontrollerApi = stripecontrollerApi;
    }
    //得到ephemeral_key
    public Observable<BaseBean> getEphemeralKeyUsingPOST(String apiVersion) {
        return stripecontrollerApi.getEphemeralKeyUsingPOST(apiVersion)
                .map(new Func1<SingleResponseOfstring, BaseBean>() {
                    @Override
                    public BaseBean call(SingleResponseOfstring simpleResponse) {
                        if (simpleResponse.getSuccess()){
                            BaseBean baseBean = new BaseBean();
                            baseBean.setError_code(0);
                            baseBean.setResult(simpleResponse.getItem());
                            return baseBean;
                        }else{
                            return toErrorBean(simpleResponse.getError(),simpleResponse.getErrorDescription(),BaseBean.class);
                        }

                    }
                });
    }
}
