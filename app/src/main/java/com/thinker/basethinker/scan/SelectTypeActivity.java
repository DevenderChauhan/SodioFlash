package com.thinker.basethinker.scan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.CabinetBean;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.bean.SystemParamsBean;
import com.thinker.basethinker.dialog.StanderdDialog;
import com.thinker.basethinker.login.LoginActivity;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.PreferencesUtils;
import com.thinker.basethinker.utils.Utils;
import com.thinker.basethinker.wallet.DepositActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by thinker on 17/12/22.
 */

public class SelectTypeActivity extends MvpActivity<SelectTypePresenter,MvpView> implements MvpView, View.OnClickListener {
    private SelectTypePresenter myPresenter;
    @Override
    protected SelectTypePresenter CreatePresenter() {
        return myPresenter = new SelectTypePresenter(this);
    }
    private ImageView head_left;
    private TextView head_title;
    private TextView sellerName;
    private TextView sellerRules;
    private TextView phoneNum;
    private TextView borrowType1;
    private TextView borrowType2;
    private TextView borrowType3;
    private TextView borrow;
    private LinearLayout type1;
    private LinearLayout type2;
    private LinearLayout type3;
    private String batteryType;
    private String code;
    private String servicePhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this, getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_select_type);
        code = getIntent().getStringExtra("CODE");
        showLoading();
        myPresenter.profileUsingGET(code);
        initView();
    }

    private void initView() {
        head_left = findViewById(R.id.head_left);
        head_title = findViewById(R.id.head_title);
        sellerName = findViewById(R.id.sellerName);
        sellerRules = findViewById(R.id.sellerRules);
        phoneNum = findViewById(R.id.phoneNum);
        borrowType1 = findViewById(R.id.borrowType1);
        borrowType2 = findViewById(R.id.borrowType2);
        borrowType3 = findViewById(R.id.borrowType3);
        borrow = findViewById(R.id.borrow);
        type1 = findViewById(R.id.type1);
        type2 = findViewById(R.id.type2);
        type3 = findViewById(R.id.type3);
        head_title.setText(getString(R.string.selecttype_title));
        head_left.setOnClickListener(this);
        phoneNum.setOnClickListener(this);
        borrow.setOnClickListener(this);
        type1.setOnClickListener(this);
        type2.setOnClickListener(this);
        type3.setOnClickListener(this);
        borrow.setEnabled(false);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                finish();
                break;
            case R.id.phoneNum:
                Utils.playPhone(this,servicePhone);
                break;
            case R.id.borrow:
                showLoading();
                myPresenter.createOrderUsingPOST(code,batteryType);
                break;
            case R.id.type1:
                batteryType = "2";
                type1.setSelected(true);
                type2.setSelected(false);
                type3.setSelected(false);
                borrow.setEnabled(true);
                break;
            case R.id.type2:
                batteryType = "3";
                type2.setSelected(true);
                type1.setSelected(false);
                type3.setSelected(false);
                borrow.setEnabled(true);
                break;
            case R.id.type3:
                batteryType = "4";
                type3.setSelected(true);
                type2.setSelected(false);
                type1.setSelected(false);
                borrow.setEnabled(true);
                break;
            default:

                break;
        }
    }

    public void initData(CabinetBean cabinetBean) {
        sellerName.setText(Utils.object2String(cabinetBean.getSeller().getSellerName()));
        sellerRules.setText(Html.fromHtml(getThemeColorStr(Utils.object2String(cabinetBean.getChargeRuleDesc()))));

//        SystemParamsBean systemParamsBean =MyUtils.getSystemData();
        servicePhone = cabinetBean.getSeller().getContactMobile();

        phoneNum.setText(Utils.object2String(servicePhone));//20180110 全部显示平台号码 胡阳 易晓梅  20180129修改
        if(cabinetBean.getBatteryType2Count() == null ||cabinetBean.getBatteryType3Count() == null||cabinetBean.getBatteryType4Count() == null){
            return;
        }
        if (cabinetBean.getBatteryType2Count() < 1){
            borrowType1.setSelected(false);
            type1.setEnabled(false);
        }else{
            borrowType1.setSelected(true);
            type1.setEnabled(true);
        }
        if (cabinetBean.getBatteryType3Count() < 1){
            borrowType2.setSelected(false);
            type2.setEnabled(false);
        }else{
            type2.setEnabled(true);
            borrowType2.setSelected(true);
        }
        if (cabinetBean.getBatteryType4Count() < 1){
            type3.setEnabled(false);
            borrowType3.setSelected(false);
        }else{
            type3.setEnabled(true);
            borrowType3.setSelected(true);
        }
        borrowType1.setText(Utils.object2String(cabinetBean.getBatteryType2Count())+" "+getString(R.string.seller_msg_borrow));
        borrowType2.setText(Utils.object2String(cabinetBean.getBatteryType3Count())+" "+getString(R.string.seller_msg_borrow));
        borrowType3.setText(Utils.object2String(cabinetBean.getBatteryType4Count())+" "+getString(R.string.seller_msg_borrow));
    }

    public void borrowData(OrderBean orderBean) {
        Intent it = new Intent();
        it.putExtra("BORROW",orderBean.getOrderCode());
        setResult(RESULT_OK,it);
        finish();
    }
    private String getThemeColorStr(String string){
//        String themeColor = "#"+Integer.toHexString(getResources().getColor(R.color.color_theme));
        String themeColor = "#F02022";
        themeColor = themeColor.toUpperCase();
        String result = " ";
        if (TextUtils.isEmpty(string)){
            return result;
        }

        String ex = "<s>(.*?)</s>";
        Pattern p = Pattern.compile(ex);
        Matcher m = p.matcher(string);
        while (m.find()) {
            int i = 1;
            String str = "<font color=\'"+themeColor+"\' >"+m.group(i)+"</font>";

            string = string.replace("<s>"+m.group(i)+"</s>",str);
            i++;
        }

        return string;
    }
    public void showErrorNone(final BaseBean bean){
        final StanderdDialog dialog = new StanderdDialog(SelectTypeActivity.this, getString(R.string.validationErrors),
                bean.getResult(), getString(R.string.toast_27),getString(R.string.share_cancel),
                new StanderdDialog.OnDialogClickListener() {
                    @Override
                    public void doAnyClick() {
                    }

                    @Override
                    public void doMainClick() {
                        if (bean.getError_code() == 409){
                            Intent it = new Intent(SelectTypeActivity.this, DepositActivity.class);
                            it.putExtra("FROM",1);
                            startActivity(it);
                        }
                    }
                });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }
}
