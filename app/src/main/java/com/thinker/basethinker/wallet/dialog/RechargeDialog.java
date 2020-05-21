package com.thinker.basethinker.wallet.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.bean.SystemParamsBean;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;


/**
 * Created by farley on 17/5/25.
 * description:充值流程
 */

public class RechargeDialog extends AlertDialog {
    public interface OnDialogClickListener{
        void onClick(int type);
    }
    private OnDialogClickListener onDialogClickListener;
    private ImageView cancel;
    private ImageView alipay_fill;
    private ImageView wxpay_fill;
    private Button refound;
    private RelativeLayout wxpay,alipay;
    private int payType = 1;//0微信1支付宝
    private TextView text2;//支付彩虹单车押金299元
    private Double myCherge;
    private Context mContext;
    public RechargeDialog(Double myCherge, Context context, OnDialogClickListener listener) {
        super(context, R.style.myDialog);
        onDialogClickListener = listener;
        this.myCherge = myCherge;
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_recharge);
        initView();
    }

    private void initView() {
        text2 = (TextView) findViewById(R.id.text2);
        wxpay = (RelativeLayout) findViewById(R.id.wxpay);
        alipay = (RelativeLayout) findViewById(R.id.alipay);
        wxpay_fill = (ImageView) findViewById(R.id.wxpay_fill);
        alipay_fill = (ImageView) findViewById(R.id.alipay_fill);
        cancel = (ImageView) findViewById(R.id.cancel);
        refound = (Button) findViewById(R.id.refound);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        refound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogClickListener.onClick(payType);
                dismiss();
            }
        });
        wxpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 0;
                wxpay_fill.setVisibility(View.VISIBLE);
                alipay_fill.setVisibility(View.GONE);
            }
        });
        alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 1;
                wxpay_fill.setVisibility(View.GONE);
                alipay_fill.setVisibility(View.VISIBLE);
            }
        });
        PersonalBean bean = MyUtils.getPersonData();

        text2.setText(mContext.getString(R.string.pay_deposit)+" "+Utils.object2String(bean.getCurrency())+myCherge);
//        SystemParamsBean systemParamsBean = MyUtils.getSystemData();
//        if (systemParamsBean != null) {
//            if (systemParamsBean.getPayWay().contentEquals("1")) {
//                wxpay.setVisibility(View.VISIBLE);
//                alipay.setVisibility(View.GONE);
//            } else if (systemParamsBean.getPayWay().contentEquals("2")) {
//                alipay.setVisibility(View.VISIBLE);
//                alipay.setVisibility(View.VISIBLE);
//            } else if (systemParamsBean.getPayWay().contentEquals("1,2")) {
//                wxpay.setVisibility(View.VISIBLE);
//                alipay.setVisibility(View.VISIBLE);
//            }
//        }
    }


}
