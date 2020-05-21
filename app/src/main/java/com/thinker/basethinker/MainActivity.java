package com.thinker.basethinker;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.stripe.android.model.Source;
import com.stripe.android.model.SourceCardData;
import com.stripe.android.view.PaymentMethodsActivity;
import com.thinker.basethinker.api.Config;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.bean.AllSellerBean;
import com.thinker.basethinker.bean.BasicCostBean;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.bean.SellerBean;
import com.thinker.basethinker.bean.SystemParamsBean;
import com.thinker.basethinker.bestdeals.BestDealsActivity;
import com.thinker.basethinker.bottomlayout.BottomToolsFragment;
import com.thinker.basethinker.bottomlayout.SellerDetailFragment;
import com.thinker.basethinker.dialog.GeneralMemberDialog;
import com.thinker.basethinker.dialog.StanderdDialog;
import com.thinker.basethinker.eventbus.MainMsgEvent;
import com.thinker.basethinker.eventbus.MessageEvent;
import com.thinker.basethinker.feedback.FeedBackActivity;
import com.thinker.basethinker.googleutils.GoogleMapUtils;
import com.thinker.basethinker.login.LoginActivity;
import com.thinker.basethinker.luckywheel.LuckyWheelActivity;
import com.thinker.basethinker.member.RechartMemberActivity;
import com.thinker.basethinker.messagecenter.MessageCenterActivity;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.ControllerActivity;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.myoffer.MyOfferActivity;
import com.thinker.basethinker.nearbyseller.NearBySellerActivity;
import com.thinker.basethinker.nearbyseller.SellerDetailActivity;
import com.thinker.basethinker.nearbyseller.adapter.NearSellerAdapter;
import com.thinker.basethinker.orderlist.MyStrokeActivity;
import com.thinker.basethinker.personal.ModifySyscodeActivity;
import com.thinker.basethinker.personal.PersonalActivity;
import com.thinker.basethinker.rechange.RechangeActivity;
import com.thinker.basethinker.rideover.RideOverActivity;
import com.thinker.basethinker.scan.LoadingActivity;
import com.thinker.basethinker.scan.ScanActivity;
import com.thinker.basethinker.scan.SelectTypeActivity;
import com.thinker.basethinker.service.RideStatusService;
import com.thinker.basethinker.service.RideStatusUtils;
import com.thinker.basethinker.set.SetActivity;
import com.thinker.basethinker.stripe.StripeUtils;
import com.thinker.basethinker.toplayout.BuyBoxDialog;
import com.thinker.basethinker.toplayout.UsingFragment;
import com.thinker.basethinker.userguide.UserGuideActivity;
import com.thinker.basethinker.utils.ErrorDialogHandler;
import com.thinker.basethinker.utils.LogUpToKiBaNA;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.PreferencesUtils;
import com.thinker.basethinker.utils.Utils;
import com.thinker.basethinker.utils.autoupdata.PyUtils;
import com.thinker.basethinker.wallet.DepositActivity;
import com.thinker.basethinker.wallet.WalletActiity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.thinker.basethinker.stripe.StripeUtils.REQUEST_CODE_SELECT_SOURCE;

public class MainActivity extends MvpActivity<MainPresenter, IMainView> implements IMainView, View.OnClickListener, OnMapReadyCallback, Observer {
    private DrawerLayout drawerLayout;
    private LinearLayout menu_xml;
    private LinearLayout my_offer;
    private LinearLayout wallet;
    private LinearLayout order;
    private LinearLayout mySet;
    private LinearLayout ll_vip;
    private LinearLayout ll_rechange;
    private LinearLayout vip_ll;
    private LinearLayout ll_phone;
    private LinearLayout ll_help;
    private LinearLayout ll_shop;
    private LinearLayout ll_lucky;
    private LinearLayout ll_prom;
    private TextView nickNameText;
    private TextView vip_text;
    private TextView txt_vip_desc;
    private ImageView vip_img;
    private ImageView person;
    private ImageView nav_message;
    private ImageView nav_seach;
    private CircleImageView icon_my;

    private GoogleMap googleMap;
    private BottomToolsFragment bottomToolsFragment;//底部菜单
    private SellerDetailFragment sellerDetailFragment;//商户信息
    private UsingFragment usingFragment;//用户当前的订单在使用中
    private GoogleMapUtils googleMapUtils;
    private LatLng startLatLng = new LatLng(0,0);//地图画线的起始位置
    private LatLng endLayLng;//地图画线的起始位置
    private Marker currentSlelctedMarker;//选中的标记
    private static final int PLACE_PICKER_REQUEST = 121;//选取地址
    private static final int SCAN_CODE = 122;//扫码
    private static final int TO_BORROW = 123;//去借
    private static final int NEAR_SELLER = 125;//商户列表
    private static final int BORROW_SUCCESS = 124;//借成功了
    private static final int GOOGLE_READ_PHONE_STATE = 126;//权限申请
    private static final int SDCARD_PREMISSION = 127;//权限申请
    private AllSellerBean allSellerBean;//所有商户信息
    private RelativeLayout needle;//中间位置的大头针
    private SellerBean selelctedSellerBean;//被选中的商户信息

    //监听状态服务
    private RideStatusService.MyBinder myBinder;
    private RideStatusService rideStatusService;
    private RideStatusUtils rideStatusUtils;
    private Observable observableForSevice;
    private boolean isRegistService = false;//是否启动服务
    private String isInUsing = "1";//用户当前是否在使用中1 一般问题 2 行程中 3 已完成用户
    private List<String> servicePhone;
    private boolean haveFeedback = false;//有问题反馈的时候 不现实图标 不加载附近商户
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
            observableForSevice.addObserver(MainActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.getScreenWithAndHeight(this);//获取屏幕的尺寸
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
//                != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(getApplicationContext(),getString(R.string.premiss_file),Toast.LENGTH_SHORT).show();
//            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
//            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
//                    }, SDCARD_PREMISSION);
//        }else{
        Utils.canUpdata(this);//控制是否强制更新
//        }


        myPresenter.getAdvImages();
        myPresenter.getSystemParams();
        myPresenter.getInvateAndShareParams();
        if (MyApplication.isIsLoginStatus()) {
            myPresenter.getHomeMessage();
        }
        initView();
        bottomToolsFragment = new BottomToolsFragment();
        MyUtils.showFragment(this, true, bottomToolsFragment);//加载底部菜单

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initGoogleMap() {
        googleMapUtils = new GoogleMapUtils(this, googleMap);
        googleMapUtils.setMyUiSetting();
        googleMapUtils.createLocationRequest();//设置请求
        googleMapUtils.setMyLocationClient();//设置定位更新准则
        googleMapUtils.startLocationUpdates();//开始定位
    }


    @Override
    protected void onResume() {
        super.onResume();
        isOpenRideOver = false;//支付又可以点击了
        if (MyApplication.isIsLoginStatus()) {
            myPresenter.getMyData();
            myPresenter.getBasicCostUsingGET();
            myPresenter.findDoingInfoUsingGET();//获取用户的当前订单状态
        }
        if (googleMapUtils != null) {
            googleMapUtils.startLocationUpdates();
        }
        if (observableForSevice != null) {
            observableForSevice.addObserver(MainActivity.this);
        }
        if (drawerLayout != null){
            if (MyApplication.isIsLoginStatus()) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }else{
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        }
        initDespostConfig();
    }
    //加载是否有押金了
    private void initDespostConfig() {
        PersonalBean personalBean = MyUtils.getPersonData();
        if (personalBean != null && personalBean.getDeposit() != null && personalBean.getDeposit() > 0){
            isHaveDesponse = true;
        }else{
            isHaveDesponse = false;
        }
    }

    private MainPresenter myPresenter;

    @Override
    protected MainPresenter CreatePresenter() {
        return myPresenter = new MainPresenter(this);
    }

    private void initView() {
        icon_my = (CircleImageView) findViewById(R.id.icon_my);
        order = (LinearLayout) findViewById(R.id.order);
        wallet = (LinearLayout) findViewById(R.id.wallet);
        my_offer = (LinearLayout) findViewById(R.id.my_offer);
        menu_xml = (LinearLayout) findViewById(R.id.menu_xml);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mySet = (LinearLayout) findViewById(R.id.mySet);
        nickNameText = (TextView)findViewById(R.id.nickName);
        person =(ImageView) findViewById(R.id.person);
        nav_message = (ImageView)findViewById(R.id.nav_message);
        nav_seach = (ImageView)findViewById(R.id.nav_seach);
        needle = (RelativeLayout) findViewById(R.id.needle);
        vip_text = (TextView) findViewById(R.id.vip_text);
        vip_img = (ImageView)findViewById(R.id.vip_img);
        vip_ll = (LinearLayout) findViewById(R.id.vip_ll);
        ll_vip = (LinearLayout)findViewById(R.id.ll_vip);
        ll_help = (LinearLayout)findViewById(R.id.ll_help);
        ll_rechange = (LinearLayout)findViewById(R.id.ll_rechange);
        ll_phone = (LinearLayout)findViewById(R.id.ll_phone);
        ll_shop = (LinearLayout)findViewById(R.id.ll_shop);
        ll_lucky = (LinearLayout)findViewById(R.id.ll_lucky);
        ll_prom = (LinearLayout)findViewById(R.id.ll_prom);
        txt_vip_desc = (TextView) findViewById(R.id.txt_vip_desc);

        ll_shop.setOnClickListener(this);
        ll_lucky.setOnClickListener(this);
        ll_prom.setOnClickListener(this);
        ll_phone.setOnClickListener(this);
        mySet.setOnClickListener(this);
        ll_rechange.setOnClickListener(this);
        icon_my.setOnClickListener(this);
        my_offer.setOnClickListener(this);
        wallet.setOnClickListener(this);
        order.setOnClickListener(this);
        vip_ll.setOnClickListener(this);
        ll_vip.setOnClickListener(this);
        person.setOnClickListener(this);
        nav_message.setOnClickListener(this);
        nav_seach.setOnClickListener(this);
        ll_help.setOnClickListener(this);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_seach:
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(MainActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.nav_message:
                if (MyApplication.isIsLoginStatus()) {
                    startActivity(new Intent(MainActivity.this, MessageCenterActivity.class));
                } else {
//                    showErrorNone(new BaseBean(getString(R.string.error_login),-10),this);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

                break;
            case R.id.mySet:
                startActivity(new Intent(MainActivity.this, SetActivity.class));
                break;
            case R.id.person:
                if (MyApplication.isIsLoginStatus()) {
                    drawerLayout.openDrawer(GravityCompat.START);
                } else {
//                    showErrorNone(new BaseBean(getString(R.string.error_login),-10),this);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

                break;
            case R.id.icon_my:
                if (MyApplication.isIsLoginStatus()) {
                    startActivity(new Intent(MainActivity.this, PersonalActivity.class));
                } else {
//                    showErrorNone(new BaseBean(getString(R.string.error_login),-10),this);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                break;
            case R.id.my_offer:
                startActivity(new Intent(MainActivity.this, MyOfferActivity.class));
                break;
            case R.id.wallet:
                startActivity(new Intent(MainActivity.this, WalletActiity.class));
                break;
            case R.id.order:
                startActivity(new Intent(MainActivity.this, MyStrokeActivity.class));
                break;
            case R.id.vip_ll:
                if (isBasicPay){
                    startActivity(new Intent(MainActivity.this, RechartMemberActivity.class));
                }else {
                    showMemberDialog();//调用缴纳会员费功能
                }
                break;
            case R.id.ll_vip:
                if (isBasicPay){
                    startActivity(new Intent(MainActivity.this, RechartMemberActivity.class));
                }else {
                    showMemberDialog();//调用缴纳会员费功能
                }
                break;
            case R.id.ll_rechange:
                startActivity(new Intent(MainActivity.this, RechangeActivity.class));
                break;
            case R.id.ll_help:
                startActivity(new Intent(MainActivity.this, UserGuideActivity.class));
                break;
            case R.id.ll_phone:
                Utils.playPhoneForList(this, servicePhone);
                break;
            case R.id.ll_shop:
                Intent shop = new Intent(MainActivity.this, BestDealsActivity.class);
                startActivity(shop);
                break;
            case R.id.ll_lucky:
                Intent lucky = new Intent(MainActivity.this, LuckyWheelActivity.class);
                startActivity(lucky);
                break;
            case R.id.ll_prom:
                Intent ss = new Intent(MainActivity.this, ModifySyscodeActivity.class);
                startActivityForResult(ss, 104);
                break;
            default:
                break;
        }
    }
    private boolean isBasicPay = false;//是否缴纳了基础会员费
    private boolean isHaveDesponse = false;//是否缴纳了押金
    //设置个人资料
    public void setPersonData(PersonalBean personalBean) {
//        icon_my.setImageDrawable(getDrawable(R.drawable.portrait));
        if (!TextUtils.isEmpty(personalBean.getHeadImgPath())) {
            Glide.with(this).load(personalBean.getHeadImgPath()).into(icon_my);
        }
        nickNameText.setText(personalBean.getNickname());
        if (personalBean.getPayBasicCost()) {//是否缴纳了基础会员费
            isBasicPay = true;
            if (personalBean.getVIP()) {
                vip_text.setSelected(true);
                vip_img.setSelected(true);
                if (personalBean.getVipExpiresIn() != null) {
                    String remainDate = personalBean.getVipDxpireDateDesc();
                    Long remainDateLong = personalBean.getVipExpiresIn().getTime();
                    vip_text.setText(getString(R.string.vip_date) + " " + Utils.stampToDate3(personalBean.getVipExpiresIn().getTime()));
                }
                vip_ll.setSelected(true);
            } else {
                vip_text.setSelected(false);
                vip_img.setSelected(false);
                vip_text.setText(getString(R.string.person_vip));
                vip_ll.setSelected(false);
            }
            txt_vip_desc.setVisibility(View.INVISIBLE);
        }else{
            isBasicPay = false;
            vip_text.setSelected(false);
            vip_img.setSelected(false);
            vip_text.setText(getString(R.string.person_basic_vip));
            vip_ll.setSelected(false);
            txt_vip_desc.setVisibility(View.VISIBLE);
        }


    }

    private long firstTime = 0;//就第一次点击的时间

    @Override
    public void onBackPressed() {
        //按两次退出
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(this, getString(R.string.exit_main), Toast.LENGTH_SHORT).show();
            firstTime = secondTime;//更新firstTime
        } else {
            ControllerActivity.finishAll();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }


    //收到监听
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyEvent(MainMsgEvent event) {
        switch (event.getCode()) {
            case 0://大头针的位置
                //如果移动距离大于500 则自动刷新
                LogUtils.e("haveFeedback===" + haveFeedback);
                if (!haveFeedback) {//如果有问题反馈 就不去搜附近的商户了
                    float distance = MyUtils.twoLatlngDistance(startLatLng, event.getLatLng());
                    LogUtils.e("移动距离distance=" + distance);
                    if (distance > 500) {
                        if (bottomToolsFragment != null) {
                            mapCenterLatlng = event.getLatLng();
                            bottomToolsFragment.setRefreshStart();
                        }
                    }

                    startLatLng = event.getLatLng();
                }
                break;
            case 1://用户选择的位置
                needle.setVisibility(View.GONE);//选择商户之后 隐藏中间图标
                googleMapUtils.deleteStartPointMarkers();//删除起点marker
                googleMapUtils.addStartPointerMarkers(startLatLng);
                if (currentSlelctedMarker != null) {//把上个给恢复
                    currentSlelctedMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.battery_position_n));
                }
                currentSlelctedMarker = event.getmMarker();
                endLayLng = currentSlelctedMarker.getPosition();
                myPresenter.getMapDerections(startLatLng.latitude + "," + startLatLng.longitude, endLayLng.latitude + "," + endLayLng.longitude);
                //选择商户后 显示商户详情
                int thePosition = event.getPosition();
                selelctedSellerBean = allSellerBean.getDataList().get(thePosition);
                break;
            case 2://第一次定位后给到
                LatLng currentLocation = event.getLatLng();
                googleMapUtils.deleteMarkersToMap();
                if (!haveFeedback) {//如果有问题反馈 就不去搜附近的商户了
//                    myPresenter.findByLocationAndDistanceUsingGET(currentLocation, 5000);
                }
                break;
            case 3://地图点击事件
                if (currentSlelctedMarker != null) {
                    currentSlelctedMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.battery_position_n));
                }
                needle.setVisibility(View.VISIBLE);//点击之后 显示中间图标
                googleMapUtils.deleteStartPointMarkers();//删除起点marker
                if (polyline == null) {//如果有线 那就删除
                    return;
                }
                polyline.remove();
                MyUtils.showFragment(this, true, bottomToolsFragment);//加载底部菜单
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {//地址位置选择后返回

            final Place place = PlacePicker.getPlace(this, data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            LogUtils.e("name=" + name + ";mAddress=" + address + ";attributions=" + place.getLatLng().toString());
            CameraPosition mPositont = new CameraPosition.Builder().target(place.getLatLng())
                    .zoom(15.5f)
                    .bearing(0)
                    .tilt(25)
                    .build();
            googleMapUtils.changeCamera(true, CameraUpdateFactory.newCameraPosition(mPositont), null);
        }
        if (requestCode == SCAN_CODE//扫描后返回
                && resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra(CodeUtils.RESULT_STRING);
//            LogUtils.e("code="+ result);
//            String[] codes = result.split("=");
//            if (codes.length < 2){
//                return;
//            }else{
//                Intent select = new Intent(MainActivity.this, SelectTypeActivity.class);
//                select.putExtra("CODE",codes[1]);
//                startActivityForResult(select,TO_BORROW);
//            }
            Intent select = new Intent(MainActivity.this, SelectTypeActivity.class);
            select.putExtra("CODE", result);
            startActivityForResult(select, TO_BORROW);
        }
        if (requestCode == NEAR_SELLER//附近的商户
                && resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra("CODE");
            LogUtils.e("code=" + result);
            Intent select = new Intent(MainActivity.this, SelectTypeActivity.class);
            select.putExtra("CODE", result);
            startActivityForResult(select, TO_BORROW);
        }
        if (requestCode == TO_BORROW//选择后返回
                && resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra("BORROW");
            LogUtils.e("orderCode=" + result);
            startActivityForResult(new Intent(MainActivity.this, LoadingActivity.class), BORROW_SUCCESS);
        }
        if (requestCode == BORROW_SUCCESS//借成功后返回
                && resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra("BORROW");
            LogUtils.e("orderCode=" + result);
            startActivityForResult(new Intent(MainActivity.this, LoadingActivity.class), BORROW_SUCCESS);
        }
        if (requestCode == REQUEST_CODE_SELECT_SOURCE && resultCode == RESULT_OK) {
            String selectedSource = data.getStringExtra(PaymentMethodsActivity.EXTRA_SELECTED_PAYMENT);
            Source source = Source.fromString(selectedSource);
            // Note: it isn't possible for a null or non-card source to be returned.
            if (source != null && Source.CARD.equals(source.getType())) {
                SourceCardData cardData = (SourceCardData) source.getSourceTypeModel();
                showLoading();
                myPresenter.paymetBasicCostUsing(MyUtils.PAY_TYPE_STRIPE);
            }

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), getString(R.string.premiss_location), Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE}, GOOGLE_READ_PHONE_STATE);
        } else {
            initGoogleMap();
        }
    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case GOOGLE_READ_PHONE_STATE:
                if (grantResults.length < 1) {
                    return;
                }
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    initGoogleMap();
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), getString(R.string.premiss_location), Toast.LENGTH_SHORT).show();
                }
                break;
            case SDCARD_PREMISSION:
//                if (grantResults.length < 1){
//                    return;
//                }
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Utils.canUpdata(this);//控制是否强制更新
//                } else {
//                    Toast.makeText(getApplicationContext(), getString(R.string.premiss_location), Toast.LENGTH_SHORT).show();
//                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleMapUtils != null) {
            googleMapUtils.stopLocationUpdates();
        }
        if (observableForSevice != null) {
            observableForSevice.deleteObserver(this);
        }
    }

    //定位自己的位置
    @Override
    public void setMyLocation() {
//        googleMapUtils.moveToMyLocation();
        if (googleMapUtils != null) {
            googleMapUtils.setMyLocationClient();
        }
    }

    //刷新所有标记
    private LatLng mapCenterLatlng;

    @Override
    public void setRefresh() {
        if (googleMapUtils != null) {
            if (!haveFeedback) {//如果有问题反馈 就不去搜附近的商户了
                googleMapUtils.deleteMarkersToMap();
                LogUtils.e("Config.mapSearchScope:"+Config.mapSearchScope);
                myPresenter.findByLocationAndDistanceUsingGET(mapCenterLatlng, Config.mapSearchScope);
            }
        }
    }

    //扫码
    @Override
    public void scanLock() {//
        if (MyApplication.isIsLoginStatus()) {
//            if (isHaveDesponse) {20180530根据胡杨要求修改
                startActivityForResult(new Intent(MainActivity.this, ScanActivity.class), SCAN_CODE);
//            }else{
//                noHaveDespost(new BaseBean(getString(R.string.error_str9),409),this);
//            }
        } else {
//            showErrorNone(new BaseBean(getString(R.string.error_login),-10),this);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

    }

    //附近的商户
    @Override
    public void getNearSeller() {
        startActivityForResult(new Intent(MainActivity.this, NearBySellerActivity.class), NEAR_SELLER);
    }

    //设置客服电话
    public void setServicePhone(SystemParamsBean systemParamsBean) {
        servicePhone = systemParamsBean.getContactUs();
    }

    //问题反馈    isInUsing 1 一般问题 2 行程中 3 已完成用户
    @Override
    public void feedback() {
        if (MyApplication.isIsLoginStatus()) {
            String orderCode = PreferencesUtils.getString(this, "CURRENTORDERCODE");
            Intent it = new Intent(MainActivity.this, FeedBackActivity.class);
            it.putExtra("TYPE", isInUsing);
            it.putExtra("TRIPID", orderCode);
            startActivity(it);
        } else {
//            showErrorNone(new BaseBean(getString(R.string.error_login),-10),this);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    //详情
    @Override
    public void sellerDetail(Long uuid) {
        Intent it = new Intent(MainActivity.this, SellerDetailActivity.class);
        it.putExtra("UUID", uuid);
        startActivityForResult(it, NEAR_SELLER);
    }
    //  购买box
    @Override
    public void buyBoxFun() {
        BuyBoxDialog dialog = new BuyBoxDialog( MainActivity.this, new BuyBoxDialog.OnDialogClickListener() {
            @Override
            public void onClick() {
                myPresenter.paymetPbUsingGET();
            }
        });
        dialog.show();
    }

    private Polyline polyline;//路线图

    public void addPloylineMold(final PolylineOptions lineOptions, final String time, final String distance) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lineOptions.width(8);
                lineOptions.color(Color.BLUE);
                if (polyline != null) {
                    polyline.remove();
                }
                polyline = googleMap.addPolyline(lineOptions);
                Double latZong = 0d;
                Double lonZong = 0d;
                for (LatLng latLng : lineOptions.getPoints()) {
                    latZong += latLng.latitude;
                    lonZong += latLng.longitude;
                }
                double centerLat = latZong;
                double centerLon = lonZong;
                if (lineOptions.getPoints().size() > 0) {
                    centerLat = latZong / lineOptions.getPoints().size();
                    centerLon = lonZong / lineOptions.getPoints().size();
                }
                LatLng mCenterLatlng = new LatLng(centerLat, centerLon);
                googleMapUtils.changeCamera(true, CameraUpdateFactory.newLatLngZoom(mCenterLatlng, 15.0f), null);
//                currentSlelctedMarker = googleMap.addMarker(new MarkerOptions()
//                        .snippet(distance)
//                        .position(currentSlelctedMarker.getPosition())
//                        .title(time)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.battery_position_s))
//                );
                currentSlelctedMarker.setSnippet(distance);
                currentSlelctedMarker.setTitle(time);
                currentSlelctedMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.battery_position_s));
                currentSlelctedMarker.showInfoWindow();

                //去获取商户详细信息
                myPresenter.profileUsingGET(selelctedSellerBean.getUid());
            }
        });

    }

    //加载所有商户数据
    public void initAllSellerData(AllSellerBean allSellerBean) {
        this.allSellerBean = allSellerBean;
        MyUtils.savaSellerData(allSellerBean);
        googleMapUtils.addMarkersToMap(allSellerBean);//添加标记
    }

    public void initSellerData(SellerBean sellerBean) {
        sellerDetailFragment = new SellerDetailFragment(sellerBean);
        MyUtils.showFragment(MainActivity.this, true, sellerDetailFragment);
    }

    //查询进行中的订单
    public void initDoingData(OrderBean orderBean) {
        if (orderBean.getStatus() == null) {
            haveFeedback = false;
            MyUtils.initAskFragment(this, false);
            if (bottomToolsFragment != null) {
                bottomToolsFragment.isFeedbackIng(true);
            }
            isInUsing = "1";//当前在使用中，控制问题返回的问题列表
            return;
        }
        int orderStatus = orderBean.getStatus();//获取当前订单的状态
        LogUtils.e("当前订单的状态=" + orderStatus);
        switch (orderStatus) {
            case 10://创建中
                break;
            case 20://创建失败
                break;
            case 30://进行中

                isInUsing = "2";//当前在使用中，控制问题返回的问题列表

                MyUtils.showUsingFragment(MainActivity.this, true, orderBean);
                startListener();
                if (orderBean.getDoingFeedbacks() != null && orderBean.getDoingFeedbacks().size() > 0) {
                    MyUtils.initAskFragment(this, true);
                    haveFeedback = true;
                    googleMap.clear();
                    if (bottomToolsFragment != null) {
                        bottomToolsFragment.isFeedbackIng(false);
                    }
                    return;
                } else {
                    haveFeedback = false;
                    if (bottomToolsFragment != null) {
                        bottomToolsFragment.isFeedbackIng(true);
                    }
                    MyUtils.initAskFragment(this, false);
                }
                if (bottomToolsFragment != null) {
                    bottomToolsFragment.setUsingGone();
                }
                break;
            case 40://未支付
                haveFeedback = false;
                MyUtils.initAskFragment(this, false);//隐藏问题反馈提示框
                isInUsing = "3";//当前在使用中，控制问题返回的问题列表
                PreferencesUtils.putString(this, "CURRENTORDERCODE", null);
                strokePay();
                break;
            case 50://已支付
                isInUsing = "1";//当前在使用中，控制问题返回的问题列表
                break;
        }
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
    }

    private void colseLisner() {
        if (isRegistService) {
            if (myBinder != null) {
                myBinder.stopCheckStatus();
            }
            isRegistService = false;
            unbindService(connection);
            Intent stopIntent = new Intent(MainActivity.this, RideStatusService.class);
            stopService(stopIntent);
        }
    }

    public void turnToRideOver() {
        if (bottomToolsFragment != null) {//如果没有进行中的订单 那就显示扫描
            bottomToolsFragment.isFeedbackIng(true);
        }
        String orderCode = PreferencesUtils.getString(this, "CURRENTORDERCODE");
        LogUtils.e("bena是空的，上个订单号是=" + orderCode);
        if (!TextUtils.isEmpty(orderCode)) {
            PreferencesUtils.putString(this, "CURRENTORDERCODE", null);
            MyUtils.showUsingFragment(MainActivity.this, false, null);//如果使用中界面还在 就去除
            Intent it = new Intent(MainActivity.this, RideOverActivity.class);
            it.putExtra("ORDERCODE", orderCode);
            startActivity(it);
        }
    }
    public void buySuccess(){
        colseLisner();
        PreferencesUtils.putString(this, "CURRENTORDERCODE", null);
        MyUtils.showUsingFragment(MainActivity.this, false, null);
        if (bottomToolsFragment != null) {//如果没有进行中的订单 那就显示扫描
            bottomToolsFragment.isFeedbackIng(true);
        }
    }
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof RideStatusUtils) {
            RideStatusUtils utils = (RideStatusUtils) o;
            OrderBean orderBean = utils.getOrderBean();
            if (orderBean == null) {//如果订单为空 则去判断之前是否有订单 有就去看详情
                haveFeedback = false;
                MyUtils.initAskFragment(this, false);
                colseLisner();
                turnToRideOver();
                isInUsing = "1";//当前在使用中，控制问题返回的问题列表
                return;
            }
            int orderStatus = orderBean.getStatus();//获取当前订单的状态
            LogUtils.e("订单状态的轮询=" + orderStatus);
            switch (orderStatus) {
                case 10://创建中
                    break;
                case 20://创建失败
                    break;
                case 30://进行中
                    if (orderBean.getDoingFeedbacks() != null && orderBean.getDoingFeedbacks().size() > 0) {
                        MyUtils.initAskFragment(this, true);
                        haveFeedback = true;
                        return;
                    } else {
                        haveFeedback = false;
                        MyUtils.initAskFragment(this, false);
                    }
                    isInUsing = "2";//当前在使用中，控制问题返回的问题列表
                    PreferencesUtils.putString(this, "CURRENTORDERCODE", orderBean.getOrderCode());
                    EventBus.getDefault().post(new MessageEvent(0, orderBean));
                    break;
                case 40://未支付
                    haveFeedback = false;
                    MyUtils.initAskFragment(this, false);//隐藏问题反馈提示框
                    isInUsing = "3";//当前在使用中，控制问题返回的问题列表
                    PreferencesUtils.putString(this, "CURRENTORDERCODE", null);
                    MyUtils.showUsingFragment(MainActivity.this, false, orderBean);
                    strokePay();
                    break;
                case 50://已支付
                    isInUsing = "1";//当前在使用中，控制问题返回的问题列表
                    break;
            }
        }
    }

    /**
     * 行程支付
     */
    private boolean isOpenRideOver = false;//控制跳转支付的提示
    private StanderdDialog standerdDialog;

    private void strokePay() {
        if (!isOpenRideOver) {
            if (standerdDialog == null) {
                standerdDialog = new StanderdDialog(MainActivity.this, getString(R.string.toast_23),
                        getString(R.string.toast_24), getString(R.string.toast_25), getString(R.string.toast_26)
                        , new StanderdDialog.OnDialogClickListener() {
                    @Override
                    public void doAnyClick() {
                        isOpenRideOver = true;
                    }

                    @Override
                    public void doMainClick() {
                        startActivity(new Intent(MainActivity.this, RideOverActivity.class));
                    }
                });
                standerdDialog.show();
            } else {
                standerdDialog.show();
            }
        }
    }
//    private void showErrorNone(final BaseBean bean, final Activity activity){
//        final StanderdDialog dialog = new StanderdDialog(activity, bean.getResult(), activity.getString(R.string.feedback_toast5),
//                new StanderdDialog.OnDialogClickListener() {
//                    @Override
//                    public void doAnyClick() {
//                    }
//
//                    @Override
//                    public void doMainClick() {
//                        if (bean.getError_code() == -10){
//                            JPushInterface.setAlias(activity, "11", new TagAliasCallback() {
//                                @Override
//                                public void gotResult(int i, String s, Set<String> set) {
//                                    LogUtils.e("注销jpush======" + s);
//                                }
//                            });
//                            String token = "";
//                            PreferencesUtils.putString(activity,"RADISHSAAS_IS_BIND",token);
//                            MyApplication.setIsLoginStatus(true);
//                            activity.startActivity(new Intent(activity, LoginActivity.class));
//                        }
//                        if (bean.getError_code() == 409){
//                            activity.startActivity(new Intent(activity, DepositActivity.class));
//                        }
//                    }
//                });
//        dialog.show();
//        dialog.setCanceledOnTouchOutside(false);
//    }

    //获取谷歌线路图失败
    public void setGetGooglePlineFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "google connect error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private BasicCostBean basicCost;
    //弹出缴纳基础会员费
    private void showMemberDialog(){
        GeneralMemberDialog dialog = new GeneralMemberDialog(basicCost, this, new GeneralMemberDialog.OnDialogClickListener() {
            @Override
            public void onClick() {
                StripeUtils stripeUtils = new StripeUtils(MainActivity.this, new StripeUtils.onMyPayLisener() {
                    @Override
                    public void onpay() {
                        showLoading();
                        myPresenter.paymetBasicCostUsing(MyUtils.PAY_TYPE_STRIPE);
                    }
                });
                stripeUtils.initCustomer();

            }
        });
        dialog.show();
    }

    public void initBasicCost(BasicCostBean basicCostBean) {
        basicCost = basicCostBean;
    }
    public void noHaveDespost(final BaseBean bean, final Activity activity){
        final StanderdDialog dialog = new StanderdDialog(activity, bean.getResult(), activity.getString(R.string.feedback_toast5),
                new StanderdDialog.OnDialogClickListener() {
                    @Override
                    public void doAnyClick() {
                    }

                    @Override
                    public void doMainClick() {
                        if (bean.getError_code() == 409){
                            activity.startActivity(new Intent(activity, WalletActiity.class));
                        }
                    }
                });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }
}
