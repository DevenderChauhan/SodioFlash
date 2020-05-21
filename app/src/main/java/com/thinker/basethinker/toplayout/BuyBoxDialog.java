package com.thinker.basethinker.toplayout;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.thinker.basethinker.R;

/**
 * Created by Farley on 2018/4/20.
 */

public class BuyBoxDialog extends AlertDialog {
    public interface OnDialogClickListener{
        void onClick();
    }
    private OnDialogClickListener onDialogClickListener;
    private Context mContext;
    private Button buy_box_btn;
    private ImageView cancel;
    public BuyBoxDialog( Context context, OnDialogClickListener listener) {
        super(context, R.style.myDialog);
        onDialogClickListener = listener;
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_buy_box);
        initView();
    }

    private void initView() {
        cancel = (ImageView) findViewById(R.id.cancel);
        buy_box_btn = (Button) findViewById(R.id.buy_box_btn);
        buy_box_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDialogClickListener.onClick();
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}
