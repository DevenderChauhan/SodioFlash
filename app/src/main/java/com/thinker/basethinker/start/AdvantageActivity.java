package com.thinker.basethinker.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinker.basethinker.MainActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.bean.AdvImages;
import com.thinker.basethinker.eventbus.MessageEvent;
import com.thinker.basethinker.login.LoginActivity;
import com.thinker.basethinker.utils.CountDownTimerUtils;
import com.thinker.basethinker.utils.CountDownUtils;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.PreferencesUtils;
import com.thinker.basethinker.utils.WebViewActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinker on 17/12/7.
 */

public class AdvantageActivity extends Activity {
    private TextView turnout;
    private ImageView advance;
    private List<File> fileList = new ArrayList<>();
    private int times = 0;
    private int position = 0;
    private int downTime = 5000;//默认倒计时5秒
    private AdvImages advImages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this, getResources().getColor(R.color.translate));
        setContentView(R.layout.activity_advantage);
        initView();
        advImages = MyUtils.getAdvanceData();
        downTime = PreferencesUtils.getInt(MyApplication.appContext, "ADV_IMAGE_TIME", 5000);
        File file = MyUtils.fileIsHave();
        if (file.exists() && file.isDirectory() && file.listFiles() != null && file.listFiles().length > 0) {
            for (File myFile : file.listFiles()) {
                fileList.add(myFile);
            }
        } else {
            LogUtils.e("没有广告直接跳转");
            turnMain();
        }
        if (fileList.size() > 0) {
            LogUtils.e("fileList.get(position)" + fileList.get(position));
            Glide.with(this).load(fileList.get(position)).into(advance);
        }
        if (fileList.size() > 0) {
            times = downTime / fileList.size();
        } else {
            times = downTime;
        }
        CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(downTime, 1000,
                this, turnout);
        countDownTimerUtils.start();
        LogUtils.e("times=" + times);
        LogUtils.e("downTime=" + downTime);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CountDownUtils changeImg = new CountDownUtils(downTime, times);
                changeImg.start();
            }
        }, times);


    }

    private void initView() {
        turnout = (TextView) findViewById(R.id.turnout);
        advance = (ImageView) findViewById(R.id.advance);

        turnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("直接跳转");
                turnMain();
            }
        });
        advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (advImages != null && advImages.getImgList().size() > position) {
                    if (advImages.getImgList().get(position).getLinkUrl() != null) {
                        Intent ad = new Intent(AdvantageActivity.this, WebViewActivity.class);
                        ad.putExtra("TITLE", getString(R.string.app_name));
                        ad.putExtra("VIEWURL", advImages.getImgList().get(position).getLinkUrl());
                        ad.putExtra("NEEDTOKEN", false);
                        ad.putExtra("NEEDBASEURL", false);
                        startActivityForResult(ad, 1002);
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyEvent(MessageEvent event) {
        switch (event.getCode()) {
            case 0:
                turnMain();
                break;
            case 2:
                LogUtils.e("position=" + position);
                position++;
                if (fileList.size() > position) {
                    LogUtils.e("fileList.get(position)=" + fileList.get(position));
                    Glide.with(this).load(fileList.get(position)).into(advance);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002) {
            turnMain();
        }
    }

    private void turnMain() {
        /*String token = PreferencesUtils.getString(this,"RADISHSAAS_IS_BIND");
        if (TextUtils.isEmpty(token)){
            startActivity(new Intent(AdvantageActivity.this, LoginActivity.class));
            finish();
            return;
        }*/
        startActivity(new Intent(AdvantageActivity.this, MainActivity.class));
        finish();
    }
}
