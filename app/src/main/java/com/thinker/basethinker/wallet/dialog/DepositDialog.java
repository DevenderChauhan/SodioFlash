package com.thinker.basethinker.wallet.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.thinker.basethinker.R;


/**
 * Created by farley on 17/5/18.
 * description:退款提醒
 */

public class DepositDialog extends AlertDialog {
    public interface OnDialogClickListener{
        void onClick();
    }
    private OnDialogClickListener onDialogClickListener;
    private ImageView cancel;
    private Button refound;
    public DepositDialog(Context context, OnDialogClickListener listener) {
        super(context, R.style.myDialog);
        onDialogClickListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_deposit);

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
    }


}
