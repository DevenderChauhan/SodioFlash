package com.thinker.basethinker.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.IntegralListBean;
import com.thinker.basethinker.bean.IntegralLogBean;
import com.thinker.basethinker.bean.RechartTypeListBean;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;
import com.thinker.basethinker.utils.WebViewActivity;
import com.thinker.basethinker.wallet.adapter.ItemScoreAdapter;
import com.thinker.basethinker.wallet.adapter.ItemWalletAdapter;

import java.util.ArrayList;
import java.util.List;

import vc.thinker.colours.client.ApiClient;

/**
 * Created by thinker on 17/12/13.
 */

public class ScoreActivity extends MvpActivity<ScorePresenter,MvpView> implements MvpView, View.OnClickListener {
    private ScorePresenter myPresenter;
    @Override
    protected ScorePresenter CreatePresenter() {
        return myPresenter = new ScorePresenter(this);
    }
    private ImageView head_left;
    private TextView head_title;
    private TextView myScore;
    private TextView scare_rules;
    private Long scoreInt = 0L;
    private ListView listview;
    private ItemScoreAdapter adapter;
    private List<IntegralLogBean> dataList = new ArrayList<>();
    private LinearLayout empty_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this,getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_score);
        scoreInt = getIntent().getLongExtra("SCORE",0L);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myPresenter.getScoreList(null,null);
    }

    private void initView() {
        empty_view = (LinearLayout) findViewById(R.id.empty_view);
        listview = (ListView) findViewById(R.id.listview);
        myScore = (TextView) findViewById(R.id.myScore);
        head_title = (TextView) findViewById(R.id.head_title);
        scare_rules = (TextView) findViewById(R.id.scare_rules);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_title.setText(getString(R.string.score_title_d));
        head_left.setOnClickListener(this);
        scare_rules.setOnClickListener(this);
        myScore.setText(Utils.object2String(scoreInt));
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (dataList.size() > 9) {
                    myPresenter.getScoreList(dataList.get(dataList.size() - 1).getCreateTime().getTime(),null);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    public void initData(IntegralListBean bean,Long page) {
        if (page == null) {
            dataList.clear();
        }
        if (bean.getDataList() == null){
            return;
        }
        dataList.addAll(bean.getDataList());
        if (dataList.size() > 0){
            empty_view.setVisibility(View.GONE);
        }else{
            empty_view.setVisibility(View.VISIBLE);
        }
        if (adapter == null) {
            adapter = new ItemScoreAdapter(ScoreActivity.this, dataList);
            listview.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                finish();
                break;
            case R.id.scare_rules:
                Intent webview = new Intent(this, WebViewActivity.class);
                webview.putExtra("TITLE", getString(R.string.score_rules));
                webview.putExtra("VIEWURL", "share/integra_rules");
                webview.putExtra("NEEDTOKEN",true);
                startActivity(webview);
                break;
            default:
                break;
        }
    }
}
