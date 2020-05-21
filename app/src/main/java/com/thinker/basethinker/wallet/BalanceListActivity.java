package com.thinker.basethinker.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.RechartHistoryBean;
import com.thinker.basethinker.bean.RechartHistoryListBean;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.wallet.adapter.YueListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinker on 17/12/13.
 */

public class BalanceListActivity extends MvpActivity<BalancePresenter, MvpView> implements MvpView, View.OnClickListener {
    private BalancePresenter myPresenter;

    @Override
    protected BalancePresenter CreatePresenter() {
        return myPresenter = new BalancePresenter(this);
    }

    private ImageView head_left;
    private TextView head_title;
    private List<RechartHistoryBean> dataList = new ArrayList<>();
    private ListView listview;
    private YueListAdapter yueListAdapter;
    private LinearLayout empty_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this, getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_deposit_list);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myPresenter.getYueList(null);
    }

    private void initView() {
        listview = (ListView) findViewById(R.id.listview);
        head_title = (TextView) findViewById(R.id.head_title);
        head_left = (ImageView) findViewById(R.id.head_left);
        empty_view =  findViewById(R.id.empty_view);
        head_title.setText(getString(R.string.deposit_detail));
        head_left.setOnClickListener(this);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (dataList.size() > 9) {
                    myPresenter.getYueList(dataList.get(dataList.size() - 1).getCreateTime().getTime());
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_left:
                finish();
                break;
            default:
                break;
        }
    }

    public void loadMoreAmountDetail(RechartHistoryListBean bean, Long time) {
        if (time == null) {
            dataList.clear();
        }
        if (bean.getDataList() == null) {
            empty_view.setVisibility(View.VISIBLE);
            return;
        }else{
            empty_view.setVisibility(View.GONE);
        }
        dataList.addAll(bean.getDataList());
        if (yueListAdapter == null) {
            yueListAdapter = new YueListAdapter(BalanceListActivity.this, dataList);
            listview.setAdapter(yueListAdapter);
        } else {
            yueListAdapter.notifyDataSetChanged();
        }
    }
}
