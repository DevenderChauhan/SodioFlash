package com.thinker.basethinker.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.utils.LanguageUtil;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;

/**
 * Created by thinker on 17/12/15.
 */

public class StartActivity extends MvpActivity<StartPresenter,MvpView> implements MvpView {
    private TextView copyright;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this,getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_start);
        initView();
        initText();
        myPresenter.getMyData();
        if (Utils.isNetOk(this)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(StartActivity.this,AdvantageActivity.class));
                    finish();
                }
            },2000);
        }
    }

    private void initView() {
        copyright = (TextView) findViewById(R.id.copyright);
    }

    private void initText() {
//        LanguageUtil
    }

    private StartPresenter myPresenter;
    @Override
    protected StartPresenter CreatePresenter() {
        return myPresenter = new StartPresenter(this);
    }

    public void setGetSuccess() {
        MyApplication.setIsLoginStatus(true);
    }

    public void setGetFailed() {
        MyApplication.setIsLoginStatus(false);
    }
}
