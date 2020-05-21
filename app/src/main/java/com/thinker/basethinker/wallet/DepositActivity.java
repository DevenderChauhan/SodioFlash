package com.thinker.basethinker.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;
import com.stripe.android.model.Source;
import com.stripe.android.model.SourceCardData;
import com.stripe.android.view.PaymentMethodsActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.ChergeBean;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.bean.SystemParamsBean;
import com.thinker.basethinker.bean.WalletBean;
import com.thinker.basethinker.bean.WalletItemData;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.stripe.StripeUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;
import com.thinker.basethinker.wallet.adapter.ItemWalletAdapter;
import com.thinker.basethinker.wallet.dialog.DepositDialog;
import com.thinker.basethinker.wallet.dialog.RechargeDialog;

import java.util.ArrayList;
import java.util.List;

import static com.thinker.basethinker.stripe.StripeUtils.REQUEST_CODE_SELECT_SOURCE;

/**
 * Created by thinker on 17/12/13.
 */

public class DepositActivity extends MvpActivity<DepositPresenter, MvpView> implements MvpView, View.OnClickListener {
    private DepositPresenter presenter;
    private ImageView head_left, head_right;
    private TextView head_title;
    private ListView listview;
    private List<WalletItemData> dataList = new ArrayList<>();
    private ItemWalletAdapter adapter;
    private Button btn_deposit;
    private TextView charge;
    private boolean isRecharge;//是否充值了押金
//    private SystemParamsBean systemBean;
    private LinearLayout empty_view;
    private int from = 0;//来源控制
    @Override
    protected DepositPresenter CreatePresenter() {
        return presenter = new DepositPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this, getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_deposit);
        from = getIntent().getIntExtra("FROM",0);
        initView();
//        systemBean = MyUtils.getSystemData();
    }

    public void initData(PersonalBean bean) {
        if (bean != null) {
            charge.setText(getString(R.string.wallet_charge) +" "+ Utils.object2String(bean.getCurrency())+" " + bean.getDeposit());
            if (bean.getDeposit() != null && bean.getDeposit() > 0) {
                btn_deposit.setText(getString(R.string.wallet_dialog_btn));
                isRecharge = true;
                if (from == 1){
                    finish();
                }
            } else {
                charge.setText(getString(R.string.wallet_charge) +" "+ Utils.object2String(bean.getCurrency())+" " + "0");
                btn_deposit.setText(getString(R.string.wallet_rechrge));
                isRecharge = false;
            }
        } else {
            charge.setText(getString(R.string.wallet_charge) +" "+ Utils.object2String(bean.getCurrency())+" " + "0");
            btn_deposit.setText(getString(R.string.wallet_rechrge));
            isRecharge = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getMyWallet(null);
        presenter.getMyData();
        presenter.getCherge();
    }

    private Double myCherge = 0d;

    public void setCherge(final ChergeBean chergeBean) {
        myCherge = chergeBean.getDeposit();
    }

    private void initView() {
        empty_view = (LinearLayout) findViewById(R.id.empty_view);
        btn_deposit = (Button) findViewById(R.id.btn_deposit);
        charge = (TextView) findViewById(R.id.charge);
        head_title = (TextView) findViewById(R.id.head_title);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_right = (ImageView) findViewById(R.id.head_right);
        listview = (ListView) findViewById(R.id.listview);
        head_title.setText(Utils.object2String(getString(R.string.wallet_deposit)));
        head_right.setVisibility(View.GONE);
        head_left.setOnClickListener(this);
        btn_deposit.setOnClickListener(this);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (dataList.size() > 9) {
                    presenter.getMyWallet(dataList.get(dataList.size() - 1).getCreateTime().getTime());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.btn_deposit:
                if (isRecharge) {//已经充值了 走退押金流程
                    showRefoundDialog();
                } else {//未充值，走充值流程
//                    if (systemBean.getPayWay().contentEquals("1")) {
//                        presenter.recharge(MyUtils.PAY_TYPE_WXPAY);
//                    } else if (systemBean.getPayWay().contentEquals("2")) {
//                        presenter.recharge(MyUtils.PAY_TYPE_ALIPAY);
//                    } else if (systemBean.getPayWay().contentEquals("1,2")) {
                    showRechargeDialog();
//                    }
                }

                break;
            default:
                break;
        }
    }

    public void loadMore(final WalletBean bean, final Long page) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (page == null) {
                            dataList.clear();
                        }
                        if (bean.getDatas() == null) {
                            return;
                        }
                        dataList.addAll(bean.getDatas());
                        if (dataList.size() > 0){
                            empty_view.setVisibility(View.GONE);
                        }else{
                            empty_view.setVisibility(View.VISIBLE);
                        }
                        if (adapter == null) {
                            adapter = new ItemWalletAdapter(DepositActivity.this, dataList);
                            listview.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }.start();
    }

    /**
     * 退押金
     */
    private void showRefoundDialog() {
        DepositDialog dialog = new DepositDialog(DepositActivity.this, new DepositDialog.OnDialogClickListener() {

            @Override
            public void onClick() {
                presenter.refundDeposit();
            }
        });
        dialog.show();
    }

    /**
     * 充值押金
     */
    private void showRechargeDialog() {
        RechargeDialog dialog = new RechargeDialog(myCherge, DepositActivity.this, new RechargeDialog.OnDialogClickListener() {
            @Override
            public void onClick(int type) {
                if (type == 0) {
                    presenter.recharge(MyUtils.PAY_TYPE_WXPAY);
                } else if (type == 1) {
                    StripeUtils stripeUtils = new StripeUtils(DepositActivity.this, new StripeUtils.onMyPayLisener() {
                        @Override
                        public void onpay() {
                            showLoading();
                            presenter.recharge(MyUtils.PAY_TYPE_STRIPE);
                        }
                    });
                    stripeUtils.initCustomer();
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_SOURCE && resultCode == RESULT_OK) {
            String selectedSource = data.getStringExtra(PaymentMethodsActivity.EXTRA_SELECTED_PAYMENT);
            Source source = Source.fromString(selectedSource);
            // Note: it isn't possible for a null or non-card source to be returned.
            if (source != null && Source.CARD.equals(source.getType())) {
                SourceCardData cardData = (SourceCardData) source.getSourceTypeModel();
                showLoading();
                presenter.recharge(MyUtils.PAY_TYPE_STRIPE);
            }

        }
    }
}
