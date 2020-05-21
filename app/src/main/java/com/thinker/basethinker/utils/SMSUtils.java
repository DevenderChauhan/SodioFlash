package com.thinker.basethinker.utils;

import android.app.Activity;

import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by thinker on 17/12/27.
 */

public class SMSUtils {
    //获取验证码
    public static void getAuthCode(String country,String phone){
        SMSSDK.getVerificationCode(country, phone, new OnSendMessageHandler() {
            @Override
            public boolean onSendMessage(String s, String s1) {
                return false;
            }
        });
    }
    //获取短信支持的国家
    public void getSupportedCountries(){
        SMSSDK.getSupportedCountries();
    }
    //验证
    public void submitVerificationCode(String country, String phone, String code){
        SMSSDK.submitVerificationCode(country,phone,code);
    }
}
