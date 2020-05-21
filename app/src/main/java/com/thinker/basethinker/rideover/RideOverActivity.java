package com.thinker.basethinker.rideover;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinker.basethinker.MainActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.bean.UserMoneyBean;
import com.thinker.basethinker.feedback.FeedBackActivity;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.myoffer.MyOfferActivity;
import com.thinker.basethinker.orderlist.OrderDetailActivity;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.PreferencesUtils;
import com.thinker.basethinker.utils.Utils;
import com.thinker.basethinker.wallet.ToRechartWalletActivity;
import com.thinker.basethinker.wallet.WalletActiity;

/**
 * Created by thinker on 17/12/25.
 */

public class RideOverActivity extends MvpActivity<RideOverPresenter,MvpView> implements MvpView, View.OnClickListener {
    private RideOverPresenter myPredenter;
    @Override
    protected RideOverPresenter CreatePresenter() {
        return myPredenter = new RideOverPresenter(this);
    }
    private ImageView head_left;
    private TextView head_title;
    private TextView orderCode;
    private TextView orderTotalPrice;
    private TextView useTime;
    private TextView myOffer;
    private LinearLayout ll_payForcard;
    private LinearLayout ll_wallet;
    private LinearLayout ll_payBtn;
    private LinearLayout btn_pay;
    private RelativeLayout rr_question;
    private RelativeLayout rr_detail;
    private RelativeLayout btn_my_offer;
    private TextView toPayPrice;
    private TextView offerPrice;
    private TextView wallet_text;
    private String orderCodeStr;
    private String returnMachineCode;
    private Long cid;
    private int OFFER_CODE = 132;//优惠券
    private int WALLET_CODE = 133;//充值去
    private String payTypeStr;//支付方式
    private String lastOrderCode;//从上个界面传过来的数据
    private TextView rulesDesc;
    private String currentyStr ;//订单的币种
    private int toRechart = 0;//控制支付
    private Double price = 0d;//该支付的价格
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_over);
        Intent it = getIntent();
        if (it != null){//查看订单
            lastOrderCode = it.getStringExtra("ORDERCODE");
            if (lastOrderCode != null) {
                myPredenter.profileUsingGET(lastOrderCode);
            }else{
                myPredenter.findNotPayUsingGET();
            }
        }else{
            myPredenter.findNotPayUsingGET();
        }
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initView() {
        rr_detail = (RelativeLayout) findViewById(R.id.rr_detail);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_title = (TextView)findViewById(R.id.head_title);
        orderCode = (TextView)findViewById(R.id.orderCode);
        orderTotalPrice = (TextView)findViewById(R.id.orderTotalPrice);
        useTime =(TextView) findViewById(R.id.useTime);
        myOffer =(TextView) findViewById(R.id.myOffer);
        ll_payForcard = (LinearLayout) findViewById(R.id.ll_payForcard);
        ll_wallet = (LinearLayout) findViewById(R.id.ll_wallet);
        rr_question = (RelativeLayout) findViewById(R.id.rr_question);
        toPayPrice = (TextView)findViewById(R.id.toPayPrice);
        offerPrice = (TextView)findViewById(R.id.offerPrice);
        ll_payBtn = (LinearLayout) findViewById(R.id.ll_payBtn);
        wallet_text = (TextView)findViewById(R.id.wallet_text);
        btn_pay = (LinearLayout) findViewById(R.id.btn_pay);
        btn_my_offer = (RelativeLayout)findViewById(R.id.btn_my_offer);
        rulesDesc = (TextView)findViewById(R.id.rulesDesc);


        head_title.setText(getString(R.string.ride_over_title));
        head_left.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
        btn_my_offer.setOnClickListener(this);
        ll_wallet.setOnClickListener(this);
        rr_question.setOnClickListener(this);
        rr_detail.setOnClickListener(this);
//        btn_pay.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                finish();
                break;
            case R.id.btn_pay:
                if (toRechart == 1){
                    LogUtils.e("price="+price);
                    Intent it = new Intent(RideOverActivity.this, ToRechartWalletActivity.class);
                    it.putExtra("PRICE",price);
                    startActivityForResult(it,WALLET_CODE);
                }else{
                    myPredenter.paymetUsingPOST(orderCodeStr,cid,payTypeStr);
                }

                break;
            case R.id.btn_my_offer:
                Intent it = new Intent(RideOverActivity.this, RideOfferActivity.class);
                it.putExtra("TYPE",true);
                startActivityForResult(it,OFFER_CODE);
                break;
            case R.id.ll_wallet:
                startActivityForResult(new Intent(RideOverActivity.this, ToRechartWalletActivity.class),WALLET_CODE);
                break;
            case R.id.rr_question:
                Intent itMain = new Intent(RideOverActivity.this, FeedBackActivity.class);
                itMain.putExtra("TYPE","3");
                itMain.putExtra("TRIPID",returnMachineCode);
                startActivity(itMain);
                break;
            case R.id.rr_detail:
                Intent detail = new Intent(this, OrderDetailActivity.class);
                detail.putExtra("ORDERCODE", orderCodeStr);
                startActivity(detail);
                break;
        }
    }
    private Double toPayPriceDou = 0d;//订单金额
    //加载数据
    public void initData(OrderBean orderBean) {
        if (orderBean.getExchangeRateDesc() != null){
            rulesDesc.setText(Utils.object2String(orderBean.getExchangeRateDesc()));
            rulesDesc.setVisibility(View.VISIBLE);
        }else{
            rulesDesc.setVisibility(View.GONE);
        }
        toPayPriceDou = orderBean.getPrice();
        orderCode.setText(Utils.object2String(orderBean.getOrderCode()));
        orderTotalPrice.setText(getString(R.string.ride_over_tital_price)+" "+Utils.object2String(orderBean.getPrice())+Utils.object2String(orderBean.getCurrency()));
        useTime.setText(Utils.object2String(orderBean.getRideTime())+"min");
        currentyStr = orderBean.getCurrency();
        returnMachineCode = orderBean.getReturnSysCode();
        //判断是否有优惠券
        if (orderBean.getFitCoupon() == null){
            selectedOfferPrice = toPayPriceDou;
            cid = null;
            btn_my_offer.setEnabled(false);
            myOffer.setTextColor(getResources().getColor(R.color.color_aux_gray));
            myOffer.setText(getString(R.string.ride_over_offer_no));
            offerPrice.setText(getString(R.string.ride_over_offering)+" "+currentyStr+" "+0);
        }else{
            cid = orderBean.getFitCoupon().getId();
            selectedOfferPrice = toPayPriceDou - orderBean.getFitCoupon().getAmount();
            myOffer.setText("-"+currentyStr+orderBean.getFitCoupon().getAmount());
            myOffer.setTextColor(getResources().getColor(R.color.color_theme));
            if (orderBean.getFitCoupon().getAmount() == null){
                offerPrice.setText(getString(R.string.ride_over_offering)+"0");
            }else{

                offerPrice.setText(getString(R.string.ride_over_offering)+" "+currentyStr+" "+Utils.object2String(orderBean.getFitCoupon().getAmount()));
            }
        }
        if (selectedOfferPrice < 0){
            selectedOfferPrice = 0d;
        }
        String payType = orderBean.getPayType();
        //判断支付方式 免费：free  现金： cash  会员卡：vip  余额：balance
        if (payType != null){
            switch (payType){
                case "free":
                    selectedOfferPrice = 0d;
                    ll_payForcard.setVisibility(View.GONE);
                    ll_wallet.setVisibility(View.GONE);
                    rr_question.setVisibility(View.GONE);
                    ll_payBtn.setVisibility(View.GONE);
                    payTypeStr = null;
                    break;
                case "cash":
                    ll_payForcard.setVisibility(View.VISIBLE);
                    ll_wallet.setVisibility(View.GONE);
                    rr_question.setVisibility(View.VISIBLE);
                    ll_payBtn.setVisibility(View.VISIBLE);
                    payTypeStr = "stripe";
                    btn_pay.setEnabled(true);
                    break;
                case "vip":
                    selectedOfferPrice = 0d;
                    ll_payForcard.setVisibility(View.GONE);
                    ll_wallet.setVisibility(View.GONE);
                    rr_question.setVisibility(View.GONE);
                    ll_payBtn.setVisibility(View.GONE);
                    myOffer.setText(getString(R.string.ride_over_free));
                    payTypeStr = null;
                    break;
                case "balance":
                    ll_payForcard.setVisibility(View.GONE);
                    ll_wallet.setVisibility(View.VISIBLE);
                    rr_question.setVisibility(View.VISIBLE);
                    ll_payBtn.setVisibility(View.VISIBLE);
                    myPredenter.getMyBalanceUsingGET();
                    payTypeStr = "balance";
                    break;
            }
        }

        orderCodeStr = orderBean.getOrderCode();
        toPayPrice.setText(getString(R.string.ride_over_to_pay)+" "+currentyStr+selectedOfferPrice);
        price = selectedOfferPrice;
        LogUtils.e("price="+selectedOfferPrice);
        if (lastOrderCode != null){//如果是从详情过来的，那么就不现实价格和支付
            selectedOfferPrice = 0d;
            ll_payForcard.setVisibility(View.GONE);
            ll_wallet.setVisibility(View.GONE);
            rr_question.setVisibility(View.GONE);
            ll_payBtn.setVisibility(View.GONE);
            payTypeStr = null;
        }
    }

    public void setBalence(UserMoneyBean belance) {
        hideLoading();
        if (!currentyStr.equals(belance.getCurrency())){
            wallet_text.setTextColor(getResources().getColor(R.color.color_aux_gray));
            wallet_text.setText(Utils.object2String(belance.getCurrency())+Utils.object2String(belance.getAvailableBalance()));
            btn_pay.setEnabled(true);
            return;
        }
        if (belance.getAvailableBalance() >= selectedOfferPrice) {
            wallet_text.setTextColor(getResources().getColor(R.color.color_aux_gray));
            wallet_text.setText(Utils.object2String(belance.getCurrency())+Utils.object2String(belance.getAvailableBalance()));
            btn_pay.setEnabled(true);
            toRechart = 0;
        }else{
            wallet_text.setText(getString(R.string.ride_over_wallet_no));
            wallet_text.setTextColor(getResources().getColor(R.color.color_theme));
//            btn_pay.setEnabled(false);胡杨修改
            toRechart = 1;
        }
    }
    private Double selectedOfferPrice = 0d;//实际应该支付的金额
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OFFER_CODE && resultCode == RESULT_OK){
            cid = data.getLongExtra("CID",-1);
            Double price = data.getDoubleExtra("MONEY",0);
            if (cid < 0){
                cid = null;
            }
            selectedOfferPrice = toPayPriceDou - price;
            if (selectedOfferPrice < 0){
                selectedOfferPrice = 0d;
            }
            myOffer.setText("-"+currentyStr+price);
            toPayPrice.setText(getString(R.string.ride_over_to_pay)+" "+currentyStr+selectedOfferPrice);
            myOffer.setTextColor(getResources().getColor(R.color.color_theme));
            offerPrice.setText(getString(R.string.ride_over_offering)+" "+currentyStr+Utils.object2String(price));
        }
        if (requestCode == WALLET_CODE && resultCode == RESULT_OK){
            showLoading();
            myPredenter.getMyBalanceUsingGET();
        }
    }
}
