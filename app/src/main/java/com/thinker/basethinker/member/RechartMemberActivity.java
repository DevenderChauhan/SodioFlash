package com.thinker.basethinker.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.stripe.android.model.Source;
import com.stripe.android.model.SourceCardData;
import com.stripe.android.view.PaymentMethodsActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.bean.SystemParamsBean;
import com.thinker.basethinker.member.adapter.MemberVipAdapter;
import com.thinker.basethinker.member.bean.MemberVipCardBean;
import com.thinker.basethinker.member.bean.VipListBean;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.stripe.StripeUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;

import java.util.List;

import static com.thinker.basethinker.stripe.StripeUtils.REQUEST_CODE_SELECT_SOURCE;


/**
 * Created by farley on 17/8/17.
 * description:
 */

public class RechartMemberActivity extends MvpActivity<RechartMembrePresenter,MvpView> implements MvpView, View.OnClickListener {
    private RechartMembrePresenter myPresenter;
    @Override
    protected RechartMembrePresenter CreatePresenter() {
        return myPresenter = new RechartMembrePresenter(this);
    }
    private ImageView head_left;
    private TextView head_title;
    private ListView dataList;
    private MemberVipAdapter adapter;
    private String[] descList;
    private LinearLayout ll_desc;
    private TextView updata;
//    private boolean isVip;
    private Double myCherge;
    List<MemberVipCardBean> vipBean;
    private Long cardId;
    private TextView remainTime;//到期时间
    private TextView meturityData;//到期时间
    private TextView freeTime;
    private TextView head_right;
//    private String remainDate;
//    private long remainDateLong;
    private TextView vipcard;
    private SystemParamsBean systemBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this,getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_rechart_member);
//        isVip = getIntent().getBooleanExtra("ISVIP",false);
//        remainDate = getIntent().getStringExtra("REMAINDATE");
//        remainDateLong = getIntent().getLongExtra("REMAINDATELONG",0);
        systemBean = MyUtils.getSystemData();
        initView();
        initData();
    }

    private void initData() {
        SystemParamsBean bean = MyUtils.getSystemData();
        if (bean != null){
            descList = bean.getCardDesc().split("\r\n");
            if (descList != null){
                int allHeight = 0;
                LinearLayout myLine = new LinearLayout(this);
                myLine.setOrientation(LinearLayout.VERTICAL);
                myLine.setBackgroundColor(getResources().getColor(R.color.color_white));
                myLine.setPadding(0,15,0,15);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View myView = inflater.inflate(R.layout.lineview, null);

                for (String desc:descList){
                    View view  = inflater.inflate(R.layout.sb_view, null);
                    TextView desc_text = (TextView) view.findViewById(R.id.desc_text);
                    desc_text.setText(desc);
//                    int w = View.MeasureSpec.makeMeasureSpec(0,
//                            View.MeasureSpec.UNSPECIFIED);
//                    int h = View.MeasureSpec.makeMeasureSpec(0,
//                            View.MeasureSpec.UNSPECIFIED);
//                    desc_text.measure(w, h);
//                    allHeight = allHeight + desc_text.getMeasuredHeight();
//                    ll_desc.addView(view);
                    myLine.addView(view);
                }
                dataList.addFooterView(myLine);
                dataList.addFooterView(myView);
            }
            vipcard.setText(Utils.object2String(bean.getAppName())+" "+getString(R.string.member_vip_title));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        myPresenter.getMemberData();
        myPresenter.getMyData();
        hideLoading();
    }

    private void initView() {
        ll_desc = (LinearLayout) findViewById(R.id.ll_desc);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_title = (TextView) findViewById(R.id.head_title);
        vipcard = (TextView) findViewById(R.id.vipcard);
        head_right = (TextView) findViewById(R.id.head_right);
        updata = (TextView) findViewById(R.id.updata);
        remainTime = (TextView) findViewById(R.id.remainTime);
        meturityData = (TextView) findViewById(R.id.meturityData);
        freeTime = (TextView) findViewById(R.id.freeTime);
        dataList = (ListView) findViewById(R.id.dataList);
        head_left.setOnClickListener(this);
        head_title.setText(getString(R.string.member_title));
        head_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent remain = new Intent(RechartMemberActivity.this,MemberListActivity.class);
                startActivity(remain);
            }
        });
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (vipBean != null && adapter != null && position < vipBean.size()){
                    adapter.setSelectedPos(position);
                    myCherge = vipBean.get(position).getCardAmount();
                    cardId = vipBean.get(position).getId();
                }
            }
        });
//        if (isVip){
//            updata.setText("续费");
//            remainTime.setText("会员卡剩余"+remainDate);
//            meturityData.setText(Utils.stampToDate3(remainDateLong)+"前可使用");
//            freeTime.setVisibility(View.VISIBLE);
//        }else{
//            updata.setText("开通会员");
//            remainTime.setText("未开通");
//            meturityData.setVisibility(View.INVISIBLE);
//            freeTime.setVisibility(View.INVISIBLE);
//        }
        updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCherge != null) {
                    /*MemberRechartDialog dialog = new MemberRechartDialog(myCherge, RechartMemberActivity.this, new MemberRechartDialog.OnDialogClickListener() {
                        @Override
                        public void onClick(int type) {
                            if (type == 0) {
                                myPresenter.recharge(cardId,MyUtils.PAY_TYPE_WXPAY);
                            } else if (type == 1) {
//                                myPresenter.recharge(cardId,MyUtils.PAY_TYPE_STRIPE);
                                showLoading();
                                StripeUtils stripeUtils = new StripeUtils(RechartMemberActivity.this);
                                stripeUtils.initCustomer();
                            }
                        }
                    });
                    dialog.show();
                    Window window = dialog.getWindow();
                    if (dialog != null && window != null) {
                        window.getDecorView().setPadding(0, 0, 0, 0);
                        WindowManager.LayoutParams attr = window.getAttributes();
                        if (attr != null) {
                            attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            attr.width = ViewGroup.LayoutParams.MATCH_PARENT;
                            attr.gravity = Gravity.BOTTOM;//设置dialog 在布局中的位置
                            window.setAttributes(attr);
                        }
                    }*/
                    showLoading();
                    StripeUtils stripeUtils = new StripeUtils(RechartMemberActivity.this, new StripeUtils.onMyPayLisener() {
                        @Override
                        public void onpay() {
                            myPresenter.recharge(cardId,MyUtils.PAY_TYPE_STRIPE);
                        }
                    });
                    stripeUtils.initCustomer();
                }else{
                    Toast.makeText(RechartMemberActivity.this,getString(R.string.member_text6),Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                myPresenter.recharge(cardId,MyUtils.PAY_TYPE_STRIPE);
            }

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                setResult(RESULT_OK);
                finish();
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

    public void getDataListFailed() {
    }

    public void getDataListSuccess(VipListBean vipListBean) {
        vipBean = vipListBean.getDataList();
        if (vipBean == null){
        }else{
            adapter = new MemberVipAdapter(RechartMemberActivity.this,vipBean);
            dataList.setAdapter(adapter);
            adapter.setSelectedPos(0);
            myCherge = vipBean.get(0).getCardAmount();
            cardId = vipBean.get(0).getId();
        }
    }

    public void setPersonData(PersonalBean personalBean) {
        boolean isVip = personalBean.getVIP();
        if (isVip){
            String  remainDate = personalBean.getVipDxpireDateDesc();
            Long remainDateLong = personalBean.getVipExpiresIn().getTime();
            updata.setText(getString(R.string.member_text1));
            remainTime.setText(getString(R.string.member_text2)+ " "+remainDate);
            if (Utils.getLauguage(this).equals("en")){
                meturityData.setText(getString(R.string.member_text3)+ " " + Utils.stampToDate3(remainDateLong));
            }else{
                meturityData.setText(Utils.stampToDate3(remainDateLong)+getString(R.string.member_text3));
            }

            meturityData.setVisibility(View.VISIBLE);
            freeTime.setVisibility(View.VISIBLE);
        }else{
            updata.setText(getString(R.string.member_text4));
            remainTime.setText(getString(R.string.member_text5));
            meturityData.setVisibility(View.INVISIBLE);
            freeTime.setVisibility(View.INVISIBLE);
        }
    }
}