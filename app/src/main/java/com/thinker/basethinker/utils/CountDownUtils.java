package com.thinker.basethinker.utils;

import android.os.CountDownTimer;

import com.thinker.basethinker.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by thinker on 17/12/7.
 */

public class CountDownUtils extends CountDownTimer {
    public CountDownUtils(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        EventBus.getDefault().post(new MessageEvent(2,"successd"));
    }

    @Override
    public void onFinish() {

    }
}
