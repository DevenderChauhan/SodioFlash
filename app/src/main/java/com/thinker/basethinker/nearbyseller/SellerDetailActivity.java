package com.thinker.basethinker.nearbyseller;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.SellerController;
import com.thinker.basethinker.bean.AllSellerBean;
import com.thinker.basethinker.bean.SellerBean;
import com.thinker.basethinker.bean.SystemParamsBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.scan.ScanActivity;
import com.thinker.basethinker.utils.ErrorDialogHandler;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thinker on 17/12/26.
 */

public class SellerDetailActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView head_left;
    private TextView head_title;
    private ViewPager viewPage;
    private List<String> images = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private Long UUID ;
    private LinearLayout ll_viewCount;
    private TextView sellerName;
    private TextView type1Num;
    private TextView type2Num;
    private TextView type3Num;
    private TextView returnNum;
    private TextView sellerAddress;
    private TextView sellerTime;
    private TextView text_scan;
    private TextView sellerPhone;
    private LinearLayout scan_Code;
    private LinearLayout ll_navi;
    private static final int SCAN_CODE = 122;//扫码
    private SellerController sellerController = APIControllerFactory.getSellerApiNoToken();
    private SellerBean sellerBean;
    private ErrorDialogHandler errorDialogHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this, getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_seller_detail);
        UUID = getIntent().getLongExtra("UUID", 0);
        errorDialogHandler = new ErrorDialogHandler(getFragmentManager());
        initView();
        profileUsingGET(UUID);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initData(SellerBean sellerBean) {
        this.sellerBean = sellerBean;
        images = sellerBean.getSellerCover();
        for (int i = 0; i < images.size(); i++) {
            View view = new View(this);
            view.setBackground(getDrawable(R.drawable.seller_detail_view_bg));
            LinearLayout.LayoutParams tvLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            // 设置 margin
            tvLayoutParams.width = 20;
            tvLayoutParams.height = 20;
            tvLayoutParams.leftMargin = 20;
            view.setLayoutParams(tvLayoutParams);
            views.add(view);
            ll_viewCount.addView(view);
        }
        viewPage.setAdapter(new SimpleFragmentAdapter(getSupportFragmentManager()));
        if (views.size() > 0) {
            views.get(0).setSelected(true);
        }
        sellerName.setText(Utils.object2String(sellerBean.getSellerName()));
        if (sellerBean.getBatteryType2Count() < 1) {
            type1Num.setSelected(true);
        } else {
            type1Num.setSelected(false);
        }
        if (sellerBean.getBatteryType3Count() < 1) {
            type2Num.setSelected(true);
        } else {
            type2Num.setSelected(false);
        }
        if (sellerBean.getBatteryType4Count() < 1) {
            type3Num.setSelected(true);
        } else {
            type3Num.setSelected(false);
        }
        type1Num.setText(Utils.object2String(sellerBean.getBatteryType2Count()) +" "+ getString(R.string.seller_msg_borrow));
        type2Num.setText(Utils.object2String(sellerBean.getBatteryType3Count()) +" "+ getString(R.string.seller_msg_borrow));
        type3Num.setText(Utils.object2String(sellerBean.getBatteryType4Count()) +" "+ getString(R.string.seller_msg_borrow));
        returnNum.setText(Utils.object2String(sellerBean.getIdlePositionNum()));
        sellerTime.setText(Utils.object2String(sellerBean.getServiceTime()));
//        SystemParamsBean systemParamsBean =MyUtils.getSystemData();
//        String servicePhone = systemParamsBean.getContactMobile();
        sellerPhone.setText(Utils.object2String(sellerBean.getContactMobile()));//易晓梅
        sellerAddress.setText(Utils.object2String(sellerBean.getLocationAddress() + sellerBean.getLocationDesc()));
    }

    private void initView() {
        head_left = (ImageView) findViewById(R.id.head_left);
        head_title = (TextView)findViewById(R.id.head_title);
        viewPage = (ViewPager) findViewById(R.id.viewPage);
        ll_viewCount = (LinearLayout) findViewById(R.id.ll_viewCount);
        sellerName = (TextView)findViewById(R.id.sellerName);
        type1Num = (TextView)findViewById(R.id.type1Num);
        type2Num = (TextView)findViewById(R.id.type2Num);
        type3Num = (TextView)findViewById(R.id.type3Num);
        returnNum = (TextView)findViewById(R.id.returnNum);
        sellerPhone = (TextView)findViewById(R.id.sellerPhone);
        sellerAddress = (TextView)findViewById(R.id.sellerAddress);
        sellerTime = (TextView)findViewById(R.id.sellerTime);
        scan_Code = (LinearLayout) findViewById(R.id.scan_Code);
        text_scan = (TextView)findViewById(R.id.text_scan);
        ll_navi = (LinearLayout) findViewById(R.id.ll_navi);

        head_title.setText(getString(R.string.seller_detail_title));
        head_left.setOnClickListener(this);
        scan_Code.setOnClickListener(this);
        sellerPhone.setOnClickListener(this);
        ll_navi.setOnClickListener(this);
        viewPage.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.scan_Code:
                startActivityForResult(new Intent(SellerDetailActivity.this, ScanActivity.class), SCAN_CODE);
                break;
            case R.id.sellerPhone:
                String sellerPhonestr = sellerPhone.getText().toString().trim();
                Utils.playPhone(this,sellerPhonestr);
                break;
            case R.id.ll_navi:
                if (sellerBean != null){
                    MyUtils.turnToGoogleMap(this,sellerBean.getLocationLat(),sellerBean.getLocationLon());
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCAN_CODE//扫描后返回
                && resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra(CodeUtils.RESULT_STRING);
            LogUtils.e("code=" + result);
            Intent it = new Intent();
            it.putExtra("CODE", result);
            setResult(RESULT_OK, it);
            finish();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        LogUtils.e("position=" + position);
        for (View view : views) {
            view.setSelected(false);
        }
        views.get(position).setSelected(true);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class SimpleFragmentAdapter extends FragmentPagerAdapter {
        public SimpleFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ImageFragment(images.get(position));
        }

        @Override
        public int getCount() {
            return images.size();
        }
    }

    //商户信息
    public void profileUsingGET(Long uuid) {
        sellerController.profileUsingGET(uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SellerBean>() {
                               @Override
                               public void call(SellerBean sellerBean) {
                                   if (sellerBean.getError_code() == 0) {
//                                           activity.initSellerData(sellerBean);
                                       initData(sellerBean);
                                   } else {
                                       errorDialogHandler.showError(sellerBean.getError_code(),sellerBean.getResult());
//                                           showErrorNone(sellerBean, activity);
                                   }
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   throwable.printStackTrace();
                                   BaseBean bean = new BaseBean(getString(R.string.error_str1), -1);
                                   if (throwable != null && throwable.getMessage() != null) {
                                       if (throwable.getMessage().contains("UnknownHostException")) {
                                           bean = new BaseBean(getString(R.string.error_str2), -10);
                                       } else if (throwable.getMessage().contains("OAuthProblemException")) {
                                           bean = new BaseBean(getString(R.string.error_login), -10);//登录失效
                                       } else if (throwable.getMessage().contains("isConnected failed")) {
                                           bean = new BaseBean(getString(R.string.error_str3), -20);//服务器链接失败
                                       } else if (throwable.getMessage().contains("failed to connect to")) {
                                           bean = new BaseBean(getString(R.string.error_str4), -30);//服务器链接失败
                                       } else if (throwable.getMessage().contains("thinker.vc")) {
                                           bean = new BaseBean(getString(R.string.error_str4), -30);//服务器链接失败
                                       } else if (throwable.getMessage().contains("Bad credentials")) {
                                           bean = new BaseBean(getString(R.string.error_str5), -35);//验证码错误
                                       } else if (throwable.getMessage().contains("HTTP 500 Server Error")) {
                                           bean = new BaseBean(getString(R.string.error_str6), -36);
                                       } else if (throwable.getMessage().contains("HTTP 502 Bad Gateway")) {
                                           bean = new BaseBean(getString(R.string.error_str7), -37);
                                       } else if (throwable.getMessage().contains("OAuthSystemException")) {
                                           bean = new BaseBean(getString(R.string.error_str8), -38);
                                       } else if (throwable.getMessage().contains("timeout")) {
                                           bean = new BaseBean(getString(R.string.error_str8), -39);
                                       } else {
                                           bean = new BaseBean(throwable.getMessage(), -1);
                                       }

                                   }
                                   errorDialogHandler.showError(bean.getError_code(),bean.getResult());
                               }
                           }
                );
    }
}
