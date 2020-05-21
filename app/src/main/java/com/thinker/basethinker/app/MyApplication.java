package com.thinker.basethinker.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.mob.MobApplication;
import com.pgyersdk.crash.PgyCrashManager;
import com.stripe.android.PaymentConfiguration;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.thinker.basethinker.api.Config;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.PreferencesUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;


/**
 * Created by farley on 17/5/15.
 * description:application
 */

public class MyApplication extends MobApplication {
    public static Context appContext;
    public static IWXAPI wxApi;
    private static boolean isLoginStatus = false;//默认没有登录
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        //注册微信
        wxApi = WXAPIFactory.createWXAPI(this, Config.wxAppId);
        wxApi.registerApp(Config.wxAppId);
        //API初始化
        Config.init(getApplicationContext());
        Config.getAdvImageMd5();
        //sharesdk初始化
//        ShareSDK.initSDK(this);
        //初始化二维码工具类
        ZXingLibrary.initDisplayOpinion(this);
        PgyCrashManager.register(this,Config.APPID);
        LogUtils.e("MyApplication-onCreate");
        initJiguang();
    }
    /**
     * 极光推送的初始化
     */
    private void initJiguang() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
    public static Context getIntance(){
        return  appContext;
    }
    @Override
    public void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }
    public static boolean isIsLoginStatus() {
        LogUtils.e("isIsLoginStatus"+isLoginStatus);
        return isLoginStatus;
    }

    public static void setIsLoginStatus(boolean isLoginStatus) {
        LogUtils.e("setIsLoginStatus"+isLoginStatus);
        MyApplication.isLoginStatus = isLoginStatus;
    }
}
