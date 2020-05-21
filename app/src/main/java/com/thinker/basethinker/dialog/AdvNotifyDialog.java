package com.thinker.basethinker.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.MessageHomeBean;
import com.thinker.basethinker.utils.Utils;
import com.thinker.basethinker.utils.WebViewActivity;

/**
 * Created by farley on 17/8/31.
 * description:
 */

public class AdvNotifyDialog extends AlertDialog {
    private Context mContext;
    private MessageHomeBean messageHomeBean;
    public AdvNotifyDialog(Context context, MessageHomeBean messageHomeBean) {
        super(context, R.style.myDialog);
        mContext = context;
        this.messageHomeBean = messageHomeBean;
    }
    private ImageView img;
    private LinearLayout ll_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_adv_notigy);

        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        img = (ImageView) findViewById(R.id.advance);
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) img.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        linearParams.width = Utils.screenWidth-200;// 控件的宽强制设成30
        linearParams.height = (int)((Utils.screenWidth-200)*7.0f/6);
        img.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webview = new Intent(mContext, WebViewActivity.class);
                webview.putExtra("TITLE", "详情");
                webview.putExtra("VIEWURL", "/api/message/sys_view/" + messageHomeBean.getImageTextId());
                webview.putExtra("NEEDTOKEN",true);
                mContext.startActivity(webview);
                dismiss();
            }
        });
        findViewById(R.id.backIt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        String src = messageHomeBean.getAdCover();
        if (!TextUtils.isEmpty(src)) {
            Glide.with(mContext).load(src).into(img);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ll_content.setVisibility(View.VISIBLE);
                }
            }, 500);
        }
    }
    public interface OnAdvCclickListener{
        void onClick();
    }
    private OnAdvCclickListener onAdvCclickListener;

    public void setOnAdvCclickListener(OnAdvCclickListener onAdvCclickListener) {
        this.onAdvCclickListener = onAdvCclickListener;
    }
}
