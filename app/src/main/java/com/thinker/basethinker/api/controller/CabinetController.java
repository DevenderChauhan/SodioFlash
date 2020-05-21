package com.thinker.basethinker.api.controller;

import com.thinker.basethinker.api.CommonController;
import com.thinker.basethinker.bean.CabinetBean;
import com.thinker.basethinker.utils.PropertiesUtils;

import rx.Observable;
import rx.functions.Func1;
import vc.thinker.colours.client.api.CabinetcontrollerApi;
import vc.thinker.colours.client.model.SingleResponseOfAPICabinetBO;

/**
 * Created by thinker on 17/12/22.
 */

public class CabinetController extends CommonController {
    private CabinetcontrollerApi cabinetcontrollerApi;

    public CabinetController(CabinetcontrollerApi cabinetcontrollerApi) {
        this.cabinetcontrollerApi = cabinetcontrollerApi;
    }
    //获取充电柜详情信息
    public Observable<CabinetBean> profileUsingGET(String qrCode) {
        return cabinetcontrollerApi.profileUsingGET(qrCode)
                .map(new Func1<SingleResponseOfAPICabinetBO, CabinetBean>() {
                    @Override
                    public CabinetBean call(SingleResponseOfAPICabinetBO simpleResponse) {
                        if (simpleResponse.getSuccess()){
                            return PropertiesUtils.copyBeanProperties(simpleResponse.getItem(),CabinetBean.class);
                        }else{
                            return toErrorBean(simpleResponse.getError(),simpleResponse.getErrorDescription(),CabinetBean.class);
                        }
                    }
                });
    }
}
