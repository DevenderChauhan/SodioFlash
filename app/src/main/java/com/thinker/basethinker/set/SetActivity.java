package com.thinker.basethinker.set;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinker.basethinker.MainActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.login.LoginActivity;
import com.thinker.basethinker.mvp.ControllerActivity;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.personal.ModifyPawActivity;
import com.thinker.basethinker.set.adapter.SetAdapter;
import com.thinker.basethinker.set.bean.SetBean;
import com.thinker.basethinker.set.clearcache.ClearCacheUtils;
import com.thinker.basethinker.set.clearcache.DataCleanManager;
import com.thinker.basethinker.utils.LanguageUtil;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.PreferencesUtils;
import com.thinker.basethinker.utils.Utils;
import com.thinker.basethinker.utils.WebViewActivity;
import com.thinker.basethinker.views.NewTextView;

import java.util.Locale;


/**
 * Created by farley on 17/5/18.
 * description:
 */

public class SetActivity extends MvpActivity<SetPresenter,ISetView> implements ISetView ,View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView head_left,head_right;
    private TextView head_title;
//    private RelativeLayout about_us,protocol,chrge,clear;
    private RelativeLayout clear;
    private TextView cache;
    private NewTextView appcode;
    private SetPresenter presenter;
    private ListView listView;
    private SetAdapter adapter;
    private SetBean setBean;
    private RelativeLayout logout;
    private RelativeLayout changelau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this,getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_set);
        initView();
    }

    @Override
    protected SetPresenter CreatePresenter() {
        return presenter = new SetPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getGuideList(2);//彩虹单车的
//        presenter.getGuideList(9);//小E
    }

    private void initView() {
//        about_us = (RelativeLayout) findViewById(R.id.about_us);
//        protocol = (RelativeLayout) findViewById(R.id.protocol);
        clear = (RelativeLayout) findViewById(R.id.clear);
        logout = (RelativeLayout) findViewById(R.id.logout);
        changelau = (RelativeLayout) findViewById(R.id.changelau);
//        chrge = (RelativeLayout) findViewById(R.id.chrge);
        cache = (TextView) findViewById(R.id.cache);
        appcode = (NewTextView) findViewById(R.id.appcode);
        appcode.setTextIsSelectable(true);
        head_title = (TextView) findViewById(R.id.head_title);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_right = (ImageView) findViewById(R.id.head_right);
        listView = (ListView) findViewById(R.id.listview);
        head_title.setText(Utils.object2String(getString(R.string.set_title)));
        head_right.setVisibility(View.GONE);
        head_left.setOnClickListener(this);
//        about_us.setOnClickListener(this);
//        protocol.setOnClickListener(this);
//        chrge.setOnClickListener(this);
        clear.setOnClickListener(this);
        logout.setOnClickListener(this);
        changelau.setOnClickListener(this);
        try {
            cache.setText(Utils.object2String(DataCleanManager.getTotalCacheSize(this)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        listView.setOnItemClickListener(this);


        appcode.setText(Utils.getAppMsg(this));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                finish();
                break;
            case R.id.logout:
                presenter.logout();
                break;
            case R.id.changelau:
                LanguageUtil.changeAppLanguage(this, Locale.ENGLISH);
                restartApplication();
                break;
           /* case R.id.about_us:
                ShowToast.show(this,"关于我们");
                break;
            case R.id.protocol:
                ShowToast.show(this,"用户协议");
                break;
            case R.id.chrge:
                ActivityController.startWebView(this,"押金说明","api/guide_detail_app?id");
                break;*/
            case R.id.clear:
                ClearCacheUtils.clearCache(this, new ClearCacheUtils.OnClearListener() {
                    @Override
                    public void clear() {
                        cache.setText("0KB");
                    }
                });
                break;
            default:
                break;
        }
    }
    public void setData(SetBean bean){
        if (bean == null){
            return;
        }
        if (bean.getDatas() == null){
            return;
        }
        setBean = bean;
        adapter = new SetAdapter(this,bean.getDatas());
        listView.setAdapter(adapter);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent webview = new Intent(this, WebViewActivity.class);
        webview.putExtra("TITLE", setBean.getDatas().get(position).getTitle());
        webview.putExtra("VIEWURL", "api/guide_detail_app?id="+setBean.getDatas().get(position).getId());
        webview.putExtra("NEEDTOKEN",true);
        startActivity(webview);
    }

    public void logoutSuccess() {
        MyApplication.setIsLoginStatus(false);
        String token = "";
        PreferencesUtils.putString(SetActivity.this,"RADISHSAAS_IS_BIND",token);
        startActivity(new Intent(SetActivity.this, LoginActivity.class));
        ControllerActivity.finishAll();
    }
    private void restartApplication() {
        //切换语言信息，需要重启 Activity 才能实现
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
