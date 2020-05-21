package com.thinker.basethinker.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.BasicCostBean;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.utils.MyUtils;

/**
 * Created by thinker on 18/3/2.
 * 缴纳基础会员费
 */

public class GeneralMemberDialog extends AlertDialog {
    public interface OnDialogClickListener{
        void onClick();
    }
    private OnDialogClickListener onDialogClickListener;
    private ImageView cancel;
    private Button refound;
    private Context mContext;
    private BasicCostBean basicCostBean;
    public GeneralMemberDialog(BasicCostBean basicCostBean, Context context, OnDialogClickListener listener) {
        super(context, R.style.myDialog);
        onDialogClickListener = listener;
        this.basicCostBean = basicCostBean;
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_general_member);
        initView();
    }

    private void initView() {
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
                onDialogClickListener.onClick();
                dismiss();
            }
        });
        if (basicCostBean != null) {
            refound.setText(mContext.getString(R.string.dialog_general_member_pay) + basicCostBean.getCost() + " " + basicCostBean.getCurrency());
        }
        PersonalBean bean = MyUtils.getPersonData();

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
