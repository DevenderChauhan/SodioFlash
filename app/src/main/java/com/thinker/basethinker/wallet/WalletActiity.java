package com.thinker.basethinker.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stripe.android.model.Source;
import com.stripe.android.model.SourceCardData;
import com.stripe.android.view.PaymentMethodsActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.personal.PersonalActivity;
import com.thinker.basethinker.stripe.StripeUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;

import org.slf4j.helpers.Util;

import static com.thinker.basethinker.stripe.StripeUtils.REQUEST_CODE_SELECT_SOURCE;

/**
 * Created by thinker on 17/12/12.
 */

public class WalletActiity extends MvpActivity<WalletPresenter,MvpView> implements MvpView, View.OnClickListener {
    private WalletPresenter myPresenter;
    @Override
    protected WalletPresenter CreatePresenter() {
        return myPresenter = new WalletPresenter(this);
    }
    private Button Btnrechart;
    private TextView detail;
    private TextView myBalance;
    private TextView statusDeposit;
    private TextView myScore;
    private TextView money_unit;
    private RelativeLayout deposit;
    private RelativeLayout score_ll;
    private RelativeLayout bankCard;
    private Long score;
    private ImageView head_left;
    private TextView reward_coms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this,getResources().getColor(R.color.color_theme));
        setContentView(R.layout.activity_wallet);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myPresenter.getMyData();
        hideLoading();
    }

    private void initView() {
        reward_coms =  findViewById(R.id.reward_coms);
        head_left = (ImageView) findViewById(R.id.head_left);
        deposit = (RelativeLayout) findViewById(R.id.deposit);
        score_ll = (RelativeLayout) findViewById(R.id.score_ll);
        bankCard = (RelativeLayout) findViewById(R.id.bankCard);
        myScore = (TextView) findViewById(R.id.myScore);
        myBalance = (TextView) findViewById(R.id.myBalance);
        detail = (TextView) findViewById(R.id.detail);
        statusDeposit = (TextView) findViewById(R.id.statusDeposit);
        Btnrechart = (Button) findViewById(R.id.Btnrechart);
        money_unit =  findViewById(R.id.money_unit);
        Btnrechart.setOnClickListener(this);
        detail.setOnClickListener(this);
        deposit.setOnClickListener(this);
        score_ll.setOnClickListener(this);
        head_left.setOnClickListener(this);
        bankCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.Btnrechart:
                startActivity(new Intent(WalletActiity.this,ToRechartWalletActivity.class));
                break;
            case R.id.detail:
                startActivity(new Intent(WalletActiity.this,BalanceListActivity.class));
                break;
            case R.id.deposit:
                startActivity(new Intent(WalletActiity.this,DepositActivity.class));
                break;
            case R.id.score_ll:
                Intent it = new Intent(WalletActiity.this,ScoreActivity.class);
                it.putExtra("SCORE",score);
                startActivity(it);
                break;
            case R.id.bankCard:
                showLoading();
                StripeUtils stripeUtils = new StripeUtils(WalletActiity.this, new StripeUtils.onMyPayLisener() {
                    @Override
                    public void onpay() {
                        Intent payIntent = PaymentMethodsActivity.newIntent(WalletActiity.this);
                        startActivityForResult(payIntent, REQUEST_CODE_SELECT_SOURCE);
                    }
                });
                stripeUtils.initCustomer();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {

        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
    public void initData(PersonalBean personalBean) {
        myBalance.setText(Utils.object2String(personalBean.getBalance()));
        if (personalBean.getDeposit() > 0){
            statusDeposit.setText(getString(R.string.wallet_have_deposit));
        }else{
            statusDeposit.setText(getString(R.string.wallet_no_deposit));
        }
        score = personalBean.getIntegral();
        if (score == null){
            score = 0L;
        }
        myScore.setText(Utils.object2String(score)+ " "+getString(R.string.score_unit));
        money_unit.setText(Utils.object2String(personalBean.getCurrency()));
        if (personalBean.getRewardAmount() == null){
            reward_coms.setText(Utils.object2String(personalBean.getCurrency()) + " " +0);
        }else {
            reward_coms.setText(Utils.object2String(personalBean.getCurrency()) + " " + Utils.object2String(personalBean.getRewardAmount()));
        }
    }
}
