package com.thinker.basethinker.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.thinker.basethinker.R;

/**
 * Created by farley on 17/5/18.
 * description:
 */

public class ShareDialog extends AlertDialog implements View.OnClickListener {

    public interface OnDialogClickListener{
        void choose(int i);
        void cancel();
    }
    private TextView cancel;
    private TextView wechat;
    private TextView moment;
    private TextView qq;
    private TextView qzone;
    private TextView facebook;
    private OnDialogClickListener onDialogClickListener;
    public ShareDialog(Context context, OnDialogClickListener listener) {
        super(context, R.style.myDialog);
        onDialogClickListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);

        initView();
    }

    private void initView() {
        cancel = (TextView) findViewById(R.id.cancel);
        qzone = (TextView) findViewById(R.id.qzone);
        qq = (TextView) findViewById(R.id.qq);
        moment = (TextView) findViewById(R.id.moment);
        wechat = (TextView) findViewById(R.id.wechat);
        facebook = (TextView) findViewById(R.id.facebook);
        qzone.setOnClickListener(this);
        qq.setOnClickListener(this);
        moment.setOnClickListener(this);
        wechat.setOnClickListener(this);
        cancel.setOnClickListener(this);
        facebook.setOnClickListener(this);

    }
    public void setShareType(boolean isWechat,boolean isWechatMoments,boolean isQq,boolean isQzone){
        if (!isWechat){
            wechat.setVisibility(View.GONE);
        }
        if (!isWechatMoments){
            moment.setVisibility(View.GONE);
        }
        if (!isQzone){
            qq.setVisibility(View.GONE);
        }
        if (!isQq){
            qzone.setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.qzone) {
            onDialogClickListener.choose(0);
        }else if (i == R.id.qq) {
            onDialogClickListener.choose(1);
        }else if (i == R.id.moment) {
            onDialogClickListener.choose(2);
        }else if (i == R.id.wechat) {
            onDialogClickListener.choose(3);
        }else if (i == R.id.facebook) {
            onDialogClickListener.choose(4);
        }else if (i == R.id.cancel){
            onDialogClickListener.cancel();
        }
        dismiss();
    }
}
