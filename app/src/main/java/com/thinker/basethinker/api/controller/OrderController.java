package com.thinker.basethinker.api.controller;

import com.thinker.basethinker.api.CommonController;
import com.thinker.basethinker.bean.AllSellerBean;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.bean.OrderListBean;
import com.thinker.basethinker.bean.PayDetailBean;
import com.thinker.basethinker.myoffer.bean.MyOfferBean;
import com.thinker.basethinker.myoffer.bean.MyOfferData;
import com.thinker.basethinker.utils.PropertiesUtils;

import rx.Observable;
import rx.functions.Func1;
import vc.thinker.colours.client.api.OrdercontrollerApi;
import vc.thinker.colours.client.model.ListResponseOfAPIUserCouponBO;
import vc.thinker.colours.client.model.PageResponseOfAPIOrderBO;
import vc.thinker.colours.client.model.SingleResponseOfAPIOrderBO;
import vc.thinker.colours.client.model.SingleResponseOfPayDetailsBO;
import vc.thinker.colours.client.model.SingleResponseOfdouble;

/**
 * Created by thinker on 17/12/22.
 */

public class OrderController extends CommonController {
    private OrdercontrollerApi ordercontrollerApi;

    public OrderController(OrdercontrollerApi ordercontrollerApi) {
        this.ordercontrollerApi = ordercontrollerApi;
    }
    //获取充电柜详情信息
    public Observable<OrderBean> createOrderUsingPOST(String qrCode, String batteryType) {
        return ordercontrollerApi.createOrderUsingPOST(qrCode,batteryType)
                .map(new Func1<SingleResponseOfAPIOrderBO, OrderBean>() {
                    @Override
                    public OrderBean call(SingleResponseOfAPIOrderBO simpleResponse) {
                        if (simpleResponse.getSuccess()){
                            return PropertiesUtils.copyBeanProperties(simpleResponse.getItem(),OrderBean.class);
                        }else{
                            return toErrorBean(simpleResponse.getError(),simpleResponse.getErrorDescription(),OrderBean.class);
                        }
                    }
                });
    }
    //获取当前进行中的订单
    public Observable<OrderBean> findDoingInfoUsingGET(){
        return ordercontrollerApi.findDoingInfoUsingGET()
                .map(new Func1<SingleResponseOfAPIOrderBO, OrderBean>() {
                    @Override
                    public OrderBean call(SingleResponseOfAPIOrderBO singleResponseOfAPIOrderBO) {
                        if (singleResponseOfAPIOrderBO.getSuccess()){
                            return PropertiesUtils.copyBeanProperties(singleResponseOfAPIOrderBO.getItem(),OrderBean.class);
                        }else{
                            return toErrorBean(singleResponseOfAPIOrderBO.getError(),singleResponseOfAPIOrderBO.getErrorDescription(),OrderBean.class);
                        }
                    }
                });
    }
    //获取可使用的优惠券列表
    public Observable<MyOfferBean> findCouponListUsingPOST(){
        return ordercontrollerApi.findCouponListUsingPOST()
                .map(new Func1<ListResponseOfAPIUserCouponBO, MyOfferBean>() {
                    @Override
                    public MyOfferBean call(ListResponseOfAPIUserCouponBO singleResponseOfAPIOrderBO) {
                        if (singleResponseOfAPIOrderBO.getSuccess()){
                            return new MyOfferBean(PropertiesUtils.copyBeanListProperties(singleResponseOfAPIOrderBO.getItems(),MyOfferData.class));
                        }else{
                            return toErrorBean(singleResponseOfAPIOrderBO.getError(),singleResponseOfAPIOrderBO.getErrorDescription(),MyOfferBean.class);
                        }
                    }
                });
    }
    //获取未支付的订单
    public Observable<OrderBean> findNotPayUsingGET(){
        return ordercontrollerApi.findNotPayUsingGET()
                .map(new Func1<SingleResponseOfAPIOrderBO, OrderBean>() {
                    @Override
                    public OrderBean call(SingleResponseOfAPIOrderBO singleResponseOfAPIOrderBO) {
                        if (singleResponseOfAPIOrderBO.getSuccess()){
                            return PropertiesUtils.copyBeanProperties(singleResponseOfAPIOrderBO.getItem(),OrderBean.class);
                        }else{
                            return toErrorBean(singleResponseOfAPIOrderBO.getError(),singleResponseOfAPIOrderBO.getErrorDescription(),OrderBean.class);
                        }
                    }
                });
    }
    //支付
    public Observable<PayDetailBean> paymetUsingPOST(String orderCode,Long cid,String paymentMark){
        return ordercontrollerApi.paymetUsingPOST(orderCode,cid,paymentMark)
                .map(new Func1<SingleResponseOfPayDetailsBO, PayDetailBean>() {
                    @Override
                    public PayDetailBean call(SingleResponseOfPayDetailsBO singleResponseOfAPIOrderBO) {
                        if (singleResponseOfAPIOrderBO.getSuccess()){
                            return PropertiesUtils.copyBeanProperties(singleResponseOfAPIOrderBO.getItem(),PayDetailBean.class);
                        }else{
                            return toErrorBean(singleResponseOfAPIOrderBO.getError(),singleResponseOfAPIOrderBO.getErrorDescription(),PayDetailBean.class);
                        }
                    }
                });
    }
    /**
     * 获取订单详情
     */
    public Observable<OrderBean> profileUsingGET(String ordercode) {
        return ordercontrollerApi.profileUsingGET(ordercode)
                .map(new Func1<SingleResponseOfAPIOrderBO, OrderBean>() {
                    @Override
                    public OrderBean call(SingleResponseOfAPIOrderBO singleResponseOfAPIOrderBO) {
                        if (singleResponseOfAPIOrderBO.getSuccess()){
                            return PropertiesUtils.copyBeanProperties(singleResponseOfAPIOrderBO.getItem(),OrderBean.class);
                        }else{
                            return toErrorBean(singleResponseOfAPIOrderBO.getError(),singleResponseOfAPIOrderBO.getErrorDescription(),OrderBean.class);
                        }
                    }
                });
    }
    /**
     * 获取订单列表
     */
    public Observable<OrderListBean> findOrderListUsingPOST(Long ltTime) {
        return ordercontrollerApi.findOrderListUsingPOST(ltTime)
                .map(new Func1<PageResponseOfAPIOrderBO, OrderListBean>() {
                    @Override
                    public OrderListBean call(PageResponseOfAPIOrderBO pageResponseOfAPIOrderBO) {
                        if (pageResponseOfAPIOrderBO.getSuccess()){
                            return new OrderListBean(PropertiesUtils.copyBeanListProperties(pageResponseOfAPIOrderBO.getContent(),OrderBean.class));
                        }else{
                            return toErrorBean(pageResponseOfAPIOrderBO.getError(),pageResponseOfAPIOrderBO.getErrorDescription(),OrderListBean.class);
                        }
                    }
                });
    }
}
