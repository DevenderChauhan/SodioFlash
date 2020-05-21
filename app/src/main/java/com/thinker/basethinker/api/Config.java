package com.thinker.basethinker.api;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;


import com.stripe.android.PaymentConfiguration;
import com.thinker.basethinker.MainActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.PreferencesUtils;
import com.thinker.basethinker.utils.Utils;
import com.thinker.basethinker.utils.autoupdata.PyUtils;

import vc.thinker.colours.client.ApiClient;

/**
 * Created by farley on 17/3/13.
 * description:后台api的配置
 */

public class Config {
    public static final String wxAppId = "wx76e6724a56b2d819";
    public static final String wxAppSecret = "03fb5d7cfd2851d091a8e595ec076b39";
    public static final String APPID="5c25f8cb2a653a2f5df391b496a7f517";// 蒲公英上传应用appId
    public static final String _api_key="b38fe152743f3459cb999e246d10ebb1";// 蒲公英上传应用appId apikey
    public static final  String appClientId="3000001";
    public static final  String appClientSecret="92aa5479e9b3fc62c4f7faff7d415310";
    public static String ADV_IMAGE_MD5 = "START";//广告的md5
    public static String ADV_IMAGE_DATA = "ADV_IMAGE_DATA";//广告配置
    public static String MYSTRIPEKEY = "pk_test_0XPHg4i5moVoZMXOsFDMCAYm";//stripe public key  测试
//    public static String MYSTRIPEKEY = "pk_live_yKBGm4iQC4qAd52ZMcuvn8bK";//stripe public key  正式
    public static String Google_Maps_Directions_API_KEY = "AIzaSyChyJWST4GBOdFU08KqGVgHm3suWkMmYgk";//google 路线key
    public static int mapSearchScope = 5000;
    public static enum Mode{devMode,testMode,releaseMode}
    private static final String TOKEN_URL = "http://nomo.oauth.thinker.vc/oauth/token";
//    private static final String TOKEN_URL_TEST = "http://nomo.oauth.thinker.vc/oauth/token";
    private static final String TOKEN_URL_TEST = "http://nomotest.oauth.thinker.vc/oauth/token";
    private static final String TOKEN_URL_DEV = "http://nomotest.api.thinker.vc/oauth/token";

    private static final String API_URL = "http://nomo.api.thinker.vc/";
//    private static final String API_URL_TEST = "http://nomo.api.thinker.vc/";
    private static final String API_URL_TEST = "http://nomotest.api.thinker.vc/";//李南芝的测试部署
    private static final String API_URL_DEV = "http://nomotest.api.thinker.vc/";
    private static Mode myMode = Mode.testMode;//当前模式
    public static void init(Context context){
        PaymentConfiguration.init(MYSTRIPEKEY);
        ApiClient.userAgent = Utils.getAppMsg(context);
        ApiClient.language = Utils.getLauguage(context);
        LogUtils.e("ApiClient.language="+ApiClient.language);
        switch (myMode){
            case devMode:
                ApiClient.tokenUrl = TOKEN_URL_DEV;
                ApiClient.baseUrl = API_URL_DEV;
                break;
            case testMode:
                ApiClient.tokenUrl = TOKEN_URL_TEST;
                ApiClient.baseUrl = API_URL_TEST;
                break;
            case releaseMode:
                ApiClient.tokenUrl = TOKEN_URL;
                ApiClient.baseUrl = API_URL;
                break;
            default:
                break;

        }
        getAdvImageMd5();
    }


    //是否更新
   public static String getAdvImageMd5() {
        ADV_IMAGE_MD5 = PreferencesUtils.getString(MyApplication.appContext,"ADV_IMAGE_MD5");
        if (TextUtils.isEmpty(ADV_IMAGE_MD5)){
            ADV_IMAGE_MD5 = "1";
        }
        return ADV_IMAGE_MD5;
    }

    public static void setAdvImageMd5(String advImageMd5) {
        ADV_IMAGE_MD5 = advImageMd5;
        PreferencesUtils.putString(MyApplication.appContext,"ADV_IMAGE_MD5",ADV_IMAGE_MD5);
    }

    public static Mode getMyMode() {
        return myMode;
    }
}
