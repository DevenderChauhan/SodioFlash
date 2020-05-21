package com.thinker.basethinker;

import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.Config;
import com.thinker.basethinker.api.controller.AppParamController;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.api.controller.MessageCenterController;
import com.thinker.basethinker.api.controller.OrderController;
import com.thinker.basethinker.api.controller.SellerController;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.bean.AdvImages;
import com.thinker.basethinker.bean.AllSellerBean;
import com.thinker.basethinker.bean.BasicCostBean;
import com.thinker.basethinker.bean.ImagesBean;
import com.thinker.basethinker.bean.InvateAndShateBean;
import com.thinker.basethinker.bean.MessageHomeBean;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.bean.SellerBean;
import com.thinker.basethinker.bean.SystemParamsBean;
import com.thinker.basethinker.dialog.AdvNotifyDialog;
import com.thinker.basethinker.googleutils.DirectionBean;
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
import com.thinker.basethinker.utils.SaveImgeUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import vc.thinker.colours.client.model.WeiXinPaymetBO;

import static com.thinker.basethinker.app.MyApplication.wxApi;

/**
 * Created by farley on 17/12/5.
 * description:
 */

public class MainPresenter extends BasePresenter<MvpView> {
    private MainActivity activity;
    MessageCenterController messageCenterController = APIControllerFactory.getMessag();
    private AppParamController appParamController = APIControllerFactory.getSystemParams();
    private MemberController memberController = APIControllerFactory.getMemberApi();
//    private SellerController sellerController = APIControllerFactory.getSellerApi();
    private SellerController sellerControllerNoToken = APIControllerFactory.getSellerApiNoToken();
    private OrderController orderController = APIControllerFactory.getOrderController();
    public MainPresenter(MainActivity activity) {
        this.activity = activity;
    }

    /**
     * 获取系统配置参数
     */
    public void getSystemParams() {
        addSubscription(appParamController.getSystemParams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SystemParamsBean>() {
                    @Override
                    public void call(SystemParamsBean systemParamsBean) {
                        if (systemParamsBean.getError_code() == 0) {
                            MyUtils.savaSystemData(systemParamsBean);
                            activity.setServicePhone(systemParamsBean);
                            if (systemParamsBean.getMapSearchScope() != null){
                                int distance = systemParamsBean.getMapSearchScope() * 1000;
                                Config.mapSearchScope = distance;
                            }
                        } else {
                            showErrorNone(systemParamsBean,activity);
                        }
                    }
                }, getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        showErrorNone(bean,activity);
                    }
                })));
    }
    /**
     * 获取邀请和分享的参数
     */
    public void getInvateAndShareParams() {
        addSubscription(appParamController.getInvateAndShareParams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<InvateAndShateBean>() {
                    @Override
                    public void call(InvateAndShateBean invateAndShateBean) {
                        if (invateAndShateBean.getError_code() == 0) {
                            MyUtils.savaInvateAndShareData(invateAndShateBean);
                        } else {
                            showErrorNone(invateAndShateBean, activity);
                        }
                    }
                }, getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        showErrorNone(bean, activity);
                    }
                })));
    }
    /*获取启动页广告*/
    public void getAdvImages(){
        addSubscription(
                appParamController.getAdvImages()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<AdvImages>() {
                            @Override
                            public void call(AdvImages advImages) {
                                if (advImages.getError_code() == 0){
                                    if (!Config.ADV_IMAGE_MD5.contains(advImages.getMd5()) || !MyUtils.fileIsHave().exists() || MyUtils.fileIsHave().listFiles() != null){
                                        MyUtils.deleteMyFiles();
                                        MyUtils.savaAdvanceData(advImages);
//                                        Config.setAdvImageMd5(advImages.getMd5());//下载成功后再添加上MD5 防止没下载完就保存了
                                        PreferencesUtils.putInt(MyApplication.appContext,"ADV_IMAGE_TIME",advImages.getTime()*1000);
                                        for (ImagesBean image:advImages.getImgList()) {
                                            new Thread(new SaveImgeUtils(activity, image.getInitImg(),advImages.getMd5())).start();
                                        }
                                    }
                                }
                            }
                        }, getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                showErrorNone(bean,activity);
                            }
                        }))
        );
    }
    public void getHomeMessage(){
        Long timeTamp = PreferencesUtils.getLong(MyApplication.appContext,"ADV_TIME_TANMP");
        addSubscription(
                messageCenterController.findHomeMessage(timeTamp)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<MessageHomeBean>() {
                            @Override
                            public void call(MessageHomeBean messageHomeBean) {
                                if (messageHomeBean != null && messageHomeBean.getError_code() ==0 &&
                                        !TextUtils.isEmpty(messageHomeBean.getAdCover())) {
                                    PreferencesUtils.putLong(MyApplication.appContext,"ADV_TIME_TANMP",messageHomeBean.getSendTime().getTime());
                                    AdvNotifyDialog dialog = new AdvNotifyDialog(activity, messageHomeBean);
                                    dialog.show();
                                }else {

                                }
                            }
                        },getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                showErrorNone(bean,activity);
                            }
                        }))
        );
    }
    /**
     * 获取个人资料
     */
    public void getMyData() {
        addSubscription(memberController.getPersonalData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PersonalBean>() {
                    @Override
                    public void call(PersonalBean personalBean) {
                        if (personalBean.getError_code() == 0) {
                            MyUtils.savaPesonData(personalBean);
                            activity.setPersonData(personalBean);
                        } else {
                            showErrorNone(personalBean,activity);
                        }
                    }
                }, getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        showErrorNone(bean,activity);
                    }
                })));
    }

    //获取两端的最近距离
    public void getMapDerections(String origin, String destination){
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+origin+"&destination="+destination+"&mode=walking"+"&key="+Config.Google_Maps_Directions_API_KEY;
        LogUtils.e( "===url===" + url);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS).build();
        activity.showLoading();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtils.e( "===msg===" + e.getLocalizedMessage());
                    activity.hideLoading();
                    activity.setGetGooglePlineFailed();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    activity.hideLoading();
                    Gson gson = new Gson();
                    String jsonStr = response.body().string();
                    DirectionBean bean = gson.fromJson(jsonStr,DirectionBean.class);
                    LogUtils.e("bean = "+bean);
                    PolylineOptions polylineOptions = new PolylineOptions();
                    String time = "0";
                    String distance = "0";
                    for (int i = 0 ;i < bean.getRoutes().size();i++){
                        for (int j = 0;j < bean.getRoutes().get(0).getLegs().get(0).getSteps().size();j++){
                            DirectionBean.RoutesBean.LegsBean.StepsBean data = bean.getRoutes().get(0).getLegs().get(0).getSteps().get(j);
                            polylineOptions.add(new LatLng(data.getStart_location().getLat(),data.getStart_location().getLng()));

                            if (j == bean.getRoutes().get(0).getLegs().get(0).getSteps().size()-1){//吧最后一个的end位置也加进去
                                polylineOptions.add(new LatLng(data.getEnd_location().getLat(),data.getEnd_location().getLng()));
                            }
                        }
                    }
                    time = bean.getRoutes().get(0).getLegs().get(0).getDuration().getText();
                    distance = bean.getRoutes().get(0).getLegs().get(0).getDistance().getText();
                    activity.addPloylineMold(polylineOptions,time,distance);
                }
            });
    }
    //获取所有商户
    public void findByLocationAndDistanceUsingGET(LatLng latLng,Integer distance){
        if (latLng == null){
            return;
        }
        addSubscription(
                sellerControllerNoToken.findByLocationAndDistanceUsingGET(latLng,distance)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<AllSellerBean>() {
                            @Override
                            public void call(AllSellerBean allSellerBean) {
                                if (allSellerBean.getError_code() == 0){
                                    if (allSellerBean.getDataList() != null) {
                                        LogUtils.e("获取到的标记数量===" + allSellerBean.getDataList().size());
                                    }
                                    activity.initAllSellerData(allSellerBean);
                                }else{
                                    showErrorNone(allSellerBean,activity);
                                }
                            }
                        },getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                showErrorNone(bean,activity);
                            }
                        }))
        );
    }
    //商户信息
    public void profileUsingGET(Long uuid){
        addSubscription(
                sellerControllerNoToken.profileUsingGET(uuid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<SellerBean>() {
                            @Override
                            public void call(SellerBean sellerBean) {
                                if (sellerBean.getError_code() == 0){
                                    activity.initSellerData(sellerBean);
                                }else{
                                    showErrorNone(sellerBean,activity);
                                }
                            }
                        },getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                showErrorNone(bean,activity);
                            }
                        }))
        );
    }
    //获取基础费用
    public void getBasicCostUsingGET(){
        addSubscription(
                memberController.getBasicCostUsingGET()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<BasicCostBean>() {
                            @Override
                            public void call(BasicCostBean basicCostBean) {
                                if (basicCostBean.getError_code() == 0){
                                    activity.initBasicCost(basicCostBean);
                                }else{
                                    showErrorNone(basicCostBean,activity);
                                }
                            }
                        },getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                showErrorNone(bean,activity);
                            }
                        }))
        );
    }
    //获取进行中的订单
    public void findDoingInfoUsingGET(){
        LogUtils.e("获取用户状态");
        addSubscription(
                orderController.findDoingInfoUsingGET()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<OrderBean>() {
                            @Override
                            public void call(OrderBean orderBean) {
                                LogUtils.e("获取到的OrderBean="+orderBean);
                                if (orderBean == null){
                                    activity.turnToRideOver();
                                    return;
                                }
                                if (orderBean.getError_code() == 0){
                                    activity.initDoingData(orderBean);
                                }else{
                                    showErrorNone(orderBean,activity);
                                }
                            }
                        },getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                showErrorNone(bean,activity);
                            }
                        }))
        );
    }
    //购买充电宝
    public void paymetPbUsingGET(){
        addSubscription(
                memberController.paymetPbUsingGET()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<BaseBean>() {
                            @Override
                            public void call(BaseBean orderBean) {
                                LogUtils.e("购买调用结果");
                                if (orderBean.getError_code() == 0){
                                    LogUtils.e("购买调用成功");
                                    Toast.makeText(activity,activity.getString(R.string.wallet_list_purchase_success),Toast.LENGTH_SHORT).show();
                                    activity.buySuccess();
                                }else{
                                    showErrorNone(orderBean,activity);
                                }
                            }
                        },getErrorAction(new OnHttpListener() {
                            @Override
                            public void onResult(BaseBean bean) {
                                showErrorNone(bean,activity);
                            }
                        }))
        );
    }
    /**
     * 缴纳基础会员费
     *
     */
    public void paymetBasicCostUsing(final String payTpye){
        addSubscription(memberController.paymetBasicCostUsing(payTpye)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PayDetails>() {
                    @Override
                    public void call(PayDetails payDetails) {
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
                            if (payDetails != null) {
                                Toast.makeText(activity, activity.getString(R.string.pay_success), Toast.LENGTH_SHORT).show();
                                getMyData();
                            }
                            activity.hideLoading();
                        }
                    }
                }, getErrorAction(new OnHttpListener() {
                    @Override
                    public void onResult(BaseBean bean) {
                        activity.hideLoading();
                        showErrorNone(bean, activity);
                    }
                })));
    }
}
