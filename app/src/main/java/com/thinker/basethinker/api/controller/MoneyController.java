package com.thinker.basethinker.api.controller;

import com.thinker.basethinker.api.CommonController;
import com.thinker.basethinker.bean.PayDetailBean;
import com.thinker.basethinker.bean.RechartHistoryBean;
import com.thinker.basethinker.bean.RechartHistoryListBean;
import com.thinker.basethinker.bean.RechartTypeBean;
import com.thinker.basethinker.bean.RechartTypeListBean;
import com.thinker.basethinker.bean.UserAmount;
import com.thinker.basethinker.bean.UserMoneyBean;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.PropertiesUtils;

import rx.Observable;
import rx.functions.Func1;
import vc.thinker.colours.client.api.MoneycontrollerApi;
import vc.thinker.colours.client.model.ListResponseOfAPIPayAmountBO;
import vc.thinker.colours.client.model.PageResponseOfAPIUserMoneyLogBO;
import vc.thinker.colours.client.model.SingleResponseOfAPIUserMoneyBO;
import vc.thinker.colours.client.model.SingleResponseOfPayDetailsBO;
import vc.thinker.colours.client.model.SingleResponseOfdouble;

/**
 * Created by farley on 17/8/21.
 * description:
 */

public class MoneyController extends CommonController {
    private MoneycontrollerApi moneycontrollerApi;

    public MoneyController(MoneycontrollerApi moneycontrollerApi) {
        this.moneycontrollerApi = moneycontrollerApi;
    }
    //获取余额
    public Observable<UserMoneyBean> getMoney() {
        return moneycontrollerApi.getMyBalanceUsingGET()
                .map(new Func1<SingleResponseOfAPIUserMoneyBO, UserMoneyBean>() {
                    @Override
                    public UserMoneyBean call(SingleResponseOfAPIUserMoneyBO simpleResponse) {
                        if (simpleResponse.getSuccess()){
                            return PropertiesUtils.copyBeanProperties(simpleResponse.getItem(),UserMoneyBean.class);
                        }else{
                            return toErrorBean(simpleResponse.getError(),simpleResponse.getErrorDescription(),UserMoneyBean.class);
                        }
                    }
                });
    }
    //充值
    public Observable<PayDetailBean> getRechartResult(Long money, String type) {
        LogUtils.e(money+"money,"+type);
        return moneycontrollerApi.paymetBalanceUsingPOST(money,type)
                .map(new Func1<SingleResponseOfPayDetailsBO, PayDetailBean>() {
                    @Override
                    public PayDetailBean call(SingleResponseOfPayDetailsBO simpleResponse) {
                        LogUtils.e(simpleResponse.getSuccess()+"1");
                        LogUtils.e(simpleResponse.getError()+"1");
                        LogUtils.e(simpleResponse.getErrorDescription()+"1");
                        if (simpleResponse.getSuccess()){
                            return PropertiesUtils.copyBeanProperties(simpleResponse.getItem(),PayDetailBean.class);
                        }else{
                            LogUtils.e("error");
                            return toErrorBean(simpleResponse.getError(),simpleResponse.getErrorDescription(),PayDetailBean.class);
                        }
                    }
                });
    }
    //获取充值金额类型列表
    public Observable<RechartTypeListBean> getRechartList() {
        return moneycontrollerApi.findPayAmountListUsingGET()
                .map(new Func1<ListResponseOfAPIPayAmountBO, RechartTypeListBean>() {
                    @Override
                    public RechartTypeListBean call(ListResponseOfAPIPayAmountBO simpleResponse) {
                        if (simpleResponse.getSuccess()){
                            return new RechartTypeListBean(PropertiesUtils.copyBeanListProperties(simpleResponse.getItems(),RechartTypeBean.class));
                        }else{
                            return toErrorBean(simpleResponse.getError(),simpleResponse.getErrorDescription(),RechartTypeListBean.class);
                        }
                    }
                });
    }
    //GET /api/money/money_log_list 获取用户余额日志列表
    public Observable<RechartHistoryListBean> getRechartHistoryList(Long lTime) {
        return moneycontrollerApi.findMoneyLogListUsingGET(lTime)
                .map(new Func1<PageResponseOfAPIUserMoneyLogBO, RechartHistoryListBean>() {
                    @Override
                    public RechartHistoryListBean call(PageResponseOfAPIUserMoneyLogBO simpleResponse) {
                        if (simpleResponse.getSuccess()){
                            return new RechartHistoryListBean(PropertiesUtils.copyBeanListProperties(simpleResponse.getContent(),RechartHistoryBean.class));
                        }else{
                            return toErrorBean(simpleResponse.getError(),simpleResponse.getErrorDescription(),RechartHistoryListBean.class);
                        }
                    }
                });
    }
}
