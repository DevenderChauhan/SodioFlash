package com.thinker.basethinker.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.thinker.basethinker.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by thinker on 17/12/7.
 */

public class CountDownTimerUtils extends CountDownTimer {
    private Context mContext;
    private TextView mTextView;

    public CountDownTimerUtils(long millisInFuture, long countDownInterval, Context mContext, TextView mTextView) {
        super(millisInFuture, countDownInterval);
        this.mContext = mContext;
        this.mTextView = mTextView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setText(String.valueOf(millisUntilFinished/1000));
    }

    @Override
    public void onFinish() {
        EventBus.getDefault().post(new MessageEvent( 0,"success"));
    }
}
