package com.thinker.basethinker.login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;
import com.thinker.basethinker.MainActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.personal.PersonalActivity;
import com.thinker.basethinker.utils.LogUpToKiBaNA;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.PreferencesUtils;
import com.thinker.basethinker.utils.Utils;

import java.util.HashMap;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import vc.thinker.colours.client.ApiClient;

/**
 * Created by thinker on 17/12/7.
 */

public class LoginActivity extends MvpActivity<LoginPresenter,MvpView> implements MvpView, View.OnClickListener,Handler.Callback,PlatformActionListener {
    private LoginPresenter myPresenter;
    @Override
    protected LoginPresenter CreatePresenter() {
        return myPresenter = new LoginPresenter(this);
    }
    private Button login_regist;
    private Button login_account;
    private ImageView back;//退出
    private TextView areaCode;
    private static int CHOOSECOUNTRYCODE = 1001;//选择国家
    private EditText phoneNum;
    private EditText login_pwd;
    private LinearLayout wechat,facebook;
    private TextView forgotPaw;
    private String countryCode = "65";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this,getResources().getColor(R.color.color_theme));
        setContentView(R.layout.activity_login);
        initView();
        String logMessage = "Token:";
        String myToken = PreferencesUtils.getString(this,"RADISHSAAS_IS_BIND");
        String tokentype = PreferencesUtils.getString(this,"RADISHSAAS_IS_BIND_LOGOUTTYPE");
        if (myToken == null){
            logMessage = logMessage + "noToken";
        }else {
            logMessage = logMessage + "myToken";
        }
        new LogUpToKiBaNA(this,logMessage+";"+tokentype,"verbose",336).start();
    }

    private void initView() {
        wechat = (LinearLayout) findViewById(R.id.wechat);
        facebook = (LinearLayout) findViewById(R.id.facebook);
        back = (ImageView) findViewById(R.id.back);
        forgotPaw = (TextView) findViewById(R.id.forgotPaw);
        areaCode = (TextView) findViewById(R.id.areaCode);
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        login_pwd = (EditText) findViewById(R.id.login_pwd);
        login_pwd.setTypeface(Typeface.DEFAULT);
        login_pwd.setTransformationMethod(new PasswordTransformationMethod());
        login_account = (Button) findViewById(R.id.login_account);
        login_regist = (Button) findViewById(R.id.login_regist);
        login_regist.setOnClickListener(this);
        login_account.setOnClickListener(this);
        back.setOnClickListener(this);
        areaCode.setOnClickListener(this);
        wechat.setOnClickListener(this);
        facebook.setOnClickListener(this);
        forgotPaw.setOnClickListener(this);
        login_account.setEnabled(false);
        String codeStr = PreferencesUtils.getString(this,"COUNTRYCODE_SAVE",countryCode);
        if (!TextUtils.isEmpty(codeStr)){
            countryCode = codeStr;
            areaCode.setText("+"+codeStr);
        }
        login_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = login_pwd.getText().toString();
                if (str.length() > 5){
                    login_account.setEnabled(true);
                }else{
                    login_account.setEnabled(false);
                }
            }
        });
        String rePhone = PreferencesUtils.getString(MyApplication.appContext,"USER_PHONE");
        if (!TextUtils.isEmpty(rePhone)){
            phoneNum.setText(rePhone);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.areaCode:
                startActivityForResult(new Intent(this,ChooseCodeActivity.class),CHOOSECOUNTRYCODE);
                break;
            case R.id.login_account:
                String phone = phoneNum.getText().toString();
                if (!Utils.isPhoneNum(phone)){
                    phoneNum.setError(getString(R.string.phone_tips));
                    return;
                }
                String pawstr = login_pwd.getText().toString();
                if (!Utils.validPwdMy(pawstr)){
                    login_pwd.setError(getString(R.string.paw_tips_my));
                    return;
                }
                PreferencesUtils.putString(MyApplication.appContext,"USER_PHONE",phone);
                String upPhone = countryCode + "-" +phone ;
                showLoading();
                login_account.setEnabled(false);
                LogUtils.e("ApiClient.language="+ ApiClient.language);
                myPresenter.loginService(upPhone,pawstr);
                break;
            case R.id.facebook:
                showLoading();
                authorize(ShareSDK.getPlatform(Facebook.NAME),true);
                break;
            case R.id.wechat:
                showLoading();
                authorize(ShareSDK.getPlatform(Wechat.NAME),true);
//                wechat();
                break;
            case R.id.login_regist:
                startActivity(new Intent(this,RegistActivity.class));
                break;
            case R.id.forgotPaw:
                startActivity(new Intent(this,ForgotActivity.class));
                break;
//            case R.id.login:
//            myPresenter.getAuth("18737193721");
//                myPresenter.loginService("18737193721","123456");
//                break;
//            case R.id.wxlogin:
//                authorize(ShareSDK.getPlatform(Facebook.NAME),true);
//                break;
            default:
                break;
        }
    }

    public void turnToMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
    public void turnBind() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                startActivity(new Intent(LoginActivity.this, BindPhoneActivity.class));
                startActivity(new Intent(LoginActivity.this, AddInfoActivity.class));
//                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideLoading();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == CHOOSECOUNTRYCODE && data != null){
                String code = data.getStringExtra("COUNTRYCODE");
                countryCode = code;
                PreferencesUtils.putString(this,"COUNTRYCODE_SAVE",countryCode);
                areaCode.setText("+"+code);
            }
        }
    }

    // 授权登录
    private void authorize(Platform plat, Boolean isSSO) {
        // 判断指定平台是否已经完成授权
        /*if (plat.isAuthValid()) {
            // 已经完成授权，直接读取本地授权信息，执行相关逻辑操作（如登录操作）
            String userId = plat.getDb().getUserId();
            if (!TextUtils.isEmpty(userId)) {
//                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                login(plat);
                return;
            }
        }*/
        if (plat.isAuthValid()){
            plat.removeAccount(true);
        }
        plat.setPlatformActionListener(this);
        // 是否使用SSO授权：true不使用，false使用
        plat.SSOSetting(isSSO);
        // 获取用户资料
        plat.showUser(null);
        LogUtils.e("开始认证");
    }
    // 取消授权
    private void cancleAuth(){
        hideLoading();
        LogUtils.e("取消授权");
        Platform wxPlatform = ShareSDK.getPlatform(Wechat.NAME);
//        wxPlatform.removeAccount();
        Toast.makeText(this,getString(R.string.cancel_success), Toast.LENGTH_SHORT).show();
    }

    // 回调：授权成功
    public void onComplete(final Platform platform, int action, HashMap<String, Object> res) {
        LogUtils.e("授权成功");
        hideLoading();
        if (action == Platform.ACTION_USER_INFOR) {
//            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    login(platform);
                }
            });

        }
    }
    // 回调：授权失败
    public void onError(Platform platform, int action, Throwable t) {
        LogUtils.e("授权失败");
        hideLoading();
//        if (action == Platform.ACTION_USER_INFOR) {
//            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
//        }
        t.printStackTrace();
    }
    // 回调：授权取消
    public void onCancel(Platform platform, int action) {
        LogUtils.e("授权取消");
        hideLoading();
//        if (action == Platform.ACTION_USER_INFOR) {
//            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
//        }
    }
    // 业务逻辑：登录处理
    private void login(Platform platform) {
        LogUtils.e("登录处理");
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
        myPresenter.thirdPartyLoginUsingPOST(userId,token,type);
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


    public void loginSuccess() {
        hideLoading();
        login_account.setEnabled(true);
    }

    public void loginFailed() {
        hideLoading();
        login_account.setEnabled(true);
    }

}
