package com.thinker.basethinker.rechange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.IntegralListBean;
import com.thinker.basethinker.bean.IntegralLogBean;
import com.thinker.basethinker.bean.InvateAndShateBean;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.bean.SystemParamsBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.scan.ScanActivity;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;
import com.thinker.basethinker.wallet.adapter.ItemScoreAdapter;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.wechat.friends.Wechat;
import vc.thinker.colours.client.ApiClient;

/**
 * Created by thinker on 17/12/26.
 */

public class RechangeActivity extends MvpActivity<RechangePresenter,MvpView> implements MvpView, View.OnClickListener {
    private RechangePresenter myPresenter;
    @Override
    protected RechangePresenter CreatePresenter() {
        return myPresenter = new RechangePresenter(this);
    }
    private ImageView head_left;
    private ImageView head_right;
    private TextView head_title;
    private ListView listview;
    private TextView scoreNum;
    private TextView grallNum;
    private TextView invateCode;
    private ImageView btnScan;
    private LinearLayout ll_exchange;
    private static final int SCAN_CODE = 122;//扫码
    private EditText edt_rechangeNum;
    private List<IntegralLogBean> dataList = new ArrayList<>();
    private ItemScoreAdapter adapter;
    private String myCode;
    private String shareTitle = "NOMO";
    private String shareContent = "NOMO desc";
    private LinearLayout empty_view;
    private TextView grall_unit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechange);
        initView();
        initData();
        myPresenter.getScoreList(null,"exchange");
    }

    private void initData() {
        PersonalBean bean = MyUtils.getPersonData();
        if (bean.getIntegral() != null){
            scoreNum.setText(Utils.object2String(bean.getIntegral()));
        }
        if (bean.getRewardAmount() != null){
            grallNum.setText(Utils.object2String(bean.getRewardAmount()));
        }

        grall_unit.setText(Utils.object2String(bean.getCurrency()));
        invateCode.setText(Utils.object2String(bean.getInviteCode()));
        myCode = bean.getInviteCode();
        InvateAndShateBean invateAndShateBean = MyUtils.getInvateAndShareData();
        shareTitle = invateAndShateBean.getShareTitle();
        shareContent = invateAndShateBean.getShareContent();
    }

    private void initView() {
        empty_view = (LinearLayout) findViewById(R.id.empty_view);
        grall_unit = (TextView)findViewById(R.id.grall_unit);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_title = (TextView)findViewById(R.id.head_title);
        scoreNum = (TextView)findViewById(R.id.scoreNum);
        grallNum = (TextView)findViewById(R.id.grallNum);
        btnScan = (ImageView) findViewById(R.id.btnScan);
        edt_rechangeNum = (EditText) findViewById(R.id.edt_rechangeNum);
        listview = (ListView) findViewById(R.id.listview);
        invateCode = (TextView)findViewById(R.id.invateCode);
        ll_exchange = (LinearLayout) findViewById(R.id.ll_exchange);
        head_right = (ImageView) findViewById(R.id.head_right);

        head_title.setText(getString(R.string.rechange_title));
        head_left.setOnClickListener(this);
        btnScan.setOnClickListener(this);
        ll_exchange.setOnClickListener(this);
        head_right.setOnClickListener(this);
        ll_exchange.setEnabled(false);
        edt_rechangeNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 4){
                    ll_exchange.setEnabled(true);
                }else{
                    ll_exchange.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (dataList.size() > 9) {
                    myPresenter.getScoreList(dataList.get(dataList.size() - 1).getCreateTime().getTime(),"exchange");
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                finish();
                break;
            case R.id.btnScan:
                startActivityForResult(new Intent(RechangeActivity.this, ScanActivity.class),SCAN_CODE);
                break;
            case R.id.ll_exchange:
                String code = edt_rechangeNum.getText().toString().trim();
                myPresenter.exchangeCodeUsingPOST(code);
                break;
            case R.id.head_right:
                share();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCAN_CODE//扫描后返回
                && resultCode == Activity.RESULT_OK){
            String result = data.getStringExtra(CodeUtils.RESULT_STRING);
            LogUtils.e("code="+ result);
//            edt_rechangeNum.setText(result);
            if (!TextUtils.isEmpty(result)){
                myPresenter.exchangeCodeUsingPOST(result);
            }

        }
    }

    public void getScareData(IntegralListBean bean, Long ltTime) {
        if (ltTime == null) {
            dataList.clear();
        }
        if (bean.getDataList() != null){
            dataList.addAll(bean.getDataList());
        }
        if (dataList.size() > 0){
            empty_view.setVisibility(View.GONE);
        }else{
            empty_view.setVisibility(View.VISIBLE);
        }
        if (adapter == null) {
            adapter = new ItemScoreAdapter(RechangeActivity.this, dataList);
            listview.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void exchange(BaseBean bean) {
        Toast.makeText(this,getString(R.string.rechange_rechange_success),Toast.LENGTH_SHORT).show();
        edt_rechangeNum.setText("");
        myPresenter.getScoreList(null,"exchange");
        myPresenter.getMyData();
    }

    public void setPersonData(PersonalBean personalBean) {
        if (personalBean.getIntegral() != null){
            scoreNum.setText(Utils.object2String(personalBean.getIntegral()));
        }
        if (personalBean.getRewardAmount() != null){
            grallNum.setText(Utils.object2String(personalBean.getRewardAmount()));
        }

        invateCode.setText(Utils.object2String(personalBean.getInviteCode()));
        grall_unit.setText(Utils.object2String(personalBean.getCurrency()));
    }
    private void share(){
        MyUtils.myShareDialog(true,this, new MyUtils.ShareCancelListener() {
            @Override
            public void cancel() {
            }

            @Override
            public void mySelect(int pos) {
                LogUtils.e("pos="+ApiClient.baseUrl+"share/invite_share?inviteCode=" + myCode);
                if (pos == 3) {
                   MyUtils.showWechat(Wechat.NAME,RechangeActivity.this, shareTitle, ApiClient.baseUrl+"share/invite_share?inviteCode=" + myCode, shareContent);
                } else if (pos == 4) {
                    MyUtils.showWechat(Facebook.NAME,RechangeActivity.this, shareTitle, ApiClient.baseUrl+"share/invite_share?inviteCode=" + myCode, shareContent);
                } else if (pos == 2) {

                } else if (pos == 3) {
                }
            }
        });
    }

}
