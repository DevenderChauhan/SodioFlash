package com.thinker.basethinker.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stripe.android.model.Card;
import com.stripe.android.model.CustomerSource;
import com.stripe.android.model.Source;
import com.stripe.android.model.SourceCardData;
import com.stripe.android.view.PaymentMethodsActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.RechartTypeListBean;
import com.thinker.basethinker.bean.SystemParamsBean;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.stripe.StripeUtils;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.WebViewActivity;
import com.thinker.basethinker.wallet.adapter.RechartViewAdapter;

import static com.thinker.basethinker.stripe.StripeUtils.REQUEST_CODE_SELECT_SOURCE;


/**
 * Created by farley on 17/8/21.
 * description:
 */

public class ToRechartWalletActivity extends MvpActivity<ToRechartWalletPresenter, MvpView> implements MvpView, View.OnClickListener {
    private ToRechartWalletPresenter myPresenter;

    @Override
    protected ToRechartWalletPresenter CreatePresenter() {
        return myPresenter = new ToRechartWalletPresenter(this);
    }

    private ImageView head_left;
    private TextView head_title;
    private GridView myGridview;
    private RechartViewAdapter adapter;
    private RelativeLayout wx_pay, stripe_pay;
    private ImageView img_selected;
    private ImageView img_stripe;
    private TextView toRechart;
    //    private SystemParamsBean systemBean;
    private Long payMoney;
    private String payType = MyUtils.PAY_TYPE_STRIPE;
    private RechartTypeListBean mBean;
    private TextView rechartRules;
    private Double price = 0d;//该支付的价格
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this, getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_to_rechart);
        price = getIntent().getDoubleExtra("PRICE",0d);
//        systemBean = MyUtils.getSystemData();
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        myPresenter.getRechartType();
        hideLoading();
    }

    private void initView() {
        wx_pay = (RelativeLayout) findViewById(R.id.wx_pay);
        stripe_pay = (RelativeLayout) findViewById(R.id.stripe_pay);
        img_stripe = (ImageView) findViewById(R.id.img_stripe);
        img_selected = (ImageView) findViewById(R.id.img_selected);
        head_left = (ImageView) findViewById(R.id.head_left);
        toRechart = (TextView) findViewById(R.id.toRechart);
        head_title = (TextView) findViewById(R.id.head_title);
        rechartRules = (TextView) findViewById(R.id.rechartRules);
        myGridview = (GridView) findViewById(R.id.myGridview);
        head_title.setText(getString(R.string.to_rechart_title));
        head_left.setOnClickListener(this);
        myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter != null && mBean.getDataList().size() > 0) {
                    adapter.setMySelected(position);
                    payMoney = mBean.getDataList().get(position).getId();
                }
            }
        });
        wx_pay.setOnClickListener(this);
        stripe_pay.setOnClickListener(this);
        toRechart.setOnClickListener(this);
//        if (systemBean.getPayWay().contentEquals("1")) {
//            stripe_pay.setVisibility(View.GONE);
//            img_selected.setSelected(true);
//            img_selected.setVisibility(View.VISIBLE);
//            payType = MyUtils.PAY_TYPE_WXPAY;
//        } else if (systemBean.getPayWay().contentEquals("2")) {
//            wx_pay.setVisibility(View.GONE);
//            img_stripe.setSelected(true);
//            img_stripe.setVisibility(View.VISIBLE);
//            payType = MyUtils.PAY_TYPE_ALIPAY;
//        } else if (systemBean.getPayWay().contentEquals("1,2")) {
//            img_selected.setSelected(true);
//            img_selected.setVisibility(View.VISIBLE);
//            payType = MyUtils.PAY_TYPE_WXPAY;
//        }
        rechartRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webview = new Intent(ToRechartWalletActivity.this, WebViewActivity.class);
                webview.putExtra("TITLE", getString(R.string.to_rechart_rules));
                webview.putExtra("VIEWURL", "share/balance_pay_rules");
                webview.putExtra("NEEDTOKEN", false);
                startActivity(webview);
            }
        });
        img_stripe.setSelected(true);//胡阳  当前只要stripe
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.wx_pay:
                if (img_selected.isSelected()) {
                } else {
                    payType = MyUtils.PAY_TYPE_WXPAY;
                    img_selected.setSelected(true);
                    img_selected.setVisibility(View.VISIBLE);
                    img_stripe.setSelected(false);
                    img_stripe.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.stripe_pay:
                if (img_stripe.isSelected()) {
                } else {
                    payType = MyUtils.PAY_TYPE_STRIPE;
                    img_stripe.setSelected(true);
                    img_stripe.setVisibility(View.VISIBLE);
                    img_selected.setSelected(false);
                    img_selected.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.toRechart:

                if (img_selected.isSelected() || img_stripe.isSelected()) {
                   showLoading();
                    if (MyUtils.PAY_TYPE_STRIPE.contentEquals(payType)) {
                        StripeUtils stripeUtils = new StripeUtils(this, new StripeUtils.onMyPayLisener() {
                            @Override
                            public void onpay() {
                                toRechart.setEnabled(false);         
                                myPresenter.recharge(payMoney, payType);
                            }
                        });
                        stripeUtils.initCustomer();
                        return;
                    }
                    myPresenter.recharge(payMoney, payType);
                } else {
                    Toast.makeText(ToRechartWalletActivity.this, getString(R.string.choose_pay_type), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_SOURCE && resultCode == RESULT_OK) {
            String selectedSource = data.getStringExtra(PaymentMethodsActivity.EXTRA_SELECTED_PAYMENT);
//            CustomerSource card = CustomerSource.fromString(selectedSource);
//            Card card1 = card.asCard();
            Source source = Source.fromString(selectedSource);
            // Note: it isn't possible for a null or non-card source to be returned.
            if (source != null && Source.CARD.equals(source.getType())) {
                SourceCardData cardData = (SourceCardData) source.getSourceTypeModel();
//                StripeUtils stripeUtils = new StripeUtils(this,card1);
//                stripeUtils.stripePayForCard();
                toRechart.setEnabled(false);
                myPresenter.recharge(payMoney, payType);
            }else{
                Toast.makeText(ToRechartWalletActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void settoRechartTrue(){
        toRechart.setEnabled(true);
        setResult(RESULT_OK);
        finish();
    }
    public void initData(RechartTypeListBean rechartTypeListBean) {
        mBean = rechartTypeListBean;
        if (mBean.getDataList() != null) {
            adapter = new RechartViewAdapter(this, rechartTypeListBean.getDataList());
            myGridview.setAdapter(adapter);
            if (mBean.getDataList().size() > 0) {
                payMoney = mBean.getDataList().get(0).getId();
            }
            for (int i = 0; i < mBean.getDataList().size();i++){
                LogUtils.e("price="+price);
                if ( mBean.getDataList().get(i).getPayAmount() >= price){
                    adapter.setMySelected(i);
                    myGridview.setSelection(i);
                    break;
                }
            }
        }

    }
}
