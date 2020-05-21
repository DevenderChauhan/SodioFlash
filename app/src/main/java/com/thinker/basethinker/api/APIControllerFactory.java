package com.thinker.basethinker.api;


import android.content.Context;


import com.thinker.basethinker.api.controller.AppParamController;
import com.thinker.basethinker.api.controller.CabinetController;
import com.thinker.basethinker.api.controller.FileController;
import com.thinker.basethinker.api.controller.MemberController;
import com.thinker.basethinker.api.controller.MessageCenterController;
import com.thinker.basethinker.api.controller.MoneyController;
import com.thinker.basethinker.api.controller.OrderController;
import com.thinker.basethinker.api.controller.SellerController;
import com.thinker.basethinker.api.controller.StripeController;
import com.thinker.basethinker.api.controller.UserController;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.utils.PreferencesUtils;

import vc.thinker.colours.client.ApiClient;
import vc.thinker.colours.client.api.AppparamcontrollerApi;
import vc.thinker.colours.client.api.CabinetcontrollerApi;
import vc.thinker.colours.client.api.FileuploadcontrollerApi;
import vc.thinker.colours.client.api.MembercontrollerApi;
import vc.thinker.colours.client.api.MessagecontrollerApi;
import vc.thinker.colours.client.api.MoneycontrollerApi;
import vc.thinker.colours.client.api.OrdercontrollerApi;
import vc.thinker.colours.client.api.SellercontrollerApi;
import vc.thinker.colours.client.api.StripecontrollerApi;
import vc.thinker.colours.client.api.UsercontrollerApi;

public class APIControllerFactory {

    private static ApiClient mOauth2ApiClient = new ApiClient("oauth2-password");

    private static ApiClient mClientApiClient = new ApiClient("oauth2-clientcredentials", Config.appClientId, Config.appClientSecret);

    private static Context mContext = MyApplication.appContext;

    private APIControllerFactory() {
    }

    public static MemberController getMemberApi() {
        mOauth2ApiClient.setAccessToken(PreferencesUtils.getString(mContext, "RADISHSAAS_IS_BIND"));
        return new MemberController(mOauth2ApiClient.createService(MembercontrollerApi.class));
    }
    public static SellerController getSellerApi() {
        mOauth2ApiClient.setAccessToken(PreferencesUtils.getString(mContext, "RADISHSAAS_IS_BIND"));
        return new SellerController(mOauth2ApiClient.createService(SellercontrollerApi.class));
    }
    public static SellerController getSellerApiNoToken() {
        return new SellerController(mClientApiClient.createService(SellercontrollerApi.class));
    }
    public static MemberController getMemberApiNoToken() {
        return new MemberController(mClientApiClient.createService(MembercontrollerApi.class));
    }
    /**
     * 获取系统配置
     */
    public static AppParamController getSystemParams() {
        return new AppParamController(mClientApiClient.createService(AppparamcontrollerApi.class));
    }
    public static MessageCenterController getMessagNoToken() {
        return new MessageCenterController(mClientApiClient.createService(MessagecontrollerApi.class));
    }
    public static MessageCenterController getMessag() {
        mOauth2ApiClient.setAccessToken(PreferencesUtils.getString(mContext, "RADISHSAAS_IS_BIND"));
        return new MessageCenterController(mOauth2ApiClient.createService(MessagecontrollerApi.class));
    }
    public static UserController getUserApi() {
        mOauth2ApiClient.setAccessToken(PreferencesUtils.getString(mContext, "RADISHSAAS_IS_BIND"));
        return new UserController(mOauth2ApiClient.createService(UsercontrollerApi.class));
    }
    public static FileController getClientFileController() {
        return new FileController(mClientApiClient.createService(FileuploadcontrollerApi.class));
    }
    public static MoneyController getMoneyController() {
        mOauth2ApiClient.setAccessToken(PreferencesUtils.getString(mContext, "RADISHSAAS_IS_BIND"));
        return new MoneyController(mOauth2ApiClient.createService(MoneycontrollerApi.class));
    }
    public static CabinetController getCabinetController() {
        mOauth2ApiClient.setAccessToken(PreferencesUtils.getString(mContext, "RADISHSAAS_IS_BIND"));
        return new CabinetController(mOauth2ApiClient.createService(CabinetcontrollerApi.class));
    }
    public static StripeController getStripeController() {
        mOauth2ApiClient.setAccessToken(PreferencesUtils.getString(mContext, "RADISHSAAS_IS_BIND"));
        return new StripeController(mOauth2ApiClient.createService(StripecontrollerApi.class));
    }
    public static OrderController getOrderController() {
        mOauth2ApiClient.setAccessToken(PreferencesUtils.getString(mContext, "RADISHSAAS_IS_BIND"));
        return new OrderController(mOauth2ApiClient.createService(OrdercontrollerApi.class));
    }
}
