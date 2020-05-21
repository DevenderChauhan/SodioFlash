package com.thinker.basethinker.scan;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.thinker.basethinker.MainActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.dialog.StanderdDialog;
import com.thinker.basethinker.eventbus.MessageEvent;
import com.thinker.basethinker.login.LoginActivity;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.rideover.RideOverActivity;
import com.thinker.basethinker.service.RideStatusService;
import com.thinker.basethinker.service.RideStatusUtils;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.PreferencesUtils;
import com.thinker.basethinker.views.OpenLockLoadingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by thinker on 17/12/22.
 */

public class LoadingActivity extends Activity implements Observer {
    private OpenLockLoadingView openLoading;
    private TextView progressNum;
    private RideStatusService.MyBinder myBinder;
    private RideStatusService rideStatusService;
    private RideStatusUtils rideStatusUtils;
    private Observable observableForSevice;
    private boolean isRegistService = false;//是否启动服务
    private boolean isFount = false;//当前界面的状态

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (RideStatusService.MyBinder) service;
            myBinder.startCheckStatus();
            //返回一个MsgService对象
            rideStatusService = ((RideStatusService.MyBinder) service).getService();
            rideStatusUtils = new RideStatusUtils(rideStatusService);
            observableForSevice = rideStatusUtils;
            observableForSevice.addObserver(LoadingActivity.this);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this, getResources().getColor(R.color.translate));
        setContentView(R.layout.activity_loading);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFount = true;
        EventBus.getDefault().register(this);
    }

    private void initView() {
        openLoading = findViewById(R.id.openLoading);
        progressNum = findViewById(R.id.progressNum);
        openLoading.startOpen();
        openLoading.setOnResultReturnLisener(new OpenLockLoadingView.OnResultReturnLisener() {
            @Override
            public void onResult(int result) {
                LogUtils.e("result=" + result);
                progressNum.setText(getString(R.string.loading) + result+"%");
            }
        });
        startListener();
    }
    //开启服务监听
    private void startListener() {
        if (!isRegistService) {
            //开始轮询服务
            Intent startIntent = new Intent(this, RideStatusService.class);
            startService(startIntent);
            //把服务于这个activity绑定
            Intent bindIntent = new Intent(this, RideStatusService.class);
            bindService(bindIntent, connection, BIND_AUTO_CREATE);
            isRegistService = true;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFount) {
                    failedOpen();
                }
            }
        }, 50000);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof RideStatusUtils) {
            RideStatusUtils utils = (RideStatusUtils) o;
            OrderBean orderBean = utils.getOrderBean();
            if (orderBean == null) {
                return;
            }
            int orderStatus = orderBean.getStatus();//获取当前订单的状态
            LogUtils.e("开锁的时候轮询当前订单的状态=" + orderStatus);
            switch (orderStatus) {
                case 10://创建中

                    break;
                case 20://创建失败
                    if (isFount) {
                        failedOpen();
                    }
                    break;
                case 30://进行中
                    openLoading.setItOver();
                    progressNum.setText(getString(R.string.load_success));
                    finish();
                    break;
                case 40://未支付
                    break;
                case 50://已支付
                    break;
            }
        }
    }
    private void colseLisner(){
        if (isRegistService) {
            if (myBinder != null){
                myBinder.stopCheckStatus();
            }
            isRegistService = false;
            unbindService(connection);
            Intent stopIntent = new Intent(LoadingActivity.this, RideStatusService.class);
            stopService(stopIntent);
        }
    }
    public void failedOpen() {
        openLoading.setItOver();
        progressNum.setText(getString(R.string.load_title));
        StanderdDialog dialog = new StanderdDialog(this, getString(R.string.load_title),
                getString(R.string.load_desc), getString(R.string.load_sure), getString(R.string.load_cancel)
                , new StanderdDialog.OnDialogClickListener() {
            @Override
            public void doAnyClick() {
                colseLisner();
                finish();
            }

            @Override
            public void doMainClick() {
                openLoading.setReset();
                startListener();
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }
    //收到监听
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyEvent(MessageEvent event){
        if (event == null){
            return;
        }
        if (event.getReceiverBean() == null){
            return;
        }
        switch (event.getReceiverBean().getMsgType()){
            case "order_create":
                LogUtils.e("收到消息，订单创建成功");
                openLoading.setItOver();
                progressNum.setText(getString(R.string.load_success));
                finish();
                break;
            case  "99999":
                showLoginError(event.getReceiverBean().getContent());
                break;
        }
    }
    private void showLoginError(String result){
        StanderdDialog dialog = new StanderdDialog(LoadingActivity.this, result, getString(R.string.feedback_toast5),
                new StanderdDialog.OnDialogClickListener() {
                    @Override
                    public void doAnyClick() {
                    }

                    @Override
                    public void doMainClick() {
                        JPushInterface.setAlias(LoadingActivity.this, "11", new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                LogUtils.e("注销jpush======" + s);
                            }
                        });
                        String token = "";
                        PreferencesUtils.putString(LoadingActivity.this,"RADISHSAAS_IS_BIND",token);
                        MyApplication.setIsLoginStatus(true);
                        startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                        finish();
                    }
                });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }
    @Override
    public void onBackPressed() {
        //什么也不做
    }

    @Override
    protected void onPause() {
        colseLisner();
        isFount = false;
        observableForSevice.deleteObserver(this);
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
