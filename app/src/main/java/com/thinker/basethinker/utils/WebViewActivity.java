package com.thinker.basethinker.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;


import com.thinker.basethinker.R;

import java.util.HashMap;
import java.util.Map;

import vc.thinker.colours.client.ApiClient;


public class WebViewActivity extends Activity implements View.OnClickListener {

    private WebView mWebView;
    private String title="详情";
    private String viewUrl;
    private ImageView head_left,head_right;
    private TextView head_title;
    private Boolean needToken = true;
    private Boolean needBaseUrl = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this,getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_webview);
        Intent intent = getIntent();
        title = intent.getStringExtra("TITLE");
        viewUrl = intent.getStringExtra("VIEWURL");
        needToken = intent.getBooleanExtra("NEEDTOKEN",true);
        needBaseUrl = intent.getBooleanExtra("NEEDBASEURL",true);
        mWebView = (WebView) findViewById(R.id.sys_webView);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_right = (ImageView) findViewById(R.id.head_right);
        head_title = (TextView) findViewById(R.id.head_title);
        head_right.setVisibility(View.GONE);
        head_title.setText(title);
        head_left.setOnClickListener(this);
        mWebView.getSettings().setJavaScriptEnabled(true);

        Map<String, String> extraHeaders = new HashMap<String, String>();
        if (needToken) {
            extraHeaders.put("Authorization", "Bearer " + PreferencesUtils.getString(this, "RADISHSAAS_IS_BIND"));
        }else{
            extraHeaders.put("Authorization", "Bearer oauth2-password");
        }
        extraHeaders.put("Accept-Language", ApiClient.language);
        if (needBaseUrl) {
            LogUtils.e(ApiClient.baseUrl + viewUrl);
            mWebView.loadUrl(ApiClient.baseUrl + viewUrl, extraHeaders);
        }else{
            LogUtils.e(viewUrl);
            mWebView.loadUrl(viewUrl, extraHeaders);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                finish();
                break;
            default:
                break;
        }
    }
}
