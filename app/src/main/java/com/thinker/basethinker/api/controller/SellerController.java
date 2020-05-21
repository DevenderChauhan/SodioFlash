package com.thinker.basethinker.api.controller;

import com.google.android.gms.maps.model.LatLng;
import com.thinker.basethinker.api.CommonController;
import com.thinker.basethinker.bean.AllSellerBean;
import com.thinker.basethinker.bean.SellerBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.utils.PropertiesUtils;

import rx.Observable;
import rx.functions.Func1;
import vc.thinker.colours.client.api.SellercontrollerApi;
import vc.thinker.colours.client.model.ListResponseOfAPISellerBO;
import vc.thinker.colours.client.model.SingleResponseOfAPISellerBO;
import vc.thinker.colours.client.model.SingleResponseOfstring;

/**
 * Created by thinker on 17/12/21.
 */

public class SellerController extends CommonController {
    private SellercontrollerApi sellercontrollerApi;

    public SellerController(SellercontrollerApi sellercontrollerApi) {
        this.sellercontrollerApi = sellercontrollerApi;
    }
    //根据位置查找所有商户
    public Observable<AllSellerBean> findByLocationAndDistanceUsingGET(LatLng latLng,Integer distance){
        return sellercontrollerApi.findByLocationAndDistanceUsingGET(latLng.longitude,latLng.latitude,distance)
                .map(new Func1<ListResponseOfAPISellerBO, AllSellerBean>() {
                    @Override
                    public AllSellerBean call(ListResponseOfAPISellerBO listResponseOfAPISellerBO) {
                        if (listResponseOfAPISellerBO.getSuccess()){
                            return new AllSellerBean(PropertiesUtils.copyBeanListProperties(listResponseOfAPISellerBO.getItems(), SellerBean.class));
                        }else{
                            return toErrorBean(listResponseOfAPISellerBO.getError(),listResponseOfAPISellerBO.getErrorDescription(),AllSellerBean.class);
                        }

                    }
                });
    }
    //商户信息
    public Observable<SellerBean> profileUsingGET(Long sellerId){
        return sellercontrollerApi.profileUsingGET(sellerId)
                .map(new Func1<SingleResponseOfAPISellerBO, SellerBean>() {
                    @Override
                    public SellerBean call(SingleResponseOfAPISellerBO listResponseOfAPISellerBO) {
                        if (listResponseOfAPISellerBO.getSuccess()){
                            return PropertiesUtils.copyBeanProperties(listResponseOfAPISellerBO.getItem(), SellerBean.class);
                        }else{
                            return toErrorBean(listResponseOfAPISellerBO.getError(),listResponseOfAPISellerBO.getErrorDescription(),SellerBean.class);
                        }

                    }
                });
    }
}
