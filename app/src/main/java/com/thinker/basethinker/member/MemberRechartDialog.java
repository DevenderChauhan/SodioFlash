package com.thinker.basethinker.member;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.SystemParamsBean;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;

/**
 * Created by farley on 17/8/17.
 * description:
 */

public class MemberRechartDialog extends AlertDialog {
    public interface OnDialogClickListener{
        void onClick(int type);
    }
    public MemberRechartDialog(Double myCherge, Context context,OnDialogClickListener listener) {
        super(context, R.style.myDialog);
        onDialogClickListener = listener;
        this.myCherge = myCherge;
        mContext = context;
    }
    private TextView pay;
    private TextView payMoney;
    private RelativeLayout alipay_pay;
    private RelativeLayout wx_pay;
    private ImageView img_wx;
    private ImageView img_ali;
    private Double myCherge;
    private Context mContext;
    private OnDialogClickListener onDialogClickListener;
    private int payType = 0;//0微信1支付宝
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_member_rechart);
        initView();
    }

    private void initView() {
        pay = (TextView) findViewById(R.id.pay);
        payMoney = (TextView) findViewById(R.id.payMoney);
        img_wx = (ImageView) findViewById(R.id.img_wx);
        img_ali = (ImageView) findViewById(R.id.img_ali);
        alipay_pay = (RelativeLayout) findViewById(R.id.alipay_pay);
        wx_pay = (RelativeLayout) findViewById(R.id.wx_pay);
        alipay_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 1;
                img_wx.setVisibility(View.GONE);
                img_ali.setVisibility(View.VISIBLE);
            }
        });
        wx_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 0;
                img_wx.setVisibility(View.VISIBLE);
                img_ali.setVisibility(View.GONE);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogClickListener.onClick(payType);
                dismiss();
            }
        });
        payMoney.setText(Utils.object2String(myCherge));
//        SystemParamsBean systemParamsBean = MyUtils.getSystemData();
//        if (systemParamsBean != null) {
//            if (systemParamsBean.getPayWay().contentEquals("1")) {
//                payType = 0;
//                wx_pay.setVisibility(View.VISIBLE);
//                img_wx.setVisibility(View.VISIBLE);
//                alipay_pay.setVisibility(View.GONE);
//            } else if (systemParamsBean.getPayWay().contentEquals("2")) {
//                payType = 1;
//                wx_pay.setVisibility(View.GONE);
//                alipay_pay.setVisibility(View.VISIBLE);
//                img_ali.setVisibility(View.VISIBLE);
//            } else if (systemParamsBean.getPayWay().contentEquals("1,2")) {
                wx_pay.setVisibility(View.VISIBLE);
                alipay_pay.setVisibility(View.VISIBLE);
//            }
//        }
    }
}
