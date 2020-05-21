package com.thinker.basethinker.api.controller;


import com.thinker.basethinker.api.CommonController;

import rx.Observable;
import rx.functions.Func1;
import vc.thinker.colours.client.api.TripcontrollerApi;
import vc.thinker.colours.client.model.PageResponseOfTripBO;

/**
 * Created by farley on 17/5/23.
 * description:
 */

public class TripController extends CommonController {
    private TripcontrollerApi tripcontrollerApi;

    public TripController(TripcontrollerApi tripcontrollerApi) {
        this.tripcontrollerApi = tripcontrollerApi;
    }

    /**
     * 获取用户所有的行程数据
     *
     * @return
     */
//    public Observable<ItemStrokeBean> getMyAllStroke(Long time) {
//        return tripcontrollerApi.findTripListUsingPOST(time)
//                .map(new Func1<PageResponseOfTripBO, ItemStrokeBean>() {
//                    @Override
//                    public ItemStrokeBean call(PageResponseOfTripBO pageResponseOfTripBO) {
//                        if (pageResponseOfTripBO.getSuccess()) {
//                            ItemStrokeBean itemStrokeBean = new ItemStrokeBean();
//                            itemStrokeBean.setContent(listBean);
//                            return itemStrokeBean;
//                        } else
//                            return toErrorBean(pageResponseOfTripBO.getError(), pageResponseOfTripBO.getErrorDescription(), ItemStrokeBean.class);
//                    }
//                });
//    }
}
