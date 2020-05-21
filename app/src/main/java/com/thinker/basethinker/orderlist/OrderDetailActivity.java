package com.thinker.basethinker.orderlist;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.googleutils.GoogleMapUtils;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by thinker on 17/12/26.
 */

public class OrderDetailActivity extends MvpActivity<OrderDetailPresenter,MvpView> implements MvpView, View.OnClickListener, OnMapReadyCallback {
    private OrderDetailPresenter myPresenter;
    @Override
    protected OrderDetailPresenter CreatePresenter() {
        return myPresenter = new OrderDetailPresenter(this);
    }
    private ImageView head_left;
    private TextView head_title;
    private TextView userName;
    private String orderCodeStr;
    private TextView use_time;
    private TextView use_money;
    private TextView borrow_time;
    private TextView borrow_address;
    private TextView return_time;
    private TextView return_address;
    private TextView orderCode;
    private TextView price_utils;
    private CircleImageView icon_my;
    private GoogleMap googleMap;
    private GoogleMapUtils googleMapUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        orderCodeStr = getIntent().getStringExtra("ORDERCODE");
        myPresenter.profileUsingGET(orderCodeStr);
        initView();

    }

    private void initView() {
        head_left = (ImageView) findViewById(R.id.head_left);
        head_title = (TextView)findViewById(R.id.head_title);
        userName = (TextView)findViewById(R.id.userName);
        icon_my = (CircleImageView) findViewById(R.id.icon_my);
        use_time = (TextView)findViewById(R.id.use_time);
        use_money = (TextView)findViewById(R.id.use_money);
        borrow_time = (TextView)findViewById(R.id.borrow_time);
        borrow_address = (TextView)findViewById(R.id.borrow_address);
        return_time = (TextView)findViewById(R.id.return_time);
        return_address = (TextView)findViewById(R.id.return_address);
        orderCode = (TextView)findViewById(R.id.orderCode);
        price_utils = (TextView)findViewById(R.id.price_utils);

        head_title.setText(getString(R.string.orderDetail_title));
        head_left.setOnClickListener(this);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                finish();
                break;
        }
    }

    public void initData(OrderBean orderBean) {
        PersonalBean bean = MyUtils.getPersonData();
        userName.setText(Utils.object2String(bean.getNickname()));
        if(!TextUtils.isEmpty(bean.getHeadImgPath())) {
            Glide.with(this).load(bean.getHeadImgPath()).into(icon_my);
        }
        use_time.setText(Utils.object2String(orderBean.getRideTime()));
        use_money.setText(Utils.object2String(orderBean.getPrice()));
        price_utils.setText(getString(R.string.orderDetail_price)+"("+Utils.object2String(orderBean.getCurrency())+")");
        borrow_time.setText(getString(R.string.orderDetail_borrow_time)+Utils.stampToDate2(orderBean.getBeginTime().getTime()));
        if (orderBean.getBeginLocationDetails() != null) {
            borrow_address.setText(getString(R.string.orderDetail_borrow_address) + Utils.object2String(orderBean.getBeginLocationDetails()));
        }else{
            borrow_address.setText(getString(R.string.orderDetail_borrow_address));
        }
        return_time.setText(getString(R.string.orderDetail_return_time)+Utils.stampToDate2(orderBean.getFinishTime().getTime()));
        if (orderBean.getEndLocaitonDetails() != null) {
            return_address.setText(getString(R.string.orderDetail_return_address) + Utils.object2String(orderBean.getEndLocaitonDetails()));
        }else{
            return_address.setText(getString(R.string.orderDetail_return_address));
        }
        orderCode.setText(getString(R.string.orderDetail_order_code)+Utils.object2String(orderBean.getOrderCode()));

        googleMapUtils.addMarkersInOrderDetail(orderBean);//添加开始和结束的标记
        googleMapUtils.moveToOrderCenter(orderBean);//移动到中间
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        initGoogleMap();
    }
    private void initGoogleMap() {
        googleMapUtils = new GoogleMapUtils(this, googleMap);
        googleMapUtils.setMyUiSetting();
    }

}
