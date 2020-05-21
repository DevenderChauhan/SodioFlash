package com.thinker.basethinker.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.login.BindPhoneActivity;
import com.thinker.basethinker.login.LoginActivity;
import com.thinker.basethinker.mvp.ControllerActivity;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;

import java.util.HashMap;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by thinker on 17/12/11.
 */

public class AcountBindActivity extends MvpActivity<AcountBindPresenter,MvpView> implements MvpView, View.OnClickListener, Handler.Callback, PlatformActionListener {
    private AcountBindPresenter myPresenter;
    @Override
    protected AcountBindPresenter CreatePresenter() {
        return myPresenter = new AcountBindPresenter(this);
    }
    private ImageView head_left;
    private TextView wx_bind;
    private TextView facebook_bind;
    private TextView head_title;
    private LinearLayout ll_facebook,ll_wechat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this,getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_acount_bing);
        initView();
        myPresenter.getMyData();
        showLoading();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initView() {
        facebook_bind = (TextView) findViewById(R.id.facebook_bind);
        wx_bind = (TextView) findViewById(R.id.wx_bind);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_title = (TextView) findViewById(R.id.head_title);
        ll_facebook = (LinearLayout) findViewById(R.id.ll_facebook);
        ll_wechat = (LinearLayout) findViewById(R.id.ll_wechat);
        head_title.setText(getString(R.string.acount_bind));
        head_left.setOnClickListener(this);
        wx_bind.setOnClickListener(this);
        facebook_bind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                finish();
                break;
            case R.id.wx_bind:
                //20180420 易小梅需求
//                showLoading();
//                if (wx_bind.isSelected()){
//                    myPresenter.unbundledThirdPartyUsingPOST("weixin");
//                }else{
//                    authorize(ShareSDK.getPlatform(Wechat.NAME),true);
//                }
                break;
            case R.id.facebook_bind:
//                showLoading();
//                if (facebook_bind.isSelected()){
//                    myPresenter.unbundledThirdPartyUsingPOST("facebook");
//                }else{
//                    authorize(ShareSDK.getPlatform(Facebook.NAME),true);
//                }
                break;
            default:
                break;
        }
    }
    // 授权登录
    private void authorize(Platform plat, Boolean isSSO) {
        // 判断指定平台是否已经完成授权
//        if (plat.isAuthValid()) {
//            // 已经完成授权，直接读取本地授权信息，执行相关逻辑操作（如登录操作）
//            String userId = plat.getDb().getUserId();
//            if (!TextUtils.isEmpty(userId)) {
//                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
//                login(plat);
//                return;
//            }
//        }
        plat.setPlatformActionListener(this);
        // 是否使用SSO授权：true不使用，false使用
        plat.SSOSetting(isSSO);
        // 获取用户资料
        plat.showUser(null);
    }
    // 取消授权
    private void cancleAuth(){
        Platform wxPlatform = ShareSDK.getPlatform(Wechat.NAME);
//        wxPlatform.removeAccount();
        Toast.makeText(this,getString(R.string.cancel_success), Toast.LENGTH_SHORT).show();
    }

    // 回调：授权成功
    public void onComplete(Platform platform, int action,HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            login(platform);
        }
    }
    // 回调：授权失败
    public void onError(Platform platform, int action, Throwable t) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        t.printStackTrace();
    }
    // 回调：授权取消
    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }
    // 业务逻辑：登录处理
    private void login(Platform platform) {
        String token = platform.getDb().getToken(); // token
        String userId	= platform.getDb().getUserId();	  // 用户Id
        String platName = platform.getName();			  // 平台名称
        Log.d("farley","token="+token + ";userId="+userId+";platName="+platName);
        String type = "";
        switch (platName){
            case "Wechat":
                type = "weixin";
                break;
            case "Facebook":
                type = "facebook";
                break;
        }
        myPresenter.boundThirdPartyUsingPOST(userId,token,type);
//        Message msg = new Message();
//        msg.what    = MSG_LOGIN;
//        msg.obj     = platName;
//        UIHandler.sendMessage(msg, this);
    }

    // 统一消息处理
    private static final int MSG_USERID_FOUND 	= 1; // 用户信息已存在
    private static final int MSG_LOGIN 			= 2; // 登录操作
    private static final int MSG_AUTH_CANCEL 	= 3; // 授权取消
    private static final int MSG_AUTH_ERROR 	= 4; // 授权错误
    private static final int MSG_AUTH_COMPLETE 	= 5; // 授权完成

    public boolean handleMessage(Message msg) {
        switch (msg.what) {

            case MSG_USERID_FOUND:
                Toast.makeText(this, getString(R.string.user_exist), Toast.LENGTH_SHORT).show();
                break;
            case MSG_LOGIN:
                if (msg.obj.equals("Wechat")) {
                    Toast.makeText(this, getString(R.string.wechat_login), Toast.LENGTH_SHORT).show();
                }else if (msg.obj.equals("Facebook")){

                }
                break;
            case MSG_AUTH_CANCEL:
                hideLoading();
                Toast.makeText(this, getString(R.string.auth_cancel), Toast.LENGTH_SHORT).show();
                break;
            case MSG_AUTH_ERROR:
                hideLoading();
                Toast.makeText(this, getString(R.string.auth_error), Toast.LENGTH_SHORT).show();
                break;
            case MSG_AUTH_COMPLETE:
                Toast.makeText(this,getString(R.string.auth_success), Toast.LENGTH_SHORT).show();
                // 执行相关业务逻辑操作，比如登录操作
                String userName = new Wechat().getDb().getUserName(); // 用户昵称
                String userId	= new Wechat().getDb().getUserId();   // 用户Id
                String platName = new Wechat().getName();			   // 平台名称

//                login(platName, userId, null);
                break;
        }
        return false;
    }


    public void initData(PersonalBean personalBean) {
        if (personalBean.getBindWeixin()){
            wx_bind.setSelected(true);
            ll_wechat.setSelected(true);
            wx_bind.setText(getString(R.string.unbind));
        }else{
            ll_wechat.setSelected(false);
            wx_bind.setSelected(false);
            wx_bind.setText(getString(R.string.bind));
        }
        if (personalBean.getBindFasebook()){
            facebook_bind.setSelected(true);
            ll_facebook.setSelected(true);
            facebook_bind.setText(getString(R.string.unbind));
        }else{
            facebook_bind.setSelected(false);
            ll_facebook.setSelected(false);
            facebook_bind.setText(getString(R.string.bind));
        }
    }
}
