package com.thinker.basethinker.login;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.thinker.basethinker.MainActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.api.Config;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.bean.SmsErrorBean;
import com.thinker.basethinker.eventbus.MessageEvent;
import com.thinker.basethinker.mvp.ControllerActivity;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.utils.CountDownTimerUtils;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyRegistCountDown;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.PreferencesUtils;
import com.thinker.basethinker.utils.Utils;
import com.thinker.basethinker.utils.WebViewActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by thinker on 17/12/8.
 */

public class RegistActivity extends MvpActivity<RegistPresenter, MvpView> implements MvpView, View.OnClickListener {
    private RegistPresenter myPresenter;

    @Override
    protected RegistPresenter CreatePresenter() {
        return myPresenter = new RegistPresenter(this);
    }

    private ImageView head_left;
    private TextView head_title;
    private TextView areaCode;
    private static int CHOOSECOUNTRYCODE = 1001;//选择国家
    private EditText phoneNum;
    private EditText regist_pwd;
    private EditText login_auth;
    private Button regist;
    private TextView getAuthBtn;
    private String countryCode = "65";
    private ImageView pawShow;
    private boolean isMychecked = false;
    private TextView registRules;
    private EventHandler eventHandler;
    private EditText edt_medCode;//充电柜编码
    private LinearLayout ll_bind_syscode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this, getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_regist_modify_forgot);
        initView();

        // 创建EventHandler对象
        eventHandler=new EventHandler(){

            @Override
            public void afterEvent(final int event, final int result, final Object data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.e("result="+result);
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            //回调完成
                            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                                LogUtils.e("提交验证码成功="+result+";"+data.toString());
                                String phonestr = phoneNum.getText().toString().trim();
                                String loginstr = login_auth.getText().toString().trim();
                                String registstr = regist_pwd.getText().toString().trim();
                                String syscode = edt_medCode.getText().toString().trim();
                                myPresenter.regist(phonestr, countryCode, loginstr, registstr,syscode);
                                //提交验证码成功
                            }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                                //获取验证码成功
                                LogUtils.e("获取验证码成功="+result+";"+data.toString());

                                Toast.makeText(RegistActivity.this,getString(R.string.getauthsuccess),Toast.LENGTH_SHORT).show();
                            }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                                //返回支持发送验证码的国家列表
                                LogUtils.e("返回支持发送验证码的国家列表="+result+";"+data.toString());
                            }
                        }else{
                            ((Throwable)data).printStackTrace();
                            hideLoading();
                            regist.setEnabled(true);
                            Gson gson = new Gson();
                            SmsErrorBean smsErrorBean = gson.fromJson(((Throwable) data).getMessage(),SmsErrorBean.class);
                            if (smsErrorBean == null){
                                Toast.makeText(RegistActivity.this,"Error",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(RegistActivity.this," "+smsErrorBean.getDetail(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        };

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }

    private void initView() {
        registRules = (TextView)findViewById(R.id.registRules);
        regist = (Button) findViewById(R.id.regist);
        getAuthBtn = (TextView) findViewById(R.id.getAuthBtn);
        areaCode = (TextView) findViewById(R.id.areaCode);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_title = (TextView) findViewById(R.id.head_title);
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        regist_pwd = (EditText) findViewById(R.id.regist_pwd);
        regist_pwd.setTypeface(Typeface.DEFAULT);
        regist_pwd.setTransformationMethod(new PasswordTransformationMethod());
        login_auth = (EditText) findViewById(R.id.login_auth);
        edt_medCode = (EditText) findViewById(R.id.edt_medCode);
        pawShow = (ImageView) findViewById(R.id.pawShow);
        ll_bind_syscode = (LinearLayout) findViewById(R.id.ll_bind_syscode);
        ll_bind_syscode.setVisibility(View.VISIBLE);
        head_title.setText(getString(R.string.regist_title));
        head_left.setOnClickListener(this);
        pawShow.setOnClickListener(this);
        areaCode.setOnClickListener(this);
        regist.setOnClickListener(this);
        getAuthBtn.setOnClickListener(this);
        regist.setEnabled(false);
        String codeStr = PreferencesUtils.getString(this,"COUNTRYCODE_SAVE",countryCode);
        if (!TextUtils.isEmpty(codeStr)){
            countryCode = codeStr;
            areaCode.setText("+"+codeStr);
        }
        regist_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = regist_pwd.getText().toString();
                if (str.length() > 5) {
                    regist.setEnabled(true);
                } else {
                    regist.setEnabled(false);
                }
            }
        });
        registRules.setOnClickListener(this);
        String rePhone = PreferencesUtils.getString(MyApplication.appContext,"USER_PHONE");
        if (!TextUtils.isEmpty(rePhone)){
            phoneNum.setText(rePhone);
        }
        Long countTimes = PreferencesUtils.getLong(this, "COUNT_TIMES", 0);
        if (countTimes > 0) {
            getAuthBtn.setEnabled(false);
            MyRegistCountDown countDownTimerUtils = new MyRegistCountDown(countTimes*1000, 1000,
                    this, getAuthBtn);
            countDownTimerUtils.start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.getAuthBtn:
                String phone = phoneNum.getText().toString().trim();
                if (!Utils.isPhoneNum(phone)) {
                    phoneNum.setError(getString(R.string.phone_tips));
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, getString(R.string.pawisempty), Toast.LENGTH_SHORT).show();
                } else {
                    PreferencesUtils.putString(MyApplication.appContext,"USER_PHONE",phone);
                    getAuthBtn.setEnabled(false);
                    getAuthSuccess();
                    SMSSDK.getVerificationCode(countryCode, phone, new OnSendMessageHandler() {
                        @Override
                        public boolean onSendMessage(String s, String s1) {
                            return false;
                        }
                    });
                }
                break;
            case R.id.regist:
                String phonestr = phoneNum.getText().toString().trim();
                String loginstr = login_auth.getText().toString().trim();
                String registstr = regist_pwd.getText().toString().trim();
                String syscode = edt_medCode.getText().toString().trim();
                if (!Utils.isPhoneNum(phonestr)) {
                    phoneNum.setError(getString(R.string.phone_tips));
                    return;
                }
                if (!Utils.validPwdMy(registstr)) {
                    regist_pwd.setError(getString(R.string.paw_tips_my));
                    return;
                }
                if (TextUtils.isEmpty(loginstr)) {
                    Toast.makeText(this, getString(R.string.authisempty), Toast.LENGTH_SHORT).show();
                } else {
                    showLoading();
                    regist.setEnabled(false);
                   if (Config.getMyMode() == Config.Mode.testMode){
                       myPresenter.regist(phonestr, countryCode, loginstr, registstr,syscode);
                   }else if (Config.getMyMode() == Config.Mode.releaseMode){
                       SMSSDK.submitVerificationCode(countryCode,phonestr,loginstr);
                   }

//
                }
                break;
            case R.id.areaCode:
                startActivityForResult(new Intent(this, ChooseCodeActivity.class), CHOOSECOUNTRYCODE);
                break;
            case R.id.pawShow:
                if (!isMychecked){
                    isMychecked = true;
                    pawShow.setSelected(true);
                    regist_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());  //密码以明文显示
                }else{
                    isMychecked = false;
                    pawShow.setSelected(false);
                    regist_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());  //以密文显示，以.代替
                }
                break;
            case R.id.registRules:
                Intent webview = new Intent(this, WebViewActivity.class);
                webview.putExtra("TITLE", getString(R.string.regist_desc_red));
                webview.putExtra("VIEWURL", "share/use_car_rules");
                webview.putExtra("NEEDTOKEN",false);
                startActivity(webview);
                break;
            default:
                break;
        }
    }

    public void getAuthSuccess() {
        MyRegistCountDown countDownTimerUtils = new MyRegistCountDown(60000, 1000,
                this, getAuthBtn);
        countDownTimerUtils.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CHOOSECOUNTRYCODE && data != null) {
                String code = data.getStringExtra("COUNTRYCODE");
                countryCode = code;
                PreferencesUtils.putString(this,"COUNTRYCODE_SAVE",countryCode);
                areaCode.setText("+" + code);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyEvent(MessageEvent event) {
        switch (event.getCode()) {
            case 0:
                getAuthBtn.setEnabled(true);
                getAuthBtn.setText(getString(R.string.get_auth));
                break;
        }
    }

    public void registSuccess() {
        String phonestr = phoneNum.getText().toString().trim();
        String loginstr = login_auth.getText().toString().trim();
        String registstr = regist_pwd.getText().toString().trim();
        myPresenter.loginService( countryCode+"-"+phonestr, registstr);
    }

    public void turnToMain() {
        startActivity(new Intent(RegistActivity.this, MainActivity.class));
        ControllerActivity.finishAll();
    }

    public void setRegistcomplete() {
        hideLoading();
        regist.setEnabled(true);
    }
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
