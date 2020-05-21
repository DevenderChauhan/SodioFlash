package com.thinker.basethinker.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by thinker on 17/12/8.
 */

public class MyRegistCountDown extends CountDownTimer {
    private Context mContext;
    private TextView mTextView;
    public MyRegistCountDown(long millisInFuture, long countDownInterval, Context mContext, TextView mTextView) {
        super(millisInFuture, countDownInterval);
        this.mContext = mContext;
        this.mTextView = mTextView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setText("("+String.valueOf(millisUntilFinished/1000)+mContext.getString(R.string.regist_reget));
        mTextView.setTextColor(Color.parseColor("#B2B2B2"));
        PreferencesUtils.putLong(MyApplication.appContext,"COUNT_TIMES",millisUntilFinished/1000);
    }

    @Override
    public void onFinish() {
        mTextView.setTextColor(Color.parseColor("#FF4100"));
        EventBus.getDefault().post(new MessageEvent( 0,"success"));
    }
}
